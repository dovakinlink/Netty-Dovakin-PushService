package org.dovakin.push.core.annotations;


import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.dovakin.push.core.httpserver.control.HttpTask;
import org.dovakin.push.core.httpserver.executor.TaskExecutor;
import org.dovakin.push.core.pushserver.Executable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by liuhuanchao on 2017/7/28.
 */
public class AnnotationDispatcher {

    private static LinkedHashMap<String, Class<?>> httpTaskMap
            = new LinkedHashMap<String, Class<?>>();

    private static LinkedHashMap<Integer, Class<?>> nglsMap
            = new LinkedHashMap<Integer, Class<?>>();

    public static void init(String packageName){

    }

    public static void addHttpPackage(String packageName){
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

    public static void addNGLSPackage(String packageName){
        Set<Class<?>> classes
                = AnnotationScanner.find(NGLSController.class, packageName);
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()){
            Class<?> cls = iterator.next();
            NGLSController nglsController = cls.getAnnotation(NGLSController.class);
            int type = nglsController.type();
            nglsMap.put(type,cls);
        }
    }

    public static <T> void dispatch(ChannelHandlerContext ctx, byte[] stream, int type){
        Class targetClass = nglsMap.get(type);
        if(targetClass == null){
            //TODO 路由错误处理
            return;
        }
        try {
            Class<T> entityClass;
            Type genType = targetClass.getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            entityClass = (Class)params[0];

            String protocolStr = new String(stream, CharsetUtil.UTF_8);
            T entity = new Gson().fromJson(protocolStr, entityClass);
            Constructor constructor
                    = targetClass.getDeclaredConstructor(
                            new Class[]{ChannelHandlerContext.class, entityClass}
                     );
            constructor.setAccessible(true);
            Executable executable
                    = (Executable) constructor.newInstance(ctx,entity);
            executable.run();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T> void dispatch(ChannelHandlerContext ctx, ByteBuf buf, String routeType){
        Class targetClass = httpTaskMap.get(routeType);
        if(targetClass == null){
            //TODO 路由错误处理
            return;
        }
        try {
            Class<T> entityClass;
            Type genType = targetClass.getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            entityClass = (Class)params[0];

            T entity = new Gson().fromJson(buf.toString(CharsetUtil.UTF_8), entityClass);
            Constructor constructor
                    = targetClass.getDeclaredConstructor(
                                new Class[]{ChannelHandlerContext.class, entityClass});
            constructor.setAccessible(true);
            HttpTask task
                    = (HttpTask)constructor.newInstance(ctx, entity);
            TaskExecutor.submit(task);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static <T> T decodeProtocol(Class<T> clazz, byte[] bytes){
        Class<T> entityClass;
        Type genType = clazz.getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class)params[0];

        String protocolStr = new String(bytes, CharsetUtil.UTF_8);
        T entity = new Gson().fromJson(protocolStr, entityClass);
        return entity;
    }
}
