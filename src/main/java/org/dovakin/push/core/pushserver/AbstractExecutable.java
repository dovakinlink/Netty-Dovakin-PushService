package org.dovakin.push.core.pushserver;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by liuhuanchao on 2017/8/1.
 */
public abstract class AbstractExecutable implements Executable {

    private byte[] data;
    private ChannelHandlerContext mContext;

    public AbstractExecutable(ChannelHandlerContext ctx, byte[] stream){
        this.data = stream;
        this.mContext = ctx;
    }

    protected byte[] data(){return this.data;}

    protected ChannelHandlerContext channelHandlerContext(){return this.mContext;}
}
