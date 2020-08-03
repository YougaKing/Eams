package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.os.Bundle;

public class ActivityLifeCycleDispatcher extends AbsDispatcher<ActivityLifeCycleDispatcher.LifeCycleListener> {
    public ActivityLifeCycleDispatcher() {
    }

    public void a(final Activity var1, final Bundle var2, final long var3) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {

            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.a(var1, var2, var3);
            }
        });
    }

    public void a(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.a(var1, var2);
            }
        });
    }

    public void b(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.b(var1, var2);
            }
        });
    }

    public void c(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.c(var1, var2);
            }
        });
    }

    public void d(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.d(var1, var2);
            }
        });
    }

    public void e(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.e(var1, var2);
            }
        });
    }

    public interface LifeCycleListener {
        void a(Activity var1, Bundle var2, long var3);

        void a(Activity var1, long var2);

        void b(Activity var1, long var2);

        void c(Activity var1, long var2);

        void d(Activity var1, long var2);

        void e(Activity var1, long var2);
    }
}