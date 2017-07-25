package org.dovakin.httpserver.handler;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;
import org.dovakin.event.Event;
import org.dovakin.httpserver.bean.PushRequest;
import org.dovakin.httpserver.control.AbstractHttpTaskFactory;
import org.dovakin.httpserver.control.DefaultHttpTaskFactory;
import org.dovakin.httpserver.control.HttpTask;
import org.dovakin.httpserver.control.task.PushTask;
import org.dovakin.httpserver.executor.TaskExecutor;
import org.dovakin.message.PushMessage;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class HttpDispatcherControl extends SimpleChannelInboundHandler<FullHttpRequest> {


    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)
            throws Exception {
        dispatchUri(ctx, request);
    }

    private void dispatchUri(ChannelHandlerContext ctx, FullHttpRequest request){
        HttpHeaders headers = request.headers();
        String uri = request.getUri();
        ByteBuf buf = request.content();
        String content = buf.toString(CharsetUtil.UTF_8);

        //TODO 根据Uri进行事件分发
        if(uri.equals("/push")){
            Event event = getEvent(request.content(), PushRequest.class);
            AbstractHttpTaskFactory factory = new DefaultHttpTaskFactory();
            HttpTask task = factory.create(ctx, event);
            TaskExecutor.submit(task);
        }
        //TaskExecutor.joinQueue(new PushTask(ctx,content));
        //TaskExecutor.submit(new PushTask(ctx, content));
    }

    private Event getEvent(ByteBuf buf, Class clazz){
        String content = buf.toString(CharsetUtil.UTF_8);
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        PushRequest request = gson.fromJson(content, PushRequest.class);
        Event<PushMessage> event = new Event();
        event.setType(request.getEventType());
        event.setContent(request.getMessage());
        return event;
    }
}
