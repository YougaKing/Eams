package com.taobao.monitor.impl.processor.fragmentload;

import androidx.fragment.app.Fragment;

import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.trace.FragmentLifecycleDispatcher;

import java.util.HashMap;
import java.util.Map;


public class FragmentModelLifecycle implements FragmentLifecycleDispatcher.LifecycleListener {
    private Map<Fragment, ModelLifecycleListener> map = new HashMap<>();
    private Map<Fragment, com.taobao.monitor.impl.processor.fragmentload.a.b> a = new HashMap<>();
    private Fragment fragment;
    private int k = 0;
    private final IProcessorFactory<d> a = new e();
    private final IProcessorFactory<com.taobao.monitor.impl.processor.fragmentload.b> b = new c();

    public FragmentModelLifecycle() {
    }

    public void a(Fragment var1, long var2) {
        GlobalStats.activityStatusManager.b(var1.getClass().getName());
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.fragment.createProcessor();
        if (var4 != null) {
            this.map.put(var1, var4);
            var4.a(var1, var2);
            this.fragment = var1;
        }

    }

    public void b(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.b(var1, var2);
        }

    }

    public void c(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.c(var1, var2);
        }

    }

    public void d(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.d(var1, var2);
        }

    }

    public void e(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.e(var1, var2);
        }

    }

    public void f(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.f(var1, var2);
        }

    }

    public void g(Fragment var1, long var2) {
        ++this.k;
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.g(var1, var2);
        }

        if (this.fragment != var1 && FragmentInterceptorProxy.INSTANCE.needPopFragment(var1)) {
            com.taobao.monitor.impl.processor.fragmentload.a.b var5 = (com.taobao.monitor.impl.processor.fragmentload.a.b) this.b.createProcessor();
            if (var5 != null) {
                var5.g(var1);
                this.fragment.put(var1, var5);
            }
        }

        this.fragment = var1;
    }

    public void h(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.h(var1, var2);
        }

    }

    public void i(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.i(var1, var2);
        }

    }

    public void j(Fragment var1, long var2) {
        --this.k;
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.j(var1, var2);
        }

        com.taobao.monitor.impl.processor.fragmentload.a.b var5 = (com.taobao.monitor.impl.processor.fragmentload.a.b) this.fragment.get(var1);
        if (var5 != null) {
            var5.j(var1);
            this.fragment.remove(var1);
        }

        if (this.k == 0) {
            this.fragment = null;
        }

    }

    public void k(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.k(var1, var2);
        }

    }

    public void l(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.l(var1, var2);
        }

    }

    public void m(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.m(var1, var2);
        }

    }

    public void n(Fragment var1, long var2) {
        com.taobao.monitor.impl.processor.fragmentload.a.a var4 = (com.taobao.monitor.impl.processor.fragmentload.a.a) this.map.get(var1);
        if (var4 != null) {
            var4.n(var1, var2);
        }

        this.map.remove(var1);
    }

    public interface b {
        void g(Fragment var1);

        void j(Fragment var1);
    }

    public interface ModelLifecycleListener {
        void a(Fragment var1, long var2);

        void b(Fragment var1, long var2);

        void c(Fragment var1, long var2);

        void d(Fragment var1, long var2);

        void e(Fragment var1, long var2);

        void f(Fragment var1, long var2);

        void g(Fragment var1, long var2);

        void h(Fragment var1, long var2);

        void i(Fragment var1, long var2);

        void j(Fragment var1, long var2);

        void k(Fragment var1, long var2);

        void l(Fragment var1, long var2);

        void m(Fragment var1, long var2);

        void n(Fragment var1, long var2);
    }
}
