package com.wjw.rpc.core.remote;

import com.wjw.rpc.core.service.DefaultRpcContext;
import com.wjw.rpc.core.service.RpcContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-30 17:23
 **/
public class DefaultClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println();
        System.out.println(msg);
        System.out.println(msg.getClass().getTypeName());
//        RpcContext rpcContext = DefaultRpcContext.instance;
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("123");
    }
}
