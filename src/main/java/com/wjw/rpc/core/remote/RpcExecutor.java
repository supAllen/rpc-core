package com.wjw.rpc.core.remote;

import com.wjw.rpc.core.command.Command;
import com.wjw.rpc.core.command.Response;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-30 17:03
 **/
public class RpcExecutor {

    private RpcClient rpcClient;

    public RpcExecutor() {
        this.rpcClient = new RpcClient();
    }

    public Object exectue(Command command) {
//        String action = command.getAction();
        return rpcClient.send(command);
    }
}
