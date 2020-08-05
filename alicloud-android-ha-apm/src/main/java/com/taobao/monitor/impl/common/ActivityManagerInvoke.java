package com.taobao.monitor.impl.common;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Build.VERSION;
import android.util.Log;

import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.impl.util.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* compiled from: ActivityManagerHook */
public class ActivityManagerInvoke {
    public static void start() {
        Object a;
        Log.d("ActivityManagerHook", "start Hook IActivityManager...");
        try {
            @SuppressLint("PrivateApi") Class cls = Class.forName("android.app.ActivityManagerNative");
            if (VERSION.SDK_INT >= 26) {
                a = FieldUtils.setAccessible(null, ActivityManager.class.getDeclaredField("IActivityManagerSingleton"));
            } else {
                a = FieldUtils.setAccessible(null, cls.getDeclaredField("gDefault"));
            }
            @SuppressLint("PrivateApi") Class cls2 = Class.forName("android.util.Singleton");
            try {
                Method declaredMethod = cls2.getDeclaredMethod("get");
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(a);
            } catch (Exception e) {
            }
            Field declaredField = cls2.getDeclaredField("mInstance");
            Object a2 = FieldUtils.setAccessible(a, declaredField);
            if (a2 != null) {
                @SuppressLint("PrivateApi") Class cls3 = Class.forName("android.app.IActivityManager");
                FieldUtils.setValue(a, declaredField, a2, Proxy.newProxyInstance(ActivityManagerInvoke.class.getClassLoader(), new Class[]{cls3}, new ActivityManagerHook(a2)));
                Logger.d("ActivityManagerHook", "Hook IActivityManager success");
            }
        } catch (Exception e2) {
            Logger.d("ActivityManagerHook", "Hook IActivityManager failed");
        }
    }
}
