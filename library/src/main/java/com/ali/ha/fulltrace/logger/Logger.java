//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.logger;

import android.util.Log;

public class Logger {
    private static final String TAG = "FTLogger";
    private static boolean isDebug = true;
    private static final boolean THROW_EXCEPTION = false;

    private Logger() {
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        Logger.isDebug = isDebug;
    }

    public static void i(String tag, Object... msg) {
        if (isDebug) {
            String s = format2String(msg);
            Log.i("FTLogger", tag + ":" + s);
        }

    }

    public static void d(String tag, Object... msg) {
        if (isDebug) {
            String s = format2String(msg);
            Log.d("FTLogger", tag + ":" + s);
        }

    }

    public static void e(String tag, Object... msg) {
        if (isDebug) {
            String s = format2String(msg);
            Log.e("FTLogger", tag + ":" + s);
        }

    }

    public static void w(String tag, Object... msg) {
        if (isDebug) {
            String s = format2String(msg);
            Log.w("FTLogger", tag + ":" + s);
        }

    }

    public static void throwException(Throwable e) {
        if (isDebug) {
        }

    }

    private static String format2String(Object... objects) {
        if (objects != null) {
            StringBuilder builder = new StringBuilder();
            Object[] var2 = objects;
            int var3 = objects.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object o = var2[var4];
                if (o != null) {
                    builder.append("->").append(o.toString());
                }
            }

            return builder.toString();
        } else {
            return "";
        }
    }
}
