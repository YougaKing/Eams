package com.taobao.monitor.impl.processor.fragmentload;

import android.support.v4.app.Fragment;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import java.util.HashMap;
import java.util.Map;

/* compiled from: FragmentModelLifecycle */
public class a implements com.taobao.monitor.impl.trace.l.a {
    private Fragment a;

    /* renamed from: a reason: collision with other field name */
    private final IProcessorFactory<d> f48a = new e();

    /* renamed from: a reason: collision with other field name */
    private Map<Fragment, b> f49a = new HashMap();
    private final IProcessorFactory<b> b = new c();
    private int k = 0;
    private Map<Fragment, C0002a> map = new HashMap();

    /* renamed from: com.taobao.monitor.impl.processor.fragmentload.a$a reason: collision with other inner class name */
    /* compiled from: FragmentModelLifecycle */
    public interface C0002a {
        void a(Fragment fragment, long j);

        void b(Fragment fragment, long j);

        void c(Fragment fragment, long j);

        void d(Fragment fragment, long j);

        void e(Fragment fragment, long j);

        void f(Fragment fragment, long j);

        void g(Fragment fragment, long j);

        void h(Fragment fragment, long j);

        void i(Fragment fragment, long j);

        void j(Fragment fragment, long j);

        void k(Fragment fragment, long j);

        void l(Fragment fragment, long j);

        void m(Fragment fragment, long j);

        void n(Fragment fragment, long j);
    }

    /* compiled from: FragmentModelLifecycle */
    public interface b {
        void g(Fragment fragment);

        void j(Fragment fragment);
    }

    public void a(Fragment fragment, long j) {
        GlobalStats.activityStatusManager.b(fragment.getClass().getName());
        C0002a aVar = (C0002a) this.f48a.createProcessor();
        if (aVar != null) {
            this.map.put(fragment, aVar);
            aVar.a(fragment, j);
            this.a = fragment;
        }
    }

    public void b(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.b(fragment, j);
        }
    }

    public void c(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.c(fragment, j);
        }
    }

    public void d(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.d(fragment, j);
        }
    }

    public void e(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.e(fragment, j);
        }
    }

    public void f(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.f(fragment, j);
        }
    }

    public void g(Fragment fragment, long j) {
        this.k++;
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.g(fragment, j);
        }
        if (this.a != fragment && FragmentInterceptorProxy.INSTANCE.needPopFragment(fragment)) {
            b bVar = (b) this.b.createProcessor();
            if (bVar != null) {
                bVar.g(fragment);
                this.f49a.put(fragment, bVar);
            }
        }
        this.a = fragment;
    }

    public void h(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.h(fragment, j);
        }
    }

    public void i(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.i(fragment, j);
        }
    }

    public void j(Fragment fragment, long j) {
        this.k--;
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.j(fragment, j);
        }
        b bVar = (b) this.f49a.get(fragment);
        if (bVar != null) {
            bVar.j(fragment);
            this.f49a.remove(fragment);
        }
        if (this.k == 0) {
            this.a = null;
        }
    }

    public void k(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.k(fragment, j);
        }
    }

    public void l(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.l(fragment, j);
        }
    }

    public void m(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.m(fragment, j);
        }
    }

    public void n(Fragment fragment, long j) {
        C0002a aVar = (C0002a) this.map.get(fragment);
        if (aVar != null) {
            aVar.n(fragment, j);
        }
        this.map.remove(fragment);
    }
}
