package org.dovakin;

import org.dovakin.httpserver.server.HttpServer;
import org.dovakin.pushserver.server.NGLSServer;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class Startup {

    public static void main(String[] args){
        ServerBinder.bind(new NGLSServer(), 9999);
        ServerBinder.bind(new HttpServer(), 8081);
    }
}
