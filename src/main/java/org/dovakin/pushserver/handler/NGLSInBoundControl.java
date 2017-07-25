package org.dovakin.pushserver.handler;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.dovakin.cache.GlobalChannelMap;
import org.dovakin.event.EventType;
import org.dovakin.pushserver.bean.AuthAction;
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
        int action = nglsProtocolCodec.getTYPE();
        switch (action){
            case EventType.PUSH:
                break;
            case EventType.AUTH:
                Gson gson = new Gson();
                AuthAction authAction
                        = gson.fromJson
                        (new String(nglsProtocolCodec.getContent(), CharsetUtil.UTF_8),
                                AuthAction.class);
                GlobalChannelMap.put(authAction.getClientId(), ctx.channel());
                System.out.println("NGLS通道建立[ClientID]: " + authAction.getClientId() + "\n");
                break;
        }
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
}
