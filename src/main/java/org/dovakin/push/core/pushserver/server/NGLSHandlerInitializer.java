package org.dovakin.push.core.pushserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.dovakin.push.core.pushserver.codec.NGLSProtocolCodec;
import org.dovakin.push.core.pushserver.handler.NGLSInBoundControl;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class NGLSHandlerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 心跳检测机制
        pipeline.addLast(new IdleStateHandler(180,0,0, TimeUnit.SECONDS));
        // NGLS协议编解码器
        pipeline.addLast(new NGLSProtocolCodec());
        // NGLS入栈控制器S
        pipeline.addLast(new NGLSInBoundControl());

    }
}
