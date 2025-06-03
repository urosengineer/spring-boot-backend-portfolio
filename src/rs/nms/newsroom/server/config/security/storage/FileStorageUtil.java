package rs.nms.newsroom.server.config.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileStorageUtil {

    private final Path fileStorageLocation;
    private final String relativeUrlBase = "/profile-images/";

    @Autowired
    public FileStorageUtil(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new IOException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            throw new IOException("Cannot store file with relative path outside current directory: " + originalFileName);
        }

        String fileExtension = "";
        if (originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String uniqueFileName = UUID.randomUUID() + fileExtension;
        Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // VRAĆAMO SAMO RELATIVNI WEB PATH!
        return relativeUrlBase + uniqueFileName;
    }

    public void deleteFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }

        // filePath je relativni web path (npr. /profile-images/xxx.jpg), a nama treba fizički path
        String fileName = Paths.get(filePath).getFileName().toString();
        Path absolutePath = this.fileStorageLocation.resolve(fileName);

        if (!Files.exists(absolutePath)) {
            return;
        }

        try {
            Files.delete(absolutePath);
        } catch (IOException ex) {
            throw new IOException("Could not delete file: " + absolutePath, ex);
        }
    }

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }
}
