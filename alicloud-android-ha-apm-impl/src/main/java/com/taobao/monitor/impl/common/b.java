package com.taobao.monitor.impl.common;

import android.app.ActivityManager;
import android.os.Build.VERSION;
import android.util.Log;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.impl.util.c;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* compiled from: ActivityManagerHook */
public class b {
    public static void start() {
        Object a;
        Log.d("ActivityManagerHook", "start Hook IActivityManager...");
        try {
            Class cls = Class.forName("android.app.ActivityManagerNative");
            if (VERSION.SDK_INT >= 26) {
                a = c.a(null, ActivityManager.class.getDeclaredField("IActivityManagerSingleton"));
            } else {
                a = c.a(null, cls.getDeclaredField("gDefault"));
            }
            Class cls2 = Class.forName("android.util.Singleton");
            try {
                Method declaredMethod = cls2.getDeclaredMethod("get", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(a, new Object[0]);
            } catch (Exception e) {
            }
            Field declaredField = cls2.getDeclaredField("mInstance");
            Object a2 = c.a(a, declaredField);
            if (a2 != null) {
                Class cls3 = Class.forName("android.app.IActivityManager");
                c.a(a, declaredField, a2, Proxy.newProxyInstance(b.class.getClassLoader(), new Class[]{cls3}, new c(a2)));
                Logger.d("ActivityManagerHook", "Hook IActivityManager success");
            }
        } catch (Exception e2) {
            Logger.d("ActivityManagerHook", "Hook IActivityManager failed");
        }
    }
}
