package com.taobao.monitor.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static Executor executor = Executors.newFixedThreadPool(3);

    public static void start(Runnable runnable) {
        executor.execute(runnable);
    }
}
