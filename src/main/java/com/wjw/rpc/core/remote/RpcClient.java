package com.wjw.rpc.core.remote;

import com.wjw.rpc.core.command.Command;
import com.wjw.rpc.core.command.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-30 15:56
 **/
public class RpcClient {
    private String ip = "127.0.0.1";
    private Integer port = 15000;
    private ChannelFuture cf;
    private Channel channel;

    public RpcClient() {
        init();
    }

    private void init() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new DefaultClientHandler());
                        }
                    });
            cf = bootstrap.connect(ip, port);
            channel = cf.sync().channel();
            System.out.println("已连接 " + String.format("IP: %s, port: %d", ip, port));
        } catch (Exception e){
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public Response send(Command command) {
        ChannelFuture channelFuture = channel.writeAndFlush(command);
        return new Response();
    }
}
