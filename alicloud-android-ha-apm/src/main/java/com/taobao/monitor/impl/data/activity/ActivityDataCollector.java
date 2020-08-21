package com.taobao.monitor.impl.data.activity;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;

import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.data.AbstractDataCollector;
import com.taobao.monitor.impl.data.DrawTimeCollector;
import com.taobao.monitor.impl.processor.launcher.PageList;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;

import java.lang.reflect.Proxy;

import static android.view.MotionEvent.ACTION_MOVE;

/* compiled from: ActivityDataCollector */
class ActivityDataCollector extends AbstractDataCollector<Activity> implements ActivityLifecycle.LifecycleListener,
        WindowCallbackProxy.CallbackListener {
    private DrawTimeCollector mDrawTimeCollector;

    /* renamed from: a reason: collision with other field name */
    private ActivityEventDispatcher mActivityEventDispatcher = null;

    /* renamed from: a reason: collision with other field name */
    private ActivityLifeCycleDispatcher mActivityLifeCycleDispatcher = null;

    /* renamed from: a reason: collision with other field name */
    private boolean mResumed = false;
    private final Activity mActivity;

    ActivityDataCollector(Activity activity) {
        super(activity);
        this.mActivity = activity;
        if (VERSION.SDK_INT >= 16) {
            this.mDrawTimeCollector = new DrawTimeCollector();
        }
        initDispatcher();
    }

    /* access modifiers changed from: protected */
    public void initDispatcher() {
        super.initDispatcher();
        IDispatcher a2 = APMContext.getDispatcher("ACTIVITY_LIFECYCLE_DISPATCHER");
        if (a2 instanceof ActivityLifeCycleDispatcher) {
            this.mActivityLifeCycleDispatcher = (ActivityLifeCycleDispatcher) a2;
        }
        IDispatcher a3 = APMContext.getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        if (a3 instanceof ActivityEventDispatcher) {
            this.mActivityEventDispatcher = (ActivityEventDispatcher) a3;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        initDispatcher();
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityLifeCycleDispatcher)) {
            this.mActivityLifeCycleDispatcher.onActivityCreated(activity, bundle, TimeUtils.currentTimeMillis());
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityLifeCycleDispatcher)) {
            this.mActivityLifeCycleDispatcher.onActivityStarted(activity, TimeUtils.currentTimeMillis());
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityLifeCycleDispatcher)) {
            this.mActivityLifeCycleDispatcher.onActivityResumed(activity, TimeUtils.currentTimeMillis());
        }
        Window window = activity.getWindow();
        if (window != null) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                if (!PageList.inBlackList(ActivityUtils.getName(activity))) {
                    onResume(decorView);
                }
                if (!this.mResumed) {
                    Callback callback = window.getCallback();
                    if (callback != null) {
                        try {
                            window.setCallback((Callback) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Callback.class}, new WindowCallbackProxy(callback, this)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    this.mResumed = true;
                }
                if (VERSION.SDK_INT >= 16) {
                    decorView.getViewTreeObserver().addOnDrawListener(this.mDrawTimeCollector);
                }
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityLifeCycleDispatcher)) {
            this.mActivityLifeCycleDispatcher.onActivityPaused(activity, TimeUtils.currentTimeMillis());
        }
        if (VERSION.SDK_INT >= 16) {
            activity.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener(this.mDrawTimeCollector);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityLifeCycleDispatcher)) {
            if (!PageList.inBlackList(ActivityUtils.getName(activity))) {
                e();
            }
            this.mActivityLifeCycleDispatcher.onActivityStopped(activity, TimeUtils.currentTimeMillis());
        }
        if (!PageList.inBlackList(ActivityUtils.getName(activity))) {
            onStop();
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityLifeCycleDispatcher)) {
            this.mActivityLifeCycleDispatcher.onActivityDestroyed(activity, TimeUtils.currentTimeMillis());
        }
    }

    @Override
    public void dispatchTouchEvent(MotionEvent motionEvent) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityEventDispatcher)) {
            this.mActivityEventDispatcher.onMotionEvent(this.mActivity, motionEvent, TimeUtils.currentTimeMillis());
        }
        usable(3, TimeUtils.currentTimeMillis());
        if (motionEvent.getAction() == ACTION_MOVE && VERSION.SDK_INT >= 16) {
            this.mDrawTimeCollector.mOnTouchEvent();
        }
    }

    @Override
    public void dispatchKeyEvent(KeyEvent keyEvent) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.mActivityEventDispatcher)) {
            this.mActivityEventDispatcher.onKeyEvent(this.mActivity, keyEvent, TimeUtils.currentTimeMillis());
        }
    }
}
