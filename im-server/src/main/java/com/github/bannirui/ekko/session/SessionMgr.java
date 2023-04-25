package com.github.bannirui.ekko.session;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地保存跟自己建立tcp连接的客户端.
 * <ul>因为im-server是集群模式
 * <li>1 出现单点故障 跟自己连接着的客户端都会收到channel inactive事件 客户端进行重连操作即可 在im-mgr的活跃服务端清单里面lb一个可用服务端</li>
 * <li>2 为啥连接信息缓存在内存而不是远程<ul>
 *     <li>2.1 诸如redis这样的远程数据库 对Channel\Socket这种强语言性质的Entity序列化\反序列怎么做是个问题</li>
 *     <li>2.2 跟场景1一样 什么时候内存缓存会出现安全故障 要么OOM要么掉电丢失 两种情况都可以主观上归因与机器单点故障</li>
 * </ul></li>
 * </ul>
 *
 * @author dingrui
 * @since 2023/4/25
 */
public class SessionMgr {

    private static final Map<Long, Channel> UID_CHANNEL_MAP = new ConcurrentHashMap<>(128);

    /**
     * 服务端客户端连接的channel.
     *
     * @param client 客户端uid
     * @param ch     服务端channel
     */
    public static void route(Long client, Channel ch) {
        UID_CHANNEL_MAP.put(client, ch);
    }

    /**
     * 服务端\客户端连接的channel.
     *
     * @param uid 客户端uid
     */
    public static Channel peerChannel(Long uid) {
        return UID_CHANNEL_MAP.get(uid);
    }
}
