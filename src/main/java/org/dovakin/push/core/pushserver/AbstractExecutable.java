package org.dovakin.push.core.pushserver;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by liuhuanchao on 2017/8/1.
 */
public abstract class AbstractExecutable<T> implements Executable {

    private byte[] data;
    private Class<T> entityClass;

    public AbstractExecutable(ChannelHandlerContext ctx, byte[] stream){
        this.data = stream;

        handleData(decodeProtocol());
    }

    private T decodeProtocol(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class)params[0];

        String protocolStr = new String(this.data, CharsetUtil.UTF_8);
        T entity = new Gson().fromJson(protocolStr, entityClass);
        return entity;
    }

    public abstract void handleData(T protocol);
}
