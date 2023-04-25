package com.github.bannirui.ekko.controller;

import com.github.bannirui.ekko.bean.constants.MessageType;
import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.bean.pb.MessageProto;
import com.github.bannirui.ekko.bean.pb.MessageProto.Message;
import com.github.bannirui.ekko.bean.pb.MessageProto.Message.Builder;
import com.github.bannirui.ekko.cnxn.CnxnManager;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 私聊.
 *
 * @author dingrui
 * @since 2023/4/23
 */
@RestController
@RequestMapping("/chat")
public class PrivateChatController {

    private static final Logger LOG = LoggerFactory.getLogger(PrivateChatController.class);

    private final CnxnManager cnxnManager;

    @Autowired
    private PrivateChatController SELF;

    public PrivateChatController(CnxnManager cnxnManager) {
        this.cnxnManager = cnxnManager;
    }

    /**
     * 私聊.
     *
     * @param sender   发送者
     * @param receiver 接收者
     * @param msg      消息
     */
    // TODO: 2023/4/24 鉴权
    @PostMapping("/private")
    public ResponseEntity<Long> chat(Long sender, Long receiver, String msg) {
        if (Objects.isNull(sender) || Objects.isNull(receiver)) {
            return ResponseEntity.ok(OpCode.PARAM_INVALID);
        }
        Builder builder = Message.newBuilder()
            .setSender(sender)
            .setContent(msg);
        MessageProto.Message message = null;
        if (Objects.equals(sender, receiver)) {
            message = builder.setType(MessageType.CHAT_SELF).build();
        } else {
            message = builder.setType(MessageType.CHAT).setReceiver(receiver).build();
        }
        long op = this.cnxnManager.send(sender, message);
        LOG.info("[IM-CLIENT] 发送聊天信息 {}->{} 信息: {} 操作码={}", sender, receiver, msg, op);
        return ResponseEntity.ok(op);
    }

    /**
     * 自己给自己发送消息.
     */
    @PostMapping("/self")
    public ResponseEntity<Long> self(Long sender, String msg) {
        return this.SELF.chat(sender, sender, msg);
    }
}
