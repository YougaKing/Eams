package com.taobao.monitor.impl.logger;

import android.util.Log;

public class Logger {
    private static final String TAG = "APMLogger";
    private static boolean isDebug = false;

    private Logger() {
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean z) {
        isDebug = z;
    }

    public static void i(String str, Object... objArr) {
        if (isDebug) {
            Log.i(TAG, str + ":" + format2String(objArr));
        }
    }

    public static void d(String str, Object... objArr) {
        if (isDebug) {
            Log.d(TAG, str + ":" + format2String(objArr));
        }
    }

    public static void e(String str, Object... objArr) {
        Log.e(TAG, str + ":" + format2String(objArr));
    }

    public static void w(String str, Object... objArr) {
        if (isDebug) {
            Log.w(TAG, str + ":" + format2String(objArr));
        }
    }

    public static void throwException(Throwable th) {
        if (isDebug) {
            throw new RuntimeException(th);
        }
    }

    private static String format2String(Object... objArr) {
        if (objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : objArr) {
            if (obj != null) {
                sb.append("->").append(obj.toString());
            }
        }
        return sb.toString();
    }
}
