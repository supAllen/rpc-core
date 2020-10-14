package com.wjw.rpc.core.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.Objects;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-10-14 10:54
 **/
public class ZKClient {
    static final String CONN_ADDR = System.getProperty("zk-addr", "127.0.0.1:2181");
    static final int SESSION_TIMEOUT = 5000;
    private static CuratorFramework cf;
    public static final ZKClient instance = new ZKClient();
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
        cf.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
    }

    public void createNode(String path, String data, CreateMode mode) throws Exception {
        cf.create().creatingParentsIfNeeded().withMode(mode).forPath(path, data.getBytes());
    }

    public void delNode(String path) throws Exception {
        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
    }

    public String getData(String path) throws Exception {
        byte[] data = cf.getData().forPath(path);
        return Objects.isNull(data) ? null : new String(data);
    }
}
