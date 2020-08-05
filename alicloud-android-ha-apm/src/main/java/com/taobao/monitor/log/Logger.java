package com.taobao.monitor.log;

import android.util.Log;

/* compiled from: Logger */
public class Logger {
    private static boolean isDebug = false;

    public static void i(String str, Object... objArr) {
        if (isDebug) {
            Log.i("APMLogger", str + ":" + a(objArr));
        }
    }

    public static void throwException(Throwable th) {
        if (isDebug) {
        }
    }

    private static String a(Object... objArr) {
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
