package com.taobao.monitor.impl.processor.pageload;


import android.app.Activity;
import android.os.Bundle;

import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;

import java.util.HashMap;
import java.util.Map;

public class PageModelLifecycle implements ActivityLifeCycleDispatcher.LifeCycleListener {
    private Map<Activity, e.a> map = new HashMap();
    private Map<Activity, e.b> a = new HashMap();
    private Activity a = null;
    private int v = 0;
    private final IProcessorFactory<c> d = new d();
    private final IProcessorFactory<com.taobao.monitor.impl.processor.pageload.a> e = new com.taobao.monitor.impl.processor.pageload.b();

    public e() {
    }

    public void a(Activity var1, Bundle var2, long var3) {
        c var5 = (c)this.d.createProcessor();
        if (var5 != null) {
            this.map.put(var1, var5);
            var5.a(var1, var2, var3);
        }

        this.a = var1;
    }

    public void a(Activity var1, long var2) {
        ++this.v;
        e.a var4 = (e.a)this.map.get(var1);
        if (var4 != null) {
            var4.a(var1, var2);
        }

        if (this.a != var1) {
            e.b var5 = (e.b)this.e.createProcessor();
            if (var5 != null) {
                var5.onActivityStarted(var1);
                this.a.put(var1, var5);
            }
        }

        this.a = var1;
    }

    public void b(Activity var1, long var2) {
        e.a var4 = (e.a)this.map.get(var1);
        if (var4 != null) {
            var4.b(var1, var2);
        }

    }

    public void c(Activity var1, long var2) {
        e.a var4 = (e.a)this.map.get(var1);
        if (var4 != null) {
            var4.c(var1, var2);
        }

    }

    public void d(Activity var1, long var2) {
        --this.v;
        e.a var4 = (e.a)this.map.get(var1);
        if (var4 != null) {
            var4.d(var1, var2);
        }

        e.b var5 = (e.b)this.a.get(var1);
        if (var5 != null) {
            var5.onActivityStopped(var1);
            this.a.remove(var1);
        }

        if (this.v == 0) {
            this.a = null;
        }

    }

    public void e(Activity var1, long var2) {
        e.a var4 = (e.a)this.map.get(var1);
        if (var4 != null) {
            var4.e(var1, var2);
        }

        this.map.remove(var1);
        if (var1 == this.a) {
            this.a = null;
        }

    }

    public interface b {
        void onActivityStarted(Activity var1);

        void onActivityStopped(Activity var1);
    }

    public interface a {
        void a(Activity var1, long var2);

        void b(Activity var1, long var2);

        void c(Activity var1, long var2);

        void d(Activity var1, long var2);

        void e(Activity var1, long var2);
    }
}
