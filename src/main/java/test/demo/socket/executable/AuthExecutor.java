package test.demo.socket.executable;

import io.netty.channel.ChannelHandlerContext;
import org.dovakin.push.core.annotations.NGLSController;
import org.dovakin.push.core.cache.GlobalChannelMap;
import test.demo.EventType;
import org.dovakin.push.core.pushserver.AbstractExecutable;
import org.dovakin.push.core.pushserver.protocol.NGLSProtocol;
import test.demo.socket.bean.AuthAction;

/**
 * Created by liuhuanchao on 2017/8/1.
 */
@NGLSController(type = EventType.AUTH)
public class AuthExecutor extends AbstractExecutable<AuthAction> {

    private ChannelHandlerContext mContext;
    private AuthAction authAction;

    public AuthExecutor(ChannelHandlerContext ctx, AuthAction authAction) {
        super(ctx,authAction);
        mContext = ctx;
        this.authAction = authAction;
    }

    public void run() {
        GlobalChannelMap.put(authAction.getClientId(), mContext.channel());
        System.out.println("NGLS通道建立[ClientID]: " + authAction.getClientId() + "\n");
        byte[] result = "o".getBytes();
        NGLSProtocol nglsProtocol = new NGLSProtocol(result.length, result);
        nglsProtocol.setTYPE(EventType.AUTH_SUCCESS);
        mContext.writeAndFlush(nglsProtocol);
    }
}
