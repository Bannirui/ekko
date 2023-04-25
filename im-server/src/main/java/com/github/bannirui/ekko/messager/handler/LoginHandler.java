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
 * 客户端登陆请求.
 * <ul>对于客户端用户的登陆 可以看成几个组成
 * <li>用户信息登陆</li>
 * <li>客户端\服务端Socket连接建立</li>
 * <li>通过Socket通信 客户端向服务端发送消息 服务端保存跟客户端映射关系</li>
 * </ul>
 *
 * @author dingrui
 * @since 2023/4/25
 */
@PeerMessagerFlag(type = MessageType.LOGIN)
public class LoginHandler extends AbstractMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    protected long soProcess(Message message, Object attach) {
        LOG.info("[IM-SERVER::LOGIN-HANDLER] 收到客户端的信息 message={} attach={}}", message, attach);
        Long sender = null;
        if (Objects.isNull(sender = message.getSender()) || Objects.isNull(attach)) {
            return OpCode.PARAM_INVALID;
        }
        SessionMgr.route(sender, (Channel) attach);
        return OpCode.SUCC;
    }
}
