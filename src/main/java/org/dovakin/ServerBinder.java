package org.dovakin;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class ServerBinder {

    public static void bind(final DovakinServer ds , final int port){

        Thread serverThread = new Thread(new Runnable() {

            public void run() {
                ds.start(port);
            }
        });
        serverThread.start();
    }
}
