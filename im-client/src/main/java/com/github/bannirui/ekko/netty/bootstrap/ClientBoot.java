package com.github.bannirui.ekko.netty.bootstrap;

import com.github.bannirui.ekko.cnxn.CnxnManager;
import com.github.bannirui.ekko.common.ex.BizException;
import com.github.bannirui.ekko.common.util.SpringCtxUtil;
import com.github.bannirui.ekko.netty.handler.MyHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty客户端.
 *
 * @author dingrui
 * @since 2023/4/23
 */
public class ClientBoot implements Runnable {

    private Long sender; // 客户端用户uid
    private String host; // 客户端要连接的服务端ip
    private Integer port; // 客户端要连接的服务端端口


    public ClientBoot(Long sender, String host, Integer port) {
        this.sender = sender;
        this.host = host;
        this.port = port;
    }

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new MyHandlerInitializer());
            ChannelFuture f = b.connect(this.host, this.port).sync();
            Channel ch = f.channel();
            CnxnManager cnxn = SpringCtxUtil.getBean(CnxnManager.class);
            cnxn.cacheCh(this.sender, ch);
            ch.closeFuture().sync();
        } catch (Exception e) {
            throw new BizException("[IM-CLIENT] 初始化netty失败", e);
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        this.connect();
    }
}
