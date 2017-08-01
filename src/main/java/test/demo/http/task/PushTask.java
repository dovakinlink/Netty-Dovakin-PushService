package test.demo.http.task;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.dovakin.push.core.annotations.RequestTask;
import org.dovakin.push.core.cache.GlobalChannelMap;
import org.dovakin.push.core.event.Event;
import test.demo.EventType;
import org.dovakin.push.core.httpserver.control.HttpTask;
import test.demo.http.bean.PushRequest;
import test.demo.http.bean.PushMessage;
import org.dovakin.push.core.pushserver.protocol.NGLSProtocol;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
@RequestTask(uri = "/push")
public class PushTask extends HttpTask {


    public PushTask(ChannelHandlerContext ctx, ByteBuf buf){
        super(ctx, buf);
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

    protected Event getEvent(ByteBuf buf) {
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
