package com.github.bannirui.ekko.netty.handler;

import com.github.bannirui.ekko.bean.pb.MessageProto;
import com.github.bannirui.ekko.common.util.SpringCtxUtil;
import com.github.bannirui.ekko.messager.MessageHandler;
import com.github.bannirui.ekko.messager.MessageHandlerArgs;
import com.github.bannirui.ekko.service.HealthService;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dingrui
 * @since 2023/4/19
 */
@Sharable
public class MyHandler extends SimpleChannelInboundHandler<MessageProto.Message> {

    private static final Logger LOG = LoggerFactory.getLogger(MyHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProto.Message message) throws Exception {
        LOG.info("[IM-SERVER] 收到客户端消息: {}", message);
        MessageHandler handler = SpringCtxUtil.getBean(MessageHandler.class);
        handler.process(message.getType(), new MessageHandlerArgs(message, channelHandlerContext.channel()));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent ise) {
            LOG.info("[IM-SERVER] 没有收到客户端的心跳");
            // 服务端没收到客户端的心跳
            HealthService handler = SpringCtxUtil.getBean(HealthService.class);
            handler.process();
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-SERVER] 客户端下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-SERVER] 发生异常: {}", cause.getMessage());
        ctx.close();
    }
}
