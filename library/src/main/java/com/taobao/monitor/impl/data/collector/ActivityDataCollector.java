package com.taobao.monitor.impl.data.collector;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.taobao.monitor.impl.data.AbstractDataCollector;
import com.taobao.monitor.impl.processor.launcher.PageList;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;

import java.lang.reflect.Proxy;

public class ActivityDataCollector extends AbstractDataCollector<Activity> implements com.taobao.monitor.impl.data.activity.b.a, com.taobao.monitor.impl.data.activity.d.a {
    private final Activity c;
    private c a = null;
    private b a = null;
    private d a;
    private boolean a = false;

    a(Activity var1) {
        super(var1);
        this.c = var1;
        if (Build.VERSION.SDK_INT >= 16) {
            this.a = new d();
        }

        this.initDispatcher();
    }

    protected void initDispatcher() {
        super.initDispatcher();
        IDispatcher var1 = com.taobao.monitor.impl.common.a.a("ACTIVITY_LIFECYCLE_DISPATCHER");
        if (var1 instanceof c) {
            this.a = (c) var1;
        }

        var1 = com.taobao.monitor.impl.common.a.a("ACTIVITY_EVENT_DISPATCHER");
        if (var1 instanceof b) {
            this.a = (b) var1;
        }

    }

    public void onActivityCreated(Activity var1, Bundle var2) {
        this.initDispatcher();
        if (!g.a(this.a)) {
            this.a.a(var1, var2, TimeUtils.currentTimeMillis());
        }

    }

    public void onActivityStarted(Activity var1) {
        if (!g.a(this.a)) {
            this.a.a(var1, TimeUtils.currentTimeMillis());
        }

    }

    public void onActivityResumed(Activity var1) {
        if (!g.a(this.a)) {
            this.a.b(var1, TimeUtils.currentTimeMillis());
        }

        Window var2 = var1.getWindow();
        if (var2 != null) {
            View var3 = var2.getDecorView();
            if (var3 != null) {
                if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(var1))) {
                    this.a((View) var3);
                }

                if (!this.a) {
                    Window.Callback var4 = var2.getCallback();
                    if (var4 != null) {
                        try {
                            Window.Callback var5 = (Window.Callback) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Window.Callback.class}, new com.taobao.monitor.impl.data.activity.d(var4, this));
                            var2.setCallback(var5);
                        } catch (Exception var6) {
                            var6.printStackTrace();
                        }
                    }

                    this.a = true;
                }

                if (Build.VERSION.SDK_INT >= 16) {
                    ViewTreeObserver var7 = var3.getViewTreeObserver();
                    var7.addOnDrawListener(this.a);
                }

            }
        }
    }

    public void onActivityPaused(Activity var1) {
        if (!g.a(this.a)) {
            this.a.c(var1, TimeUtils.currentTimeMillis());
        }

        if (Build.VERSION.SDK_INT >= 16) {
            View var2 = var1.getWindow().getDecorView();
            ViewTreeObserver var3 = var2.getViewTreeObserver();
            var3.removeOnDrawListener(this.a);
        }

    }

    public void onActivityStopped(Activity var1) {
        if (!g.a(this.a)) {
            if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(var1))) {
                this.e();
            }

            this.a.d(var1, TimeUtils.currentTimeMillis());
        }

        if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(var1))) {
            this.b();
        }

    }

    public void onActivityDestroyed(Activity var1) {
        if (!g.a(this.a)) {
            this.a.e(var1, TimeUtils.currentTimeMillis());
        }

    }

    public void a(MotionEvent var1) {
        if (!g.a(this.a)) {
            this.a.a(this.c, var1, TimeUtils.currentTimeMillis());
        }

        this.a(3, TimeUtils.currentTimeMillis());
        int var2 = var1.getAction();
        if (var2 == 2 && Build.VERSION.SDK_INT >= 16) {
            this.a.f();
        }

    }

    public void a(KeyEvent var1) {
        if (!g.a(this.a)) {
            this.a.a(this.c, var1, TimeUtils.currentTimeMillis());
        }

    }
}
