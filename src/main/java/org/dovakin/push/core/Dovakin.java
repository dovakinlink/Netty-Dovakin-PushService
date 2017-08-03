package org.dovakin.push.core;

import com.google.gson.Gson;
import org.dovakin.push.core.annotations.AnnotationDispatcher;
import org.dovakin.push.core.config.DovakinConfig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by liuhuanchao on 2017/8/3.
 */
public class Dovakin {

    private final static String CONFIG_DEFAULT_PATH
            = System.getProperty("user.dir") + "/src/main/resources/config.dovakin";

    private static String config_path;

    public static void init(){
        config_path = CONFIG_DEFAULT_PATH;
        try {
            initAnno();
            initJDBC();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void init(String path){
        config_path = path;
        try {
            initAnno();
            initJDBC();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void initAnno(){
        try {
            DovakinConfig config = readConfig();
            if(config == null){
                //TODO 配置文件启动错误处理
            }
            if(config.getHttptask_package() != null)
                AnnotationDispatcher
                        .addHttpPackage(config.getHttptask_package());
            if(config.getNgls_package() != null){
                AnnotationDispatcher
                        .addNGLSPackage(config.getNgls_package());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void initJDBC() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    private static DovakinConfig readConfig(){
        DovakinConfig config = null;
        try {
            BufferedReader br
                    = new BufferedReader(new FileReader(config_path));
            StringBuilder content = new StringBuilder("");
            String line;
            while((line = br.readLine()) != null){
                content.append(line);
            }
            config = new Gson()
                    .fromJson(content.toString(), DovakinConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }
}
