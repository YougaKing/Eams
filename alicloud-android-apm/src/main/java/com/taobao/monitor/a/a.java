package com.taobao.monitor.a;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: ThreadUtils */
public class a {
    private static Executor a = Executors.newFixedThreadPool(3);

    public static void start(Runnable runnable) {
        a.execute(runnable);
    }
}
