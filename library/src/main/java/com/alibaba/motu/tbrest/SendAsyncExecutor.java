//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SendAsyncExecutor {
    public static ScheduledExecutorService threadPoolExecutor;
    public static int prop = 1;
    public static final AtomicInteger INTEGER = new AtomicInteger();
    public Integer corePoolSize = 2;

    public SendAsyncExecutor() {
    }

    public synchronized void start(Runnable task) {
        try {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = Executors.newScheduledThreadPool(this.corePoolSize, new SendAsyncExecutor.SendThreadFactory(prop));
            }

            threadPoolExecutor.submit(task);
        } catch (Throwable var3) {
            var3.printStackTrace();
        }

    }

    static class SendThreadFactory implements ThreadFactory {
        private int priority;

        public SendThreadFactory(int proiority) {
            this.priority = proiority;
        }

        public Thread newThread(Runnable r) {
            int index = SendAsyncExecutor.INTEGER.getAndIncrement();
            Thread thread = new Thread(r, "RestSend:" + index);
            thread.setPriority(this.priority);
            return thread;
        }
    }
}
