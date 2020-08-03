//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.ali.alihadeviceevaluator.AliHAHardware;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.DisplayedEvent;
import com.ali.ha.fulltrace.event.FirstDrawEvent;
import com.ali.ha.fulltrace.event.FirstInteractionEvent;
import com.ali.ha.fulltrace.event.LauncherUsableEvent;
import com.ali.ha.fulltrace.event.OpenAppFromURL;
import com.ali.ha.fulltrace.event.StartUpBeginEvent;
import com.ali.ha.fulltrace.event.StartUpEndEvent;
import com.taobao.application.common.IAppLaunchListener;
import com.taobao.application.common.data.AppLaunchHelper;
import com.taobao.application.common.data.c;
import com.taobao.application.common.impl.ApmImpl;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.OnUsableVisibleListener;
import com.taobao.monitor.impl.data.tracker.TrafficTracker;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.processor.a;
import com.taobao.monitor.impl.processor.pageload.PageModelLifecycle;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FragmentFunctionDispatcher;
import com.taobao.monitor.impl.trace.FragmentFunctionListener;
import com.taobao.monitor.impl.trace.FragmentLifecycleDispatcher;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
import com.taobao.monitor.impl.trace.j;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LauncherProcessor extends AbsProcessor implements OnUsableVisibleListener<Activity>,
        PageModelLifecycle.a,
        ActivityEventDispatcher.EventListener,
        ApplicationBackgroundChangedDispatcher.BackgroundChangedListener,
        ApplicationGCDispatcher.GCListener,
        ApplicationLowMemoryDispatcher.LowMemoryListener,
        FragmentLifecycleDispatcher.LifecycleListener,
        FragmentFunctionListener,
        ImageStageDispatcher.StageListener,
        NetworkStageDispatcher.StageListener {
    public static volatile String sLaunchType = "COLD";
    public static boolean isBackgroundLaunch = false;
    private String lastTopActivityName;
    private String activityName;
    private Activity activity = null;
    private IProcedure launcherProcedure;
    private IDispatcher activityEventDispatcher;
    private IDispatcher applicationLowMemoryDispatcher;
    private IDispatcher activityUsableVisibleDispatcher;
    private IDispatcher activityFpsDispatcher;
    private IDispatcher applicationGcDispatcher;
    private IDispatcher applicationBackgroundChangedDispatcher;
    private IDispatcher networkStageDispatcher;
    private IDispatcher imageStageDispatcher;
    private List<String> simpleActivityNameList = new ArrayList<>(4);
    private List<Integer> b = new ArrayList<>();
    private int c = 0;
    private int l = 0;
    private long launchStartTime;
    private boolean u = false;
    private long[] flowBytes;
    private HashMap<String, Integer> b = new HashMap<>();
    private String launchType;
    private volatile boolean v;
    IAppLaunchListener appLaunchListener;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private boolean o;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean i;

    public LauncherProcessor() {
        super(false);
        this.launchType = sLaunchType;
        this.v = false;
        this.appLaunchListener = ApmImpl.instance().appLaunchListener();
        this.o = true;
        this.r = true;
        this.s = true;
        this.t = true;
        this.i = false;
    }

    protected void n() {
        super.n();
        this.flowBytes = TrafficTracker.flowBytes();
        (new AppLaunchHelper()).launchType(this.launchType);
        this.launcherProcedure = ProcedureManagerProxy.PROXY.getLauncherProcedure();
        if (this.launcherProcedure == null || !this.launcherProcedure.isAlive()) {
            ProcedureConfig procedureConfig = (new Builder())
                    .setIndependent(false)
                    .setUpload(true)
                    .setParentNeedStats(true)
                    .setParent(null)
                    .build();

            this.launcherProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/startup"), procedureConfig);
            this.launcherProcedure.begin();
            ProcedureManagerSetter.instance().setCurrentLauncherProcedure(this.launcherProcedure);
        }

        long var4 = TimeUtils.currentTimeMillis();
        this.launcherProcedure.stage("procedureStartTime", var4);
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
        this.addProperty();
        StartUpBeginEvent startUpBeginEvent = new StartUpBeginEvent();
        startUpBeginEvent.firstInstall = GlobalStats.isFirstInstall;
        startUpBeginEvent.launchType = sLaunchType;
        startUpBeginEvent.isBackgroundLaunch = isBackgroundLaunch;
        DumpManager.getInstance().append(startUpBeginEvent);
        isBackgroundLaunch = false;
    }

    private void addProperty() {
        this.launchStartTime = "COLD".equals(simpleActivityNameList) ? GlobalStats.launchStartTime : TimeUtils.currentTimeMillis();
        this.launcherProcedure.addProperty("errorCode", 1);
        this.launcherProcedure.addProperty("launchType", simpleActivityNameList);
        this.launcherProcedure.addProperty("isFirstInstall", GlobalStats.isFirstInstall);
        this.launcherProcedure.addProperty("isFirstLaunch", GlobalStats.isFirstLaunch);
        this.launcherProcedure.addProperty("installType", GlobalStats.installType);
        this.launcherProcedure.addProperty("oppoCPUResource", GlobalStats.oppoCPUResource);
        this.launcherProcedure.addProperty("leaveType", "other");
        this.launcherProcedure.addProperty("lastProcessStartTime", GlobalStats.lastProcessStartTime);
        this.launcherProcedure.addProperty("systemInitDuration", GlobalStats.launchStartTime - GlobalStats.processStartTime);
        this.launcherProcedure.stage("processStartTime", GlobalStats.processStartTime);
        this.launcherProcedure.stage("launchStartTime", GlobalStats.launchStartTime);
    }

    public void a(Activity activity, Bundle bundle, long var3) {
        String simpleName = ActivityUtils.getSimpleName(activity);
        this.activityName = ActivityUtils.getName(activity);
        if (!this.u) {
            this.activity = activity;
            this.n();
            this.launcherProcedure.addProperty("systemRecovery", false);
            if ("COLD".equals(sLaunchType) && this.activityName.equals(GlobalStats.lastTopActivity)) {
                this.launcherProcedure.addProperty("systemRecovery", true);
                this.lastTopActivityName = this.activityName;
                this.simpleActivityNameList.add(simpleName);
            }

            Intent intent = activity.getIntent();
            if (intent != null) {
                String var7 = intent.getDataString();
                if (!TextUtils.isEmpty(var7)) {
                    this.launcherProcedure.addProperty("schemaUrl", var7);
                    OpenAppFromURL openAppFromURL = new OpenAppFromURL();
                    openAppFromURL.url = var7;
                    openAppFromURL.time = var3;
                    DumpManager.getInstance().append(openAppFromURL);
                }
            }

            this.launcherProcedure.addProperty("firstPageName", simpleName);
            this.launcherProcedure.stage("firstPageCreateTime", var3);
            this.launchType = sLaunchType;
            sLaunchType = "HOT";
            this.u = true;
        }

        if (this.simpleActivityNameList.size() < 10 && TextUtils.isEmpty(this.lastTopActivityName)) {
            this.simpleActivityNameList.add(simpleName);
        }

        if (TextUtils.isEmpty(this.lastTopActivityName) && (PageList.isWhiteListEmpty() || PageList.inWhiteList(this.activityName))) {
            this.lastTopActivityName = this.activityName;
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("timestamp", var3);
        map.put("pageName", simpleName);
        this.launcherProcedure.event("onActivityCreated", map);
    }

    public void a(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.launcherProcedure.event("onActivityStarted", var4);
    }

    public void b(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.launcherProcedure.event("onActivityResumed", var4);
    }

    public void c(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.launcherProcedure.event("onActivityPaused", var4);
    }

    public void d(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.launcherProcedure.event("onActivityStopped", var4);
        if (var1 == this.lastTopActivityName) {
            this.o();
        }

    }

    public void e(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.launcherProcedure.event("onActivityDestroyed", var4);
        if (var1 == this.lastTopActivityName) {
            this.r = true;
            this.o();
        }

    }

    public void onLowMemory() {
        HashMap var1 = new HashMap(1);
        var1.put("timestamp", TimeUtils.currentTimeMillis());
        this.launcherProcedure.event("onLowMemory", var1);
    }

    public void a(Activity var1, MotionEvent var2, long var3) {
        if (this.o) {
            if (PageList.inBlackList(com.taobao.monitor.impl.util.a.a(var1))) {
                return;
            }

            if (TextUtils.isEmpty(this.lastTopActivityName)) {
                this.lastTopActivityName = com.taobao.monitor.impl.util.a.a(var1);
            }

            if (var1 == this.lastTopActivityName) {
                this.launcherProcedure.stage("firstInteractiveTime", var3);
                this.launcherProcedure.addProperty("firstInteractiveDuration", var3 - this.launchStartTime);
                this.launcherProcedure.addProperty("leaveType", "touch");
                this.launcherProcedure.addProperty("errorCode", 0);
                FirstInteractionEvent var7 = new FirstInteractionEvent();
                DumpManager.getInstance().append(var7);
                this.o = false;
            }
        }

    }

    public void f(Activity var1, long var2) {
        if (this.r && var1 == this.lastTopActivityName) {
            this.launcherProcedure.addProperty("appInitDuration", var2 - this.launchStartTime);
            this.launcherProcedure.stage("renderStartTime", var2);
            FirstDrawEvent var6 = new FirstDrawEvent();
            DumpManager.getInstance().append(var6);
            this.r = false;
            this.appLaunchListener.onLaunchChanged(this.a(), 0);
        }

    }

    private int a() {
        return this.f.equals("COLD") ? 0 : 1;
    }

    public void a(Activity var1, float var2, long var3) {
        if (var1 == this.lastTopActivityName) {
            this.launcherProcedure.addProperty("onRenderPercent", var2);
            this.launcherProcedure.addProperty("drawPercentTime", var3);
        }

    }

    public void a(Activity var1, int var2, int var3, long var4) {
        if (this.s) {
            if (var1 == this.lastTopActivityName && var2 == 2) {
                this.launcherProcedure.addProperty("errorCode", 0);
                this.launcherProcedure.addProperty("interactiveDuration", var4 - this.launchStartTime);
                this.launcherProcedure.addProperty("launchDuration", var4 - this.launchStartTime);
                this.launcherProcedure.addProperty("deviceLevel", AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
                this.launcherProcedure.addProperty("runtimeLevel", AliHAHardware.getInstance().getOutlineInfo().runtimeLevel);
                this.launcherProcedure.addProperty("cpuUsageOfDevcie", AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie);
                this.launcherProcedure.addProperty("memoryRuntimeLevel", AliHAHardware.getInstance().getMemoryInfo().runtimeLevel);
                this.launcherProcedure.addProperty("usableChangeType", var3);
                this.launcherProcedure.stage("interactiveTime", var4);
                LauncherUsableEvent var8 = new LauncherUsableEvent();
                var8.duration = (float) (var4 - this.launchStartTime);
                DumpManager.getInstance().append(var8);
                this.appLaunchListener.onLaunchChanged(this.a(), 2);
                this.q();
                this.s = false;
            }

        }
    }

    public void a(Activity var1, int var2, long var3) {
        if (this.t) {
            if (var2 == 2 && !PageList.inBlackList(this.e) && TextUtils.isEmpty(this.lastTopActivityName)) {
                this.lastTopActivityName = this.e;
            }

            if (var1 == this.lastTopActivityName && var2 == 2) {
                this.launcherProcedure.addProperty("displayDuration", var3 - this.launchStartTime);
                this.launcherProcedure.stage("displayedTime", var3);
                DisplayedEvent var7 = new DisplayedEvent();
                DumpManager.getInstance().append(var7);
                this.appLaunchListener.onLaunchChanged(this.a(), 1);
                this.t = false;
            }

        }
    }

    protected void o() {
        if (!this.launchStartTime) {
            this.launchStartTime = true;
            this.q();
            if (!TextUtils.isEmpty(this.lastTopActivityName)) {
                int var1 = this.lastTopActivityName.lastIndexOf(".");
                String var2 = this.lastTopActivityName.substring(var1 + 1);
                this.launcherProcedure.addProperty("currentPageName", var2);
                this.launcherProcedure.addProperty("fullPageName", this.lastTopActivityName);
            }

            this.launcherProcedure.addProperty("linkPageName", this.simpleActivityNameList.toString());
            this.simpleActivityNameList.clear();
            this.launcherProcedure.addProperty("hasSplash", GlobalStats.hasSplash);
            this.launcherProcedure.addStatistic("gcCount", this.l);
            this.launcherProcedure.addStatistic("fps", this.appLaunchListener.toString());
            this.launcherProcedure.addStatistic("jankCount", this.simpleActivityNameList);
            this.launcherProcedure.addStatistic("image", this.n);
            this.launcherProcedure.addStatistic("imageOnRequest", this.n);
            this.launcherProcedure.addStatistic("imageSuccessCount", this.o);
            this.launcherProcedure.addStatistic("imageFailedCount", this.p);
            this.launcherProcedure.addStatistic("imageCanceledCount", this.q);
            this.launcherProcedure.addStatistic("network", this.r);
            this.launcherProcedure.addStatistic("networkOnRequest", this.r);
            this.launcherProcedure.addStatistic("networkSuccessCount", this.s);
            this.launcherProcedure.addStatistic("networkFailedCount", this.t);
            this.launcherProcedure.addStatistic("networkCanceledCount", this.u);
            long[] var3 = com.taobao.monitor.impl.data.f.a.a();
            this.launcherProcedure.addStatistic("totalRx", var3[0] - this.simpleActivityNameList[0]);
            this.launcherProcedure.addStatistic("totalTx", var3[1] - this.simpleActivityNameList[1]);
            this.launcherProcedure.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            GlobalStats.hasSplash = false;
            this.f.removeListener(this);
            this.appLaunchListener.removeListener(this);
            this.lastTopActivityName.removeListener(this);
            this.simpleActivityNameList.removeListener(this);
            this.launcherProcedure.removeListener(this);
            this.e.removeListener(this);
            this.h.removeListener(this);
            this.g.removeListener(this);
            j.b.removeListener(this);
            this.launcherProcedure.end();
            StartUpEndEvent var4 = new StartUpEndEvent();
            DumpManager.getInstance().append(var4);
            super.o();
        }

    }

    public void b(int var1) {
        if (this.appLaunchListener.size() < 200) {
            this.appLaunchListener.add(var1);
        }

    }

    public void c(int var1) {
        this.simpleActivityNameList += var1;
    }

    public void gc() {
        ++this.l;
    }

    public void c(int var1, long var2) {
        if (var1 == 1) {
            HashMap var4 = new HashMap(1);
            var4.put("timestamp", var2);
            this.launcherProcedure.event("foreground2Background", var4);
            this.o();
        }

    }

    private void q() {
        if (!this.v) {
            this.appLaunchListener.onLaunchChanged(this.f.equals("COLD") ? 0 : 1, 4);
            this.v = true;
        }

    }

    public void a(Activity var1, KeyEvent var2, long var3) {
        if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(var1))) {
            if (var1 == this.lastTopActivityName) {
                int var5 = var2.getAction();
                int var6 = var2.getKeyCode();
                if (var5 == 0 && (var6 == 4 || var6 == 3)) {
                    if (TextUtils.isEmpty(this.lastTopActivityName)) {
                        this.lastTopActivityName = com.taobao.monitor.impl.util.a.a(var1);
                    }

                    if (var6 == 3) {
                        this.launcherProcedure.addProperty("leaveType", "home");
                    } else {
                        this.launcherProcedure.addProperty("leaveType", "back");
                    }

                    HashMap var7 = new HashMap(2);
                    var7.put("timestamp", var3);
                    var7.put("key", var2.getKeyCode());
                    this.launcherProcedure.event("keyEvent", var7);
                }
            }

        }
    }

    public void d(int var1) {
        if (var1 == 0) {
            ++this.n;
        } else if (var1 == 1) {
            ++this.o;
        } else if (var1 == 2) {
            ++this.p;
        } else if (var1 == 3) {
            ++this.q;
        }

    }

    public void e(int var1) {
        if (var1 == 0) {
            ++this.r;
        } else if (var1 == 1) {
            ++this.s;
        } else if (var1 == 2) {
            ++this.t;
        } else if (var1 == 3) {
            ++this.u;
        }

    }

    public void a(Activity var1, Fragment var2, String var3, long var4) {
        if (var2 != null) {
            if (var1 != null) {
                if (var1 == this.lastTopActivityName) {
                    String var6 = var2.getClass().getSimpleName();
                    String var7 = var6 + "_" + var3;
                    Integer var8 = (Integer) this.appLaunchListener.get(var7);
                    if (var8 == null) {
                        var8 = 0;
                    } else {
                        var8 = var8 + 1;
                    }

                    this.appLaunchListener.put(var7, var8);
                    this.launcherProcedure.stage(var7 + var8, var4);
                }
            }
        }
    }
}
