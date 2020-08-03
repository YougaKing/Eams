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
import com.taobao.application.common.data.c;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.h;
import com.taobao.monitor.impl.processor.a;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.j;
import com.taobao.monitor.impl.trace.k;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LauncherProcessor extends a implements h<Activity>, com.taobao.monitor.impl.processor.pageload.e.a, com.taobao.monitor.impl.trace.b.a, com.taobao.monitor.impl.trace.d.a, com.taobao.monitor.impl.trace.e.a, com.taobao.monitor.impl.trace.f.a, com.taobao.monitor.impl.trace.i.a, k, com.taobao.monitor.impl.trace.m.a, com.taobao.monitor.impl.trace.n.a {
    public static volatile String c = "COLD";
    public static boolean isBackgroundLaunch = false;
    private String d;
    private String e;
    private Activity d = null;
    private IProcedure a;
    private IDispatcher a;
    private IDispatcher b;
    private IDispatcher e;
    private IDispatcher c;
    private IDispatcher d;
    private IDispatcher f;
    private IDispatcher g;
    private IDispatcher h;
    private List<String> c = new ArrayList(4);
    private List<Integer> b = new ArrayList();
    private int c = 0;
    private int l = 0;
    private long i;
    private boolean u = false;
    private long[] c;
    private HashMap<String, Integer> b = new HashMap();
    private String f;
    private volatile boolean v;
    IAppLaunchListener b;
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

    public b() {
        super(false);
        this.f = c;
        this.v = false;
        this.b = com.taobao.application.common.impl.b.a().a();
        this.o = true;
        this.r = true;
        this.s = true;
        this.t = true;
        this.i = false;
    }

    protected void n() {
        super.n();
        this.c = com.taobao.monitor.impl.data.f.a.a();
        (new c()).a(this.f);
        this.a = ProcedureManagerProxy.PROXY.getLauncherProcedure();
        if (this.a == null || !this.a.isAlive()) {
            ProcedureConfig var1 = (new Builder()).setIndependent(false).setUpload(true).setParentNeedStats(true).setParent((IProcedure)null).build();
            this.a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/startup"), var1);
            this.a.begin();
            ProcedureManagerSetter.instance().setCurrentLauncherProcedure(this.a);
        }

        long var4 = TimeUtils.currentTimeMillis();
        this.a.stage("procedureStartTime", var4);
        this.a = this.a("ACTIVITY_EVENT_DISPATCHER");
        this.b = this.a("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.e = this.a("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        this.c = this.a("ACTIVITY_FPS_DISPATCHER");
        this.d = this.a("APPLICATION_GC_DISPATCHER");
        this.f = this.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.g = this.a("NETWORK_STAGE_DISPATCHER");
        this.h = this.a("IMAGE_STAGE_DISPATCHER");
        this.b.addListener(this);
        this.c.addListener(this);
        this.d.addListener(this);
        this.a.addListener(this);
        this.e.addListener(this);
        this.f.addListener(this);
        this.g.addListener(this);
        this.h.addListener(this);
        j.b.addListener(this);
        this.p();
        StartUpBeginEvent var3 = new StartUpBeginEvent();
        var3.firstInstall = GlobalStats.isFirstInstall;
        var3.launchType = c;
        var3.isBackgroundLaunch = isBackgroundLaunch;
        DumpManager.getInstance().append(var3);
        isBackgroundLaunch = false;
    }

    private void p() {
        this.i = "COLD".equals(c) ? GlobalStats.launchStartTime : TimeUtils.currentTimeMillis();
        this.a.addProperty("errorCode", 1);
        this.a.addProperty("launchType", c);
        this.a.addProperty("isFirstInstall", GlobalStats.isFirstInstall);
        this.a.addProperty("isFirstLaunch", GlobalStats.isFirstLaunch);
        this.a.addProperty("installType", GlobalStats.installType);
        this.a.addProperty("oppoCPUResource", GlobalStats.oppoCPUResource);
        this.a.addProperty("leaveType", "other");
        this.a.addProperty("lastProcessStartTime", GlobalStats.lastProcessStartTime);
        this.a.addProperty("systemInitDuration", GlobalStats.launchStartTime - GlobalStats.processStartTime);
        this.a.stage("processStartTime", GlobalStats.processStartTime);
        this.a.stage("launchStartTime", GlobalStats.launchStartTime);
    }

    public void a(Activity var1, Bundle var2, long var3) {
        String var5 = com.taobao.monitor.impl.util.a.b(var1);
        this.e = com.taobao.monitor.impl.util.a.a(var1);
        if (!this.u) {
            this.d = var1;
            this.n();
            this.a.addProperty("systemRecovery", false);
            if ("COLD".equals(c) && this.e.equals(GlobalStats.lastTopActivity)) {
                this.a.addProperty("systemRecovery", true);
                this.d = this.e;
                this.c.add(var5);
            }

            Intent var6 = var1.getIntent();
            if (var6 != null) {
                String var7 = var6.getDataString();
                if (!TextUtils.isEmpty(var7)) {
                    this.a.addProperty("schemaUrl", var7);
                    OpenAppFromURL var8 = new OpenAppFromURL();
                    var8.url = var7;
                    var8.time = var3;
                    DumpManager.getInstance().append(var8);
                }
            }

            this.a.addProperty("firstPageName", var5);
            this.a.stage("firstPageCreateTime", var3);
            this.f = c;
            c = "HOT";
            this.u = true;
        }

        if (this.c.size() < 10 && TextUtils.isEmpty(this.d)) {
            this.c.add(var5);
        }

        if (TextUtils.isEmpty(this.d) && (PageList.isWhiteListEmpty() || PageList.inWhiteList(this.e))) {
            this.d = this.e;
        }

        HashMap var9 = new HashMap(2);
        var9.put("timestamp", var3);
        var9.put("pageName", var5);
        this.a.event("onActivityCreated", var9);
    }

    public void a(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.a.event("onActivityStarted", var4);
    }

    public void b(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.a.event("onActivityResumed", var4);
    }

    public void c(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.a.event("onActivityPaused", var4);
    }

    public void d(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.a.event("onActivityStopped", var4);
        if (var1 == this.d) {
            this.o();
        }

    }

    public void e(Activity var1, long var2) {
        HashMap var4 = new HashMap(2);
        var4.put("timestamp", var2);
        var4.put("pageName", com.taobao.monitor.impl.util.a.b(var1));
        this.a.event("onActivityDestroyed", var4);
        if (var1 == this.d) {
            this.r = true;
            this.o();
        }

    }

    public void onLowMemory() {
        HashMap var1 = new HashMap(1);
        var1.put("timestamp", TimeUtils.currentTimeMillis());
        this.a.event("onLowMemory", var1);
    }

    public void a(Activity var1, MotionEvent var2, long var3) {
        if (this.o) {
            if (PageList.inBlackList(com.taobao.monitor.impl.util.a.a(var1))) {
                return;
            }

            if (TextUtils.isEmpty(this.d)) {
                this.d = com.taobao.monitor.impl.util.a.a(var1);
            }

            if (var1 == this.d) {
                this.a.stage("firstInteractiveTime", var3);
                this.a.addProperty("firstInteractiveDuration", var3 - this.i);
                this.a.addProperty("leaveType", "touch");
                this.a.addProperty("errorCode", 0);
                FirstInteractionEvent var7 = new FirstInteractionEvent();
                DumpManager.getInstance().append(var7);
                this.o = false;
            }
        }

    }

    public void f(Activity var1, long var2) {
        if (this.r && var1 == this.d) {
            this.a.addProperty("appInitDuration", var2 - this.i);
            this.a.stage("renderStartTime", var2);
            FirstDrawEvent var6 = new FirstDrawEvent();
            DumpManager.getInstance().append(var6);
            this.r = false;
            this.b.onLaunchChanged(this.a(), 0);
        }

    }

    private int a() {
        return this.f.equals("COLD") ? 0 : 1;
    }

    public void a(Activity var1, float var2, long var3) {
        if (var1 == this.d) {
            this.a.addProperty("onRenderPercent", var2);
            this.a.addProperty("drawPercentTime", var3);
        }

    }

    public void a(Activity var1, int var2, int var3, long var4) {
        if (this.s) {
            if (var1 == this.d && var2 == 2) {
                this.a.addProperty("errorCode", 0);
                this.a.addProperty("interactiveDuration", var4 - this.i);
                this.a.addProperty("launchDuration", var4 - this.i);
                this.a.addProperty("deviceLevel", AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
                this.a.addProperty("runtimeLevel", AliHAHardware.getInstance().getOutlineInfo().runtimeLevel);
                this.a.addProperty("cpuUsageOfDevcie", AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie);
                this.a.addProperty("memoryRuntimeLevel", AliHAHardware.getInstance().getMemoryInfo().runtimeLevel);
                this.a.addProperty("usableChangeType", var3);
                this.a.stage("interactiveTime", var4);
                LauncherUsableEvent var8 = new LauncherUsableEvent();
                var8.duration = (float)(var4 - this.i);
                DumpManager.getInstance().append(var8);
                this.b.onLaunchChanged(this.a(), 2);
                this.q();
                this.s = false;
            }

        }
    }

    public void a(Activity var1, int var2, long var3) {
        if (this.t) {
            if (var2 == 2 && !PageList.inBlackList(this.e) && TextUtils.isEmpty(this.d)) {
                this.d = this.e;
            }

            if (var1 == this.d && var2 == 2) {
                this.a.addProperty("displayDuration", var3 - this.i);
                this.a.stage("displayedTime", var3);
                DisplayedEvent var7 = new DisplayedEvent();
                DumpManager.getInstance().append(var7);
                this.b.onLaunchChanged(this.a(), 1);
                this.t = false;
            }

        }
    }

    protected void o() {
        if (!this.i) {
            this.i = true;
            this.q();
            if (!TextUtils.isEmpty(this.d)) {
                int var1 = this.d.lastIndexOf(".");
                String var2 = this.d.substring(var1 + 1);
                this.a.addProperty("currentPageName", var2);
                this.a.addProperty("fullPageName", this.d);
            }

            this.a.addProperty("linkPageName", this.c.toString());
            this.c.clear();
            this.a.addProperty("hasSplash", GlobalStats.hasSplash);
            this.a.addStatistic("gcCount", this.l);
            this.a.addStatistic("fps", this.b.toString());
            this.a.addStatistic("jankCount", this.c);
            this.a.addStatistic("image", this.n);
            this.a.addStatistic("imageOnRequest", this.n);
            this.a.addStatistic("imageSuccessCount", this.o);
            this.a.addStatistic("imageFailedCount", this.p);
            this.a.addStatistic("imageCanceledCount", this.q);
            this.a.addStatistic("network", this.r);
            this.a.addStatistic("networkOnRequest", this.r);
            this.a.addStatistic("networkSuccessCount", this.s);
            this.a.addStatistic("networkFailedCount", this.t);
            this.a.addStatistic("networkCanceledCount", this.u);
            long[] var3 = com.taobao.monitor.impl.data.f.a.a();
            this.a.addStatistic("totalRx", var3[0] - this.c[0]);
            this.a.addStatistic("totalTx", var3[1] - this.c[1]);
            this.a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            GlobalStats.hasSplash = false;
            this.f.removeListener(this);
            this.b.removeListener(this);
            this.d.removeListener(this);
            this.c.removeListener(this);
            this.a.removeListener(this);
            this.e.removeListener(this);
            this.h.removeListener(this);
            this.g.removeListener(this);
            j.b.removeListener(this);
            this.a.end();
            StartUpEndEvent var4 = new StartUpEndEvent();
            DumpManager.getInstance().append(var4);
            super.o();
        }

    }

    public void b(int var1) {
        if (this.b.size() < 200) {
            this.b.add(var1);
        }

    }

    public void c(int var1) {
        this.c += var1;
    }

    public void gc() {
        ++this.l;
    }

    public void c(int var1, long var2) {
        if (var1 == 1) {
            HashMap var4 = new HashMap(1);
            var4.put("timestamp", var2);
            this.a.event("foreground2Background", var4);
            this.o();
        }

    }

    private void q() {
        if (!this.v) {
            this.b.onLaunchChanged(this.f.equals("COLD") ? 0 : 1, 4);
            this.v = true;
        }

    }

    public void a(Activity var1, KeyEvent var2, long var3) {
        if (!PageList.inBlackList(com.taobao.monitor.impl.util.a.a(var1))) {
            if (var1 == this.d) {
                int var5 = var2.getAction();
                int var6 = var2.getKeyCode();
                if (var5 == 0 && (var6 == 4 || var6 == 3)) {
                    if (TextUtils.isEmpty(this.d)) {
                        this.d = com.taobao.monitor.impl.util.a.a(var1);
                    }

                    if (var6 == 3) {
                        this.a.addProperty("leaveType", "home");
                    } else {
                        this.a.addProperty("leaveType", "back");
                    }

                    HashMap var7 = new HashMap(2);
                    var7.put("timestamp", var3);
                    var7.put("key", var2.getKeyCode());
                    this.a.event("keyEvent", var7);
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
                if (var1 == this.d) {
                    String var6 = var2.getClass().getSimpleName();
                    String var7 = var6 + "_" + var3;
                    Integer var8 = (Integer)this.b.get(var7);
                    if (var8 == null) {
                        var8 = 0;
                    } else {
                        var8 = var8 + 1;
                    }

                    this.b.put(var7, var8);
                    this.a.stage(var7 + var8, var4);
                }
            }
        }
    }
}
