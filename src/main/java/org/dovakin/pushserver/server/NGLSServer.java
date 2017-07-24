package org.dovakin.pushserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.dovakin.DovakinServer;

import java.net.InetSocketAddress;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class NGLSServer implements DovakinServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void start(int port) {

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NGLSHandlerInitializer())
                    // 设置TCP读写缓冲区为 512KB
                    .option(ChannelOption.SO_BACKLOG, 512)
                    // Keep-Alive保活
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("NGLS推送服务器已启动...");
            Channel serverChannel = b.bind(new InetSocketAddress(port)).channel();
            ChannelFuture future = serverChannel.closeFuture();
            // 阻塞
            future.sync();

        } catch (Exception e){

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
