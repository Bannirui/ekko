package com.github.bannirui.ekko.service;

import java.util.function.Consumer;

/**
 * im server.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public interface ImPeerService {

    /**
     * load balance.
     */
    boolean lb(Consumer<String> host, Consumer<Integer> imPort);
}
