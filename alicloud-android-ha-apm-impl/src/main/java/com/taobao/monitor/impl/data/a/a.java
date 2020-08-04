package com.taobao.monitor.impl.data.a;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import com.taobao.monitor.impl.data.d;
import com.taobao.monitor.impl.processor.launcher.PageList;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.b;
import com.taobao.monitor.impl.trace.c;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.util.TimeUtils;
import java.lang.reflect.Proxy;

/* compiled from: ActivityDataCollector */
class a extends com.taobao.monitor.impl.data.a<Activity> implements a, com.taobao.monitor.impl.data.a.d.a {
    private d a;

    /* renamed from: a reason: collision with other field name */
    private b f27a = null;

    /* renamed from: a reason: collision with other field name */
    private c f28a = null;

    /* renamed from: a reason: collision with other field name */
    private boolean f29a = false;
    private final Activity c;

    a(Activity activity) {
        super(activity);
        this.c = activity;
        if (VERSION.SDK_INT >= 16) {
            this.a = new d();
        }
        initDispatcher();
    }

    /* access modifiers changed from: protected */
    public void initDispatcher() {
        super.initDispatcher();
        IDispatcher a2 = com.taobao.monitor.impl.common.a.a("ACTIVITY_LIFECYCLE_DISPATCHER");
        if (a2 instanceof c) {
            this.f28a = (c) a2;
        }
        IDispatcher a3 = com.taobao.monitor.impl.common.a.a("ACTIVITY_EVENT_DISPATCHER");
        if (a3 instanceof b) {
            this.f27a = (b) a3;
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        initDispatcher();
        if (!g.a((IDispatcher) this.f28a)) {
            this.f28a.a(activity, bundle, TimeUtils.currentTimeMillis());
        }
    }

    public void onActivityStarted(Activity activity) {
        if (!g.a((IDispatcher) this.f28a)) {
            this.f28a.a(activity, TimeUtils.currentTimeMillis());
        }
    }

    public void onActivityResumed(Activity activity) {
        if (!g.a((IDispatcher) this.f28a)) {
            this.f28a.b(activity, TimeUtils.currentTimeMillis());
        }
        Window window = activity.getWindow();
        if (window != null) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(activity))) {
                    a(decorView);
                }
                if (!this.f29a) {
                    Callback callback = window.getCallback();
                    if (callback != null) {
                        try {
                            window.setCallback((Callback) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Callback.class}, new d(callback, this)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    this.f29a = true;
                }
                if (VERSION.SDK_INT >= 16) {
                    decorView.getViewTreeObserver().addOnDrawListener(this.a);
                }
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        if (!g.a((IDispatcher) this.f28a)) {
            this.f28a.c(activity, TimeUtils.currentTimeMillis());
        }
        if (VERSION.SDK_INT >= 16) {
            activity.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener(this.a);
        }
    }

    public void onActivityStopped(Activity activity) {
        if (!g.a((IDispatcher) this.f28a)) {
            if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(activity))) {
                e();
            }
            this.f28a.d(activity, TimeUtils.currentTimeMillis());
        }
        if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(activity))) {
            b();
        }
    }

    public void onActivityDestroyed(Activity activity) {
        if (!g.a((IDispatcher) this.f28a)) {
            this.f28a.e(activity, TimeUtils.currentTimeMillis());
        }
    }

    public void a(MotionEvent motionEvent) {
        if (!g.a((IDispatcher) this.f27a)) {
            this.f27a.a(this.c, motionEvent, TimeUtils.currentTimeMillis());
        }
        a(3, TimeUtils.currentTimeMillis());
        if (motionEvent.getAction() == 2 && VERSION.SDK_INT >= 16) {
            this.a.f();
        }
    }

    public void a(KeyEvent keyEvent) {
        if (!g.a((IDispatcher) this.f27a)) {
            this.f27a.a(this.c, keyEvent, TimeUtils.currentTimeMillis());
        }
    }
}
