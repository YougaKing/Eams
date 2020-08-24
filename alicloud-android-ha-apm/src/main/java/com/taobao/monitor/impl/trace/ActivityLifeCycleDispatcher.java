package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.os.Bundle;

/* compiled from: ActivityLifeCycleDispatcher */
public class ActivityLifeCycleDispatcher extends AbsDispatcher<ActivityLifeCycleDispatcher.LifeCycleListener> {

    /* compiled from: ActivityLifeCycleDispatcher */
    public interface LifeCycleListener {
        void onActivityCreated(Activity activity, Bundle bundle, long timeMillis);

        void onActivityStarted(Activity activity, long timeMillis);

        void onActivityResumed(Activity activity, long timeMillis);

        void onActivityPaused(Activity activity, long timeMillis);

        void onActivityStopped(Activity activity, long timeMillis);

        void onActivityDestroyed(Activity activity, long timeMillis);
    }

    public void onActivityCreated(final Activity activity, final Bundle bundle, final long timeMillis) {
        dispatchRunnable(new DispatcherRunnable<LifeCycleListener>() {
            /* renamed from: a */
            public void run(LifeCycleListener aVar) {
                aVar.onActivityCreated(activity, bundle, timeMillis);
            }
        });
    }

    public void onActivityStarted(final Activity activity, final long timeMillis) {
        dispatchRunnable(new DispatcherRunnable<LifeCycleListener>() {
            /* renamed from: a */
            public void run(LifeCycleListener aVar) {
                aVar.onActivityStarted(activity, timeMillis);
            }
        });
    }

    public void onActivityResumed(final Activity activity, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifeCycleListener>() {
            /* renamed from: a */
            public void run(LifeCycleListener aVar) {
                aVar.onActivityResumed(activity, j);
            }
        });
    }

    public void onActivityPaused(final Activity activity, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifeCycleListener>() {
            /* renamed from: a */
            public void run(LifeCycleListener aVar) {
                aVar.onActivityPaused(activity, j);
            }
        });
    }

    public void onActivityStopped(final Activity activity, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifeCycleListener>() {
            /* renamed from: a */
            public void run(LifeCycleListener aVar) {
                aVar.onActivityStopped(activity, j);
            }
        });
    }

    public void onActivityDestroyed(final Activity activity, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifeCycleListener>() {
            /* renamed from: a */
            public void run(LifeCycleListener aVar) {
                aVar.onActivityDestroyed(activity, j);
            }
        });
    }
}
