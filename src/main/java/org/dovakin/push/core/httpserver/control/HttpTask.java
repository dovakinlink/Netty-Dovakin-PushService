package org.dovakin.push.core.httpserver.control;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public abstract class HttpTask<T> {

    private final ChannelHandlerContext ctx;

    private final T protocol;

    public HttpTask(ChannelHandlerContext ctx, ByteBuf buf){
        this.ctx = ctx;
        String content = buf.toString(CharsetUtil.UTF_8);
        this.protocol = decodeProtocol(content);

    }

    public String run(){
       return onInvoke();
    }

    public T getProtocol(){
        return this.protocol;
    }

    public void onSuccess(String msg){

        String responseContent = onFinish(ctx, protocol, msg);
        ChannelFuture future
                = ctx.writeAndFlush(
                        makeResponse(HttpResponseStatus.OK,responseContent));
        future.addListener(ChannelFutureListener.CLOSE);
    }

    public void onFailed(String msg){
        String responseContent = onFinish(ctx, protocol, msg);
        ChannelFuture future
                = ctx.writeAndFlush(
                        makeResponse(HttpResponseStatus.OK,responseContent));
        future.addListener(ChannelFutureListener.CLOSE);
    }

    protected abstract String onInvoke();
    protected abstract String onFinish(ChannelHandlerContext ctx, T event, String obj);
    protected abstract T decodeProtocol(String content);

    /**
     * 构造HTTP RESPONSE
     * @param responseContent
     * @return
     */
    private FullHttpResponse makeResponse(HttpResponseStatus status,String responseContent){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status,
                Unpooled.wrappedBuffer(responseContent.getBytes(CharsetUtil.UTF_8)));

        //response.headers().set()
        response.headers().set("Cotent-type","application/json;charset=UTF-8");
        response.headers().set("Content-length", response.content().readableBytes());
        return response;
    }
}
