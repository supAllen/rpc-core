package com.wjw.rpc.core.remote;

import com.wjw.rpc.core.command.Command;
import com.wjw.rpc.core.command.Request;
import com.wjw.rpc.core.command.RequestCommand;
import com.wjw.rpc.core.command.Response;
import com.wjw.rpc.core.constant.RecConstants;
import com.wjw.rpc.core.service.DefaultRpcContext;
import com.wjw.rpc.core.service.Processor;
import com.wjw.rpc.core.service.RpcContext;
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
        if (!(msg instanceof Request)) {
            System.out.println("request is illegal");
            ctx.fireChannelRead(msg);
            return;
        }
        Request request = (Request) msg;
        Command command = request.getCommand();
        RpcContext rpcContext = DefaultRpcContext.instance;
        Processor process = rpcContext.get(RecConstants.RPC_SERVICE, command.getAction());
        Response response = process.handler(new RequestCommand(request));
        ctx.writeAndFlush(response);
        ctx.fireChannelRead(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
