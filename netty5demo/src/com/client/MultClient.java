package com.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多连接客户端
 * @author liyahui
 * @create 2019-06-02
 */
public class MultClient {
    /**服务类*/
    private Bootstrap bootstrap = new Bootstrap();
    /**会话*/
    private List<Channel> channels = new ArrayList<io.netty.channel.Channel>();
   /**引用计数器*/
   private final AtomicInteger workerIndex = new AtomicInteger();
    /**初始化*/
    public void init(int count){
        EventLoopGroup worker = new NioEventLoopGroup();
        //设置线程池
        bootstrap.group(worker);
        //设置socket工厂
        bootstrap.channel(NioSocketChannel.class);
        //设置管道
        bootstrap.handler(new ChannelInitializer<io.netty.channel.Channel>() {
            @Override
            protected void initChannel(io.netty.channel.Channel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        for (int i = 1; i <=count; i++) {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 10101);
            channels.add(future.channel());
        }
    }
    /**获取会话*/
    public Channel nextChannel(){
        return getFirstActiveChannel(0);
    }

    private Channel getFirstActiveChannel(int count){
        Channel channel = channels.get(Math.abs(workerIndex.getAndIncrement() % channels.size()));
        if(!channel.isActive()){
            //重连
            reconnect(channel);
            if(count>=channels.size()){
                throw new RuntimeException("no can use channel");
            }
            return getFirstActiveChannel(count+1);
        }
        return channel;
    }

    /**重连*/
    private void reconnect(Channel channel){
        synchronized (channel){//用锁可能阻塞,可以用单任务队列
            if (channels.indexOf(channel)==-1){
                return;
            }
            Channel newChannel = bootstrap.connect("127.0.0.1", 10101).channel();
            channels.set(channels.indexOf(channel),newChannel);
        }
    }
}
