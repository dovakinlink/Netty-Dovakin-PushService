package org.dovakin.httpserver.control;

import io.netty.channel.ChannelHandlerContext;
import org.dovakin.event.Event;
import org.dovakin.event.EventType;
import org.dovakin.httpserver.control.task.PushTask;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuhuanchao on 2017/7/25.
 */
public class DefaultHttpTaskFactory extends AbstractHttpTaskFactory{


    public HttpTask create(ChannelHandlerContext ctx ,Event event) {
        switch (event.getType()){
            case EventType.AUTH:
                break;
            case EventType.PUSH:
                return new PushTask(ctx, event);
            case EventType.HEART:
                break;
            default:
                break;
        }
        return null;
    }

}
