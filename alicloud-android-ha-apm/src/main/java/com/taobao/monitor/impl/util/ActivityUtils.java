package com.taobao.monitor.impl.util;

import android.app.Activity;

/* compiled from: ActivityUtils */
public class ActivityUtils {
    public static String getName(Activity activity) {
        if (activity == null) {
            return "";
        }
        return activity.getClass().getName();
    }

    public static String getSimpleName(Activity activity) {
        if (activity == null) {
            return "";
        }
        return activity.getClass().getSimpleName();
    }
}
