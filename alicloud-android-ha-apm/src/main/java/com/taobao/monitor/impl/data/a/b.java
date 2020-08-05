package com.taobao.monitor.impl.data.a;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.BackgroundEvent;
import com.ali.ha.fulltrace.event.ForegroundEvent;
import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.d;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.util.TimeUtils;
import java.util.HashMap;
import java.util.Map;

@TargetApi(14)
/* compiled from: ActivityLifecycle */
public class b implements ActivityLifecycleCallbacks {
    private final com.taobao.application.common.data.b a = new com.taobao.application.common.data.b();

    /* renamed from: a reason: collision with other field name */
    private final c f30a = new c();
    private final ActivityLifecycleCallbacks b = com.taobao.application.common.impl.b.a().a();
    private final ActivityLifecycleCallbacks c = com.taobao.application.common.impl.b.a().b();
    private int count;
    private int i = 0;
    protected Map<Activity, a> map = new HashMap();

    /* compiled from: ActivityLifecycle */
    interface a {
        void onActivityCreated(Activity activity, Bundle bundle);

        void onActivityDestroyed(Activity activity);

        void onActivityPaused(Activity activity);

        void onActivityResumed(Activity activity);

        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);
    }

    public b() {
        this.a.a(this.i);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        com.taobao.application.common.data.b bVar = this.a;
        int i2 = this.i + 1;
        this.i = i2;
        bVar.a(i2);
        if (((a) this.map.get(activity)) == null) {
            GlobalStats.createdPageCount++;
            GlobalStats.activityStatusManager.b(com.taobao.monitor.impl.util.a.a(activity));
            a aVar = new a(activity);
            this.map.put(activity, aVar);
            aVar.onActivityCreated(activity, bundle);
            if ((activity instanceof FragmentActivity) && DynamicConstants.needFragment) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new com.taobao.monitor.impl.data.b.b(activity), true);
            }
        }
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityCreated", activity.getClass().getSimpleName());
        com.taobao.application.common.impl.b.a().a(activity);
        this.b.onActivityCreated(activity, bundle);
        this.c.onActivityCreated(activity, bundle);
    }

    public void onActivityStarted(Activity activity) {
        a aVar = (a) this.map.get(activity);
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityStarted", activity.getClass().getSimpleName());
        this.count++;
        if (this.count == 1) {
            IDispatcher a2 = g.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
            if (a2 instanceof d) {
                ((d) a2).d(0, TimeUtils.currentTimeMillis());
            }
            DataLoggerUtils.log("ActivityLifeCycle", "background2Foreground");
            this.f30a.i();
            DumpManager.getInstance().append(new ForegroundEvent());
        }
        GlobalStats.isBackground = false;
        if (aVar != null) {
            aVar.onActivityStarted(activity);
        }
        com.taobao.application.common.impl.b.a().a(activity);
        this.b.onActivityStarted(activity);
        this.c.onActivityStarted(activity);
    }

    public void onActivityResumed(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityResumed", activity.getClass().getSimpleName());
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityResumed(activity);
        }
        com.taobao.application.common.impl.b.a().a(activity);
        this.b.onActivityResumed(activity);
        this.c.onActivityResumed(activity);
    }

    public void onActivityPaused(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityPaused", activity.getClass().getSimpleName());
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityPaused(activity);
        }
        this.b.onActivityPaused(activity);
        this.c.onActivityPaused(activity);
    }

    public void onActivityStopped(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityStopped", activity.getClass().getSimpleName());
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityStopped(activity);
        }
        this.count--;
        if (this.count == 0) {
            GlobalStats.isBackground = true;
            ProcedureManagerSetter.instance().setCurrentActivityProcedure(null);
            ProcedureManagerSetter.instance().setCurrentFragmentProcedure(null);
            IDispatcher a2 = g.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
            if (a2 instanceof d) {
                ((d) a2).d(1, TimeUtils.currentTimeMillis());
            }
            DataLoggerUtils.log("ActivityLifeCycle", "foreground2Background");
            DumpManager.getInstance().append(new BackgroundEvent());
            GlobalStats.lastValidPage = "background";
            GlobalStats.lastValidTime = -1;
            this.f30a.j();
            c(com.taobao.monitor.impl.util.a.a(activity));
        }
        this.b.onActivityStopped(activity);
        this.c.onActivityStopped(activity);
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.b.onActivitySaveInstanceState(activity, bundle);
        this.c.onActivitySaveInstanceState(activity, bundle);
    }

    public void onActivityDestroyed(Activity activity) {
        DataLoggerUtils.log("ActivityLifeCycle", "onActivityDestroyed", activity.getClass().getSimpleName());
        a aVar = (a) this.map.get(activity);
        if (aVar != null) {
            aVar.onActivityDestroyed(activity);
        }
        this.map.remove(activity);
        if (this.count == 0) {
            c("");
            com.taobao.application.common.impl.b.a().a((Activity) null);
        }
        this.b.onActivityDestroyed(activity);
        this.c.onActivityDestroyed(activity);
        com.taobao.application.common.data.b bVar = this.a;
        int i2 = this.i - 1;
        this.i = i2;
        bVar.a(i2);
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
