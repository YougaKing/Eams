package com.taobao.monitor.impl.data.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.BackgroundEvent;
import com.ali.ha.fulltrace.event.ForegroundEvent;
import com.taobao.application.common.data.ActivityCountHelper;
import com.taobao.application.common.impl.ApmImpl;
import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.fragment.FragmentLifecycle;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;

import java.util.HashMap;
import java.util.Map;

@TargetApi(14)
/* compiled from: ActivityLifecycle */
public class ActivityLifecycle implements ActivityLifecycleCallbacks {
    private final ActivityCountHelper mActivityCountHelper = new ActivityCountHelper();

    /* renamed from: a reason: collision with other field name */
    private final BackgroundForegroundEventImpl mBackgroundForegroundEvent = new BackgroundForegroundEventImpl();
    private final ActivityLifecycleCallbacks b = ApmImpl.instance().instance();
    private final ActivityLifecycleCallbacks c = ApmImpl.instance().b();
    private int count;
    private int i = 0;
    protected Map<Activity, LifecycleListener> map = new HashMap();

    /* compiled from: ActivityLifecycle */
    interface LifecycleListener {
        void onActivityCreated(Activity activity, Bundle bundle);

        void onActivityDestroyed(Activity activity);

        void onActivityPaused(Activity activity);

        void onActivityResumed(Activity activity);

        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);
    }

    public ActivityLifecycle() {
        this.mActivityCountHelper.aliveActivityCount(this.i);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        ActivityCountHelper bVar = this.mActivityCountHelper;
        int i2 = this.i + 1;
        this.i = i2;
        bVar.aliveActivityCount(i2);
        if (((LifecycleListener) this.map.get(activity)) == null) {
            GlobalStats.createdPageCount++;
            GlobalStats.activityStatusManager.b(ActivityUtils.getName(activity));
            LifecycleListener aVar = new ActivityDataCollector(activity);
            this.map.put(activity, aVar);
            aVar.onActivityCreated(activity, bundle);
            if ((activity instanceof FragmentActivity) && DynamicConstants.needFragment) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifecycle(activity), true);
            }
        }
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityCreated", activity.getClass().getSimpleName());
        ApmImpl.instance().a(activity);
        this.b.onActivityCreated(activity, bundle);
        this.c.onActivityCreated(activity, bundle);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LifecycleListener aVar = (LifecycleListener) this.map.get(activity);
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityStarted", activity.getClass().getSimpleName());
        this.count++;
        if (this.count == 1) {
            IDispatcher a2 = DispatcherManager.getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
            if (a2 instanceof ApplicationBackgroundChangedDispatcher) {
                ((ApplicationBackgroundChangedDispatcher) a2).backgroundChanged(0, TimeUtils.currentTimeMillis());
            }
            DataLoggerUtils.log("ActivityLifeCycle", "background2Foreground");
            this.mBackgroundForegroundEvent.i();
            DumpManager.getInstance().append(new ForegroundEvent());
        }
        GlobalStats.isBackground = false;
        if (aVar != null) {
            aVar.onActivityStarted(activity);
        }
        ApmImpl.instance().a(activity);
        this.b.onActivityStarted(activity);
        this.c.onActivityStarted(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityResumed", activity.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityResumed(activity);
        }
        ApmImpl.instance().a(activity);
        this.b.onActivityResumed(activity);
        this.c.onActivityResumed(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityPaused", activity.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityPaused(activity);
        }
        this.b.onActivityPaused(activity);
        this.c.onActivityPaused(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityStopped", activity.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityStopped(activity);
        }
        this.count--;
        if (this.count == 0) {
            GlobalStats.isBackground = true;
            ProcedureManagerSetter.instance().setCurrentActivityProcedure(null);
            ProcedureManagerSetter.instance().setCurrentFragmentProcedure(null);
            IDispatcher a2 = DispatcherManager.getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
            if (a2 instanceof ApplicationBackgroundChangedDispatcher) {
                ((ApplicationBackgroundChangedDispatcher) a2).backgroundChanged(1, TimeUtils.currentTimeMillis());
            }
            DataLoggerUtils.log("ActivityLifeCycle", "foreground2Background");
            DumpManager.getInstance().append(new BackgroundEvent());
            GlobalStats.lastValidPage = "background";
            GlobalStats.lastValidTime = -1;
            this.mBackgroundForegroundEvent.j();
            c(ActivityUtils.getName(activity));
        }
        this.b.onActivityStopped(activity);
        this.c.onActivityStopped(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.b.onActivitySaveInstanceState(activity, bundle);
        this.c.onActivitySaveInstanceState(activity, bundle);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityDestroyed", activity.getClass().getSimpleName());
        LifecycleListener aVar = (LifecycleListener) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityDestroyed(activity);
        }
        this.map.remove(activity);
        if (this.count == 0) {
            c("");
            ApmImpl.instance().a((Activity) null);
        }
        this.b.onActivityDestroyed(activity);
        this.c.onActivityDestroyed(activity);
        ActivityCountHelper bVar = this.mActivityCountHelper;
        int i2 = this.i - 1;
        this.i = i2;
        bVar.aliveActivityCount(i2);
    }

    private void c(final String str) {
        Global.instance().handler().post(new Runnable() {
            public void run() {
                Editor edit = Global.instance().context().getSharedPreferences("apm", 0).edit();
                edit.putString("LAST_TOP_ACTIVITY", str);
                edit.commit();
            }
        });
    }
}
