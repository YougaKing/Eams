package com.taobao.monitor.log;

import android.util.Log;

public class Logger {

    private static boolean isDebug = false;

    public static void i(String var0, Object... var1) {
        if (isDebug) {
            String var2 = a(var1);
            Log.i("APMLogger", var0 + ":" + var2);
        }

    }

    public static void throwException(Throwable var0) {
        if (isDebug) {
        }

    }

    private static String a(Object... var0) {
        if (var0 != null) {
            StringBuilder var1 = new StringBuilder();
            Object[] var2 = var0;
            int var3 = var0.length;

            for(int var4 = 0; var4 < var3; ++var4) {
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
