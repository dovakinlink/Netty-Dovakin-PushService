package org.dovakin.httpserver.control;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.dovakin.event.Event;
import org.dovakin.httpserver.bean.PushRequest;
import org.dovakin.message.PushMessage;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public abstract class HttpTask {

    private final ChannelHandlerContext ctx;

    private final Event event;

    public HttpTask(ChannelHandlerContext ctx, ByteBuf buf){
        this.ctx = ctx;
        this.event = getEvent(buf);

    }

    public String run(){
       return onInvoke();
    }

    public Event getEvent(){
        return this.event;
    }

    public void onSuccess(String msg){

        String responseContent = onFinish(ctx, event.getContent().getClientId(), msg);
        ChannelFuture future
                = ctx.writeAndFlush(
                        makeResponse(HttpResponseStatus.OK,responseContent));
        future.addListener(ChannelFutureListener.CLOSE);
    }

    public void onFailed(String msg){
        String responseContent = onFinish(ctx, event.getContent().getClientId(), msg);
        ChannelFuture future
                = ctx.writeAndFlush(
                        makeResponse(HttpResponseStatus.OK,responseContent));
        future.addListener(ChannelFutureListener.CLOSE);
    }

    protected abstract String onInvoke();
    protected abstract String onFinish(ChannelHandlerContext ctx, String clientId, String obj);
    protected abstract Event getEvent(ByteBuf buf);

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
