package com.github.bannirui.ekko.service;

import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * im server节点信息维护.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Service
public class ImPeerServiceImpl implements ImPeerService {

    @Override
    public boolean lb(Consumer<String> host, Consumer<Integer> imPort) {
        // TODO: 2023/4/20 给每个登陆成功的客户端分配一个服务器
        return false;
    }
}
