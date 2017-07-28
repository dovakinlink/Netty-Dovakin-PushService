package org.dovakin.annotations;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.dovakin.httpserver.executor.TaskExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by liuhuanchao on 2017/7/28.
 */
public class AnnotationDispatcher {

    private static LinkedHashMap<String, Class<?>> httpTaskMap
            = new LinkedHashMap<String, Class<?>>();

    public static void init(String packageName){
        Set<Class<?>> classes
                = AnnotationScanner.find(RequestTask.class, packageName);
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()){
            Class<?> cls = iterator.next();
            RequestTask requestTask = cls.getAnnotation(RequestTask.class);
            String uri  = requestTask.uri();
            httpTaskMap.put(uri, cls);
        }
    }

    public static void dispatch(ChannelHandlerContext ctx, ByteBuf buf, String routeType){
        Class targetClass = httpTaskMap.get(routeType);
        if(targetClass == null){
            //TODO 路由错误处理
            return;
        }
        try {
            Constructor constructor = null;
            try {
                constructor
                        = targetClass.getDeclaredConstructor(
                                new Class[]{ChannelHandlerContext.class, ByteBuf.class});
                constructor.setAccessible(true);
                org.dovakin.httpserver.control.HttpTask task
                        = (org.dovakin.httpserver.control.HttpTask)constructor.newInstance(ctx, buf);
                TaskExecutor.submit(task);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
