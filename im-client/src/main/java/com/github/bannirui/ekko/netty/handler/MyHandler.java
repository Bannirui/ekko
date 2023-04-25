package com.github.bannirui.ekko.netty.handler;

import com.github.bannirui.ekko.bean.pb.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dingrui
 * @since 2023/4/19
 */
public class MyHandler extends SimpleChannelInboundHandler<MessageProto.Message> {

    private static final Logger LOG = LoggerFactory.getLogger(MyHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-CLIENT] 客户端连上了服务端 准备向服务端写数据");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProto.Message message) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-CLIENT] 收到服务端回传数据: {}", message);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-CLIENT] 收到自定义事件: {}", evt);
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-CLIENT] 客户端主动下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-CLIENT] 遇到异常: {}", cause.getMessage());
        ctx.close();
    }
}