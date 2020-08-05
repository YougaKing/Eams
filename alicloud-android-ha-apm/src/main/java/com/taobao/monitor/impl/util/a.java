package com.taobao.monitor.impl.util;

import android.app.Activity;

/* compiled from: ActivityUtils */
public class a {
    public static String a(Activity activity) {
        if (activity == null) {
            return "";
        }
        return activity.getClass().getName();
    }

    public static String b(Activity activity) {
        if (activity == null) {
            return "";
        }
        return activity.getClass().getSimpleName();
    }
}
