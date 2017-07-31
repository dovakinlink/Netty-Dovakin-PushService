package org.dovakin.push.core.httpserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.dovakin.push.core.annotations.AnnotationDispatcher;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class HttpDispatcherControl extends SimpleChannelInboundHandler<FullHttpRequest> {


    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)
            throws Exception {
        dispatchUri(ctx, request);
    }

    private void dispatchUri(ChannelHandlerContext ctx, FullHttpRequest request){
        String uri = request.getUri();
        ByteBuf buf = request.content();
        AnnotationDispatcher.dispatch(ctx, buf, uri);
    }


}
