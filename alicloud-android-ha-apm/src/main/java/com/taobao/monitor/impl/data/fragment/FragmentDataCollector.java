package com.taobao.monitor.impl.data.fragment;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.data.AbstractDataCollector;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.FragmentLifecycleDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;

/* compiled from: FragmentDataCollector */
public class FragmentDataCollector extends AbstractDataCollector<Fragment> implements FragmentLifecycle.LifecycleListener {
    private FragmentLifecycleDispatcher mFragmentLifecycleDispatcher;
    private final Activity mActivity;

    FragmentDataCollector(Activity activity, Fragment fragment) {
        super(fragment);
        this.mActivity = activity;
        initDispatcher();
    }

    /* access modifiers changed from: protected */
    public void initDispatcher() {
        super.initDispatcher();
        IDispatcher a2 = APMContext.getDispatcher("FRAGMENT_LIFECYCLE_DISPATCHER");
        if (a2 instanceof FragmentLifecycleDispatcher) {
            this.mFragmentLifecycleDispatcher = (FragmentLifecycleDispatcher) a2;
        }
    }

    public void onFragmentPreAttached(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentPreAttached(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentAttached(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentAttached(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentPreCreated(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentPreCreated(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentCreated(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentCreated(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentActivityCreated(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentActivityCreated(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentViewCreated(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentViewCreated(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentStarted(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentStarted(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentResumed(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentResumed(fragment, TimeUtils.currentTimeMillis());
        }
        if (this.mActivity != null) {
            Window window = this.mActivity.getWindow();
            if (window != null) {
                View decorView = window.getDecorView();
                if (decorView != null) {
                    onResume(decorView);
                }
            }
        }
    }

    public void onFragmentPaused(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentPaused(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentStopped(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentStopped(fragment, TimeUtils.currentTimeMillis());
        }
        onStop();
    }

    public void onFragmentSaveInstanceState(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentSaveInstanceState(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentViewDestroyed(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentViewDestroyed(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentDestroyed(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentDestroyed(fragment, TimeUtils.currentTimeMillis());
        }
    }

    public void onFragmentDetached(Fragment fragment) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mFragmentLifecycleDispatcher)) {
            this.mFragmentLifecycleDispatcher.onFragmentDetached(fragment, TimeUtils.currentTimeMillis());
        }
    }
}
