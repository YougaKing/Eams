package com.taobao.application.common.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
/* compiled from: MainApplicationCallbackGroup */
class MainApplicationCallbackGroup implements ActivityLifecycleCallbacks, ICallbackGroup<ActivityLifecycleCallbacks> {
    private final ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new ArrayList<>();

    MainApplicationCallbackGroup() {
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        synchronized (this.mActivityLifecycleCallbacks) {
            Iterator it = this.mActivityLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                ((ActivityLifecycleCallbacks) it.next()).onActivityCreated(activity, bundle);
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        synchronized (this.mActivityLifecycleCallbacks) {
            Iterator it = this.mActivityLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                ((ActivityLifecycleCallbacks) it.next()).onActivityStarted(activity);
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        synchronized (this.mActivityLifecycleCallbacks) {
            Iterator it = this.mActivityLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                ((ActivityLifecycleCallbacks) it.next()).onActivityResumed(activity);
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        synchronized (this.mActivityLifecycleCallbacks) {
            Iterator it = this.mActivityLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                ((ActivityLifecycleCallbacks) it.next()).onActivityPaused(activity);
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        synchronized (this.mActivityLifecycleCallbacks) {
            Iterator it = this.mActivityLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                ((ActivityLifecycleCallbacks) it.next()).onActivityStopped(activity);
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        synchronized (this.mActivityLifecycleCallbacks) {
            Iterator it = this.mActivityLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                ((ActivityLifecycleCallbacks) it.next()).onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
        synchronized (this.mActivityLifecycleCallbacks) {
            Iterator it = this.mActivityLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                ((ActivityLifecycleCallbacks) it.next()).onActivityDestroyed(activity);
            }
        }
    }

    /* renamed from: a */
    public void addCallback(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        }
        synchronized (this.mActivityLifecycleCallbacks) {
            if (!this.mActivityLifecycleCallbacks.contains(activityLifecycleCallbacks)) {
                this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
            }
        }
    }

    /* renamed from: b */
    public void removeCallback(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        }
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacks);
        }
    }
}
