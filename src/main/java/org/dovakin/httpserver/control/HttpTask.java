package org.dovakin.httpserver.control;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public abstract class HttpTask {

    private final ChannelHandlerContext ctx;

    public HttpTask(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }

    public void run(){
        begin();
        //TODO 完善response及返回数据
        String responseContent = "{\"test\":\"test\"}";
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(responseContent.getBytes(CharsetUtil.UTF_8)));

        //response.headers().set()
        response.headers().set("Cotent-type","application/json;charset=UTF-8");
        response.headers().set("Content-length", response.content().readableBytes());
        ChannelFuture future = ctx.writeAndFlush(response);
        future.addListener(ChannelFutureListener.CLOSE);
    }

    protected abstract void begin();
}
