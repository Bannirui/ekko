package com.github.bannirui.ekko.netty.handler;

import com.github.bannirui.ekko.bean.pb.TestProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author dingrui
 * @since 2023/4/19
 */
public class MyHandlerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
            .addLast(new ProtobufVarint32FrameDecoder()) // 帧解码器
            .addLast(new ProtobufDecoder(TestProto.Person.getDefaultInstance())) // 解码器
            .addLast(new ProtobufVarint32LengthFieldPrepender()) // 拆包
            .addLast(new ProtobufEncoder()) // 编码器
            .addLast(new MyHandler());
    }
}
