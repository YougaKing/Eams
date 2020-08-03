//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.pageload;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.fragment.app.Fragment;

import com.ali.alihadeviceevaluator.AliHAHardware;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.DisplayedEvent;
import com.ali.ha.fulltrace.event.FPSEvent;
import com.ali.ha.fulltrace.event.FinishLoadPageEvent;
import com.ali.ha.fulltrace.event.GCEvent;
import com.ali.ha.fulltrace.event.JankEvent;
import com.ali.ha.fulltrace.event.OpenPageEvent;
import com.ali.ha.fulltrace.event.ReceiverLowMemoryEvent;
import com.ali.ha.fulltrace.event.UsableEvent;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.OnUsableVisibleListener;
import com.taobao.monitor.impl.data.tracker.TrafficTracker;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.trace.FragmentFunctionDispatcher;
import com.taobao.monitor.impl.trace.FragmentFunctionListener;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
public class PageLoadProcessor extends AbsProcessor implements OnUsableVisibleListener<Activity>,
        PageModelLifecycle.ModelLifecycleListener,
        ActivityEventDispatcher.EventListener,
        ApplicationBackgroundChangedDispatcher.BackgroundChangedListener,
        ApplicationGCDispatcher.GCListener,
        ApplicationLowMemoryDispatcher.LowMemoryListener,
        FPSDispatcher.FPSListener,
        FragmentFunctionListener,
        ImageStageDispatcher.StageListener,
        NetworkStageDispatcher.StageListener {
    private IProcedure pageLoadProcedure;
    private long onCreatedTime;
    private Activity activity = null;
    private String pageName;
    private static String firstActivityName = "";
    private static List<String> activityNameList = new ArrayList(4);
    private IDispatcher activityEventDispatcher;
    private IDispatcher applicationLowMemoryDispatcher;
    private IDispatcher activityUsableVisibleDispatcher;
    private IDispatcher activityFpsDispatcher;
    private IDispatcher applicationGcDispatcher;
    private IDispatcher applicationBackgroundChangedDispatcher;
    private IDispatcher networkStageDispatcher;
    private IDispatcher imageStageDispatcher;
    private long onStartedTime = -1L;
    private long h = 0L;
    private long[] c;
    private long[] flowBytes = new long[2];
    private boolean p = true;
    private List<Integer> intList = new ArrayList();
    private int jankCount = 0;
    private int gcCount = 0;
    private int imageCount;
    private int imageSuccessCount;
    private int imageFailedCount;
    private int imageCanceledCount;
    private int networkCount;
    private int networkSuccessCount;
    private int networkFailedCount;
    private int networkCanceledCount;
    private FPSEvent fPSEvent = new FPSEvent();
    private int m = 0;
    private boolean q = true;
    private HashMap<String, Integer> fpsMap = new HashMap();
    private boolean firstInteractive;
    private boolean pageInit;
    private boolean interactive;
    private boolean display;
    private boolean procedure;

    public PageLoadProcessor() {
        super(false);
    }

    protected void n() {
        super.n();
        ProcedureConfig var1 = (new Builder())
                .setIndependent(false)
                .setUpload(true)
                .setParentNeedStats(true)
                .setParent(null)
                .build();
        this.pageLoadProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), var1);
        this.pageLoadProcedure.begin();
        this.activityEventDispatcher = this.getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.applicationLowMemoryDispatcher = this.getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.activityUsableVisibleDispatcher = this.getDispatcher("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        this.activityFpsDispatcher = this.getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.applicationGcDispatcher = this.getDispatcher("APPLICATION_GC_DISPATCHER");
        this.applicationBackgroundChangedDispatcher = this.getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.networkStageDispatcher = this.getDispatcher("NETWORK_STAGE_DISPATCHER");
        this.imageStageDispatcher = this.getDispatcher("IMAGE_STAGE_DISPATCHER");

        this.applicationLowMemoryDispatcher.addListener(this);
        this.activityFpsDispatcher.addListener(this);
        this.applicationGcDispatcher.addListener(this);
        this.activityEventDispatcher.addListener(this);
        this.activityUsableVisibleDispatcher.addListener(this);
        this.applicationBackgroundChangedDispatcher.addListener(this);
        this.networkStageDispatcher.addListener(this);
        this.imageStageDispatcher.addListener(this);
        FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.addListener(this);

        this.p();
        this.flowBytes[0] = 0L;
        this.flowBytes[1] = 0L;
    }

    private void p() {
        this.pageLoadProcedure.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.pageLoadProcedure.addProperty("errorCode", 1);
        this.pageLoadProcedure.addProperty("installType", GlobalStats.installType);
        this.pageLoadProcedure.addProperty("leaveType", "other");
    }

    public void onActivityCreated(Activity var1, Bundle var2, long var3) {
        this.onCreatedTime = var3;
        this.n();
        this.pageLoadProcedure.stage("loadStartTime", this.onCreatedTime);
        HashMap var5 = new HashMap(1);
        var5.put("timestamp", this.onCreatedTime);
        this.pageLoadProcedure.event("onActivityCreated", var5);
        this.activity = var1;
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.pageLoadProcedure);
        this.b(var1);
        this.c = TrafficTracker.flowBytes();
        OpenPageEvent var6 = new OpenPageEvent();
        var6.pageName = ActivityUtils.getSimpleName(var1);
        DumpManager.getInstance().append(var6);
    }

    private void b(Activity var1) {
        this.pageName = ActivityUtils.getName(var1);
        if (activityNameList.size() < 10) {
            activityNameList.add(this.pageName);
        }

        this.pageLoadProcedure.addProperty("pageName", this.pageName);
        this.pageLoadProcedure.addProperty("fullPageName", ActivityUtils.getName(var1));
        if (!TextUtils.isEmpty(firstActivityName)) {
            this.pageLoadProcedure.addProperty("fromPageName", onStartedTime);
        }

        Intent var2 = var1.getIntent();
        if (var2 != null) {
            String var3 = var2.getDataString();
            if (!TextUtils.isEmpty(var3)) {
                this.pageLoadProcedure.addProperty("schemaUrl", var3);
            }
        }

        this.pageLoadProcedure.addProperty("isFirstLaunch", GlobalStats.isFirstLaunch);
        this.pageLoadProcedure.addProperty("isFirstLoad", GlobalStats.activityStatusManager.a(ActivityUtils.getName(var1)));
        this.pageLoadProcedure.addProperty("jumpTime", GlobalStats.jumpTime);
        GlobalStats.jumpTime = -1L;
        this.pageLoadProcedure.addProperty("lastValidTime", GlobalStats.lastValidTime);
        this.pageLoadProcedure.addProperty("lastValidLinksPage", activity.toString());
        this.pageLoadProcedure.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.pageLoadProcedure.addProperty("loadType", "push");
    }

    @Override
    public void onActivityStarted(Activity var1, long var2) {
        this.q = true;
        this.onStartedTime = var2;
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.pageLoadProcedure.event("onActivityStarted", var4);
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.pageLoadProcedure);
        firstActivityName = this.pageName;
        if (this.p) {
            this.p = false;
            long[] flowBytes = TrafficTracker.flowBytes();
            this.flowBytes[0] += flowBytes[0] - this.c[0];
            this.flowBytes[1] += flowBytes[1] - this.c[1];
        }

        this.c = TrafficTracker.flowBytes();
        GlobalStats.lastValidPage = this.pageName;
        GlobalStats.lastValidTime = var2;
    }

    @Override
    public void onActivityResumed(Activity var1, long var2) {
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.pageLoadProcedure);
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.pageLoadProcedure.event("onActivityResumed", var4);
    }

    @Override
    public void onActivityPaused(Activity var1, long var2) {
        this.q = false;
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.pageLoadProcedure.event("onActivityPaused", var4);
    }

    @Override
    public void onActivityStopped(Activity var1, long var2) {
        this.h += var2 - this.onStartedTime;
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.pageLoadProcedure.event("onActivityStopped", var4);
        long[] flowBytes = TrafficTracker.flowBytes();
        this.flowBytes[0] += flowBytes[0] - this.c[0];
        this.flowBytes[1] += flowBytes[1] - this.c[1];
        this.c = flowBytes;
        if (this.intList != null && this.m > this.intList.size()) {
            Integer var6 = 0;

            for (int var7 = this.m; var7 < this.intList.size(); ++var7) {
                var6 = var6 + (Integer) this.intList.get(var7);
            }

            this.fPSEvent.averageUseFps = (float) (var6 / (this.intList.size() - this.m));
        }

        DumpManager.getInstance().append(this.fPSEvent);
    }

    @Override
    public void onActivityDestroyed(Activity var1, long var2) {
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.pageLoadProcedure.event("onActivityDestroyed", var4);
        long[] flowBytes = TrafficTracker.flowBytes();
        this.flowBytes[0] += flowBytes[0] - this.c[0];
        this.flowBytes[1] += flowBytes[1] - this.c[1];

        FinishLoadPageEvent var6 = new FinishLoadPageEvent();
        var6.pageName = ActivityUtils.getSimpleName(var1);
        DumpManager.getInstance().append(var6);
        this.o();
    }

    @Override
    public void onLowMemory() {
        HashMap var1 = new HashMap(1);
        var1.put("timestamp", TimeUtils.currentTimeMillis());
        this.pageLoadProcedure.event("onLowMemory", var1);
        ReceiverLowMemoryEvent var2 = new ReceiverLowMemoryEvent();
        var2.level = 1.0F;
        DumpManager.getInstance().append(var2);
    }

    @Override
    public void onMotionEvent(Activity var1, MotionEvent var2, long var3) {
        if (var1 == this.activity) {
            if (this.firstInteractive) {
                this.pageLoadProcedure.stage("firstInteractiveTime", var3);
                this.pageLoadProcedure.addProperty("firstInteractiveDuration", var3 - this.onCreatedTime);
                this.pageLoadProcedure.addProperty("leaveType", "touch");
                this.firstInteractive = false;
                this.pageLoadProcedure.addProperty("errorCode", 0);
            }

            activityNameList.clear();
            activityNameList.add(this.pageName);
            GlobalStats.lastValidPage = this.pageName;
            GlobalStats.lastValidTime = var3;
        }

    }

    public void f(Activity var1, long var2) {
        if (this.pageInit && var1 == this.activity) {
            this.pageLoadProcedure.addProperty("pageInitDuration", var2 - this.onCreatedTime);
            this.pageLoadProcedure.stage("renderStartTime", var2);
            this.pageInit = false;
        }

    }

    public void a(Activity var1, float var2, long var3) {
        if (var1 == this.activity) {
            this.pageLoadProcedure.addProperty("onRenderPercent", var2);
            this.pageLoadProcedure.addProperty("drawPercentTime", var3);
        }

    }

    public void a(Activity var1, int var2, int var3, long var4) {
        if (this.interactive) {
            if (var1 == this.activity && var2 == 2) {
                this.pageLoadProcedure.addProperty("interactiveDuration", var4 - this.onCreatedTime);
                this.pageLoadProcedure.addProperty("loadDuration", var4 - this.onCreatedTime);
                this.pageLoadProcedure.addProperty("usableChangeType", var3);
                this.pageLoadProcedure.stage("interactiveTime", var4);
                this.pageLoadProcedure.addProperty("errorCode", 0);
                this.pageLoadProcedure.addStatistic("totalRx", this.flowBytes[0]);
                this.pageLoadProcedure.addStatistic("totalTx", this.flowBytes[1]);
                this.interactive = false;
                UsableEvent var8 = new UsableEvent();
                var8.duration = (float) (var4 - this.onCreatedTime);
                DumpManager.getInstance().append(var8);
                if (this.intList != null && this.intList.size() != 0) {
                    Integer var9 = 0;

                    Integer var11;
                    for (Iterator var10 = this.intList.iterator(); var10.hasNext(); var9 = var9 + var11) {
                        var11 = (Integer) var10.next();
                    }

                    this.fPSEvent.averageLoadFps = (float) (var9 / this.intList.size());
                    this.m = this.intList.size();
                }
            }

        }
    }

    public void a(Activity var1, int var2, long var3) {
        if (this.display) {
            if (var1 == this.activity && var2 == 2) {
                this.pageLoadProcedure.addProperty("displayDuration", var3 - this.onCreatedTime);
                this.pageLoadProcedure.stage("displayedTime", var3);
                DisplayedEvent var7 = new DisplayedEvent();
                DumpManager.getInstance().append(var7);
                this.display = false;
            }
        }
    }

    protected void o() {
        if (!this.procedure) {
            this.procedure = true;
            this.pageLoadProcedure.addProperty("totalVisibleDuration", this.h);
            this.pageLoadProcedure.addProperty("deviceLevel", AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
            this.pageLoadProcedure.addProperty("runtimeLevel", AliHAHardware.getInstance().getOutlineInfo().runtimeLevel);
            this.pageLoadProcedure.addProperty("cpuUsageOfDevcie", AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie);
            this.pageLoadProcedure.addProperty("memoryRuntimeLevel", AliHAHardware.getInstance().getMemoryInfo().runtimeLevel);
            this.pageLoadProcedure.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            this.pageLoadProcedure.addStatistic("gcCount", this.gcCount);
            this.pageLoadProcedure.addStatistic("fps", this.fpsMap.toString());
            this.pageLoadProcedure.addStatistic("jankCount", this.jankCount);
            this.pageLoadProcedure.addStatistic("image", this.imageCount);
            this.pageLoadProcedure.addStatistic("imageOnRequest", this.imageCount);
            this.pageLoadProcedure.addStatistic("imageSuccessCount", this.imageSuccessCount);
            this.pageLoadProcedure.addStatistic("imageFailedCount", this.imageFailedCount);
            this.pageLoadProcedure.addStatistic("imageCanceledCount", this.imageCanceledCount);
            this.pageLoadProcedure.addStatistic("network", this.networkCount);
            this.pageLoadProcedure.addStatistic("networkOnRequest", this.networkCount);
            this.pageLoadProcedure.addStatistic("networkSuccessCount", this.networkSuccessCount);
            this.pageLoadProcedure.addStatistic("networkFailedCount", this.networkFailedCount);
            this.pageLoadProcedure.addStatistic("networkCanceledCount", this.networkCanceledCount);

            this.applicationLowMemoryDispatcher.removeListener(this);
            this.activityFpsDispatcher.removeListener(this);
            this.applicationGcDispatcher.removeListener(this);
            this.activityEventDispatcher.removeListener(this);
            this.activityUsableVisibleDispatcher.removeListener(this);
            this.applicationBackgroundChangedDispatcher.removeListener(this);
            this.networkStageDispatcher.removeListener(this);
            this.imageStageDispatcher.removeListener(this);
            FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.removeListener(this);

            this.pageLoadProcedure.end();
            super.o();
        }

    }

    public void b(int var1) {
        if (this.intList.size() < 200 && this.q) {
            this.intList.add(var1);
        }
    }

    public void c(int jankCount) {
        if (this.q) {
            this.jankCount += jankCount;
            JankEvent var2 = new JankEvent();
            DumpManager.getInstance().append(var2);
        }

    }

    @Override
    public void gc() {
        if (this.q) {
            ++this.gcCount;
            GCEvent var1 = new GCEvent();
            DumpManager.getInstance().append(var1);
        }

    }

    @Override
    public void backgroundChanged(int var1, long var2) {
        HashMap var4;
        if (var1 == 1) {
            var4 = new HashMap(1);
            var4.put("timestamp", var2);
            this.pageLoadProcedure.event("foreground2Background", var4);
            this.o();
        } else {
            var4 = new HashMap(1);
            var4.put("timestamp", var2);
            this.pageLoadProcedure.event("background2Foreground", var4);
        }

    }

    @Override
    public void onKeyEvent(Activity var1, KeyEvent var2, long var3) {
        if (var1 == this.activity) {
            int var5 = var2.getAction();
            int var6 = var2.getKeyCode();
            if (var5 == 0 && (var6 == 4 || var6 == 3)) {
                if (var6 == 3) {
                    this.pageLoadProcedure.addProperty("leaveType", "home");
                } else {
                    this.pageLoadProcedure.addProperty("leaveType", "back");
                }

                HashMap var7 = new HashMap(2);
                var7.put("timestamp", var3);
                var7.put("key", var2.getKeyCode());
                this.pageLoadProcedure.event("keyEvent", var7);
            }
        }

    }

    @Override
    public void imageStage(int var1) {
        if (var1 == 0) {
            ++this.imageCount;
        } else if (var1 == 1) {
            ++this.imageSuccessCount;
        } else if (var1 == 2) {
            ++this.imageFailedCount;
        } else if (var1 == 3) {
            ++this.imageCanceledCount;
        }
    }

    @Override
    public void networkStage(int var1) {
        if (var1 == 0) {
            ++this.networkCount;
        } else if (var1 == 1) {
            ++this.networkSuccessCount;
        } else if (var1 == 2) {
            ++this.networkFailedCount;
        } else if (var1 == 3) {
            ++this.networkCanceledCount;
        }
    }

    public void a(Activity var1, Fragment var2, String var3, long var4) {
        if (var2 != null) {
            if (var1 == this.activity) {
                String var6 = var2.getClass().getSimpleName();
                String var7 = var6 + "_" + var3;
                Integer var8 = (Integer) this.fpsMap.get(var7);
                if (var8 == null) {
                    var8 = 0;
                } else {
                    var8 = var8 + 1;
                }

                this.fpsMap.put(var7, var8);
                this.pageLoadProcedure.stage(var7 + var8, var4);
            }
        }
    }
}
