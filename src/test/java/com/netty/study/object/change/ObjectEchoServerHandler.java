package com.netty.study.object.change;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-10-12 11:31
 **/
public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(msg);
        String message = "";
//        System.out.println(msg.getClass().getTypeName());
//        System.out.println(msg.getClass().getSimpleName());
        System.out.println(msg.toString());
//        System.out.println(msg instanceof ByteBuf);
//        if (msg instanceof ByteBuf) {
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] byteArray = new byte[buf.capacity()];
//            buf.readBytes(byteArray);
//            message = new String(byteArray);
//            System.out.println("收到Client消息:" + message);
//        }
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
