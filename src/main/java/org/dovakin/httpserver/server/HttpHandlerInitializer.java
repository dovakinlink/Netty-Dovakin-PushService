package org.dovakin.httpserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.dovakin.httpserver.handler.HttpDispatcherControl;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class HttpHandlerInitializer extends ChannelInitializer<SocketChannel> {


    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new HttpDispatcherControl());
    }
}
