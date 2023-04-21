package com.github.bannirui.ekko.service;

import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * im server节点信息维护.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Service
public class ImPeerServiceImpl implements ImPeerService {

    private static final Logger LOG = LoggerFactory.getLogger(ImPeerServiceImpl.class);

    @Override
    public boolean lb(Consumer<String> host, Consumer<Integer> imPort) {
        // TODO: 2023/4/20 给每个登陆成功的客户端分配一个服务器
        return false;
    }

    @Override
    public void add(String path) {
        // TODO: 2023/4/21
        LOG.info("[IM-MGR] 服务器上线处理: {}", path);
    }

    @Override
    public void discard(String path) {
        // TODO: 2023/4/21
        LOG.info("[IM-MGR] 服务器下线处理: {}", path);
    }
}
