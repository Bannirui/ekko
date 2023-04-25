package com.github.bannirui.ekko.netty.bootstrap;

import com.github.bannirui.ekko.bean.constants.MessageType;
import com.github.bannirui.ekko.bean.pb.MessageProto.Message;
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
    private int c2sHeartBeatS;


    public ClientBoot(Long sender, String host, Integer port, int c2sHeartBeatS) {
        this.sender = sender;
        this.host = host;
        this.port = port;
        this.c2sHeartBeatS = c2sHeartBeatS;
    }

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new MyHandlerInitializer(this.c2sHeartBeatS));
            ChannelFuture f = b.connect(this.host, this.port).sync(); // 物理连接服务端
            Channel ch = f.channel();
            CnxnManager cnxn = SpringCtxUtil.getBean(CnxnManager.class);
            cnxn.cacheCh(this.sender, ch);
            Message message = Message.newBuilder()
                .setSender(this.sender)
                .setType(MessageType.LOGIN)
                .build();
            cnxn.send(this.sender, message); // 逻辑连接服务端 提供一个消息告知服务端自己的uid 让服务端能够将客户端的连接缓存起来
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
