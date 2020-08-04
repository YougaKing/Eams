package com.taobao.monitor.impl.data.b;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.trace.l;
import com.taobao.monitor.impl.util.TimeUtils;

/* compiled from: FragmentDataCollector */
public class a extends com.taobao.monitor.impl.data.a<Fragment> implements a {
    private l a;
    private final Activity c;

    a(Activity activity, Fragment fragment) {
        super(fragment);
        this.c = activity;
        initDispatcher();
    }

    /* access modifiers changed from: protected */
    public void initDispatcher() {
        super.initDispatcher();
        IDispatcher a2 = com.taobao.monitor.impl.common.a.a("FRAGMENT_LIFECYCLE_DISPATCHER");
        if (a2 instanceof l) {
            this.a = (l) a2;
        }
    }

    public void a(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.p(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void b(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.q(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void c(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.r(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void d(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.s(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void e(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.t(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void f(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.u(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void g(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.v(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void h(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.w(fragment, TimeUtils.currentTimeMillis());
        }
        if (this.c != null) {
            Window window = this.c.getWindow();
            if (window != null) {
                View decorView = window.getDecorView();
                if (decorView != null) {
                    a(decorView);
                }
            }
        }
    }

    public void i(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.x(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void j(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.y(fragment, TimeUtils.currentTimeMillis());
        }
        b();
    }

    public void k(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.z(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void l(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.A(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void m(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.B(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void n(Fragment fragment) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.C(fragment, TimeUtils.currentTimeMillis());
        }
    }
}
