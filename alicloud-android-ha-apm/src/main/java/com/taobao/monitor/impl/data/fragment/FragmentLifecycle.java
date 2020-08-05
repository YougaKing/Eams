package com.taobao.monitor.impl.data.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.FragmentFunctionDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;

import java.util.HashMap;
import java.util.Map;

/* compiled from: FragmentLifecycle */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {
    private static FragmentFunctionDispatcher fragmentFunctionDispatcher = FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER;
    private final Activity mActivity;
    protected Map<Fragment, LifecycleListener> map = new HashMap();

    /* compiled from: FragmentLifecycle */
    interface LifecycleListener {
        void onFragmentPreAttached(Fragment fragment);

        void onFragmentAttached(Fragment fragment);

        void onFragmentPreCreated(Fragment fragment);

        void onFragmentCreated(Fragment fragment);

        void onFragmentActivityCreated(Fragment fragment);

        void onFragmentViewCreated(Fragment fragment);

        void onFragmentStarted(Fragment fragment);

        void onFragmentResumed(Fragment fragment);

        void onFragmentPaused(Fragment fragment);

        void onFragmentStopped(Fragment fragment);

        void onFragmentSaveInstanceState(Fragment fragment);

        void onFragmentViewDestroyed(Fragment fragment);

        void onFragmentDestroyed(Fragment fragment);

        void onFragmentDetached(Fragment fragment);
    }

    public FragmentLifecycle(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onFragmentPreAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        FragmentLifecycle.super.onFragmentPreAttached(fragmentManager, fragment, context);
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentPreAttached", fragment.getClass().getSimpleName());
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentPreAttached", TimeUtils.currentTimeMillis());
        LifecycleListener aVar = this.map.get(fragment);
        if (aVar == null) {
            aVar = new FragmentDataCollector(this.mActivity, fragment);
            this.map.put(fragment, aVar);
        }
        aVar.onFragmentPreAttached(fragment);
    }

    @Override
    public void onFragmentAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        FragmentLifecycle.super.onFragmentAttached(fragmentManager, fragment, context);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentAttached", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentAttached", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentAttached(fragment);
        }
    }

    @Override
    public void onFragmentPreCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        FragmentLifecycle.super.onFragmentPreCreated(fragmentManager, fragment, bundle);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentPreCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentPreCreated", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentPreCreated(fragment);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        FragmentLifecycle.super.onFragmentCreated(fragmentManager, fragment, bundle);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentCreated", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentCreated(fragment);
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        FragmentLifecycle.super.onFragmentActivityCreated(fragmentManager, fragment, bundle);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentActivityCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentActivityCreated", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentActivityCreated(fragment);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
        FragmentLifecycle.super.onFragmentViewCreated(fragmentManager, fragment, view, bundle);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentViewCreated", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentViewCreated", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentViewCreated(fragment);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment) {
        FragmentLifecycle.super.onFragmentStarted(fragmentManager, fragment);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentStarted", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentStarted", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentStarted(fragment);
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
        FragmentLifecycle.super.onFragmentResumed(fragmentManager, fragment);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentResumed", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentResumed", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentResumed(fragment);
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment) {
        FragmentLifecycle.super.onFragmentPaused(fragmentManager, fragment);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentPaused", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentPaused", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentPaused(fragment);
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment) {
        FragmentLifecycle.super.onFragmentStopped(fragmentManager, fragment);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentStopped", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentStopped", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentStopped(fragment);
        }
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        FragmentLifecycle.super.onFragmentSaveInstanceState(fragmentManager, fragment, bundle);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentSaveInstanceState", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentSaveInstanceState", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentSaveInstanceState(fragment);
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        FragmentLifecycle.super.onFragmentViewDestroyed(fragmentManager, fragment);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentViewDestroyed", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentViewDestroyed", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentViewDestroyed(fragment);
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        FragmentLifecycle.super.onFragmentDestroyed(fragmentManager, fragment);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentDestroyed", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentDestroyed", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentDestroyed(fragment);
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
        FragmentLifecycle.super.onFragmentDetached(fragmentManager, fragment);
        fragmentFunctionDispatcher.a(fragment.getActivity(), fragment, "onFragmentDetached", TimeUtils.currentTimeMillis());
        DataLoggerUtils.log("FragmentLifecycle", "onFragmentDetached", fragment.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentDetached(fragment);
        }
        this.map.remove(fragment);
    }
}
