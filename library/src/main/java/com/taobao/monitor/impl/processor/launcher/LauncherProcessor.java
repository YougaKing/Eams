//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.launcher;

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
import com.ali.ha.fulltrace.event.FirstDrawEvent;
import com.ali.ha.fulltrace.event.FirstInteractionEvent;
import com.ali.ha.fulltrace.event.LauncherUsableEvent;
import com.ali.ha.fulltrace.event.OpenAppFromURL;
import com.ali.ha.fulltrace.event.StartUpBeginEvent;
import com.ali.ha.fulltrace.event.StartUpEndEvent;
import com.taobao.application.common.IAppLaunchListener;
import com.taobao.application.common.data.AppLaunchHelper;
import com.taobao.application.common.impl.ApmImpl;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.OnUsableVisibleListener;
import com.taobao.monitor.impl.data.tracker.TrafficTracker;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.processor.pageload.PageModelLifecycle;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
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
import com.taobao.monitor.procedure.ProcedureManagerProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LauncherProcessor extends AbsProcessor implements OnUsableVisibleListener<Activity>,
        PageModelLifecycle.ModelLifecycleListener,
        ActivityEventDispatcher.EventListener,
        ApplicationBackgroundChangedDispatcher.BackgroundChangedListener,
        ApplicationGCDispatcher.GCListener,
        ApplicationLowMemoryDispatcher.LowMemoryListener,
        FPSDispatcher.FPSListener,
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
    private List<Integer> intList = new ArrayList<>();
    private int jankCount = 0;
    private int gcCount = 0;
    private long launchStartTime;
    private boolean u = false;
    private long[] flowBytes;
    private HashMap<String, Integer> fpsMap = new HashMap<>();
    private String launchType;
    private volatile boolean v;
    IAppLaunchListener appLaunchListener;
    private int imageCount;
    private int imageSuccessCount;
    private int imageFailedCount;
    private int imageCanceledCount;
    private int networkCount;
    private int networkSuccessCount;
    private int networkFailedCount;
    private int networkCanceledCount;
    private boolean firstInteractive;
    private boolean appInit;
    private boolean interactive;
    private boolean display;
    private boolean procedure;

    public LauncherProcessor() {
        super(false);
        this.launchType = sLaunchType;
        this.v = false;
        this.appLaunchListener = ApmImpl.instance().appLaunchListener();
        this.firstInteractive = true;
        this.appInit = true;
        this.interactive = true;
        this.display = true;
        this.procedure = false;
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

    public void onActivityCreated(Activity activity, Bundle bundle, long var3) {
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

    @Override
    public void onActivityStarted(Activity var1, long var2) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("timestamp", var2);
        map.put("pageName", ActivityUtils.getSimpleName(var1));
        this.launcherProcedure.event("onActivityStarted", map);
    }

    @Override
    public void onActivityResumed(Activity var1, long var2) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("timestamp", var2);
        map.put("pageName", ActivityUtils.getSimpleName(var1));
        this.launcherProcedure.event("onActivityResumed", map);
    }

    @Override
    public void onActivityPaused(Activity var1, long var2) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("timestamp", var2);
        map.put("pageName", ActivityUtils.getSimpleName(var1));
        this.launcherProcedure.event("onActivityPaused", map);
    }

    @Override
    public void onActivityStopped(Activity var1, long var2) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("timestamp", var2);
        map.put("pageName", ActivityUtils.getSimpleName(var1));
        this.launcherProcedure.event("onActivityStopped", map);
        if (var1 == this.activity) {
            this.o();
        }

    }

    @Override
    public void onActivityDestroyed(Activity var1, long var2) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("timestamp", var2);
        map.put("pageName", ActivityUtils.getSimpleName(var1));
        this.launcherProcedure.event("onActivityDestroyed", map);
        if (var1 == this.activity) {
            this.appInit = true;
            this.o();
        }

    }

    @Override
    public void onLowMemory() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("timestamp", TimeUtils.currentTimeMillis());
        this.launcherProcedure.event("onLowMemory", map);
    }

    @Override
    public void onMotionEvent(Activity var1, MotionEvent var2, long var3) {
        if (this.firstInteractive) {
            if (PageList.inBlackList(ActivityUtils.getName(var1))) {
                return;
            }

            if (TextUtils.isEmpty(this.lastTopActivityName)) {
                this.lastTopActivityName = ActivityUtils.getName(var1);
            }

            if (var1 == this.activity) {
                this.launcherProcedure.stage("firstInteractiveTime", var3);
                this.launcherProcedure.addProperty("firstInteractiveDuration", var3 - this.launchStartTime);
                this.launcherProcedure.addProperty("leaveType", "touch");
                this.launcherProcedure.addProperty("errorCode", 0);
                FirstInteractionEvent var7 = new FirstInteractionEvent();
                DumpManager.getInstance().append(var7);
                this.firstInteractive = false;
            }
        }
    }

    public void f(Activity var1, long var2) {
        if (this.appInit && var1 == this.activity) {
            this.launcherProcedure.addProperty("appInitDuration", var2 - this.launchStartTime);
            this.launcherProcedure.stage("renderStartTime", var2);
            FirstDrawEvent var6 = new FirstDrawEvent();
            DumpManager.getInstance().append(var6);
            this.appInit = false;
            this.appLaunchListener.onLaunchChanged(this.a(), 0);
        }
    }

    private int a() {
        return this.launchType.equals("COLD") ? 0 : 1;
    }

    public void a(Activity var1, float var2, long var3) {
        if (var1 == this.activity) {
            this.launcherProcedure.addProperty("onRenderPercent", var2);
            this.launcherProcedure.addProperty("drawPercentTime", var3);
        }

    }

    public void a(Activity var1, int var2, int var3, long var4) {
        if (this.interactive) {
            if (var1 == this.activity && var2 == 2) {
                this.launcherProcedure.addProperty("errorCode", 0);
                this.launcherProcedure.addProperty("interactiveDuration", var4 - this.launchStartTime);
                this.launcherProcedure.addProperty("launchDuration", var4 - this.launchStartTime);
                this.launcherProcedure.addProperty("deviceLevel", AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
                this.launcherProcedure.addProperty("runtimeLevel", AliHAHardware.getInstance().getOutlineInfo().runtimeLevel);
                this.launcherProcedure.addProperty("cpuUsageOfDevcie", AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie);
                this.launcherProcedure.addProperty("memoryRuntimeLevel", AliHAHardware.getInstance().getMemoryInfo().runtimeLevel);
                this.launcherProcedure.addProperty("usableChangeType", var3);
                this.launcherProcedure.stage("interactiveTime", var4);
                LauncherUsableEvent launcherUsableEvent = new LauncherUsableEvent();
                launcherUsableEvent.duration = (float) (var4 - this.launchStartTime);
                DumpManager.getInstance().append(launcherUsableEvent);
                this.appLaunchListener.onLaunchChanged(this.a(), 2);
                this.q();
                this.interactive = false;
            }
        }
    }

    public void a(Activity var1, int var2, long var3) {
        if (this.display) {
            if (var2 == 2 && !PageList.inBlackList(this.activityName) && TextUtils.isEmpty(this.lastTopActivityName)) {
                this.lastTopActivityName = this.activityName;
            }

            if (var1 == this.activity && var2 == 2) {
                this.launcherProcedure.addProperty("displayDuration", var3 - this.launchStartTime);
                this.launcherProcedure.stage("displayedTime", var3);
                DisplayedEvent var7 = new DisplayedEvent();
                DumpManager.getInstance().append(var7);
                this.appLaunchListener.onLaunchChanged(this.a(), 1);
                this.display = false;
            }

        }
    }

    protected void o() {
        if (!this.procedure) {
            this.procedure = true;
            this.q();
            if (!TextUtils.isEmpty(this.lastTopActivityName)) {
                int index = this.lastTopActivityName.lastIndexOf(".");
                String currentPageName = this.lastTopActivityName.substring(index + 1);
                this.launcherProcedure.addProperty("currentPageName", currentPageName);
                this.launcherProcedure.addProperty("fullPageName", this.lastTopActivityName);
            }

            this.launcherProcedure.addProperty("linkPageName", this.simpleActivityNameList.toString());
            this.simpleActivityNameList.clear();
            this.launcherProcedure.addProperty("hasSplash", GlobalStats.hasSplash);
            this.launcherProcedure.addStatistic("gcCount", this.gcCount);
            this.launcherProcedure.addStatistic("fps", this.fpsMap.toString());
            this.launcherProcedure.addStatistic("jankCount", this.jankCount);
            this.launcherProcedure.addStatistic("image", this.imageCount);
            this.launcherProcedure.addStatistic("imageOnRequest", this.imageCount);
            this.launcherProcedure.addStatistic("imageSuccessCount", this.imageSuccessCount);
            this.launcherProcedure.addStatistic("imageFailedCount", this.imageFailedCount);
            this.launcherProcedure.addStatistic("imageCanceledCount", this.imageCanceledCount);
            this.launcherProcedure.addStatistic("network", this.networkCount);
            this.launcherProcedure.addStatistic("networkOnRequest", this.networkCount);
            this.launcherProcedure.addStatistic("networkSuccessCount", this.networkSuccessCount);
            this.launcherProcedure.addStatistic("networkFailedCount", this.networkFailedCount);
            this.launcherProcedure.addStatistic("networkCanceledCount", this.networkCanceledCount);
            long[] flowBytes = TrafficTracker.flowBytes();
            this.launcherProcedure.addStatistic("totalRx", flowBytes[0] - this.flowBytes[0]);
            this.launcherProcedure.addStatistic("totalTx", flowBytes[1] - this.flowBytes[1]);
            this.launcherProcedure.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            GlobalStats.hasSplash = false;
            this.applicationLowMemoryDispatcher.removeListener(this);
            this.activityFpsDispatcher.removeListener(this);
            this.applicationGcDispatcher.removeListener(this);
            this.activityEventDispatcher.removeListener(this);
            this.activityUsableVisibleDispatcher.removeListener(this);
            this.applicationBackgroundChangedDispatcher.removeListener(this);
            this.networkStageDispatcher.removeListener(this);
            this.imageStageDispatcher.removeListener(this);
            FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.removeListener(this);
            this.launcherProcedure.end();
            StartUpEndEvent startUpEndEvent = new StartUpEndEvent();
            DumpManager.getInstance().append(startUpEndEvent);
            super.o();
        }

    }

    public void b(int var1) {
        if (this.intList.size() < 200) {
            this.intList.add(var1);
        }
    }

    public void c(int jankCount) {
        this.jankCount += jankCount;
    }

    @Override
    public void gc() {
        ++this.gcCount;
    }

    @Override
    public void backgroundChanged(int var1, long var2) {
        if (var1 == 1) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("timestamp", var2);
            this.launcherProcedure.event("foreground2Background", map);
            this.o();
        }

    }

    private void q() {
        if (!this.v) {
            this.appLaunchListener.onLaunchChanged(this.launchType.equals("COLD") ? 0 : 1, 4);
            this.v = true;
        }
    }

    @Override
    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long var3) {
        if (!PageList.inBlackList(ActivityUtils.getName(activity))) {
            if (activity == this.activity) {
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                if (action == 0 && (keyCode == 4 || keyCode == 3)) {
                    if (TextUtils.isEmpty(this.lastTopActivityName)) {
                        this.lastTopActivityName = ActivityUtils.getName(activity);
                    }
                    if (keyCode == 3) {
                        this.launcherProcedure.addProperty("leaveType", "home");
                    } else {
                        this.launcherProcedure.addProperty("leaveType", "back");
                    }
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("timestamp", var3);
                    map.put("key", keyEvent.getKeyCode());
                    this.launcherProcedure.event("keyEvent", map);
                }
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

    public void a(Activity activity, Fragment fragment, String var3, long time) {
        if (fragment != null) {
            if (activity != null) {
                if (activity == this.activity) {
                    String simpleName = fragment.getClass().getSimpleName();
                    String var7 = simpleName + "_" + var3;
                    Integer fps = this.fpsMap.get(var7);
                    if (fps == null) {
                        fps = 0;
                    } else {
                        fps = fps + 1;
                    }
                    this.fpsMap.put(var7, fps);
                    this.launcherProcedure.stage(var7 + fps, time);
                }
            }
        }
    }
}
