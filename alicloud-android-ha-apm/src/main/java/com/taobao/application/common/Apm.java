package com.taobao.application.common;

import android.app.Application.ActivityLifecycleCallbacks;

public interface Apm {

    public interface OnActivityLifecycleCallbacks extends ActivityLifecycleCallbacks {
    }

    public interface OnApmEventListener extends IApmEventListener {
    }

    public interface OnAppLaunchListener extends IAppLaunchListener {
    }

    public interface OnPageListener extends IPageListener {
    }
}
