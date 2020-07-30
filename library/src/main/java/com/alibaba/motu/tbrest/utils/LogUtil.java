//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.utils;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "RestApi";

    public LogUtil() {
    }

    public static void d(String message) {
        Log.d("RestApi", message);
    }

    public static void w(String message, Throwable e) {
        Log.w("RestApi", message, e);
    }

    public static void i(String message) {
        Log.i("RestApi", message);
    }

    public static void e(String message) {
        Log.e("RestApi", message);
    }

    public static void e(String message, Throwable e) {
        Log.e("RestApi", message, e);
    }
}
