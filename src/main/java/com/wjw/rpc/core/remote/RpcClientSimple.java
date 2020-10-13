package com.wjw.rpc.core.remote;

import com.wjw.rpc.core.command.Command;
import com.wjw.rpc.core.command.Response;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-30 16:45
 **/
public class RpcClientSimple {
    private String serverUri;
//    private Command command;
    private RpcExecutor rpcExecutor;

    private RpcClientSimple() {
    }

    private RpcClientSimple(String serverUri) {
        this.serverUri = serverUri;
        this.rpcExecutor = new RpcExecutor();
    }

    public static RpcClientSimple getInstance(String serverUri) {
        return new RpcClientSimple(serverUri);
    }

    public Response exectue(Command command) {
        return rpcExecutor.exectue(command);
    }
}
