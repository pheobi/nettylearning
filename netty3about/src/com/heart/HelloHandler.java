package com.heart;

import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liyahui
 * @create 2019-06-03
 */
public class HelloHandler extends SimpleChannelHandler{
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage());
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if(e instanceof IdleStateEvent){
/*
            SimpleDateFormat dateFormat = new SimpleDateFormat("ss");

            IdleStateEvent event = (IdleStateEvent) e;
            System.out.println(event.getState()+"  "+dateFormat.format(new Date()));
*/
        if(((IdleStateEvent) e).getState()==IdleState.ALL_IDLE){
            System.out.println("踢完家下线");
            //关闭会话,踢完家下线
            ChannelFuture write = ctx.getChannel().write("timeout,you will close");
            write.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    ctx.getChannel().close();
                }
            });
        }
        }else{
            super.handleUpstream(ctx,e);
        }
    }

    /*    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ss");

        System.out.println(e.getState()+"  "+dateFormat.format(new Date()));
    }*/
}
