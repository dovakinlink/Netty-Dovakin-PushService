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

    public static void push(String id, NGLSProtocol msg){
        ChannelId channelId = channelMap.get(id);
        if(channelId == null){
            //TODO 推送对象不在线处理逻辑
            return;
        }
        Channel ch;
        if((ch = group.find(channelId)) != null){
            ch.writeAndFlush(msg);
        }
    }

    public static void push(NGLSProtocol msg){
        group.writeAndFlush(msg);
    }
}
