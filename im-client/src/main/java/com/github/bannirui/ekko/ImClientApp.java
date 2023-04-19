package com.github.bannirui.ekko;

import com.github.bannirui.ekko.netty.handler.MyHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main.
 *
 * @author dingrui
 * @since 2023/4/19
 */
@SpringBootApplication
public class ImClientApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ImClientApp.class);

    public static void main(String[] args) {
        SpringApplication.run(ImClientApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("[IM-CLIENT] 启动成功.");
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new MyHandlerInitializer());
            ChannelFuture f = bootstrap.connect("127.0.0.1", 9527).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
}