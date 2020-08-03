package com.taobao.application.common.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.taobao.application.common.Apm;
import com.taobao.application.common.IApmEventListener;
import com.taobao.application.common.IAppLaunchListener;
import com.taobao.application.common.IAppPreferences;
import com.taobao.application.common.IApplicationMonitor;
import com.taobao.application.common.IPageListener;
import com.taobao.monitor.impl.logger.Logger;
import java.util.concurrent.ConcurrentHashMap;

public class ApmImpl implements Apm, IApplicationMonitor {
    private final ICallbackGroup<Application.ActivityLifecycleCallbacks> a;
    private final ICallbackGroup<Application.ActivityLifecycleCallbacks> b;
    private final f<IPageListener> a;
    private final f<IAppLaunchListener> b;
    private final f<IApmEventListener> c;
    private final Handler a;
    private volatile Activity a;
    private ConcurrentHashMap<Application.ActivityLifecycleCallbacks, Boolean> a;

    private ApmImpl() {
        this.a = new MainApplicationCallbackGroup();
        this.b = new ApplicationCallbackGroup();
        this.a = new h();
        this.b = new c();
        this.c = new com.taobao.application.common.impl.a();
        this.a = new ConcurrentHashMap();
        HandlerThread var1 = new HandlerThread("Apm-Sec");
        var1.start();
        this.a = new Handler(var1.getLooper());
        Logger.e("ApmImpl", new Object[]{"init"});
    }

    public static b a() {
        return b.a.a;
    }

    @TargetApi(14)
    public void addActivityLifecycle(Application.ActivityLifecycleCallbacks var1, boolean var2) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            Boolean var3 = (Boolean)this.a.put(var1, var2);
            if (var3 != null) {
                throw new IllegalArgumentException();
            } else {
                if (var2) {
                    this.a.b(var1);
                } else {
                    this.b.b(var1);
                }

            }
        }
    }

    public void removeActivityLifecycle(Application.ActivityLifecycleCallbacks var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            boolean var2 = false;
            Boolean var3 = (Boolean)this.a.get(var1);
            if (var3 != null) {
                var2 = var3;
                this.a.remove(var1);
                if (var2) {
                    this.a.a(var1);
                } else {
                    this.b.a(var1);
                }

            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void addPageListener(IPageListener var1) {
        this.a.addListener(var1);
    }

    public void removePageListener(IPageListener var1) {
        this.a.removeListener(var1);
    }

    public void addAppLaunchListener(IAppLaunchListener var1) {
        this.b.addListener(var1);
    }

    public void removeAppLaunchListener(IAppLaunchListener var1) {
        this.b.removeListener(var1);
    }

    public void addApmEventListener(IApmEventListener var1) {
        this.c.addListener(var1);
    }

    public void removeApmEventListener(IApmEventListener var1) {
        this.c.removeListener(var1);
    }

    public IAppPreferences getAppPreferences() {
        return AppPreferencesImpl.instance();
    }

    public Activity getTopActivity() {
        return this.a;
    }

    public Looper getAsyncLooper() {
        return this.a.getLooper();
    }

    public Handler a() {
        return this.a;
    }

    @TargetApi(14)
    public Application.ActivityLifecycleCallbacks a() {
        return (Application.ActivityLifecycleCallbacks)this.a((Object)this.a);
    }

    @TargetApi(14)
    public Application.ActivityLifecycleCallbacks b() {
        return (Application.ActivityLifecycleCallbacks)this.a((Object)this.b);
    }

    public IPageListener a() {
        return (IPageListener)this.a((Object)this.a);
    }

    public IAppLaunchListener a() {
        return (IAppLaunchListener)this.a((Object)this.b);
    }

    public IApmEventListener a() {
        return (IApmEventListener)this.a((Object)this.c);
    }

    public void a(Activity var1) {
        this.a = var1;
    }

    public void b(Runnable var1) {
        this.a.post(var1);
    }

    private <T> T a(Object var1) {
        return var1;
    }

    private static class a {
        static final b a = new b();
    }
}
