package com.wjw.rpc.core.remote;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-29 18:02
 **/
public interface IServer {

    /**
     * 服务启动
     * @exception InterruptedException
     */
    void start() throws InterruptedException;

    /**
     * 服务停止
     */
    void stop();
}
