package com.alibaba.ha.adapter.service.appstatus;

import android.annotation.TargetApi;
import android.app.Application;

public class AppStatusRegHelper {
    @TargetApi(14)
    public static void registerAppStatusCallbacks(AppStatusCallbacks appStatusCallbacks) {
        if (appStatusCallbacks != null) {
            AppStatusMonitor.getInstance().registerAppStatusCallbacks(appStatusCallbacks);
        }
    }

    @TargetApi(14)
    public static void unRegisterAppStatusCallbacks(AppStatusCallbacks appStatusCallbacks) {
        if (appStatusCallbacks != null) {
            AppStatusMonitor.getInstance().unregisterAppStatusCallbacks(appStatusCallbacks);
        }
    }

    @TargetApi(14)
    public static void registeActivityLifecycleCallbacks(Application application) {
        if (application != null) {
            application.registerActivityLifecycleCallbacks(AppStatusMonitor.getInstance());
        }
    }

    @TargetApi(14)
    public static void unregisterActivityLifecycleCallbacks(Application application) {
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(AppStatusMonitor.getInstance());
        }
    }
}
