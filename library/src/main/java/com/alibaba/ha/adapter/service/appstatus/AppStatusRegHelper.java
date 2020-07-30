//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.appstatus;

import android.annotation.TargetApi;
import android.app.Application;

public class AppStatusRegHelper {
    public AppStatusRegHelper() {
    }

    @TargetApi(14)
    public static void registerAppStatusCallbacks(AppStatusCallbacks callbacks) {
        if (null != callbacks) {
            AppStatusMonitor.getInstance().registerAppStatusCallbacks(callbacks);
        }

    }

    @TargetApi(14)
    public static void unRegisterAppStatusCallbacks(AppStatusCallbacks callbacks) {
        if (null != callbacks) {
            AppStatusMonitor.getInstance().unregisterAppStatusCallbacks(callbacks);
        }

    }

    @TargetApi(14)
    public static void registeActivityLifecycleCallbacks(Application application) {
        if (null != application) {
            application.registerActivityLifecycleCallbacks(AppStatusMonitor.getInstance());
        }

    }

    @TargetApi(14)
    public static void unregisterActivityLifecycleCallbacks(Application application) {
        if (null != application) {
            application.unregisterActivityLifecycleCallbacks(AppStatusMonitor.getInstance());
        }

    }
}
