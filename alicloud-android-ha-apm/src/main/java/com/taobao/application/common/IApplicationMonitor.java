package com.taobao.application.common;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Handler;
import android.os.Looper;

public interface IApplicationMonitor {
    void addActivityLifecycle(ActivityLifecycleCallbacks activityLifecycleCallbacks, boolean z);

    void addApmEventListener(IApmEventListener iApmEventListener);

    void addAppLaunchListener(IAppLaunchListener iAppLaunchListener);

    void addPageListener(IPageListener iPageListener);

    IAppPreferences getAppPreferences();

    Handler getAsyncHandler();

    Looper getAsyncLooper();

    Activity getTopActivity();

    void removeActivityLifecycle(ActivityLifecycleCallbacks activityLifecycleCallbacks);

    void removeApmEventListener(IApmEventListener iApmEventListener);

    void removeAppLaunchListener(IAppLaunchListener iAppLaunchListener);

    void removePageListener(IPageListener iPageListener);
}
