package com.github.bannirui.ekko.service.impl;

import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.bean.constants.OpCode.Peer;
import com.github.bannirui.ekko.bean.dto.ImPeer;
import com.github.bannirui.ekko.common.util.RedisUtil;
import com.github.bannirui.ekko.constants.RedisKeyMGr;
import com.github.bannirui.ekko.service.ImPeerService;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * im server节点信息维护.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Service
@CacheConfig(cacheNames = RedisKeyMGr.Peer.Peer, keyGenerator = "keyGenerator")
public class ImPeerServiceImpl implements ImPeerService, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ImPeerServiceImpl.class);

    @Autowired
    private ImPeerServiceImpl self;

    private final AtomicInteger idx = new AtomicInteger();

    @Override
    public boolean lb(Consumer<String> host, Consumer<Integer> imPort) {
        Set<ImPeer> imPeers = RedisUtil.sGet(RedisKeyMGr.Peer.ALREADY_ONLINE_SET, ImPeer.class);
        if (CollectionUtils.isEmpty(imPeers)) {
            LOG.info("[IM-MGR] 没有可用服务器");
            return false;
        }
        // TODO: 2023/4/21 定义负载均衡策略
        ImPeer ret = new ArrayList<>(imPeers).get(this.idx.getAndIncrement() % imPeers.size());
        LOG.info("[IM-MGR] 轮询出来的可用服务器: {}", ret);
        host.accept(ret.getHost());
        imPort.accept(ret.getImPort());
        return true;
    }

    @Override
    public long add(String path) {
        ImPeer peer = this.parse(path);
        if (Objects.isNull(peer)) {
            return OpCode.PARAM_INVALID;
        }
        ImPeer exist = this.self.getCache(peer);
        if (Objects.nonNull(exist)) {
            LOG.info("[IM-MGR] 服务器{}已经处于在线状态了 不需要再上线", peer);
            return Peer.ALREADY_ONLINE;
        }
        if (!RedisUtil.sSet(RedisKeyMGr.Peer.ALREADY_ONLINE_SET, peer)) {
            return OpCode.FAIL;
        }
        this.self.putCache(peer);
        return OpCode.SUCC;
    }

    /**
     * 注解方式的缓存设置了全局过期时间. 当前场景要取消ttl. 在线的服务器一直缓存着 删除操作由服务器下线动作触发.
     */
    @CachePut(cacheNames = RedisKeyMGr.Peer.ALREADY_ONLINE, key = "#peer.host+'_'+#peer.imPort")
    public ImPeer putCache(ImPeer peer) {
        return peer;
    }

    @Cacheable(cacheNames = RedisKeyMGr.Peer.ALREADY_ONLINE, key = "#peer.host+'_'+#peer.imPort")
    public ImPeer getCache(ImPeer peer) {
        return null;
    }

    @CacheEvict(cacheNames = RedisKeyMGr.Peer.ALREADY_ONLINE, key = "#peer.host+'_'+#peer.imPort")
    public void delCache(ImPeer peer) {

    }

    @Override
    public long discard(String path) {
        ImPeer peer = this.parse(path);
        if (Objects.isNull(peer)) {
            return OpCode.PARAM_INVALID;
        }
        ImPeer exist = this.self.getCache(peer);
        if (Objects.isNull(exist)) {
            LOG.info("[IM-MGR] 服务器下线的前提是该服务器是在线的");
            return Peer.NOT_EXIST;
        }
        if (!RedisUtil.setRemove(RedisKeyMGr.Peer.ALREADY_ONLINE_SET, peer)) {
            return OpCode.FAIL;
        }
        this.self.delCache(peer);
        return OpCode.SUCC;
    }

    /**
     * 节点信息解析.
     *
     * @param s ip:imPort
     */
    private ImPeer parse(String s) {
        if (Objects.isNull(s) || StringUtils.isBlank(s)) {
            return null;
        }
        String[] arr = s.split(":");
        if (arr.length != 2) {
            return null;
        }
        return new ImPeer(arr[0], Integer.parseInt(arr[1]));
    }

    /**
     * 删除缓存上没有ttl的键.
     */
    @Override
    public void destroy() throws Exception {
        Set<ImPeer> peers = RedisUtil.sGet(RedisKeyMGr.Peer.ALREADY_ONLINE_SET, ImPeer.class);
        if (CollectionUtils.isEmpty(peers)) {
            return;
        }
        for (ImPeer peer : peers) {
            this.self.delCache(peer);
        }
        RedisUtil.del(RedisKeyMGr.Peer.ALREADY_ONLINE_SET);
    }
}
