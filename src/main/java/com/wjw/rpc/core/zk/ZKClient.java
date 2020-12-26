package com.wjw.rpc.core.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.security.SecureRandom;
import java.util.Objects;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-10-14 10:54
 **/
public class ZKClient {
    static final String CONN_ADDR = System.getProperty("zk-addr", "127.0.0.1:2181");
    static final int SESSION_TIMEOUT = 5000;
    static final SecureRandom random = new SecureRandom();
    private static CuratorFramework cf;
    static {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        cf = CuratorFrameworkFactory.builder()
                .connectString(CONN_ADDR)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();
        cf.start();
    }

    public void createNode(String path, String data) throws Exception {
        doCreateNode(path, data, CreateMode.PERSISTENT);
    }

    public void createNode(String path, String data, CreateMode mode) throws Exception {
        doCreateNode(path, data, mode);
    }

    public void delNode(String path) throws Exception {
        Stat stat = exist(path);
        if (Objects.isNull(stat)) {
            return;
        }
        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
    }

    private Stat exist(String path) throws Exception {
        return cf.checkExists().forPath(path);
    }

    public String getData(String path) throws Exception {
        byte[] data = getDataSimple(path);
        if (Objects.isNull(data)) {
            return null;
        }
        String hostAndPortList = new String(data);
        String[] split = hostAndPortList.split(",");
        return split[random.nextInt(split.length)];
    }

    private byte[] getDataSimple(String path) throws Exception {
        return cf.getData().forPath(path);
    }

    private void doCreateNode(String path, String data, CreateMode mode) throws Exception {
        byte[] bytes = getDataSimple(path);
        if (!Objects.isNull(bytes)) {
            cf.setData().forPath(path, (new String(bytes) + "," + data).getBytes());
            return;
        }
        cf.create().creatingParentsIfNeeded().withMode(mode).forPath(path, data.getBytes());
    }
}
