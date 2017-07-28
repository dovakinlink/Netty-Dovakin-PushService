package org.dovakin;

import org.dovakin.annotations.AnnotationDispatcher;
import org.dovakin.httpserver.server.HttpServer;
import org.dovakin.pushserver.server.NGLSServer;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class Startup {

    public static void main(String[] args){

        AnnotationDispatcher.init("org.dovakin.httpserver.control.task");

        ServerBinder.bind(new NGLSServer(), 9999);
        ServerBinder.bind(new HttpServer(), 8081);
    }
}
