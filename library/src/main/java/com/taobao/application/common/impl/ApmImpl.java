package com.taobao.application.common.impl;

import android.annotation.TargetApi;
import android.app.Activity;
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
    private final ICallbackGroup<ActivityLifecycleCallbacks> mainApplicationCallbackGroup;
    private final ICallbackGroup<ActivityLifecycleCallbacks> applicationCallbackGroup;
    private final IListenerGroup<IPageListener> pageListenerGroup;
    private final IListenerGroup<IAppLaunchListener> appLaunchListenerGroup;
    private final IListenerGroup<IApmEventListener> apmEventListenerGroup;
    private final Handler handler;
    private volatile Activity topActivity;
    private ConcurrentHashMap<ActivityLifecycleCallbacks, Boolean> map;

    private ApmImpl() {
        this.mainApplicationCallbackGroup = new MainApplicationCallbackGroup();
        this.applicationCallbackGroup = new ApplicationCallbackGroup();
        this.pageListenerGroup = new PageListenerGroup();
        this.appLaunchListenerGroup = new AppLaunchListenerGroup();
        this.apmEventListenerGroup = new ApmEventListenerGroup();
        this.map = new ConcurrentHashMap<>();
        HandlerThread var1 = new HandlerThread("Apm-Sec");
        var1.start();
        this.handler = new Handler(var1.getLooper());
        Logger.e("ApmImpl", "init");
    }

    public static ApmImpl instance() {
        return ApmImplHolder.APM;
    }

    @TargetApi(14)
    public void addActivityLifecycle(ActivityLifecycleCallbacks var1, boolean var2) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            Boolean var3 = this.map.put(var1, var2);
            if (var3 != null) {
                throw new IllegalArgumentException();
            } else {
                if (var2) {
                    this.mainApplicationCallbackGroup.b(var1);
                } else {
                    this.applicationCallbackGroup.b(var1);
                }

            }
        }
    }

    public void removeActivityLifecycle(ActivityLifecycleCallbacks var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            boolean var2;
            Boolean var3 = this.map.get(var1);
            if (var3 != null) {
                var2 = var3;
                this.map.remove(var1);
                if (var2) {
                    this.mainApplicationCallbackGroup.a(var1);
                } else {
                    this.applicationCallbackGroup.a(var1);
                }

            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void addPageListener(IPageListener var1) {
        this.pageListenerGroup.addListener(var1);
    }

    public void removePageListener(IPageListener var1) {
        this.pageListenerGroup.removeListener(var1);
    }

    public void addAppLaunchListener(IAppLaunchListener var1) {
        this.appLaunchListenerGroup.addListener(var1);
    }

    public void removeAppLaunchListener(IAppLaunchListener var1) {
        this.appLaunchListenerGroup.removeListener(var1);
    }

    public void addApmEventListener(IApmEventListener var1) {
        this.apmEventListenerGroup.addListener(var1);
    }

    public void removeApmEventListener(IApmEventListener var1) {
        this.apmEventListenerGroup.removeListener(var1);
    }

    public IAppPreferences getAppPreferences() {
        return AppPreferencesImpl.instance();
    }

    public Activity getTopActivity() {
        return this.topActivity;
    }

    public Looper getAsyncLooper() {
        return this.handler.getLooper();
    }

    @Override
    public Handler getAsyncHandler() {
        return null;
    }

    @TargetApi(14)
    public ActivityLifecycleCallbacks mainApplicationLifecycleCallback() {
        return (ActivityLifecycleCallbacks) this.castT(this.mainApplicationCallbackGroup);
    }

    @TargetApi(14)
    public ActivityLifecycleCallbacks applicationLifecycleCallback() {
        return (ActivityLifecycleCallbacks) this.castT(this.applicationCallbackGroup);
    }

    public IPageListener pageListener() {
        return (IPageListener) this.castT(this.mainApplicationCallbackGroup);
    }

    public IAppLaunchListener appLaunchListener() {
        return (IAppLaunchListener) this.castT(this.appLaunchListenerGroup);
    }

    public IApmEventListener apmEventListener() {
        return (IApmEventListener) this.castT(this.apmEventListenerGroup);
    }

    public void a(Activity var1) {
        this.topActivity = var1;
    }

    public void b(Runnable var1) {
        this.handler.post(var1);
    }

    private <T> T castT(Object var1) {
        return (T) var1;
    }

    private static class ApmImplHolder {
        static final ApmImpl APM = new ApmImpl();
    }
}
