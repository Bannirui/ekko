package com.github.bannirui.ekko.netty.channel;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多用户channel管理.
 *
 * @author dingrui
 * @since 2023/4/24
 */
public class NettyChannelMgr implements Runnable {

    private static final Map<Long, Channel> CHANNEL_MAP = new ConcurrentHashMap<>(128);

    public static void put(Long uid, Channel ch) {
        CHANNEL_MAP.put(uid, ch);
    }

    public static Channel load(Long uid) {
        if (Objects.isNull(uid)) {
            return null;
        }
        return CHANNEL_MAP.get(uid);
    }


    @Override
    public void run() {

    }
}
