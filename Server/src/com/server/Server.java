package com.server;

import com.cn.codc.RequestDecoder;
import com.cn.codc.RequestEncoder;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liyahui
 * @create 2019-05-29
 */
public class Server {

    public static void main(String[] args) {
        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();
        //--创建两个线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        //设置一个niosocket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));
        //设置管道工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                //pipeline.addLast("decoder",new StringDecoder());
                //pipeline.addLast("encoder",new StringEncoder());
                pipeline.addLast("decoder",new RequestDecoder());
                pipeline.addLast("encoder",new RequestEncoder());
                pipeline.addLast("helloHandler",new HelloHandler());
                return pipeline;
            }
        });

        bootstrap.bind(new InetSocketAddress(10101));

        System.out.println("start!");
    }

}
