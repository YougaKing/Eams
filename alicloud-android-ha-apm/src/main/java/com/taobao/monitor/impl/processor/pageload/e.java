package com.taobao.monitor.impl.processor.pageload;

import android.app.Activity;
import android.os.Bundle;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;

import java.util.HashMap;
import java.util.Map;

/* compiled from: PageModelLifecycle */
public class e implements ActivityLifeCycleDispatcher.LifeCycleListener {
    private Activity a = null;

    /* renamed from: a reason: collision with other field name */
    private Map<Activity, b> f113a = new HashMap();
    private final IProcessorFactory<c> d = new d();
    private final IProcessorFactory<a> e = new b();
    private Map<Activity, a> map = new HashMap();
    private int v = 0;

    /* compiled from: PageModelLifecycle */
    public interface a {
        void a(Activity activity, long j);

        void b(Activity activity, long j);

        void c(Activity activity, long j);

        void d(Activity activity, long j);

        void e(Activity activity, long j);
    }

    /* compiled from: PageModelLifecycle */
    public interface b {
        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);
    }

    public void onActivityCreated(Activity activity, Bundle bundle, long j) {
        c cVar = (c) this.d.createProcessor();
        if (cVar != null) {
            this.map.put(activity, cVar);
            cVar.a(activity, bundle, j);
        }
        this.a = activity;
    }

    public void onActivityStarted(Activity activity, long j) {
        this.v++;
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.a(activity, j);
        }
        if (this.a != activity) {
            b bVar = (b) this.e.createProcessor();
            if (bVar != null) {
                bVar.onActivityStarted(activity);
                this.f113a.put(activity, bVar);
            }
        }
        this.a = activity;
    }

    public void onActivityResumed(Activity activity, long j) {
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.b(activity, j);
        }
    }

    public void onActivityPaused(Activity activity, long j) {
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.c(activity, j);
        }
    }

    public void onActivityStopped(Activity activity, long j) {
        this.v--;
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.d(activity, j);
        }
        b bVar = (b) this.f113a.get(activity);
        if (bVar != null) {
            bVar.onActivityStopped(activity);
            this.f113a.remove(activity);
        }
        if (this.v == 0) {
            this.a = null;
        }
    }

    public void onActivityDestroyed(Activity activity, long j) {
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.e(activity, j);
        }
        this.map.remove(activity);
        if (activity == this.a) {
            this.a = null;
        }
    }
}
