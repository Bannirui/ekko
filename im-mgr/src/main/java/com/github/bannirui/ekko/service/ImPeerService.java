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

    /**
     * 服务器上线.
     *
     * @param path ip:im-port
     * @return 操作状态码 {@link com.github.bannirui.ekko.constants.OpCode}
     */
    long add(String path);

    /**
     * 服务器下线.
     *
     * @param path ip:im-port
     * @return 操作状态码 {@link com.github.bannirui.ekko.constants.OpCode}
     */
    long discard(String path);
}
