package com.github.bannirui.ekko.handler;

import com.github.bannirui.ekko.bean.pb.TestProto;
import com.github.bannirui.ekko.bean.pb.TestProto.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dingrui
 * @since 2023/4/19
 */
public class MyHandler extends SimpleChannelInboundHandler<TestProto.Person> {

    private static final Logger LOG = LoggerFactory.getLogger(MyHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-SERVER] 客户端上线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Person person) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-SERVER] 收到客户端消息: {}", person);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // TODO: 2023/4/19
        LOG.info("[IM-SERVER] 自定义事件: {}", evt);
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
