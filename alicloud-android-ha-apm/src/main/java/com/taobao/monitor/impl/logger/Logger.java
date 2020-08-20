//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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

    public static void setDebug(boolean var0) {
        isDebug = var0;
    }

    public static void i(String var0, Object... var1) {
        if (isDebug) {
            String var2 = format2String(var1);
            Log.i("APMLogger", var0 + ":" + var2);
        }

    }

    public static void d(String var0, Object... var1) {
        if (isDebug) {
            String var2 = format2String(var1);
            Log.d("APMLogger", var0 + ":" + var2);
        }

    }

    public static void e(String var0, Object... var1) {
        String var2 = format2String(var1);
        Log.e("APMLogger", var0 + ":" + var2);
    }

    public static void w(String var0, Object... var1) {
        if (isDebug) {
            String var2 = format2String(var1);
            Log.w("APMLogger", var0 + ":" + var2);
        }

    }

    public static void throwException(Throwable throwable) {
        if (isDebug) {
            throwable.printStackTrace();
        }
    }

    private static String format2String(Object... var0) {
        if (var0 != null) {
            StringBuilder var1 = new StringBuilder();
            Object[] var2 = var0;
            int var3 = var0.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object var5 = var2[var4];
                if (var5 != null) {
                    var1.append("->").append(var5.toString());
                }
            }

            return var1.toString();
        } else {
            return "";
        }
    }
}
