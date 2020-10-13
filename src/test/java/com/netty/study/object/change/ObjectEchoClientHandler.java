package com.netty.study.object.change;

import com.wjw.rpc.core.command.Request;
import com.wjw.rpc.core.command.Response;
import com.wjw.rpc.core.future.ResponseFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-10-12 11:39
 **/
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send the first message if this handler is a client-side handler.
        System.out.println("建立连接成功...");
        ctx.writeAndFlush("ping");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        System.out.println(msg.getClass().getSimpleName());
        if (msg instanceof Request) {
            System.out.println("设置响应结果\t" + System.currentTimeMillis() + "\t" +msg + "\t" +((Request) msg).getId());
            ResponseFuture.responseReceived(new Response(msg, ((Request) msg).getId()));
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
