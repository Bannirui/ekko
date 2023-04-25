package com.github.bannirui.ekko.messager.handler;

import com.github.bannirui.ekko.bean.constants.MessageType;
import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.bean.pb.MessageProto.Message;
import com.github.bannirui.ekko.common.annotations.PeerMessagerFlag;
import com.github.bannirui.ekko.messager.AbstractMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自己给自己发信息.
 *
 * @author dingrui
 * @since 2023/4/25
 */
@PeerMessagerFlag(type = MessageType.CHAT_SELF)
public class ChatSelfHandler extends AbstractMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ChatSelfHandler.class);

    @Override
    protected long soProcess(Message message, Object attach) {
        LOG.info("[IM-SERVER::CHAT-SELF-HANDLER] 收到客户端的信息 message={}", message);
        return OpCode.SUCC;
    }
}
