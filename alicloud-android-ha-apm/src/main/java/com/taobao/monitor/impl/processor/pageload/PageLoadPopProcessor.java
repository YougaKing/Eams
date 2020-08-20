package com.taobao.monitor.impl.processor.pageload;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.traffic.TrafficTracker;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.processor.pageload.PageModelLifecycle.ModelPairLifecycleListener;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@TargetApi(16)
/* compiled from: PageLoadPopProcessor */
public class PageLoadPopProcessor extends AbsProcessor implements ModelPairLifecycleListener,
        ActivityEventDispatcher.EventListener,
        ApplicationGCDispatcher.GCListener,
        ApplicationLowMemoryDispatcher.LowMemoryListener,
        FPSDispatcher.FPSListener {
    private IDispatcher a;

    /* renamed from: a reason: collision with other field name */
    private IProcedure mPageLoadProcedure;
    private IDispatcher b;

    /* renamed from: b reason: collision with other field name */
    private List<Integer> f90b = new ArrayList();

    /* renamed from: b reason: collision with other field name */
    private long[] f91b = new long[2];
    private int c = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher f92c;
    private Activity d = null;

    /* renamed from: d reason: collision with other field name */
    private IDispatcher f93d;
    private long f;
    private long g = -1;
    private long h = 0;
    private int l = 0;
    private boolean o = true;
    private String pageName;

    public PageLoadPopProcessor() {
        super(false);
    }

    /* access modifiers changed from: protected */
    public void procedureBegin() {
        super.procedureBegin();
        this.mPageLoadProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), new Builder()
                .setIndependent(false)
                .setUpload(true)
                .setParentNeedStats(false)
                .setParent(null)
                .build());
        this.mPageLoadProcedure.begin();
        this.a = getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.b = getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.f92c = getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.f93d = getDispatcher("APPLICATION_GC_DISPATCHER");
        this.f93d.addListener(this);
        this.b.addListener(this);
        this.a.addListener(this);
        this.f92c.addListener(this);
        p();
    }

    private void p() {
        this.mPageLoadProcedure.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.mPageLoadProcedure.addProperty("errorCode", Integer.valueOf(1));
        this.mPageLoadProcedure.addProperty("installType", GlobalStats.installType);
    }

    private void b(Activity activity) {
        this.pageName = ActivityUtils.getSimpleName(activity);
        this.mPageLoadProcedure.addProperty("pageName", this.pageName);
        this.mPageLoadProcedure.addProperty("fullPageName", activity.getClass().getName());
        Intent intent = activity.getIntent();
        if (intent != null) {
            String dataString = intent.getDataString();
            if (TextUtils.isEmpty(dataString)) {
                this.mPageLoadProcedure.addProperty("schemaUrl", dataString);
            }
        }
        this.mPageLoadProcedure.addProperty("isInterpretiveExecution", Boolean.valueOf(false));
        this.mPageLoadProcedure.addProperty("isFirstLaunch", Boolean.valueOf(GlobalStats.isFirstLaunch));
        this.mPageLoadProcedure.addProperty("isFirstLoad", Boolean.valueOf(GlobalStats.activityStatusManager.get(ActivityUtils.getName(activity))));
        this.mPageLoadProcedure.addProperty("jumpTime", Long.valueOf(GlobalStats.jumpTime));
        this.mPageLoadProcedure.addProperty("lastValidTime", Long.valueOf(GlobalStats.lastValidTime));
        this.mPageLoadProcedure.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.mPageLoadProcedure.addProperty("loadType", "pop");
    }

    public void onActivityStarted(Activity activity) {
        procedureBegin();
        this.f = TimeUtils.currentTimeMillis();
        b(activity);
        this.g = this.f;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.mPageLoadProcedure.event("onActivityStarted", hashMap);
        long[] a2 = TrafficTracker.traffics();
        this.f91b[0] = a2[0];
        this.f91b[1] = a2[1];
        this.mPageLoadProcedure.stage("loadStartTime", this.f);
        long currentTimeMillis = TimeUtils.currentTimeMillis();
        this.mPageLoadProcedure.addProperty("pageInitDuration", Long.valueOf(currentTimeMillis - this.f));
        this.mPageLoadProcedure.stage("renderStartTime", currentTimeMillis);
        long currentTimeMillis2 = TimeUtils.currentTimeMillis();
        this.mPageLoadProcedure.addProperty("interactiveDuration", Long.valueOf(currentTimeMillis2 - this.f));
        this.mPageLoadProcedure.addProperty("loadDuration", Long.valueOf(currentTimeMillis2 - this.f));
        this.mPageLoadProcedure.stage("interactiveTime", currentTimeMillis2);
        this.mPageLoadProcedure.addProperty("displayDuration", Long.valueOf(TimeUtils.currentTimeMillis() - this.f));
        this.mPageLoadProcedure.stage("displayedTime", this.f);
    }

    public void onActivityStopped(Activity activity) {
        this.h += TimeUtils.currentTimeMillis() - this.g;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.mPageLoadProcedure.event("onActivityStopped", hashMap);
        long[] a2 = TrafficTracker.traffics();
        this.f91b[0] = a2[0] - this.f91b[0];
        this.f91b[1] = a2[1] - this.f91b[1];
        this.mPageLoadProcedure.addProperty("totalVisibleDuration", Long.valueOf(this.h));
        this.mPageLoadProcedure.addProperty("errorCode", Integer.valueOf(0));
        this.mPageLoadProcedure.addStatistic("totalRx", Long.valueOf(this.f91b[0]));
        this.mPageLoadProcedure.addStatistic("totalTx", Long.valueOf(this.f91b[1]));
        procedureEnd();
    }

    public void onLowMemory() {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.mPageLoadProcedure.event("onLowMemory", hashMap);
    }

    public void onMotionEvent(Activity activity, MotionEvent motionEvent, long j) {
        if (activity == this.d && this.o) {
            this.mPageLoadProcedure.stage("firstInteractiveTime", j);
            this.mPageLoadProcedure.addProperty("firstInteractiveDuration", Long.valueOf(j - this.f));
            this.o = false;
        }
    }

    /* access modifiers changed from: protected */
    public void procedureEnd() {
        this.mPageLoadProcedure.stage("procedureEndTime", TimeUtils.currentTimeMillis());
        this.mPageLoadProcedure.addStatistic("gcCount", Integer.valueOf(this.l));
        this.mPageLoadProcedure.addStatistic("fps", this.f90b.toString());
        this.mPageLoadProcedure.addStatistic("jankCount", Integer.valueOf(this.c));
        this.b.removeListener(this);
        this.a.removeListener(this);
        this.f92c.removeListener(this);
        this.f93d.removeListener(this);
        this.mPageLoadProcedure.end();
        super.procedureEnd();
    }

    public void fps(int i) {
        if (this.f90b.size() < 60) {
            this.f90b.add(Integer.valueOf(i));
        }
    }

    public void jank(int i) {
        this.c += i;
    }

    public void gc() {
        this.l++;
    }

    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long j) {
        if (keyEvent.getAction() != 0) {
            return;
        }
        if (keyEvent.getKeyCode() == 4 || keyEvent.getKeyCode() == 3) {
            HashMap hashMap = new HashMap(2);
            hashMap.put("timestamp", Long.valueOf(j));
            hashMap.put("key", Integer.valueOf(keyEvent.getKeyCode()));
            this.mPageLoadProcedure.event("keyEvent", hashMap);
        }
    }
}
