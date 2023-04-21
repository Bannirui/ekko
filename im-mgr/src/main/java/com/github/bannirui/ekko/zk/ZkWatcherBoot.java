package com.github.bannirui.ekko.zk;

import com.github.bannirui.ekko.common.ex.BizException;
import com.github.bannirui.ekko.common.util.SpringCtxUtil;
import com.github.bannirui.ekko.service.ImPeerService;
import java.util.Objects;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听器. 监听在zk上的服务器注册根节点
 * <ul>服务器变更状态
 * <li>新服务器上线</li>
 * <li>服务器下线</li>
 * </ul>
 *
 * @author dingrui
 * @since 2023/4/21
 */
public class ZkWatcherBoot implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ZkWatcherBoot.class);

    private String zkHost;

    private int zkPort;

    private String zkRoot;

    public ZkWatcherBoot(String zkHost, int zkPort, String zkRoot) {
        this.zkHost = zkHost;
        this.zkPort = zkPort;
        this.zkRoot = zkRoot;
    }

    @Override
    public void run() {
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient(this.zkHost, 10_000, 10_000, new RetryNTimes(3, 1_000));
            client.start();
            if (client.isStarted()) {
                PathChildrenCache cache = new PathChildrenCache(client, this.zkRoot, true);
                cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
                cache.getListenable().addListener(new PathChildrenCacheListener() {
                    @Override
                    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                        ImPeerService imPeerService = SpringCtxUtil.getBean(ImPeerService.class);
                        if (Objects.isNull(imPeerService)) {
                            throw new BizException("依赖的组件ImPeerService没初始化好");
                        }
                        // /im-peers/10.10.132.185:9527
                        switch (event.getType()) {
                            case CHILD_ADDED:
                                LOG.info("{}有子节点添加 新增的子节点信息为 路径={}", ZkWatcherBoot.this.zkRoot, event.getData().getPath());
                                imPeerService.add(ZkWatcherBoot.this.parse(event.getData().getPath()));
                                break;
                            case CHILD_REMOVED:
                                imPeerService.discard(ZkWatcherBoot.this.parse(event.getData().getPath()));
                                LOG.info("{}有子节点移除 删除的子节点信息为 路径={}", ZkWatcherBoot.this.zkRoot, event.getData().getPath());
                                break;
                            case CHILD_UPDATED:
                                break;
                            default:
                                break;
                        }
                    }
                });
                LOG.info("[IM-MGR] 监听zk路径: {}", this.zkRoot);
            }
        } catch (Exception e) {
            throw new BizException("[IM-MGR]监听zk失败", e);
        }
    }

    private String parse(String s) {
        String[] arr = s.split("/");
        return arr[arr.length - 1];
    }
}
