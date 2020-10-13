package com.netty.study;

import com.netty.study.object.change.ObjectEchoClient;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-10-12 11:30
 **/
public class Main {
    public static void main(String[] args) {
        ObjectEchoClient client = new ObjectEchoClient();
        for (int i = 0; i < 5; i++) {
            client.send("123");
        }
    }
}
