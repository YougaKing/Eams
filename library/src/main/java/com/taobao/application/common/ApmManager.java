//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import com.taobao.application.common.Apm.OnActivityLifecycleCallbacks;
import com.taobao.application.common.Apm.OnAppLaunchListener;
import com.taobao.application.common.Apm.OnPageListener;

public class ApmManager {
    private static IApplicationMonitor apmDelegate;

    public ApmManager() {
    }

    public static void addActivityLifecycle(OnActivityLifecycleCallbacks var0, boolean var1) {
        if (apmDelegate != null) {
            apmDelegate.addActivityLifecycle(var0, var1);
        }

    }

    public static void removeActivityLifecycle(OnActivityLifecycleCallbacks var0) {
        if (apmDelegate != null) {
            apmDelegate.removeActivityLifecycle(var0);
        }

    }

    public static void addPageListener(OnPageListener var0) {
        if (apmDelegate != null) {
            apmDelegate.addPageListener(var0);
        }

    }

    public static void removePageListener(OnPageListener var0) {
        if (apmDelegate != null) {
            apmDelegate.removePageListener(var0);
        }

    }

    public static void addAppLaunchListener(OnAppLaunchListener var0) {
        if (apmDelegate != null) {
            apmDelegate.addAppLaunchListener(var0);
        }

    }

    public static void removeAppLaunchListener(OnAppLaunchListener var0) {
        if (apmDelegate != null) {
            apmDelegate.removeAppLaunchListener(var0);
        }

    }

    public static IAppPreferences getAppPreferences() {
        return apmDelegate != null ? apmDelegate.getAppPreferences() : IAppPreferences.DEFAULT;
    }

    public static Activity getTopActivity() {
        return apmDelegate != null ? apmDelegate.getTopActivity() : null;
    }

    public static void addApmEventListener(IApmEventListener var0) {
        if (apmDelegate != null) {
            apmDelegate.addApmEventListener(var0);
        }

    }

    public static void removeApmEventListener(IApmEventListener var0) {
        if (apmDelegate != null) {
            apmDelegate.removeApmEventListener(var0);
        }

    }

    public static Handler getAsyncHandler() {
        return apmDelegate != null ? apmDelegate.getAsyncHandler() : null;
    }

    public static Looper getAsyncLooper() {
        return apmDelegate != null ? apmDelegate.getAsyncLooper() : null;
    }

    static void setApmDelegate(IApplicationMonitor var0) {
        apmDelegate = var0;
    }
}
