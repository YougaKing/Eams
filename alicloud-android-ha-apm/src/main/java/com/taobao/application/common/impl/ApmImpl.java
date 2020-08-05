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
    private volatile Activity a;

    /* renamed from: a reason: collision with other field name */
    private final Handler f3a;

    /* renamed from: a reason: collision with other field name */
    private final e<ActivityLifecycleCallbacks> f4a;

    /* renamed from: a reason: collision with other field name */
    private final f<IPageListener> f5a;

    /* renamed from: a reason: collision with other field name */
    private ConcurrentHashMap<ActivityLifecycleCallbacks, Boolean> f6a;
    private final e<ActivityLifecycleCallbacks> b;

    /* renamed from: b reason: collision with other field name */
    private final f<IAppLaunchListener> f7b;
    private final f<IApmEventListener> c;

    /* compiled from: ApmImpl */
    private static class ApmImplHolder {
        static final ApmImpl APM = new ApmImpl();
    }

    private ApmImpl() {
        this.f4a = new g();
        this.b = new d();
        this.f5a = new h();
        this.f7b = new c();
        this.c = new a();
        this.f6a = new ConcurrentHashMap<>();
        HandlerThread handlerThread = new HandlerThread("Apm-Sec");
        handlerThread.start();
        this.f3a = new Handler(handlerThread.getLooper());
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
            this.f4a.b(activityLifecycleCallbacks);
        } else {
            this.b.b(activityLifecycleCallbacks);
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
                this.f4a.a(activityLifecycleCallbacks);
            } else {
                this.b.a(activityLifecycleCallbacks);
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
        this.f7b.addListener(iAppLaunchListener);
    }

    public void removeAppLaunchListener(IAppLaunchListener iAppLaunchListener) {
        this.f7b.removeListener(iAppLaunchListener);
    }

    public void addApmEventListener(IApmEventListener iApmEventListener) {
        this.c.addListener(iApmEventListener);
    }

    public void removeApmEventListener(IApmEventListener iApmEventListener) {
        this.c.removeListener(iApmEventListener);
    }

    public IAppPreferences getAppPreferences() {
        return AppPreferencesImpl.instance();
    }

    public Activity getTopActivity() {
        return this.a;
    }

    public Looper getAsyncLooper() {
        return this.f3a.getLooper();
    }

    /* renamed from: a reason: collision with other method in class */
    public Handler getAsyncHandler() {
        return this.f3a;
    }

    @TargetApi(14)
    /* renamed from: a reason: collision with other method in class */
    public ActivityLifecycleCallbacks m0a() {
        return (ActivityLifecycleCallbacks) a((Object) this.f4a);
    }

    @TargetApi(14)
    public ActivityLifecycleCallbacks b() {
        return (ActivityLifecycleCallbacks) a((Object) this.b);
    }

    /* renamed from: a reason: collision with other method in class */
    public IPageListener m4a() {
        return (IPageListener) a((Object) this.f5a);
    }

    /* renamed from: a reason: collision with other method in class */
    public IAppLaunchListener m3a() {
        return (IAppLaunchListener) a((Object) this.f7b);
    }

    /* renamed from: a reason: collision with other method in class */
    public IApmEventListener m2a() {
        return (IApmEventListener) a((Object) this.c);
    }

    public void a(Activity activity) {
        this.a = activity;
    }

    public void b(Runnable runnable) {
        this.f3a.post(runnable);
    }

    private <T> T a(Object obj) {
        return (T) obj;
    }
}
