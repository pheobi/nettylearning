package com.client;

import com.cn.codc.RequestEncoder;
import com.cn.codc.ResponseDecoder;
import com.cn.model.Request;
import com.cn.module.fuben.request.FightRequest;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liyahui
 * @create 2019-05-29
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {

        //服务类
        ClientBootstrap bootstrap = new ClientBootstrap();

        //线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //socket工厂
        bootstrap.setFactory(new NioClientSocketChannelFactory(boss,worker));

        //管道工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                //pipeline.addLast("decoder",new StringDecoder());
                pipeline.addLast("decoder",new ResponseDecoder());
                //pipeline.addLast("encoder",new StringEncoder());
                pipeline.addLast("encoder",new RequestEncoder());
                pipeline.addLast("hiHandler",new HiHandler());
                return pipeline;
            }
        });

        //连接服务端
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10101));
        Channel channel = connect.sync().getChannel();//sync()连接成功后？

        System.out.println("client start");

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("请输入");
            //channel.write(scanner.next());
            int fubenId = Integer.parseInt(scanner.nextLine());
            int count = Integer.parseInt(scanner.nextLine());

            FightRequest fightRequest = new FightRequest();
            fightRequest.setFubenId(fubenId);
            fightRequest.setCount(count);

            Request request = new Request();
            request.setModule((short)1);
            request.setCmd((short)1);
            request.setData(fightRequest.getBytes());
            //发送请求
            channel.write(new Request());//request不能直接发 要编成channelBuffer
        }
    }
}
