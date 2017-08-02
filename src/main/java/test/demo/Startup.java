package test.demo;

import org.dovakin.push.core.ServerBinder;
import org.dovakin.push.core.annotations.AnnotationDispatcher;
import org.dovakin.push.core.httpserver.server.HttpServer;
import org.dovakin.push.core.pushserver.server.NGLSServer;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class Startup {

    public static void main(String[] args){

        AnnotationDispatcher.addHttpPackage("test.push.demo.http.task");
        AnnotationDispatcher.addNGLSPackage("test.demo.socket.executable");

        ServerBinder.bind(new NGLSServer(), 9999);
        ServerBinder.bind(new HttpServer(), 8081);
    }
}
