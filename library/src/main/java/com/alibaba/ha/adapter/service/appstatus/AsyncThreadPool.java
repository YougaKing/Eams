//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.appstatus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncThreadPool {
    public static ScheduledExecutorService threadPoolExecutor;
    public static int prop = 10;
    public static final AtomicInteger INTEGER = new AtomicInteger();
    public Integer corePoolSize = 4;

    public AsyncThreadPool() {
    }

    public synchronized void start(Runnable var1) {
        try {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = Executors.newScheduledThreadPool(this.corePoolSize, new AsyncThreadPool.AsyncThreadFactory(prop));
            }

            threadPoolExecutor.submit(var1);
        } catch (Throwable var3) {
            var3.printStackTrace();
        }

    }

    static class AsyncThreadFactory implements ThreadFactory {
        private int priority;

        public AsyncThreadFactory(int var1) {
            this.priority = var1;
        }

        public Thread newThread(Runnable var1) {
            int var2 = AsyncThreadPool.INTEGER.getAndIncrement();
            Thread var3 = new Thread(var1, "Aliha-AppStatus:" + var2);
            var3.setPriority(this.priority);
            return var3;
        }
    }
}
