package org.dovakin.push.core.httpserver.executor;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.*;
import org.dovakin.push.core.httpserver.control.HttpTask;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class TaskExecutor {

    private static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

    // 延迟执行队列
    final ConcurrentLinkedQueue<HttpTask> queue = Queues.newConcurrentLinkedQueue();

    public static void submit(final HttpTask task){
        Futures.addCallback(executorService.submit(new Callable<String>() {

            public String call() throws Exception {
                //TODO 处理实际业务逻辑
                //TEST
                return task.run();
            }
        }), new FutureCallback<String>() {

            public void onSuccess(@Nullable String s) {
                task.onSuccess(s);
            }

            public void onFailure(Throwable throwable) {
                task.onFailed(throwable.getMessage());
            }
        });

        task.run();
    }
}
