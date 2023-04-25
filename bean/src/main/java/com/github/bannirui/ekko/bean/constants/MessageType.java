package com.github.bannirui.ekko.bean.constants;

/**
 * 消息类型.
 *
 * @author dingrui
 * @since 2023/4/24
 */
public interface MessageType {

    long LOGIN = 0; // 登陆 提供连接凭证

    long CHAT = 1L; // 聊天
    long FRIEND_ADD = 1L << 1; // 请求添加好友
    long FRIEND_REJECT = 1L << 2; // 拒绝好友请求
    long FRIEND_APPROVE = 1L << 3; // 通过好友请求
    long GROUP_JOIN = 1L << 4; // 申请入群
    long GROUP_REJECT = 1L << 5; // 入群被拒
    long CHAT_SELF = 1L << 6; // 自己给自己发信息
}
