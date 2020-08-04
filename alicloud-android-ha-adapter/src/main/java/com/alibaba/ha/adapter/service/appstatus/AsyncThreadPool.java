package com.alibaba.ha.adapter.service.appstatus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncThreadPool {
    public static final AtomicInteger INTEGER = new AtomicInteger();
    public static int prop = 10;
    public static ScheduledExecutorService threadPoolExecutor;
    public Integer corePoolSize = Integer.valueOf(4);

    static class AsyncThreadFactory implements ThreadFactory {
        private int priority;

        public AsyncThreadFactory(int i) {
            this.priority = i;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "Aliha-AppStatus:" + AsyncThreadPool.INTEGER.getAndIncrement());
            thread.setPriority(this.priority);
            return thread;
        }
    }

    public synchronized void start(Runnable runnable) {
        try {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = Executors.newScheduledThreadPool(this.corePoolSize.intValue(), new AsyncThreadFactory(prop));
            }
            threadPoolExecutor.submit(runnable);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return;
    }
}
