package com.github.bannirui.ekko.zk;

import com.github.bannirui.ekko.common.ex.BizException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zk作为注册中心. 以后台线程任务写为临时节点.
 *
 * @author dingrui
 * @since 2023/4/21
 */
public class ZkRegistryBoot implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ZkRegistryBoot.class);

    private String zkHost;

    private int zkPort;

    private String zkRoot;

    private int imPort;

    public ZkRegistryBoot(String zkHost, int zkPort, String zkRoot, int imPort) {
        this.zkHost = zkHost;
        this.zkPort = zkPort;
        this.zkRoot = zkRoot;
        this.imPort = imPort;
    }

    private void createIfAbsent(CuratorFramework zkClient, String path) throws Exception {
        Stat stat = zkClient.checkExists()
            .forPath(path);
        if (Objects.nonNull(stat)) {
            return;
        }
        zkClient.create()
            .withMode(CreateMode.PERSISTENT)
            .forPath(path);
    }

    private void registry(CuratorFramework zkClient, String parent, String cur) throws Exception {
        // 父节点
        this.createIfAbsent(zkClient, parent);
        // 临时节点
        zkClient.create()
            .withMode(CreateMode.EPHEMERAL)
            .forPath(parent + "/" + cur);
    }

    private String localHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @Override
    public void run() {
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient(this.zkHost, 10_000, 10_000, new RetryNTimes(3, 1_000));
            client.start();
            if (client.isStarted()) {
                String host = this.localHost();
                this.registry(client, this.zkRoot, host + ":" + this.imPort);
                LOG.info("[IM-SERVER] 服务器节点信息注册zk.");
            }
        } catch (Exception e) {
            throw new BizException("im-server注册zk失败", e);
        }
    }
}
