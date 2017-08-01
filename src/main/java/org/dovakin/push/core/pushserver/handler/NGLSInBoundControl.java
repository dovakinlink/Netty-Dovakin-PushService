package org.dovakin.push.core.pushserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.dovakin.push.core.annotations.AnnotationDispatcher;
import org.dovakin.push.core.cache.GlobalChannelMap;
import org.dovakin.push.core.pushserver.protocol.NGLSProtocol;

/**
 * NGLS入栈控制器
 *
 * Created by liuhuanchao on 2017/7/24.
 */
public class NGLSInBoundControl extends SimpleChannelInboundHandler<NGLSProtocol> {

    protected void channelRead0(ChannelHandlerContext ctx, NGLSProtocol nglsProtocol)
            throws Exception {
        AnnotationDispatcher.dispatch(ctx, nglsProtocol.getContent(),
                nglsProtocol.getTYPE());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("SOCKET连接建立... \n");
        System.out.println("等待鉴权... \n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        GlobalChannelMap.remove(ctx.channel().id());
        System.out.println("链接断开... \n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE){
                GlobalChannelMap.close(ctx.channel().id());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
