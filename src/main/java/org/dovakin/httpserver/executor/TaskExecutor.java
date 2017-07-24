package org.dovakin.httpserver.executor;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.*;
import org.dovakin.httpserver.control.HttpTask;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class TaskExecutor {

    ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

    final ConcurrentLinkedQueue<HttpTask> queue = Queues.newConcurrentLinkedQueue();

    //TODO
    public void dosth(){

    }

    //TODO 线程池进行队列管理(FIFO),执行Task
    public static void joinQueue(HttpTask task){
        task.run();
    }
}
