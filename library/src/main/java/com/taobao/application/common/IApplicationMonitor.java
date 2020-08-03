//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Handler;
import android.os.Looper;

public interface IApplicationMonitor {
    void addActivityLifecycle(ActivityLifecycleCallbacks var1, boolean var2);

    void removeActivityLifecycle(ActivityLifecycleCallbacks var1);

    void addPageListener(IPageListener var1);

    void removePageListener(IPageListener var1);

    void addAppLaunchListener(IAppLaunchListener var1);

    void removeAppLaunchListener(IAppLaunchListener var1);

    void addApmEventListener(IApmEventListener var1);

    void removeApmEventListener(IApmEventListener var1);

    IAppPreferences getAppPreferences();

    Activity getTopActivity();

    Looper getAsyncLooper();

    Handler getAsyncHandler();
}
