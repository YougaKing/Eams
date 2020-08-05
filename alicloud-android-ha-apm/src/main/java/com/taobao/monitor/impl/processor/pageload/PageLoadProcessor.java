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
import com.taobao.monitor.impl.data.traffic.TrafficTracker;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.trace.FragmentFunctionDispatcher;
import com.taobao.monitor.impl.trace.FragmentFunctionListener;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
/* compiled from: PageLoadProcessor */
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
    private static List<String> d = new ArrayList(4);
    private static String g = "";
    private FPSEvent a = new FPSEvent();

    /* renamed from: a reason: collision with other field name */
    private IDispatcher f94a;

    /* renamed from: a reason: collision with other field name */
    private IProcedure f95a;
    private IDispatcher b;

    /* renamed from: b reason: collision with other field name */
    private HashMap<String, Integer> f96b = new HashMap<>();

    /* renamed from: b reason: collision with other field name */
    private List<Integer> f97b = new ArrayList();

    /* renamed from: b reason: collision with other field name */
    private long[] f98b = new long[2];
    private int c = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher f99c;

    /* renamed from: c reason: collision with other field name */
    private long[] f100c;

    /* renamed from: d reason: collision with other field name */
    private Activity f101d = null;

    /* renamed from: d reason: collision with other field name */
    private IDispatcher f102d;
    private IDispatcher e;
    private long f;

    /* renamed from: f reason: collision with other field name */
    private IDispatcher f103f;

    /* renamed from: g reason: collision with other field name */
    private long f104g = -1;

    /* renamed from: g reason: collision with other field name */
    private IDispatcher f105g;
    private long h = 0;

    /* renamed from: h reason: collision with other field name */
    private IDispatcher f106h;
    private boolean i = false;
    private int l = 0;
    private int m = 0;
    private int n;
    private int o;

    /* renamed from: o reason: collision with other field name */
    private boolean f107o = true;
    private int p;

    /* renamed from: p reason: collision with other field name */
    private boolean f108p = true;
    private String pageName;
    private int q;

    /* renamed from: q reason: collision with other field name */
    private boolean f109q = true;
    private int r;

    /* renamed from: r reason: collision with other field name */
    private boolean f110r = true;
    private int s;

    /* renamed from: s reason: collision with other field name */
    private boolean f111s = true;
    private int t;

    /* renamed from: t reason: collision with other field name */
    private boolean f112t = true;
    private int u;

    public PageLoadProcessor() {
        super(false);
    }

    /* access modifiers changed from: protected */
    public void n() {
        super.n();
        this.f95a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), new Builder().setIndependent(false).setUpload(true).setParentNeedStats(true).setParent(null).build());
        this.f95a.begin();
        this.f94a = getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.b = getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.e = getDispatcher("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        this.f99c = getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.f102d = getDispatcher("APPLICATION_GC_DISPATCHER");
        this.f103f = getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.f105g = getDispatcher("NETWORK_STAGE_DISPATCHER");
        this.f106h = getDispatcher("IMAGE_STAGE_DISPATCHER");
        this.f102d.addListener(this);
        this.b.addListener(this);
        this.f94a.addListener(this);
        this.e.addListener(this);
        this.f99c.addListener(this);
        this.f103f.addListener(this);
        this.f105g.addListener(this);
        this.f106h.addListener(this);
        FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.addListener(this);
        p();
        this.f98b[0] = 0;
        this.f98b[1] = 0;
    }

    private void p() {
        this.f95a.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.f95a.addProperty("errorCode", Integer.valueOf(1));
        this.f95a.addProperty("installType", GlobalStats.installType);
        this.f95a.addProperty("leaveType", "other");
    }

    public void a(Activity activity, Bundle bundle, long j) {
        this.f = j;
        n();
        this.f95a.stage("loadStartTime", this.f);
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(this.f));
        this.f95a.event("onActivityCreated", hashMap);
        this.f101d = activity;
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.f95a);
        b(activity);
        this.f100c = TrafficTracker.traffics();
        OpenPageEvent openPageEvent = new OpenPageEvent();
        openPageEvent.pageName = ActivityUtils.getSimpleName(activity);
        DumpManager.getInstance().append(openPageEvent);
    }

    private void b(Activity activity) {
        this.pageName = ActivityUtils.getSimpleName(activity);
        if (d.size() < 10) {
            d.add(this.pageName);
        }
        this.f95a.addProperty("pageName", this.pageName);
        this.f95a.addProperty("fullPageName", ActivityUtils.getName(activity));
        if (!TextUtils.isEmpty(g)) {
            this.f95a.addProperty("fromPageName", g);
        }
        Intent intent = activity.getIntent();
        if (intent != null) {
            String dataString = intent.getDataString();
            if (!TextUtils.isEmpty(dataString)) {
                this.f95a.addProperty("schemaUrl", dataString);
            }
        }
        this.f95a.addProperty("isFirstLaunch", Boolean.valueOf(GlobalStats.isFirstLaunch));
        this.f95a.addProperty("isFirstLoad", Boolean.valueOf(GlobalStats.activityStatusManager.get(ActivityUtils.getName(activity))));
        this.f95a.addProperty("jumpTime", Long.valueOf(GlobalStats.jumpTime));
        GlobalStats.jumpTime = -1;
        this.f95a.addProperty("lastValidTime", Long.valueOf(GlobalStats.lastValidTime));
        this.f95a.addProperty("lastValidLinksPage", d.toString());
        this.f95a.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.f95a.addProperty("loadType", "push");
    }

    public void onResume(Activity activity, long j) {
        this.f109q = true;
        this.f104g = j;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f95a.event("onActivityStarted", hashMap);
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.f95a);
        g = this.pageName;
        if (this.f108p) {
            this.f108p = false;
            long[] a2 = TrafficTracker.traffics();
            long[] jArr = this.f98b;
            jArr[0] = jArr[0] + (a2[0] - this.f100c[0]);
            long[] jArr2 = this.f98b;
            jArr2[1] = jArr2[1] + (a2[1] - this.f100c[1]);
        }
        this.f100c = TrafficTracker.traffics();
        GlobalStats.lastValidPage = this.pageName;
        GlobalStats.lastValidTime = j;
    }

    public void onActivityResumed(Activity activity, long j) {
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.f95a);
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f95a.event("onActivityResumed", hashMap);
    }

    public void onActivityPaused(Activity activity, long j) {
        this.f109q = false;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f95a.event("onActivityPaused", hashMap);
    }

    public void onActivityStopped(Activity activity, long j) {
        this.h += j - this.f104g;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f95a.event("onActivityStopped", hashMap);
        long[] a2 = TrafficTracker.traffics();
        long[] jArr = this.f98b;
        jArr[0] = jArr[0] + (a2[0] - this.f100c[0]);
        long[] jArr2 = this.f98b;
        jArr2[1] = jArr2[1] + (a2[1] - this.f100c[1]);
        this.f100c = a2;
        if (this.f97b != null && this.m > this.f97b.size()) {
            Integer valueOf = Integer.valueOf(0);
            int i2 = this.m;
            while (true) {
                int i3 = i2;
                if (i3 >= this.f97b.size()) {
                    break;
                }
                valueOf = Integer.valueOf(((Integer) this.f97b.get(i3)).intValue() + valueOf.intValue());
                i2 = i3 + 1;
            }
            this.a.averageUseFps = (float) (valueOf.intValue() / (this.f97b.size() - this.m));
        }
        DumpManager.getInstance().append(this.a);
    }

    public void onActivityDestroyed(Activity activity, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f95a.event("onActivityDestroyed", hashMap);
        long[] a2 = TrafficTracker.traffics();
        long[] jArr = this.f98b;
        jArr[0] = jArr[0] + (a2[0] - this.f100c[0]);
        long[] jArr2 = this.f98b;
        jArr2[1] = jArr2[1] + (a2[1] - this.f100c[1]);
        FinishLoadPageEvent finishLoadPageEvent = new FinishLoadPageEvent();
        finishLoadPageEvent.pageName = ActivityUtils.getSimpleName(activity);
        DumpManager.getInstance().append(finishLoadPageEvent);
        o();
    }

    public void onLowMemory() {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.f95a.event("onLowMemory", hashMap);
        ReceiverLowMemoryEvent receiverLowMemoryEvent = new ReceiverLowMemoryEvent();
        receiverLowMemoryEvent.level = 1.0f;
        DumpManager.getInstance().append(receiverLowMemoryEvent);
    }

    public void onMotionEvent(Activity activity, MotionEvent motionEvent, long j) {
        if (activity == this.f101d) {
            if (this.f107o) {
                this.f95a.stage("firstInteractiveTime", j);
                this.f95a.addProperty("firstInteractiveDuration", Long.valueOf(j - this.f));
                this.f95a.addProperty("leaveType", "touch");
                this.f107o = false;
                this.f95a.addProperty("errorCode", Integer.valueOf(0));
            }
            d.clear();
            d.add(this.pageName);
            GlobalStats.lastValidPage = this.pageName;
            GlobalStats.lastValidTime = j;
        }
    }

    /* renamed from: f */
    public void onActivityStarted(Activity activity, long j) {
        if (this.f110r && activity == this.f101d) {
            this.f95a.addProperty("pageInitDuration", Long.valueOf(j - this.f));
            this.f95a.stage("renderStartTime", j);
            this.f110r = false;
        }
    }

    public void visiblePercent(Activity activity, float f2, long j) {
        if (activity == this.f101d) {
            this.f95a.addProperty("onRenderPercent", Float.valueOf(f2));
            this.f95a.addProperty("drawPercentTime", Long.valueOf(j));
        }
    }

    public void usable(Activity activity, int i2, int i3, long j) {
        if (this.f111s && activity == this.f101d && i2 == 2) {
            this.f95a.addProperty("interactiveDuration", Long.valueOf(j - this.f));
            this.f95a.addProperty("loadDuration", Long.valueOf(j - this.f));
            this.f95a.addProperty("usableChangeType", Integer.valueOf(i3));
            this.f95a.stage("interactiveTime", j);
            this.f95a.addProperty("errorCode", Integer.valueOf(0));
            this.f95a.addStatistic("totalRx", Long.valueOf(this.f98b[0]));
            this.f95a.addStatistic("totalTx", Long.valueOf(this.f98b[1]));
            this.f111s = false;
            UsableEvent usableEvent = new UsableEvent();
            usableEvent.duration = (float) (j - this.f);
            DumpManager.getInstance().append(usableEvent);
            if (this.f97b != null && this.f97b.size() != 0) {
                Integer valueOf = Integer.valueOf(0);
                Iterator it = this.f97b.iterator();
                while (true) {
                    Integer num = valueOf;
                    if (it.hasNext()) {
                        Integer num2 = (Integer) it.next();
                        valueOf = Integer.valueOf(num2.intValue() + num.intValue());
                    } else {
                        this.a.averageLoadFps = (float) (num.intValue() / this.f97b.size());
                        this.m = this.f97b.size();
                        return;
                    }
                }
            }
        }
    }

    public void display(Activity activity, int i2, long j) {
        if (this.f112t && activity == this.f101d && i2 == 2) {
            this.f95a.addProperty("displayDuration", Long.valueOf(j - this.f));
            this.f95a.stage("displayedTime", j);
            DumpManager.getInstance().append(new DisplayedEvent());
            this.f112t = false;
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        if (!this.i) {
            this.i = true;
            this.f95a.addProperty("totalVisibleDuration", Long.valueOf(this.h));
            this.f95a.addProperty("deviceLevel", Integer.valueOf(AliHAHardware.getInstance().getOutlineInfo().deviceLevel));
            this.f95a.addProperty("runtimeLevel", Integer.valueOf(AliHAHardware.getInstance().getOutlineInfo().runtimeLevel));
            this.f95a.addProperty("cpuUsageOfDevcie", Float.valueOf(AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie));
            this.f95a.addProperty("memoryRuntimeLevel", Integer.valueOf(AliHAHardware.getInstance().getMemoryInfo().runtimeLevel));
            this.f95a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            this.f95a.addStatistic("gcCount", Integer.valueOf(this.l));
            this.f95a.addStatistic("fps", this.f97b.toString());
            this.f95a.addStatistic("jankCount", Integer.valueOf(this.c));
            this.f95a.addStatistic("image", Integer.valueOf(this.n));
            this.f95a.addStatistic("imageOnRequest", Integer.valueOf(this.n));
            this.f95a.addStatistic("imageSuccessCount", Integer.valueOf(this.o));
            this.f95a.addStatistic("imageFailedCount", Integer.valueOf(this.p));
            this.f95a.addStatistic("imageCanceledCount", Integer.valueOf(this.q));
            this.f95a.addStatistic("network", Integer.valueOf(this.r));
            this.f95a.addStatistic("networkOnRequest", Integer.valueOf(this.r));
            this.f95a.addStatistic("networkSuccessCount", Integer.valueOf(this.s));
            this.f95a.addStatistic("networkFailedCount", Integer.valueOf(this.t));
            this.f95a.addStatistic("networkCanceledCount", Integer.valueOf(this.u));
            this.b.removeListener(this);
            this.f94a.removeListener(this);
            this.e.removeListener(this);
            this.f99c.removeListener(this);
            this.f102d.removeListener(this);
            this.f103f.removeListener(this);
            this.f106h.removeListener(this);
            this.f105g.removeListener(this);
            FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.removeListener(this);
            this.f95a.end();
            super.o();
        }
    }

    public void fps(int i2) {
        if (this.f97b.size() < 200 && this.f109q) {
            this.f97b.add(Integer.valueOf(i2));
        }
    }

    public void jank(int i2) {
        if (this.f109q) {
            this.c += i2;
            DumpManager.getInstance().append(new JankEvent());
        }
    }

    public void gc() {
        if (this.f109q) {
            this.l++;
            DumpManager.getInstance().append(new GCEvent());
        }
    }

    public void backgroundChanged(int i2, long j) {
        if (i2 == 1) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("timestamp", Long.valueOf(j));
            this.f95a.event("foreground2Background", hashMap);
            o();
            return;
        }
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("timestamp", Long.valueOf(j));
        this.f95a.event("background2Foreground", hashMap2);
    }

    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long j) {
        if (activity == this.f101d) {
            int action = keyEvent.getAction();
            int keyCode = keyEvent.getKeyCode();
            if (action != 0) {
                return;
            }
            if (keyCode == 4 || keyCode == 3) {
                if (keyCode == 3) {
                    this.f95a.addProperty("leaveType", "home");
                } else {
                    this.f95a.addProperty("leaveType", "back");
                }
                HashMap hashMap = new HashMap(2);
                hashMap.put("timestamp", Long.valueOf(j));
                hashMap.put("key", Integer.valueOf(keyEvent.getKeyCode()));
                this.f95a.event("keyEvent", hashMap);
            }
        }
    }

    public void imageStage(int i2) {
        if (!this.f109q) {
            return;
        }
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
        if (!this.f109q) {
            return;
        }
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
        if (fragment != null && activity == this.f101d) {
            String str2 = fragment.getClass().getSimpleName() + "_" + str;
            Integer num = (Integer) this.f96b.get(str2);
            if (num == null) {
                valueOf = Integer.valueOf(0);
            } else {
                valueOf = Integer.valueOf(num.intValue() + 1);
            }
            this.f96b.put(str2, valueOf);
            this.f95a.stage(str2 + valueOf, j);
        }
    }
}
