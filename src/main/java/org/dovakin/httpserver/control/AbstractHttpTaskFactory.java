package org.dovakin.httpserver.control;

import io.netty.channel.ChannelHandlerContext;
import org.dovakin.event.Event;

/**
 * Created by liuhuanchao on 2017/7/25.
 */
public abstract class AbstractHttpTaskFactory {

    public abstract HttpTask create(ChannelHandlerContext ctx , Event event);
}
