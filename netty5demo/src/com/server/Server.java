package com.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * netty5服务端
 * @author liyahui
 * @create 2019-06-02
 */
public class Server {

    public static void main(String[] args) {
        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        //boosWorker
        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //设置线程池
            bootstrap.group(boos,worker);

            //设置socket工厂
            bootstrap.channel(NioServerSocketChannel.class);

            //设置管道工厂
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ServerHandler());
                }
            });

            //netty3中对应设置如下:
            //bootstrap.setOption("backlog",1024);
            //bootstrap.setOption("tcpNoDelay",true);
            //bootstrap.setOption("keepAlive",true);
            //设置参数 TCP参数
            bootstrap.option(ChannelOption.SO_BACKLOG,2048);//serverSocketchannel的设置,连接缓冲池的大小
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);//socketchannel的设置,维持连接的活跃,清楚死链接
            bootstrap.childOption(ChannelOption.TCP_NODELAY,true);//socketchannel的设置,关闭延迟发送

            //绑定端口
            ChannelFuture future = bootstrap.bind(10101);

            //等待服务端关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
