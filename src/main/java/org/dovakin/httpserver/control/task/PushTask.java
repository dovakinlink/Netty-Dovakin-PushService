package org.dovakin.httpserver.control.task;

import io.netty.channel.ChannelHandlerContext;
import org.dovakin.cache.GlobalChannelMap;
import org.dovakin.httpserver.control.HttpTask;
import org.dovakin.pushserver.protocol.NGLSProtocol;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class PushTask extends HttpTask{

    public final String mContent;

    public PushTask(ChannelHandlerContext ctx, String content){
        super(ctx);
        this.mContent = content;
    }

    public void begin() {
        //TODO 根据content中的推送id,推送到不同的socket中
        //TEST
        String response = "response msg";
        NGLSProtocol nglsProtocol = new NGLSProtocol(response.length(), response.getBytes());
        GlobalChannelMap.pushAll(nglsProtocol);
    }
}
