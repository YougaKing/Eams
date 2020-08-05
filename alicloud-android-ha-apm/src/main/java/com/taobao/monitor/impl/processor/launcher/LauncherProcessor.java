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
import com.taobao.monitor.impl.data.traffic.TrafficTracker;
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
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: LauncherProcessor */
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

    public static volatile String c = "COLD";
    public static boolean isBackgroundLaunch = false;
    private IDispatcher a;

    /* renamed from: a reason: collision with other field name */
    private IProcedure f71a;
    IAppLaunchListener b = ApmImpl.instance().m3a();

    /* renamed from: b reason: collision with other field name */
    private IDispatcher f72b;

    /* renamed from: b reason: collision with other field name */
    private HashMap<String, Integer> f73b = new HashMap<>();

    /* renamed from: b reason: collision with other field name */
    private List<Integer> f74b = new ArrayList();

    /* renamed from: c reason: collision with other field name */
    private int f75c = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher f76c;

    /* renamed from: c reason: collision with other field name */
    private List<String> f77c = new ArrayList(4);

    /* renamed from: c reason: collision with other field name */
    private long[] f78c;
    private Activity d = null;

    /* renamed from: d reason: collision with other field name */
    private IDispatcher f79d;

    /* renamed from: d reason: collision with other field name */
    private String f80d;
    private IDispatcher e;

    /* renamed from: e reason: collision with other field name */
    private String f81e;
    private IDispatcher f;

    /* renamed from: f reason: collision with other field name */
    private String f82f = c;
    private IDispatcher g;
    private IDispatcher h;
    private long i;

    /* renamed from: i reason: collision with other field name */
    private boolean f83i = false;
    private int l = 0;
    private int n;
    private int o;

    /* renamed from: o reason: collision with other field name */
    private boolean f84o = true;
    private int p;
    private int q;
    private int r;

    /* renamed from: r reason: collision with other field name */
    private boolean f85r = true;
    private int s;

    /* renamed from: s reason: collision with other field name */
    private boolean f86s = true;
    private int t;

    /* renamed from: t reason: collision with other field name */
    private boolean f87t = true;
    private int u;

    /* renamed from: u reason: collision with other field name */
    private boolean f88u = false;
    private volatile boolean v = false;

    public LauncherProcessor() {
        super(false);
    }

    /* access modifiers changed from: protected */
    public void n() {
        super.n();
        this.f78c = TrafficTracker.traffics();
        new AppLaunchHelper().a(this.f82f);
        this.f71a = ProcedureManagerProxy.PROXY.getLauncherProcedure();
        if (this.f71a == null || !this.f71a.isAlive()) {
            this.f71a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/startup"), new Builder().setIndependent(false).setUpload(true).setParentNeedStats(true).setParent(null).build());
            this.f71a.begin();
            ProcedureManagerSetter.instance().setCurrentLauncherProcedure(this.f71a);
        }
        this.f71a.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.a = getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.f72b = getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.e = getDispatcher("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        this.f76c = getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.f79d = getDispatcher("APPLICATION_GC_DISPATCHER");
        this.f = getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.g = getDispatcher("NETWORK_STAGE_DISPATCHER");
        this.h = getDispatcher("IMAGE_STAGE_DISPATCHER");
        this.f72b.addListener(this);
        this.f76c.addListener(this);
        this.f79d.addListener(this);
        this.a.addListener(this);
        this.e.addListener(this);
        this.f.addListener(this);
        this.g.addListener(this);
        this.h.addListener(this);
        FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.addListener(this);
        p();
        StartUpBeginEvent startUpBeginEvent = new StartUpBeginEvent();
        startUpBeginEvent.firstInstall = GlobalStats.isFirstInstall;
        startUpBeginEvent.launchType = c;
        startUpBeginEvent.isBackgroundLaunch = isBackgroundLaunch;
        DumpManager.getInstance().append(startUpBeginEvent);
        isBackgroundLaunch = false;
    }

    private void p() {
        this.i = "COLD".equals(c) ? GlobalStats.launchStartTime : TimeUtils.currentTimeMillis();
        this.f71a.addProperty("errorCode", Integer.valueOf(1));
        this.f71a.addProperty("launchType", c);
        this.f71a.addProperty("isFirstInstall", Boolean.valueOf(GlobalStats.isFirstInstall));
        this.f71a.addProperty("isFirstLaunch", Boolean.valueOf(GlobalStats.isFirstLaunch));
        this.f71a.addProperty("installType", GlobalStats.installType);
        this.f71a.addProperty("oppoCPUResource", GlobalStats.oppoCPUResource);
        this.f71a.addProperty("leaveType", "other");
        this.f71a.addProperty("lastProcessStartTime", Long.valueOf(GlobalStats.lastProcessStartTime));
        this.f71a.addProperty("systemInitDuration", Long.valueOf(GlobalStats.launchStartTime - GlobalStats.processStartTime));
        this.f71a.stage("processStartTime", GlobalStats.processStartTime);
        this.f71a.stage("launchStartTime", GlobalStats.launchStartTime);
    }

    public void onActivityCreated(Activity activity, Bundle bundle, long j) {
        String b2 = ActivityUtils.getSimpleName(activity);
        this.f81e = ActivityUtils.getName(activity);
        if (!this.f88u) {
            this.d = activity;
            n();
            this.f71a.addProperty("systemRecovery", Boolean.valueOf(false));
            if ("COLD".equals(c) && this.f81e.equals(GlobalStats.lastTopActivity)) {
                this.f71a.addProperty("systemRecovery", Boolean.valueOf(true));
                this.f80d = this.f81e;
                this.f77c.add(b2);
            }
            Intent intent = activity.getIntent();
            if (intent != null) {
                String dataString = intent.getDataString();
                if (!TextUtils.isEmpty(dataString)) {
                    this.f71a.addProperty("schemaUrl", dataString);
                    OpenAppFromURL openAppFromURL = new OpenAppFromURL();
                    openAppFromURL.url = dataString;
                    openAppFromURL.time = j;
                    DumpManager.getInstance().append(openAppFromURL);
                }
            }
            this.f71a.addProperty("firstPageName", b2);
            this.f71a.stage("firstPageCreateTime", j);
            this.f82f = c;
            c = "HOT";
            this.f88u = true;
        }
        if (this.f77c.size() < 10 && TextUtils.isEmpty(this.f80d)) {
            this.f77c.add(b2);
        }
        if (TextUtils.isEmpty(this.f80d) && (PageList.isWhiteListEmpty() || PageList.inWhiteList(this.f81e))) {
            this.f80d = this.f81e;
        }
        HashMap hashMap = new HashMap(2);
        hashMap.put("timestamp", Long.valueOf(j));
        hashMap.put("pageName", b2);
        this.f71a.event("onActivityCreated", hashMap);
    }

    public void onResume(Activity activity, long j) {
        HashMap hashMap = new HashMap(2);
        hashMap.put("timestamp", Long.valueOf(j));
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.f71a.event("onActivityStarted", hashMap);
    }

    public void onActivityResumed(Activity activity, long j) {
        HashMap hashMap = new HashMap(2);
        hashMap.put("timestamp", Long.valueOf(j));
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.f71a.event("onActivityResumed", hashMap);
    }

    public void onActivityPaused(Activity activity, long j) {
        HashMap hashMap = new HashMap(2);
        hashMap.put("timestamp", Long.valueOf(j));
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.f71a.event("onActivityPaused", hashMap);
    }

    public void onActivityStopped(Activity activity, long j) {
        HashMap hashMap = new HashMap(2);
        hashMap.put("timestamp", Long.valueOf(j));
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.f71a.event("onActivityStopped", hashMap);
        if (activity == this.d) {
            o();
        }
    }

    public void onActivityDestroyed(Activity activity, long j) {
        HashMap hashMap = new HashMap(2);
        hashMap.put("timestamp", Long.valueOf(j));
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.f71a.event("onActivityDestroyed", hashMap);
        if (activity == this.d) {
            this.f85r = true;
            o();
        }
    }

    public void onLowMemory() {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.f71a.event("onLowMemory", hashMap);
    }

    public void onMotionEvent(Activity activity, MotionEvent motionEvent, long j) {
        if (this.f84o && !PageList.inBlackList(ActivityUtils.getName(activity))) {
            if (TextUtils.isEmpty(this.f80d)) {
                this.f80d = ActivityUtils.getName(activity);
            }
            if (activity == this.d) {
                this.f71a.stage("firstInteractiveTime", j);
                this.f71a.addProperty("firstInteractiveDuration", Long.valueOf(j - this.i));
                this.f71a.addProperty("leaveType", "touch");
                this.f71a.addProperty("errorCode", Integer.valueOf(0));
                DumpManager.getInstance().append(new FirstInteractionEvent());
                this.f84o = false;
            }
        }
    }

    /* renamed from: f */
    public void onActivityStarted(Activity activity, long j) {
        if (this.f85r && activity == this.d) {
            this.f71a.addProperty("appInitDuration", Long.valueOf(j - this.i));
            this.f71a.stage("renderStartTime", j);
            DumpManager.getInstance().append(new FirstDrawEvent());
            this.f85r = false;
            this.b.onLaunchChanged(a(), 0);
        }
    }

    private int a() {
        return this.f82f.equals("COLD") ? 0 : 1;
    }

    public void visiblePercent(Activity activity, float f2, long j) {
        if (activity == this.d) {
            this.f71a.addProperty("onRenderPercent", Float.valueOf(f2));
            this.f71a.addProperty("drawPercentTime", Long.valueOf(j));
        }
    }

    public void usable(Activity activity, int i2, int i3, long j) {
        if (this.f86s && activity == this.d && i2 == 2) {
            this.f71a.addProperty("errorCode", Integer.valueOf(0));
            this.f71a.addProperty("interactiveDuration", Long.valueOf(j - this.i));
            this.f71a.addProperty("launchDuration", Long.valueOf(j - this.i));
            this.f71a.addProperty("deviceLevel", Integer.valueOf(AliHAHardware.getInstance().getOutlineInfo().deviceLevel));
            this.f71a.addProperty("runtimeLevel", Integer.valueOf(AliHAHardware.getInstance().getOutlineInfo().runtimeLevel));
            this.f71a.addProperty("cpuUsageOfDevcie", Float.valueOf(AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie));
            this.f71a.addProperty("memoryRuntimeLevel", Integer.valueOf(AliHAHardware.getInstance().getMemoryInfo().runtimeLevel));
            this.f71a.addProperty("usableChangeType", Integer.valueOf(i3));
            this.f71a.stage("interactiveTime", j);
            LauncherUsableEvent launcherUsableEvent = new LauncherUsableEvent();
            launcherUsableEvent.duration = (float) (j - this.i);
            DumpManager.getInstance().append(launcherUsableEvent);
            this.b.onLaunchChanged(a(), 2);
            q();
            this.f86s = false;
        }
    }

    public void display(Activity activity, int i2, long j) {
        if (this.f87t) {
            if (i2 == 2 && !PageList.inBlackList(this.f81e) && TextUtils.isEmpty(this.f80d)) {
                this.f80d = this.f81e;
            }
            if (activity == this.d && i2 == 2) {
                this.f71a.addProperty("displayDuration", Long.valueOf(j - this.i));
                this.f71a.stage("displayedTime", j);
                DumpManager.getInstance().append(new DisplayedEvent());
                this.b.onLaunchChanged(a(), 1);
                this.f87t = false;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        if (!this.f83i) {
            this.f83i = true;
            q();
            if (!TextUtils.isEmpty(this.f80d)) {
                this.f71a.addProperty("currentPageName", this.f80d.substring(this.f80d.lastIndexOf(".") + 1));
                this.f71a.addProperty("fullPageName", this.f80d);
            }
            this.f71a.addProperty("linkPageName", this.f77c.toString());
            this.f77c.clear();
            this.f71a.addProperty("hasSplash", Boolean.valueOf(GlobalStats.hasSplash));
            this.f71a.addStatistic("gcCount", Integer.valueOf(this.l));
            this.f71a.addStatistic("fps", this.f74b.toString());
            this.f71a.addStatistic("jankCount", Integer.valueOf(this.f75c));
            this.f71a.addStatistic("image", Integer.valueOf(this.n));
            this.f71a.addStatistic("imageOnRequest", Integer.valueOf(this.n));
            this.f71a.addStatistic("imageSuccessCount", Integer.valueOf(this.o));
            this.f71a.addStatistic("imageFailedCount", Integer.valueOf(this.p));
            this.f71a.addStatistic("imageCanceledCount", Integer.valueOf(this.q));
            this.f71a.addStatistic("network", Integer.valueOf(this.r));
            this.f71a.addStatistic("networkOnRequest", Integer.valueOf(this.r));
            this.f71a.addStatistic("networkSuccessCount", Integer.valueOf(this.s));
            this.f71a.addStatistic("networkFailedCount", Integer.valueOf(this.t));
            this.f71a.addStatistic("networkCanceledCount", Integer.valueOf(this.u));
            long[] a2 = TrafficTracker.traffics();
            this.f71a.addStatistic("totalRx", Long.valueOf(a2[0] - this.f78c[0]));
            this.f71a.addStatistic("totalTx", Long.valueOf(a2[1] - this.f78c[1]));
            this.f71a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            GlobalStats.hasSplash = false;
            this.f.removeListener(this);
            this.f72b.removeListener(this);
            this.f79d.removeListener(this);
            this.f76c.removeListener(this);
            this.a.removeListener(this);
            this.e.removeListener(this);
            this.h.removeListener(this);
            this.g.removeListener(this);
            FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.removeListener(this);
            this.f71a.end();
            DumpManager.getInstance().append(new StartUpEndEvent());
            super.o();
        }
    }

    public void fps(int i2) {
        if (this.f74b.size() < 200) {
            this.f74b.add(Integer.valueOf(i2));
        }
    }

    public void jank(int i2) {
        this.f75c += i2;
    }

    public void gc() {
        this.l++;
    }

    public void backgroundChanged(int i2, long j) {
        if (i2 == 1) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("timestamp", Long.valueOf(j));
            this.f71a.event("foreground2Background", hashMap);
            o();
        }
    }

    private void q() {
        int i2;
        if (!this.v) {
            IAppLaunchListener iAppLaunchListener = this.b;
            if (this.f82f.equals("COLD")) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            iAppLaunchListener.onLaunchChanged(i2, 4);
            this.v = true;
        }
    }

    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long j) {
        if (!PageList.inBlackList(ActivityUtils.getName(activity)) && activity == this.d) {
            int action = keyEvent.getAction();
            int keyCode = keyEvent.getKeyCode();
            if (action != 0) {
                return;
            }
            if (keyCode == 4 || keyCode == 3) {
                if (TextUtils.isEmpty(this.f80d)) {
                    this.f80d = ActivityUtils.getName(activity);
                }
                if (keyCode == 3) {
                    this.f71a.addProperty("leaveType", "home");
                } else {
                    this.f71a.addProperty("leaveType", "back");
                }
                HashMap hashMap = new HashMap(2);
                hashMap.put("timestamp", Long.valueOf(j));
                hashMap.put("key", Integer.valueOf(keyEvent.getKeyCode()));
                this.f71a.event("keyEvent", hashMap);
            }
        }
    }

    public void imageStage(int i2) {
        if (i2 == 0) {
            this.n++;
        } else if (i2 == 1) {
            this.o++;
        } else if (i2 == 2) {
            this.p++;
        } else if (i2 == 3) {
            this.q++;
        }
    }

    public void networkStage(int i2) {
        if (i2 == 0) {
            this.r++;
        } else if (i2 == 1) {
            this.s++;
        } else if (i2 == 2) {
            this.t++;
        } else if (i2 == 3) {
            this.u++;
        }
    }

    public void onFragmentAttached(Activity activity, Fragment fragment, String str, long j) {
        Integer valueOf;
        if (fragment != null && activity != null && activity == this.d) {
            String str2 = fragment.getClass().getSimpleName() + "_" + str;
            Integer num = (Integer) this.f73b.get(str2);
            if (num == null) {
                valueOf = Integer.valueOf(0);
            } else {
                valueOf = Integer.valueOf(num.intValue() + 1);
            }
            this.f73b.put(str2, valueOf);
            this.f71a.stage(str2 + valueOf, j);
        }
    }
}
