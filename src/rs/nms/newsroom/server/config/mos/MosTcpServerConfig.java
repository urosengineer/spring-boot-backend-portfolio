package rs.nms.newsroom.server.config.mos;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.nms.newsroom.server.tcp.mos.MosTcpServerInitializer;

@Configuration
public class MosTcpServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(MosTcpServerConfig.class);

    @Value("${mos.tcp.port:10540}")
    private int mosTcpPort;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    @Bean
    public Channel startMosTcpServer(MosTcpServerInitializer initializer) throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
         .channel(NioServerSocketChannel.class)
         .childHandler(initializer)
         .option(ChannelOption.SO_BACKLOG, 128)
         .option(ChannelOption.SO_REUSEADDR, true)
         .childOption(ChannelOption.SO_KEEPALIVE, true);

        logger.info("Starting MOS TCP server on port {}", mosTcpPort);
        try {
            serverChannel = b.bind(mosTcpPort).sync().channel();
            logger.info("MOS TCP server started successfully on port {}", mosTcpPort);
            return serverChannel;
        } catch (InterruptedException e) {
            logger.error("Failed to start MOS TCP server", e);
            shutdownEventLoops(true);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while starting MOS TCP server", e);
            shutdownEventLoops(true);
            throw new RuntimeException(e);
        }
    }

    private void shutdownEventLoops(boolean await) {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
            if (await) waitForShutdown(bossGroup);
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
            if (await) waitForShutdown(workerGroup);
        }
    }

    private void waitForShutdown(EventLoopGroup group) {
        try {
            group.terminationFuture().sync();
        } catch (InterruptedException e) {
            logger.warn("Interrupted while waiting for Netty EventLoop shutdown", e);
            Thread.currentThread().interrupt();
        }
    }

    @PreDestroy
    public void stopMosTcpServer() {
        logger.info("Stopping MOS TCP server...");
        try {
            if (serverChannel != null) {
                serverChannel.close().sync();
            }
            shutdownEventLoops(true);
        } catch (InterruptedException e) {
            logger.error("Error while stopping MOS TCP server", e);
            Thread.currentThread().interrupt();
        }
        logger.info("MOS TCP server stopped.");
    }
}
