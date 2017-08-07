package test.demo;

import org.dovakin.push.core.Dovakin;
import org.dovakin.push.core.ServerBinder;
import org.dovakin.push.core.httpserver.server.HttpServer;
import org.dovakin.push.core.pushserver.server.NGLSServer;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class Main{

    private final static String CONFIG_DEFAULT_PATH
            = System.getProperty("user.dir") + "/src/main/resources/config.dovakin";

    public static void main(String[] args){

        Dovakin.init(CONFIG_DEFAULT_PATH);

        ServerBinder.bind(new NGLSServer(), 9999);
        ServerBinder.bind(new HttpServer(), 8081);
    }
}
