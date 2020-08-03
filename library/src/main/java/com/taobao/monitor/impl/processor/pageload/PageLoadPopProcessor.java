//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.pageload;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.tracker.TrafficTracker;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@TargetApi(16)
public class PageLoadPopProcessor extends AbsProcessor implements PageModelLifecycle.ModelLifecyclePairListener,
        ActivityEventDispatcher.EventListener,
        ApplicationGCDispatcher.GCListener,
        ApplicationLowMemoryDispatcher.LowMemoryListener,
        FPSDispatcher.FPSListener {
    private IProcedure pageLoadProcedure;
    private long f;
    private Activity d = null;
    private String pageName;
    private IDispatcher activityEventDispatcher;
    private IDispatcher applicationLowMemoryDispatcher;
    private IDispatcher activityFpsDispatcher;
    private IDispatcher applicationGcDispatcher;
    private long g = -1L;
    private long h = 0L;
    private long[] flowBytes = new long[2];
    private List<Integer> intList = new ArrayList();
    private int jankCount = 0;
    private int gcCount = 0;
    private boolean o = true;

    public PageLoadPopProcessor() {
        super(false);
    }

    protected void n() {
        super.n();

        ProcedureConfig procedureConfig = (new Builder())
                .setIndependent(false)
                .setUpload(true)
                .setParentNeedStats(false)
                .setParent(null)
                .build();

        this.pageLoadProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), procedureConfig);
        this.pageLoadProcedure.begin();
        this.activityEventDispatcher = this.getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.applicationLowMemoryDispatcher = this.getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.activityFpsDispatcher = this.getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.applicationGcDispatcher = this.getDispatcher("APPLICATION_GC_DISPATCHER");

        this.applicationGcDispatcher.addListener(this);
        this.applicationLowMemoryDispatcher.addListener(this);
        this.activityEventDispatcher.addListener(this);
        this.activityFpsDispatcher.addListener(this);
        this.addProperty();
    }

    private void addProperty() {
        this.pageLoadProcedure.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.pageLoadProcedure.addProperty("errorCode", 1);
        this.pageLoadProcedure.addProperty("installType", GlobalStats.installType);
    }

    private void addProperty(Activity activity) {
        this.pageName = ActivityUtils.getName(activity);
        this.pageLoadProcedure.addProperty("pageName", this.pageName);
        this.pageLoadProcedure.addProperty("fullPageName", activity.getClass().getName());
        Intent intent = activity.getIntent();
        if (intent != null) {
            String var3 = intent.getDataString();
            if (TextUtils.isEmpty(var3)) {
                this.pageLoadProcedure.addProperty("schemaUrl", var3);
            }
        }

        this.pageLoadProcedure.addProperty("isInterpretiveExecution", false);
        this.pageLoadProcedure.addProperty("isFirstLaunch", GlobalStats.isFirstLaunch);
        this.pageLoadProcedure.addProperty("isFirstLoad", GlobalStats.activityStatusManager.a(ActivityUtils.getName(activity)));
        this.pageLoadProcedure.addProperty("jumpTime", GlobalStats.jumpTime);
        this.pageLoadProcedure.addProperty("lastValidTime", GlobalStats.lastValidTime);
        this.pageLoadProcedure.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.pageLoadProcedure.addProperty("loadType", "pop");
    }

    @Override
    public void onActivityStarted(Activity var1) {
        this.n();
        this.f = TimeUtils.currentTimeMillis();
        this.addProperty(var1);
        this.g = this.f;
        HashMap var2 = new HashMap(1);
        var2.put("timestamp", TimeUtils.currentTimeMillis());
        this.pageLoadProcedure.event("onActivityStarted", var2);
        long[] flowBytes = TrafficTracker.flowBytes();
        this.flowBytes[0] = flowBytes[0];
        this.flowBytes[1] = flowBytes[1];
        this.pageLoadProcedure.stage("loadStartTime", this.f);
        long var4 = TimeUtils.currentTimeMillis();
        this.pageLoadProcedure.addProperty("pageInitDuration", var4 - this.f);
        this.pageLoadProcedure.stage("renderStartTime", var4);
        long var6 = TimeUtils.currentTimeMillis();
        this.pageLoadProcedure.addProperty("interactiveDuration", var6 - this.f);
        this.pageLoadProcedure.addProperty("loadDuration", var6 - this.f);
        this.pageLoadProcedure.stage("interactiveTime", var6);
        long var8 = TimeUtils.currentTimeMillis();
        this.pageLoadProcedure.addProperty("displayDuration", var8 - this.f);
        this.pageLoadProcedure.stage("displayedTime", this.f);
    }

    @Override
    public void onActivityStopped(Activity var1) {
        this.h += TimeUtils.currentTimeMillis() - this.g;
        HashMap var2 = new HashMap(1);
        var2.put("timestamp", TimeUtils.currentTimeMillis());
        this.pageLoadProcedure.event("onActivityStopped", var2);
        long[] flowBytes = TrafficTracker.flowBytes();
        this.flowBytes[0] = flowBytes[0] - this.flowBytes[0];
        this.flowBytes[1] = flowBytes[1] - this.flowBytes[1];
        this.pageLoadProcedure.addProperty("totalVisibleDuration", this.h);
        this.pageLoadProcedure.addProperty("errorCode", 0);
        this.pageLoadProcedure.addStatistic("totalRx", this.flowBytes[0]);
        this.pageLoadProcedure.addStatistic("totalTx", this.flowBytes[1]);
        this.o();
    }

    @Override
    public void onLowMemory() {
        HashMap var1 = new HashMap(1);
        var1.put("timestamp", TimeUtils.currentTimeMillis());
        this.pageLoadProcedure.event("onLowMemory", var1);
    }

    @Override
    public void onMotionEvent(Activity var1, MotionEvent var2, long var3) {
        if (var1 == this.d && this.o) {
            this.pageLoadProcedure.stage("firstInteractiveTime", var3);
            this.pageLoadProcedure.addProperty("firstInteractiveDuration", var3 - this.f);
            this.o = false;
        }

    }

    @Override
    protected void o() {
        this.pageLoadProcedure.stage("procedureEndTime", TimeUtils.currentTimeMillis());
        this.pageLoadProcedure.addStatistic("gcCount", this.gcCount);
        this.pageLoadProcedure.addStatistic("fps", this.applicationLowMemoryDispatcher.toString());
        this.pageLoadProcedure.addStatistic("jankCount", this.activityFpsDispatcher);
        this.applicationLowMemoryDispatcher.removeListener(this);
        this.activityEventDispatcher.removeListener(this);
        this.activityFpsDispatcher.removeListener(this);
        this.applicationGcDispatcher.removeListener(this);
        this.pageLoadProcedure.end();
        super.o();
    }

    @Override
    public void b(int var1) {
        if (this.intList.size() < 60) {
            this.intList.add(var1);
        }
    }

    @Override
    public void c(int var1) {
        this.jankCount += var1;
    }

    @Override
    public void gc() {
        ++this.gcCount;
    }

    @Override
    public void onKeyEvent(Activity var1, KeyEvent var2, long var3) {
        if (var2.getAction() == 0 && (var2.getKeyCode() == 4 || var2.getKeyCode() == 3)) {
            HashMap var5 = new HashMap(2);
            var5.put("timestamp", var3);
            var5.put("key", var2.getKeyCode());
            this.pageLoadProcedure.event("keyEvent", var5);
        }

    }
}
