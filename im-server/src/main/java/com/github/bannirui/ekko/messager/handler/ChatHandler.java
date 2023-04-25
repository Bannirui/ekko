package com.github.bannirui.ekko.messager.handler;

import com.github.bannirui.ekko.bean.constants.MessageType;
import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.bean.pb.MessageProto.Message;
import com.github.bannirui.ekko.common.annotations.PeerMessagerFlag;
import com.github.bannirui.ekko.messager.AbstractMessageHandler;
import com.github.bannirui.ekko.session.SessionMgr;
import io.netty.channel.Channel;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 聊天消息.
 *
 * @author dingrui
 * @since 2023/4/25
 */
@PeerMessagerFlag(type = MessageType.CHAT)
public class ChatHandler extends AbstractMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ChatHandler.class);

    @Override
    protected long soProcess(Message message, Object attach) {
        LOG.info("[IM-SERVER::CHAT-HANDLER] 收到客户端的信息 message={}", message);
        long receiver = message.getReceiver();
        String content = message.getContent();
        if (Objects.isNull(receiver) || Objects.isNull(content)) {
            return OpCode.PARAM_INVALID;
        }
        Channel ch = SessionMgr.peerChannel(receiver);
        if (Objects.isNull(ch)) {
            // TODO: 2023/4/25 对方不在线 服务端负责将待投递信息缓存起来 等到目标客户端上线了 缓存的键是uid
            return OpCode.NOT_SUPPORT;
        }
        ch.writeAndFlush(message);
        return OpCode.SUCC;
    }
}
