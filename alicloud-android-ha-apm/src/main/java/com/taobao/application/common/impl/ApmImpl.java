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

/* compiled from: ApmImpl */
public class ApmImpl implements Apm, IApplicationMonitor {
    private volatile Activity mActivity;

    /* renamed from: a reason: collision with other field name */
    private final Handler mHandler;

    /* renamed from: a reason: collision with other field name */
    private final ICallbackGroup<ActivityLifecycleCallbacks> mMainApplicationCallbackGroup;

    /* renamed from: a reason: collision with other field name */
    private final IListenerGroup<IPageListener> f5a;

    /* renamed from: a reason: collision with other field name */
    private ConcurrentHashMap<ActivityLifecycleCallbacks, Boolean> f6a;
    private final ICallbackGroup<ActivityLifecycleCallbacks> mApplicationCallbackGroup;

    /* renamed from: b reason: collision with other field name */
    private final IListenerGroup<IAppLaunchListener> mAppLaunchListenerGroup;
    private final IListenerGroup<IApmEventListener> mApmEventListenerGroup;

    /* compiled from: ApmImpl */
    private static class ApmImplHolder {
        static final ApmImpl APM = new ApmImpl();
    }

    private ApmImpl() {
        this.mMainApplicationCallbackGroup = new MainApplicationCallbackGroup();
        this.mApplicationCallbackGroup = new ApplicationCallbackGroup();
        this.f5a = new PageListenerGroup();
        this.mAppLaunchListenerGroup = new AppLaunchListenerGroup();
        this.mApmEventListenerGroup = new ApmEventListenerGroup();
        this.f6a = new ConcurrentHashMap<>();
        HandlerThread handlerThread = new HandlerThread("Apm-Sec");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        Logger.e("ApmImpl", "init");
    }

    public static ApmImpl instance() {
        return ApmImplHolder.APM;
    }

    @TargetApi(14)
    public void addActivityLifecycle(ActivityLifecycleCallbacks activityLifecycleCallbacks, boolean z) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        } else if (((Boolean) this.f6a.put(activityLifecycleCallbacks, Boolean.valueOf(z))) != null) {
            throw new IllegalArgumentException();
        } else if (z) {
            this.mMainApplicationCallbackGroup.addCallback(activityLifecycleCallbacks);
        } else {
            this.mApplicationCallbackGroup.addCallback(activityLifecycleCallbacks);
        }
    }

    public void removeActivityLifecycle(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        }
        Boolean bool = (Boolean) this.f6a.get(activityLifecycleCallbacks);
        if (bool != null) {
            boolean booleanValue = bool.booleanValue();
            this.f6a.remove(activityLifecycleCallbacks);
            if (booleanValue) {
                this.mMainApplicationCallbackGroup.removeCallback(activityLifecycleCallbacks);
            } else {
                this.mApplicationCallbackGroup.removeCallback(activityLifecycleCallbacks);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addPageListener(IPageListener iPageListener) {
        this.f5a.addListener(iPageListener);
    }

    public void removePageListener(IPageListener iPageListener) {
        this.f5a.removeListener(iPageListener);
    }

    public void addAppLaunchListener(IAppLaunchListener iAppLaunchListener) {
        this.mAppLaunchListenerGroup.addListener(iAppLaunchListener);
    }

    public void removeAppLaunchListener(IAppLaunchListener iAppLaunchListener) {
        this.mAppLaunchListenerGroup.removeListener(iAppLaunchListener);
    }

    public void addApmEventListener(IApmEventListener iApmEventListener) {
        this.mApmEventListenerGroup.addListener(iApmEventListener);
    }

    public void removeApmEventListener(IApmEventListener iApmEventListener) {
        this.mApmEventListenerGroup.removeListener(iApmEventListener);
    }

    public IAppPreferences getAppPreferences() {
        return AppPreferencesImpl.instance();
    }

    public Activity getTopActivity() {
        return this.mActivity;
    }

    public Looper getAsyncLooper() {
        return this.mHandler.getLooper();
    }

    /* renamed from: a reason: collision with other method in class */
    public Handler getAsyncHandler() {
        return this.mHandler;
    }

    @TargetApi(14)
    /* renamed from: a reason: collision with other method in class */
    public ActivityLifecycleCallbacks mainApplicationLifecycleCallbacks() {
        return (ActivityLifecycleCallbacks) a((Object) this.mMainApplicationCallbackGroup);
    }

    @TargetApi(14)
    public ActivityLifecycleCallbacks applicationLifecycleCallbacks() {
        return (ActivityLifecycleCallbacks) a((Object) this.mApplicationCallbackGroup);
    }

    /* renamed from: a reason: collision with other method in class */
    public IPageListener m4a() {
        return (IPageListener) a((Object) this.f5a);
    }

    /* renamed from: a reason: collision with other method in class */
    public IAppLaunchListener appLaunchListener() {
        return (IAppLaunchListener) a((Object) this.mAppLaunchListenerGroup);
    }

    /* renamed from: a reason: collision with other method in class */
    public IApmEventListener apmEventListener() {
        return (IApmEventListener) a((Object) this.mApmEventListenerGroup);
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void postRunnable(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    private <T> T a(Object obj) {
        return (T) obj;
    }
}
