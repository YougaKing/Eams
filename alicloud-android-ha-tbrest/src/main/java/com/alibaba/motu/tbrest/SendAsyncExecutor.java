package com.alibaba.motu.tbrest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SendAsyncExecutor {
    public static final AtomicInteger INTEGER = new AtomicInteger();
    public static int prop = 1;
    public static ScheduledExecutorService threadPoolExecutor;
    public Integer corePoolSize = Integer.valueOf(2);

    static class SendThreadFactory implements ThreadFactory {
        private int priority;

        public SendThreadFactory(int proiority) {
            this.priority = proiority;
        }

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "RestSend:" + SendAsyncExecutor.INTEGER.getAndIncrement());
            thread.setPriority(this.priority);
            return thread;
        }
    }

    public synchronized void start(Runnable task) {
        try {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = Executors.newScheduledThreadPool(this.corePoolSize.intValue(), new SendThreadFactory(prop));
            }
            threadPoolExecutor.submit(task);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return;
    }
}
