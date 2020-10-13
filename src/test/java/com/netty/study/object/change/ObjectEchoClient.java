package com.netty.study.object.change;

import com.wjw.rpc.core.command.Command;
import com.wjw.rpc.core.command.Request;
import com.wjw.rpc.core.future.ResponseFuture;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-10-12 11:38
 **/
public class ObjectEchoClient {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    private Channel channel;
    private EventLoopGroup group;

    public ObjectEchoClient() {
        try {
            init();
        } catch (SSLException e) {
            e.printStackTrace();
        }
    }

    private void init() throws SSLException {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .remoteAddress(HOST, PORT)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEchoClientHandler());
                        }
                    });

            // Start the connection attempt.
            ChannelFuture connectFuture = b.connect();
            connectFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("connect success");
                    System.out.println("connect-"+System.currentTimeMillis());
                }
            });

            long start = System.currentTimeMillis();
            System.out.println("channel get pre-"+start);
            channel = connectFuture.channel();
            System.out.println("channel get time-"+(System.currentTimeMillis() - start));
            System.out.println(channel);

            channel.closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("channel is close");
                    System.out.println("close-"+System.currentTimeMillis());
                }
            });
        } finally {
            System.out.println("客户端创建成功...");
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public void send(Object msg) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(channel);
        System.out.println("send-"+System.currentTimeMillis());
//        channel.writeAndFlush(msg);
        if (msg instanceof Command) {
            Request request = new Request((Command) msg);
            ResponseFuture responseFuture = new ResponseFuture(request);
            ChannelFuture channelFuture = channel.writeAndFlush(request);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("send success...");
                    }
                }
            });
            try {
                Object res = responseFuture.getRes(2, TimeUnit.SECONDS);
                System.out.println("get res: " + res);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
