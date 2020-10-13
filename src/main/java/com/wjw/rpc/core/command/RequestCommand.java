package com.wjw.rpc.core.command;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-29 17:21
 **/
public class RequestCommand {
    private String method;
    private Object instance;

    public RequestCommand(String method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public String getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }
}
