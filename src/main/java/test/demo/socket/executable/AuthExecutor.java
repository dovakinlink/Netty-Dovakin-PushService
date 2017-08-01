package test.demo.socket.executable;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.dovakin.push.core.annotations.NGLSController;
import org.dovakin.push.core.cache.GlobalChannelMap;
import org.dovakin.push.core.event.EventType;
import org.dovakin.push.core.pushserver.AbstractExecutable;
import org.dovakin.push.core.pushserver.protocol.NGLSProtocol;
import test.demo.socket.bean.AuthAction;

/**
 * Created by liuhuanchao on 2017/8/1.
 */
@NGLSController(type = EventType.AUTH)
public class AuthExecutor extends AbstractExecutable {

    public AuthExecutor(ChannelHandlerContext ctx, byte[] stream) {
        super(ctx,stream);
    }

    public void run() {
        Gson gson = new Gson();
        AuthAction authAction
                = gson.fromJson
                (new String(data(), CharsetUtil.UTF_8),
                        AuthAction.class);
        GlobalChannelMap.put(authAction.getClientId(), channelHandlerContext().channel());
        System.out.println("NGLS通道建立[ClientID]: " + authAction.getClientId() + "\n");
        byte[] result = "o".getBytes();
        NGLSProtocol nglsProtocol = new NGLSProtocol(result.length, result);
        nglsProtocol.setTYPE(EventType.AUTH_SUCCESS);
        channelHandlerContext().writeAndFlush(nglsProtocol);
    }
}