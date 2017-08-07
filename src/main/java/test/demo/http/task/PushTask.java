package test.demo.http.task;

import io.netty.channel.ChannelHandlerContext;
import org.dovakin.push.core.annotations.RequestTask;
import org.dovakin.push.core.cache.GlobalChannelMap;
import org.dovakin.push.core.httpserver.control.HttpTask;
import test.demo.EventType;
import org.dovakin.push.core.pushserver.protocol.NGLSProtocol;
import test.demo.http.PushEvent;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
@RequestTask(uri = "/push")
public class PushTask extends HttpTask<PushEvent> {

    private PushEvent event;

    public PushTask(ChannelHandlerContext ctx, PushEvent event){
        super(ctx, event);
        this.event = event;
    }

    public String onInvoke() {
        return event.getMessage().getContent().toString();
    }

    protected String onFinish(ChannelHandlerContext ctx, PushEvent event,  String result) {

        NGLSProtocol nglsProtocol = new NGLSProtocol(result.length(), result.getBytes());
        nglsProtocol.setTYPE(EventType.PUSH);
        GlobalChannelMap.push(event.getMessage().getClientId(), nglsProtocol);
        return result;
    }

}
