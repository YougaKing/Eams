//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
class MainApplicationCallbackGroup implements ActivityLifecycleCallbacks, ICallbackGroup<ActivityLifecycleCallbacks> {
    private final ArrayList<ActivityLifecycleCallbacks> mLifecycleCallbacks = new ArrayList<>();

    MainApplicationCallbackGroup() {
    }

    public void onActivityCreated(Activity var1, Bundle var2) {
        synchronized(this.mLifecycleCallbacks) {
            Iterator var4 = this.mLifecycleCallbacks.iterator();

            while(var4.hasNext()) {
                ActivityLifecycleCallbacks var5 = (ActivityLifecycleCallbacks)var4.next();
                var5.onActivityCreated(var1, var2);
            }

        }
    }

    public void onActivityStarted(Activity var1) {
        synchronized(this.mLifecycleCallbacks) {
            Iterator var3 = this.mLifecycleCallbacks.iterator();

            while(var3.hasNext()) {
                ActivityLifecycleCallbacks var4 = (ActivityLifecycleCallbacks)var3.next();
                var4.onActivityStarted(var1);
            }

        }
    }

    public void onActivityResumed(Activity var1) {
        synchronized(this.mLifecycleCallbacks) {
            Iterator var3 = this.mLifecycleCallbacks.iterator();

            while(var3.hasNext()) {
                ActivityLifecycleCallbacks var4 = (ActivityLifecycleCallbacks)var3.next();
                var4.onActivityResumed(var1);
            }

        }
    }

    public void onActivityPaused(Activity var1) {
        synchronized(this.mLifecycleCallbacks) {
            Iterator var3 = this.mLifecycleCallbacks.iterator();

            while(var3.hasNext()) {
                ActivityLifecycleCallbacks var4 = (ActivityLifecycleCallbacks)var3.next();
                var4.onActivityPaused(var1);
            }

        }
    }

    public void onActivityStopped(Activity var1) {
        synchronized(this.mLifecycleCallbacks) {
            Iterator var3 = this.mLifecycleCallbacks.iterator();

            while(var3.hasNext()) {
                ActivityLifecycleCallbacks var4 = (ActivityLifecycleCallbacks)var3.next();
                var4.onActivityStopped(var1);
            }

        }
    }

    public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
        synchronized(this.mLifecycleCallbacks) {
            Iterator var4 = this.mLifecycleCallbacks.iterator();

            while(var4.hasNext()) {
                ActivityLifecycleCallbacks var5 = (ActivityLifecycleCallbacks)var4.next();
                var5.onActivitySaveInstanceState(var1, var2);
            }

        }
    }

    public void onActivityDestroyed(Activity var1) {
        synchronized(this.mLifecycleCallbacks) {
            Iterator var3 = this.mLifecycleCallbacks.iterator();

            while(var3.hasNext()) {
                ActivityLifecycleCallbacks var4 = (ActivityLifecycleCallbacks)var3.next();
                var4.onActivityDestroyed(var1);
            }

        }
    }

    public void a(ActivityLifecycleCallbacks var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            synchronized(this.mLifecycleCallbacks) {
                if (!this.mLifecycleCallbacks.contains(var1)) {
                    this.mLifecycleCallbacks.add(var1);
                }

            }
        }
    }

    public void b(ActivityLifecycleCallbacks var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            synchronized(this.mLifecycleCallbacks) {
                this.mLifecycleCallbacks.remove(var1);
            }
        }
    }
}
