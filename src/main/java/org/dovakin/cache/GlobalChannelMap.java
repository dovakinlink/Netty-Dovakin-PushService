package org.dovakin.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.dovakin.pushserver.protocol.NGLSProtocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class GlobalChannelMap {

    private final static ConcurrentHashMap<String, ChannelId> channelMap
            = new ConcurrentHashMap<String, ChannelId>();
    public final static ChannelGroup group
            = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void put(String id, Channel channel){
        group.add(channel);
        channelMap.put(id,channel.id());
    }

    public static void remove(String id){
        //TODO 同时需要移除group的对应channel
        channelMap.remove(id);
    }

    public static void remove(ChannelId id){
        for(Map.Entry entry : channelMap.entrySet()){
            if(entry.getValue() == id){
                String key = (String) entry.getKey();
                channelMap.remove(key);
            }
        }
        //TODO 不了解是否为正确用法
        group.remove(id);
    }

    public static void pushAll(NGLSProtocol msg){
        group.writeAndFlush(msg);
    }
}
