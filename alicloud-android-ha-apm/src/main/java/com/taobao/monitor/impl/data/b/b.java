package com.taobao.monitor.impl.data.b;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.view.View;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.j;
import com.taobao.monitor.impl.util.TimeUtils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: FragmentLifecycle */
public class b extends FragmentLifecycleCallbacks {
    private static j a = j.b;
    private final Activity c;
    protected Map<Fragment, a> map = new HashMap();

    /* compiled from: FragmentLifecycle */
    interface a {
        void a(Fragment fragment);

        void b(Fragment fragment);

        void c(Fragment fragment);

        void d(Fragment fragment);

        void e(Fragment fragment);

        void f(Fragment fragment);

        void g(Fragment fragment);

        void h(Fragment fragment);

        void i(Fragment fragment);

        void j(Fragment fragment);

        void k(Fragment fragment);

        void l(Fragment fragment);

        void m(Fragment fragment);

        void n(Fragment fragment);
    }

    public b(Activity activity) {
        this.c = activity;
    }

    public void onFragmentPreAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        b.super.onFragmentPreAttached(fragmentManager, fragment, context);
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentPreAttached", fragment.getClass().getSimpleName());
        a.a(fragment.getActivity(), fragment, "onFragmentPreAttached", TimeUtils.currentTimeMillis());
        a aVar = (a) this.map.get(fragment);
        if (aVar == null) {
            aVar = new a(this.c, fragment);
            this.map.put(fragment, aVar);
        }
        aVar.a(fragment);
    }

    public void onFragmentAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        b.super.onFragmentAttached(fragmentManager, fragment, context);
        a.a(fragment.getActivity(), fragment, "onFragmentAttached", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentAttached", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.b(fragment);
        }
    }

    public void onFragmentPreCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        b.super.onFragmentPreCreated(fragmentManager, fragment, bundle);
        a.a(fragment.getActivity(), fragment, "onFragmentPreCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentPreCreated", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.c(fragment);
        }
    }

    public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        b.super.onFragmentCreated(fragmentManager, fragment, bundle);
        a.a(fragment.getActivity(), fragment, "onFragmentCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentCreated", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.d(fragment);
        }
    }

    public void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        b.super.onFragmentActivityCreated(fragmentManager, fragment, bundle);
        a.a(fragment.getActivity(), fragment, "onFragmentActivityCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentActivityCreated", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.e(fragment);
        }
    }

    public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
        b.super.onFragmentViewCreated(fragmentManager, fragment, view, bundle);
        a.a(fragment.getActivity(), fragment, "onFragmentViewCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentViewCreated", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.f(fragment);
        }
    }

    public void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment) {
        b.super.onFragmentStarted(fragmentManager, fragment);
        a.a(fragment.getActivity(), fragment, "onFragmentStarted", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentStarted", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.g(fragment);
        }
    }

    public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
        b.super.onFragmentResumed(fragmentManager, fragment);
        a.a(fragment.getActivity(), fragment, "onFragmentResumed", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentResumed", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.h(fragment);
        }
    }

    public void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment) {
        b.super.onFragmentPaused(fragmentManager, fragment);
        a.a(fragment.getActivity(), fragment, "onFragmentPaused", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentPaused", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.i(fragment);
        }
    }

    public void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment) {
        b.super.onFragmentStopped(fragmentManager, fragment);
        a.a(fragment.getActivity(), fragment, "onFragmentStopped", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentStopped", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.j(fragment);
        }
    }

    public void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        b.super.onFragmentSaveInstanceState(fragmentManager, fragment, bundle);
        a.a(fragment.getActivity(), fragment, "onFragmentSaveInstanceState", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentSaveInstanceState", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.k(fragment);
        }
    }

    public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        b.super.onFragmentViewDestroyed(fragmentManager, fragment);
        a.a(fragment.getActivity(), fragment, "onFragmentViewDestroyed", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentViewDestroyed", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.l(fragment);
        }
    }

    public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        b.super.onFragmentDestroyed(fragmentManager, fragment);
        a.a(fragment.getActivity(), fragment, "onFragmentDestroyed", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentDestroyed", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.m(fragment);
        }
    }

    public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
        b.super.onFragmentDetached(fragmentManager, fragment);
        a.a(fragment.getActivity(), fragment, "onFragmentDetached", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentDetached", fragment.getClass().getSimpleName());
        a aVar = (a) this.map.get(fragment);
        if (aVar != null) {
            aVar.n(fragment);
        }
        this.map.remove(fragment);
    }
}
