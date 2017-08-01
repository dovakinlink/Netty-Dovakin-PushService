package org.dovakin.push.core.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.dovakin.push.core.pushserver.protocol.NGLSProtocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class GlobalChannelMap {

    private final static ConcurrentHashMap<String, ChannelInfo> channelMap
            = new ConcurrentHashMap<String, ChannelInfo>();
    public final static ChannelGroup group
            = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void put(String id, Channel channel){
        group.add(channel);
        ChannelInfo channelInfo = new ChannelInfo(channel.id());
        channelMap.put(id,channelInfo);
    }

    public static void remove(String id){
        group.remove(channelMap.get(id));
        channelMap.remove(id);
    }

    public static void remove(ChannelId channelId){
        for(Map.Entry entry : channelMap.entrySet()){
            if(entry.getValue() == channelId){
                String key = (String) entry.getKey();
                channelMap.remove(key);
            }
        }
        //TODO 不了解是否为正确用法
        group.remove(channelId);
    }

    public static void close(ChannelId channelId){
        Channel channel = group.find(channelId);
        channel.close();
        remove(channelId);
    }

    public static void push(String id, NGLSProtocol msg){
        ChannelInfo channelInfo = channelMap.get(id);
        if(channelInfo == null){
            //TODO 推送对象不在线处理逻辑
            return;
        }
        ChannelId channelId = channelInfo.channelId;
        Channel ch;
        if((ch = group.find(channelId)) != null){
            ch.writeAndFlush(msg);
        }
    }

    public static void push(NGLSProtocol msg){
        group.writeAndFlush(msg);
    }

    private static class ChannelInfo{
        private long timestamp;
        private ChannelId channelId;

        public ChannelInfo(ChannelId channelId){
            this.channelId = channelId;
            this.timestamp = System.currentTimeMillis();
        }

        public void hit(long time){
            this.timestamp = time;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public ChannelId getChannelId() {
            return channelId;
        }

        public void setChannelId(ChannelId channelId) {
            this.channelId = channelId;
        }
    }
}
