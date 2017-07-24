package org.dovakin.pushserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.dovakin.cache.GlobalChannelMap;
import org.dovakin.pushserver.protocol.NGLSProtocol;

/**
 * NGLS入栈控制器
 *
 * Created by liuhuanchao on 2017/7/24.
 */
public class NGLSInBoundControl extends SimpleChannelInboundHandler<NGLSProtocol> {

    protected void channelRead0(ChannelHandlerContext ctx, NGLSProtocol nglsProtocolCodec)
            throws Exception {
        //TODO 协议数据处理
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //TEST
        GlobalChannelMap.put("111",ctx.channel());
        System.out.println("链接建立... \n");
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
}
