package com.github.bannirui.ekko.messager;

/**
 * 服务端消息处理器.
 *
 * @author dingrui
 * @since 2023/4/25
 */
public interface MessageHandler {

    long process(Long type, MessageHandlerArgs args);
}
