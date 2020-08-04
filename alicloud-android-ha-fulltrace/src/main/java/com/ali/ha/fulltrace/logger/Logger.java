package com.ali.ha.fulltrace.logger;

import android.util.Log;
import com.ali.ha.fulltrace.BuildConfig;

public class Logger {
    private static final String TAG = "FTLogger";
    private static final boolean THROW_EXCEPTION = false;
    private static boolean isDebug = true;

    private Logger() {
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug2) {
        isDebug = isDebug2;
    }

    public static void i(String tag, Object... msg) {
        if (isDebug) {
            Log.i(TAG, tag + ":" + format2String(msg));
        }
    }

    public static void d(String tag, Object... msg) {
        if (isDebug) {
            Log.d(TAG, tag + ":" + format2String(msg));
        }
    }

    public static void e(String tag, Object... msg) {
        if (isDebug) {
            Log.e(TAG, tag + ":" + format2String(msg));
        }
    }

    public static void w(String tag, Object... msg) {
        if (isDebug) {
            Log.w(TAG, tag + ":" + format2String(msg));
        }
    }

    public static void throwException(Throwable e) {
        if (isDebug) {
        }
    }

    private static String format2String(Object... objects) {
        if (objects == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder builder = new StringBuilder();
        for (Object o : objects) {
            if (o != null) {
                builder.append("->").append(o.toString());
            }
        }
        return builder.toString();
    }
}
