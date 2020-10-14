package com.wjw.rpc.core.remote;

import com.wjw.rpc.core.command.Command;
import com.wjw.rpc.core.zk.ZKClient;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-30 17:03
 **/
public class RpcExecutor {

    private RpcClient rpcClient;

    public RpcExecutor(String serverUri) {
        String data = null;
        try {
            data = ZKClient.instance.getData(serverUri);
            System.out.println("host and port: "+data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isOk = false;
        if (!Objects.isNull(data)) {
            String[] hostAndPort = data.split(":");
            if (hostAndPort.length == 2) {
                String host = hostAndPort[0];
                String port = hostAndPort[1];
                if (StringUtils.isNumeric(port)) {
                    int portInt = Integer.valueOf(port);
                    this.rpcClient = new RpcClient(host, portInt);
                    isOk = true;
                }
            }
        }
        if (!isOk) {
            throw new RuntimeException("server uri is illegal");
        }
    }

    public Object exectue(Command command) {
        return rpcClient.send(command);
    }
}
