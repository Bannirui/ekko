package com.github.bannirui.ekko.messager;

import com.github.bannirui.ekko.bean.pb.MessageProto;
import com.github.bannirui.ekko.bean.pb.MessageProto.Message;

/**
 * 参数封装. 所有阈值都可能为空
 *
 * @author dingrui
 * @since 2023/4/25
 */
public class MessageHandlerArgs {

    private MessageProto.Message message;
    private Object attach;

    public MessageHandlerArgs(MessageProto.Message message, Object attach) {
        this.message = message;
        this.attach = attach;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getAttach() {
        return attach;
    }

    public void setAttach(Object attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "MessageHandlerArgs{"
            + "message=" + message
            + ", attach=" + attach
            + '}';
    }
}
