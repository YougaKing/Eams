//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.data.collector;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.BackgroundEvent;
import com.ali.ha.fulltrace.event.ForegroundEvent;
import com.taobao.application.common.data.ActivityCountHelper;
import com.taobao.application.common.impl.ApmImpl;
import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.d;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.procedure.IProcedure;

import java.util.HashMap;
import java.util.Map;

@TargetApi(14)
public class ActivityLifecycle implements ActivityLifecycleCallbacks {
    private int count;
    protected Map<Activity, ActivityLifecycleListener> map = new HashMap<>();
    private final ActivityLifecycleCallbacks mainApplicationLifecycleCallback = ApmImpl.instance().mainApplicationLifecycleCallback();
    private final ActivityLifecycleCallbacks applicationLifecycleCallback = ApmImpl.instance().applicationLifecycleCallback();
    private final BackgroundForegroundEventImpl backgroundForegroundEvent = new BackgroundForegroundEventImpl();
    private int i = 0;
    private final ActivityCountHelper activityCountHelper = new ActivityCountHelper();

    public ActivityLifecycle() {
        this.activityCountHelper.setAliveActivityCount(this.i);
    }

    @Override
    public void onActivityCreated(Activity var1, Bundle var2) {
        this.activityCountHelper.setAliveActivityCount(++this.i);
        ActivityLifecycleListener var3 = this.map.get(var1);
        if (var3 == null) {
            ++GlobalStats.createdPageCount;
            String var4 = ActivityUtils.getName(var1);
            GlobalStats.activityStatusManager.b(var4);
            com.taobao.monitor.impl.data.a.a var5 = new ActivityDataCollector(var1);
            this.map.put(var1, var5);
            var5.onActivityCreated(var1, var2);
            if (var1 instanceof FragmentActivity && DynamicConstants.needFragment) {
                FragmentActivity var6 = (FragmentActivity) var1;
                FragmentManager var7 = var6.getSupportFragmentManager();
                var7.registerFragmentLifecycleCallbacks(new com.taobao.monitor.impl.data.b.b(var1), true);
            }
        }

        DataLoggerUtils.log("ActivityLifeCycle", new Object[]{"onActivityCreated", var1.getClass().getSimpleName()});
        com.taobao.application.common.impl.b.a().a(var1);
        this.mainApplicationLifecycleCallback.onActivityCreated(var1, var2);
        this.applicationLifecycleCallback.onActivityCreated(var1, var2);
    }

    public void onActivityStarted(Activity var1) {
        ActivityLifecycleListener var2 = (ActivityLifecycleListener) this.map.get(var1);
        DataLoggerUtils.log("ActivityLifeCycle", new Object[]{"onActivityStarted", var1.getClass().getSimpleName()});
        ++this.count;
        if (this.count == 1) {
            IDispatcher var3 = g.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
            if (var3 instanceof d) {
                ((d) var3).d(0, TimeUtils.currentTimeMillis());
            }

            DataLoggerUtils.log("ActivityLifeCycle", new Object[]{"background2Foreground"});
            this.backgroundForegroundEvent.i();
            ForegroundEvent var4 = new ForegroundEvent();
            DumpManager.getInstance().append(var4);
        }

        GlobalStats.isBackground = false;
        if (var2 != null) {
            var2.onActivityStarted(var1);
        }

        com.taobao.application.common.impl.b.a().a(var1);
        this.mainApplicationLifecycleCallback.onActivityStarted(var1);
        this.applicationLifecycleCallback.onActivityStarted(var1);
    }

    public void onActivityResumed(Activity var1) {
        DataLoggerUtils.log("ActivityLifeCycle", new Object[]{"onActivityResumed", var1.getClass().getSimpleName()});
        ActivityLifecycleListener var2 = (ActivityLifecycleListener) this.map.get(var1);
        if (var2 != null) {
            var2.onActivityResumed(var1);
        }

        com.taobao.application.common.impl.b.a().a(var1);
        this.mainApplicationLifecycleCallback.onActivityResumed(var1);
        this.applicationLifecycleCallback.onActivityResumed(var1);
    }

    public void onActivityPaused(Activity var1) {
        DataLoggerUtils.log("ActivityLifeCycle", new Object[]{"onActivityPaused", var1.getClass().getSimpleName()});
        ActivityLifecycleListener var2 = (ActivityLifecycleListener) this.map.get(var1);
        if (var2 != null) {
            var2.onActivityPaused(var1);
        }

        this.mainApplicationLifecycleCallback.onActivityPaused(var1);
        this.applicationLifecycleCallback.onActivityPaused(var1);
    }

    public void onActivityStopped(Activity var1) {
        DataLoggerUtils.log("ActivityLifeCycle", new Object[]{"onActivityStopped", var1.getClass().getSimpleName()});
        ActivityLifecycleListener var2 = (ActivityLifecycleListener) this.map.get(var1);
        if (var2 != null) {
            var2.onActivityStopped(var1);
        }

        --this.count;
        if (this.count == 0) {
            GlobalStats.isBackground = true;
            ProcedureManagerSetter.instance().setCurrentActivityProcedure((IProcedure) null);
            ProcedureManagerSetter.instance().setCurrentFragmentProcedure((IProcedure) null);
            IDispatcher var3 = g.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
            if (var3 instanceof d) {
                ((d) var3).d(1, TimeUtils.currentTimeMillis());
            }

            DataLoggerUtils.log("ActivityLifeCycle", new Object[]{"foreground2Background"});
            BackgroundEvent var4 = new BackgroundEvent();
            DumpManager.getInstance().append(var4);
            GlobalStats.lastValidPage = "background";
            GlobalStats.lastValidTime = -1L;
            this.backgroundForegroundEvent.j();
            this.c(com.taobao.monitor.impl.util.a.a(var1));
        }

        this.mainApplicationLifecycleCallback.onActivityStopped(var1);
        this.applicationLifecycleCallback.onActivityStopped(var1);
    }

    public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
        this.mainApplicationLifecycleCallback.onActivitySaveInstanceState(var1, var2);
        this.applicationLifecycleCallback.onActivitySaveInstanceState(var1, var2);
    }

    public void onActivityDestroyed(Activity var1) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityDestroyed", var1.getClass().getSimpleName());
        ActivityLifecycleListener var2 = this.map.get(var1);
        if (var2 != null) {
            var2.onActivityDestroyed(var1);
        }

        this.map.remove(var1);
        if (this.count == 0) {
            this.c("");
            com.taobao.application.common.impl.b.a().a((Activity) null);
        }

        this.mainApplicationLifecycleCallback.onActivityDestroyed(var1);
        this.applicationLifecycleCallback.onActivityDestroyed(var1);
        this.backgroundForegroundEvent.a(--this.i);
    }

    private void c(final String var1) {
        Global.instance().handler().post(new Runnable() {
            public void run() {
                SharedPreferences var1x = Global.instance().context().getSharedPreferences("apm", 0);
                Editor var2 = var1x.edit();
                var2.putString("LAST_TOP_ACTIVITY", var1);
                var2.commit();
            }
        });
    }

    interface ActivityLifecycleListener {
        void onActivityCreated(Activity var1, Bundle var2);

        void onActivityStarted(Activity var1);

        void onActivityResumed(Activity var1);

        void onActivityPaused(Activity var1);

        void onActivityStopped(Activity var1);

        void onActivityDestroyed(Activity var1);
    }
}
