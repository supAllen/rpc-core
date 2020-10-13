package com.wjw.rpc.core.remote;

import com.wjw.rpc.core.command.Command;
import com.wjw.rpc.core.service.DefaultRpcContext;
import com.wjw.rpc.core.service.RpcContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-30 14:57
 **/
public class DefaultServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.getClass().getSimpleName());
        System.out.println(msg);
//        String message = "";
//        System.out.println(msg instanceof ByteBuf);
//        if (msg instanceof ByteBuf) {
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] byteArray = new byte[buf.capacity()];
//            buf.readBytes(byteArray);
//            message = new String(byteArray);
//            System.out.println("收到Client消息:" + message);
//        }
//        ctx.write(message);
//        ctx.writeAndFlush(msg);
//        System.out.println(msg);
//        System.out.println(msg.getClass().getTypeName());
//        RpcContext rpcContext = DefaultRpcContext.instance;
        ctx.writeAndFlush(msg);
        ctx.fireChannelRead(msg);
//        Processor process = rpcContext.get(RecConstants.RPC_SERVICE, "server-uri")
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        ctx.flush();
    }

}
