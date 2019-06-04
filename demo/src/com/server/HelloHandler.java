package com.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

/**
 * @author liyahui
 * @create 2019-05-29
 */
public class HelloHandler extends SimpleChannelHandler {
    /**
    * 接受消息
    * */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("messageReceived");
        //int i=1/0;
        //System.out.println(e.getMessage());//BigEndianHeapChannelBuffer(ridx=0, widx=1, cap=1)
        //ChannelBuffer message = (ChannelBuffer) e.getMessage();
        //String s = new String(message.array());//字节流被封装在了channelBuffer里 要先取出来再打印
        String s = (String) e.getMessage();
        System.out.println(s);

        //回写数据 不能直接回写字符串 用channelBuffer封装
        //ChannelBuffer buffer = ChannelBuffers.copiedBuffer("hi".getBytes());
        ctx.getChannel().write("hi");
        super.messageReceived(ctx, e);
    }

    /**
     * 捕获异常
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 新连接
     * */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 必须是连接已经建立，关闭通道的时候才会触发
     * */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * channel关闭时触发
     * */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }

    @Override
    public void connectRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.connectRequested(ctx, e);
    }
}
