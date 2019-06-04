package com.client;

import com.cn.model.Response;
import org.jboss.netty.channel.*;

/**
 * @author liyahui
 * @create 2019-05-29
 */
public class HiHandler extends SimpleChannelHandler {
    /**
     * 接受消息
     * */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
/*        System.out.println("messageReceived");
        String s = (String) e.getMessage();
        System.out.println(s);

        super.messageReceived(ctx, e);*/
        Response message = (Response) e.getMessage();

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
