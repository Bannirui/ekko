package com.github.bannirui.ekko.netty.handler;

import com.github.bannirui.ekko.bean.pb.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author dingrui
 * @since 2023/4/19
 */
public class MyHandlerInitializer extends ChannelInitializer<Channel> {

    private MyHandler myHandler = new MyHandler();

    private int client2ServerHeartBeatS;

    public MyHandlerInitializer(int client2ServerHeartBeatS) {
        this.client2ServerHeartBeatS = client2ServerHeartBeatS;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
            .addLast(new IdleStateHandler(this.client2ServerHeartBeatS, 0, 0))
            .addLast(new ProtobufVarint32FrameDecoder()) // 帧解码器
            .addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance())) // 解码器
            .addLast(new ProtobufVarint32LengthFieldPrepender()) // 拆包
            .addLast(new ProtobufEncoder()) // 编码器
            .addLast(this.myHandler);
    }
}
