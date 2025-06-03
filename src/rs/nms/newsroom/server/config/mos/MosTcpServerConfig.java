package rs.nms.newsroom.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

import jakarta.annotation.PostConstruct;
import rs.nms.newsroom.server.config.storage.FileStorageProperties;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class NmsServerApplication {

    private final DataSource dataSource;
    private final FileStorageProperties fileStorageProperties;

    public NmsServerApplication(DataSource dataSource, FileStorageProperties fileStorageProperties) {
        this.dataSource = dataSource;
        this.fileStorageProperties = fileStorageProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(NmsServerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(fileStorageProperties.getUploadDir()));
            System.out.println("Upload directory created: " + fileStorageProperties.getUploadDir());
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("""
                
                ============================================
                  Newsroom Management System Server Started
                ============================================
                """);
        
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Database connection successful!");
            System.out.println("Connection URL: " + connection.getMetaData().getURL());
            System.out.println("Database user: " + connection.getMetaData().getUserName());
            System.out.println("Database product: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("Upload directory: " + fileStorageProperties.getUploadDir());
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        
        System.out.println("""
                
                ============================================
                  Server is ready to accept requests
                ============================================
                """);
    }
}
