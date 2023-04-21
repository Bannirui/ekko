package com.github.bannirui.ekko.netty;

import com.github.bannirui.ekko.netty.handler.MyHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * netty.
 *
 * @author dingrui
 * @since 2023/4/19
 */
@Component
public class ServerBoot implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ServerBoot.class);

    @Value("${im-server.im-port}")
    private int imPort;

    @Value("${im-server.health.heart-beat.client-to-server-s}")
    private int client2ServerHeartBeatS;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                .group(this.bossGroup, this.workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new MyHandlerInitializer(this.client2ServerHeartBeatS));
            bootstrap.bind(this.imPort).sync();
            LOG.info("[IM-SERVER] 初始化netty");
        } catch (Exception e) {
            this.bossGroup.shutdownGracefully();
            this.workGroup.shutdownGracefully();
            LOG.info("[IM-SERVER] 初始化netty失败");
            throw new IllegalStateException("[IM-SERVER] 初始化netty失败");
        }
    }

    @Override
    public void destroy() throws Exception {
        this.bossGroup.shutdownGracefully().syncUninterruptibly();
        this.workGroup.shutdownGracefully().syncUninterruptibly();
    }
}
