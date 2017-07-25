package org.dovakin.httpserver.control.task;

import io.netty.channel.ChannelHandlerContext;
import org.dovakin.cache.GlobalChannelMap;
import org.dovakin.event.Event;
import org.dovakin.httpserver.control.HttpTask;
import org.dovakin.pushserver.protocol.NGLSProtocol;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class PushTask extends HttpTask{


    public PushTask(ChannelHandlerContext ctx, Event event){
        super(ctx, event);
    }

    public String onInvoke() {
        //TODO 根据content中的推送id,推送到不同的socket中
        //TEST

        return getEvent().getContent().getContent().toString();
    }

    protected String onFinish(String result) {

        NGLSProtocol nglsProtocol = new NGLSProtocol(result.length(), result.getBytes());
        GlobalChannelMap.pushAll(nglsProtocol);
        return result;
    }
}
