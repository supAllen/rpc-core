package com.netty.study;

import com.google.common.collect.ImmutableMap;
import com.netty.study.object.change.ObjectEchoClient;
import com.wjw.rpc.core.command.Command;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-10-12 11:30
 **/
public class Main {
    public static void main(String[] args) {
        ObjectEchoClient client = new ObjectEchoClient();
//        client.stop();
//        client.send("123");
        Command command = new Command("/service/rpc-demo", "hello", ImmutableMap.of("args", "hello"));
        System.out.println("========================================");
        for (int i = 0; i < 5; i++) {
            client.send(command);
        }
    }
}
