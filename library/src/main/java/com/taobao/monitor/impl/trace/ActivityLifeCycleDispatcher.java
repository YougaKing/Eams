package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.os.Bundle;

public class ActivityLifeCycleDispatcher extends AbsDispatcher<ActivityLifeCycleDispatcher.LifeCycleListener> {
    public ActivityLifeCycleDispatcher() {
    }

    public void onActivityCreated(final Activity var1, final Bundle var2, final long var3) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {

            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.onActivityCreated(var1, var2, var3);
            }
        });
    }

    public void onActivityStarted(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.onActivityStarted(var1, var2);
            }
        });
    }

    public void onActivityResumed(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.onActivityResumed(var1, var2);
            }
        });
    }

    public void onActivityPaused(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.onActivityPaused(var1, var2);
            }
        });
    }

    public void onActivityStopped(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.onActivityStopped(var1, var2);
            }
        });
    }

    public void onActivityDestroyed(final Activity var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityLifeCycleDispatcher.LifeCycleListener>() {
            @Override
            public void run(ActivityLifeCycleDispatcher.LifeCycleListener var1x) {
                var1x.onActivityDestroyed(var1, var2);
            }
        });
    }

    public interface LifeCycleListener {
        void onActivityCreated(Activity var1, Bundle var2, long var3);

        void onActivityStarted(Activity var1, long var2);

        void onActivityResumed(Activity var1, long var2);

        void onActivityPaused(Activity var1, long var2);

        void onActivityStopped(Activity var1, long var2);

        void onActivityDestroyed(Activity var1, long var2);
    }
}