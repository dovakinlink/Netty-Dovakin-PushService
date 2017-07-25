package org.dovakin.httpserver.control.task;

import io.netty.channel.ChannelHandlerContext;
import org.dovakin.cache.GlobalChannelMap;
import org.dovakin.event.Event;
import org.dovakin.event.EventType;
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
        //TODO 处理Event的Message数据,返回结果字符串
        return getEvent().getContent().getContent().toString();
    }

    protected String onFinish(ChannelHandlerContext ctx, String clientId,  String result) {

        NGLSProtocol nglsProtocol = new NGLSProtocol(result.length(), result.getBytes());
        nglsProtocol.setTYPE(EventType.PUSH);
        GlobalChannelMap.push(clientId, nglsProtocol);
        return result;
    }
}
