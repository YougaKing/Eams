//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.pageload;

import android.annotation.TargetApi;
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
import com.ali.ha.fulltrace.event.FPSEvent;
import com.ali.ha.fulltrace.event.FinishLoadPageEvent;
import com.ali.ha.fulltrace.event.GCEvent;
import com.ali.ha.fulltrace.event.JankEvent;
import com.ali.ha.fulltrace.event.OpenPageEvent;
import com.ali.ha.fulltrace.event.ReceiverLowMemoryEvent;
import com.ali.ha.fulltrace.event.UsableEvent;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.h;
import com.taobao.monitor.impl.processor.a;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.j;
import com.taobao.monitor.impl.trace.k;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
public class PageLoadProcessor extends PageLoadPopProcessor implements h<Activity>,
        com.taobao.monitor.impl.processor.pageload.e.a,
        com.taobao.monitor.impl.trace.b.a,
        com.taobao.monitor.impl.trace.d.a,
        com.taobao.monitor.impl.trace.e.a,
        com.taobao.monitor.impl.trace.f.a,
        com.taobao.monitor.impl.trace.i.a,
        k,
        com.taobao.monitor.impl.trace.m.a,
        com.taobao.monitor.impl.trace.n.a {
    private IProcedure a;
    private long f;
    private Activity d = null;
    private String pageName;
    private static String g = "";
    private static List<String> d = new ArrayList(4);
    private IDispatcher a;
    private IDispatcher b;
    private IDispatcher e;
    private IDispatcher c;
    private IDispatcher d;
    private IDispatcher f;
    private IDispatcher g;
    private IDispatcher h;
    private long g = -1L;
    private long h = 0L;
    private long[] c;
    private long[] b = new long[2];
    private boolean p = true;
    private List<Integer> b = new ArrayList();
    private int c = 0;
    private int l = 0;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private FPSEvent a = new FPSEvent();
    private int m = 0;
    private boolean q = true;
    private HashMap<String, Integer> b = new HashMap();
    private boolean o = true;
    private boolean r = true;
    private boolean s = true;
    private boolean t = true;
    private boolean i = false;

    public PageLoadProcessor() {
        super(false);
    }

    protected void n() {
        super.n();
        ProcedureConfig var1 = (new Builder()).setIndependent(false).setUpload(true).setParentNeedStats(true).setParent((IProcedure) null).build();
        this.a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), var1);
        this.a.begin();
        this.a = this.a("ACTIVITY_EVENT_DISPATCHER");
        this.b = this.a("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.e = this.a("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        this.c = this.a("ACTIVITY_FPS_DISPATCHER");
        this.d = this.a("APPLICATION_GC_DISPATCHER");
        this.f = this.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.g = this.a("NETWORK_STAGE_DISPATCHER");
        this.h = this.a("IMAGE_STAGE_DISPATCHER");
        this.d.addListener(this);
        this.b.addListener(this);
        this.a.addListener(this);
        this.e.addListener(this);
        this.c.addListener(this);
        this.f.addListener(this);
        this.g.addListener(this);
        this.h.addListener(this);
        j.b.addListener(this);
        this.p();
        this.b[0] = 0L;
        this.b[1] = 0L;
    }

    private void p() {
        this.a.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.a.addProperty("errorCode", 1);
        this.a.addProperty("installType", GlobalStats.installType);
        this.a.addProperty("leaveType", "other");
    }

    public void onActivityCreated(Activity var1, Bundle var2, long var3) {
        this.f = var3;
        this.n();
        this.a.stage("loadStartTime", this.f);
        HashMap var5 = new HashMap(1);
        var5.put("timestamp", this.f);
        this.a.event("onActivityCreated", var5);
        this.d = var1;
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.a);
        this.b(var1);
        this.c = com.taobao.monitor.impl.data.f.a.a();
        OpenPageEvent var6 = new OpenPageEvent();
        var6.pageName = com.taobao.monitor.impl.util.a.b(var1);
        DumpManager.getInstance().append(var6);
    }

    private void b(Activity var1) {
        this.pageName = com.taobao.monitor.impl.util.a.b(var1);
        if (d.size() < 10) {
            d.add(this.pageName);
        }

        this.a.addProperty("pageName", this.pageName);
        this.a.addProperty("fullPageName", com.taobao.monitor.impl.util.a.a(var1));
        if (!TextUtils.isEmpty(g)) {
            this.a.addProperty("fromPageName", g);
        }

        Intent var2 = var1.getIntent();
        if (var2 != null) {
            String var3 = var2.getDataString();
            if (!TextUtils.isEmpty(var3)) {
                this.a.addProperty("schemaUrl", var3);
            }
        }

        this.a.addProperty("isFirstLaunch", GlobalStats.isFirstLaunch);
        this.a.addProperty("isFirstLoad", GlobalStats.activityStatusManager.a(com.taobao.monitor.impl.util.a.a(var1)));
        this.a.addProperty("jumpTime", GlobalStats.jumpTime);
        GlobalStats.jumpTime = -1L;
        this.a.addProperty("lastValidTime", GlobalStats.lastValidTime);
        this.a.addProperty("lastValidLinksPage", d.toString());
        this.a.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.a.addProperty("loadType", "push");
    }

    public void a(Activity var1, long var2) {
        this.q = true;
        this.g = var2;
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.a.event("onActivityStarted", var4);
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.a);
        g = this.pageName;
        if (this.p) {
            this.p = false;
            long[] var5 = com.taobao.monitor.impl.data.f.a.a();
            long[] var10000 = this.b;
            var10000[0] += var5[0] - this.c[0];
            var10000 = this.b;
            var10000[1] += var5[1] - this.c[1];
        }

        this.c = com.taobao.monitor.impl.data.f.a.a();
        GlobalStats.lastValidPage = this.pageName;
        GlobalStats.lastValidTime = var2;
    }

    public void b(Activity var1, long var2) {
        ProcedureManagerSetter.instance().setCurrentActivityProcedure(this.a);
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.a.event("onActivityResumed", var4);
    }

    public void c(Activity var1, long var2) {
        this.q = false;
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.a.event("onActivityPaused", var4);
    }

    public void d(Activity var1, long var2) {
        this.h += var2 - this.g;
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.a.event("onActivityStopped", var4);
        long[] var5 = com.taobao.monitor.impl.data.f.a.a();
        long[] var10000 = this.b;
        var10000[0] += var5[0] - this.c[0];
        var10000 = this.b;
        var10000[1] += var5[1] - this.c[1];
        this.c = var5;
        if (this.b != null && this.m > this.b.size()) {
            Integer var6 = 0;

            for (int var7 = this.m; var7 < this.b.size(); ++var7) {
                var6 = var6 + (Integer) this.b.get(var7);
            }

            this.a.averageUseFps = (float) (var6 / (this.b.size() - this.m));
        }

        DumpManager.getInstance().append(this.a);
    }

    public void e(Activity var1, long var2) {
        HashMap var4 = new HashMap(1);
        var4.put("timestamp", var2);
        this.a.event("onActivityDestroyed", var4);
        long[] var5 = com.taobao.monitor.impl.data.f.a.a();
        long[] var10000 = this.b;
        var10000[0] += var5[0] - this.c[0];
        var10000 = this.b;
        var10000[1] += var5[1] - this.c[1];
        FinishLoadPageEvent var6 = new FinishLoadPageEvent();
        var6.pageName = com.taobao.monitor.impl.util.a.b(var1);
        DumpManager.getInstance().append(var6);
        this.o();
    }

    public void onLowMemory() {
        HashMap var1 = new HashMap(1);
        var1.put("timestamp", TimeUtils.currentTimeMillis());
        this.a.event("onLowMemory", var1);
        ReceiverLowMemoryEvent var2 = new ReceiverLowMemoryEvent();
        var2.level = 1.0F;
        DumpManager.getInstance().append(var2);
    }

    public void a(Activity var1, MotionEvent var2, long var3) {
        if (var1 == this.d) {
            if (this.o) {
                this.a.stage("firstInteractiveTime", var3);
                this.a.addProperty("firstInteractiveDuration", var3 - this.f);
                this.a.addProperty("leaveType", "touch");
                this.o = false;
                this.a.addProperty("errorCode", 0);
            }

            d.clear();
            d.add(this.pageName);
            GlobalStats.lastValidPage = this.pageName;
            GlobalStats.lastValidTime = var3;
        }

    }

    public void f(Activity var1, long var2) {
        if (this.r && var1 == this.d) {
            this.a.addProperty("pageInitDuration", var2 - this.f);
            this.a.stage("renderStartTime", var2);
            this.r = false;
        }

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
                this.a.addProperty("interactiveDuration", var4 - this.f);
                this.a.addProperty("loadDuration", var4 - this.f);
                this.a.addProperty("usableChangeType", var3);
                this.a.stage("interactiveTime", var4);
                this.a.addProperty("errorCode", 0);
                this.a.addStatistic("totalRx", this.b[0]);
                this.a.addStatistic("totalTx", this.b[1]);
                this.s = false;
                UsableEvent var8 = new UsableEvent();
                var8.duration = (float) (var4 - this.f);
                DumpManager.getInstance().append(var8);
                if (this.b != null && this.b.size() != 0) {
                    Integer var9 = 0;

                    Integer var11;
                    for (Iterator var10 = this.b.iterator(); var10.hasNext(); var9 = var9 + var11) {
                        var11 = (Integer) var10.next();
                    }

                    this.a.averageLoadFps = (float) (var9 / this.b.size());
                    this.m = this.b.size();
                }
            }

        }
    }

    public void a(Activity var1, int var2, long var3) {
        if (this.t) {
            if (var1 == this.d && var2 == 2) {
                this.a.addProperty("displayDuration", var3 - this.f);
                this.a.stage("displayedTime", var3);
                DisplayedEvent var7 = new DisplayedEvent();
                DumpManager.getInstance().append(var7);
                this.t = false;
            }

        }
    }

    protected void o() {
        if (!this.i) {
            this.i = true;
            this.a.addProperty("totalVisibleDuration", this.h);
            this.a.addProperty("deviceLevel", AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
            this.a.addProperty("runtimeLevel", AliHAHardware.getInstance().getOutlineInfo().runtimeLevel);
            this.a.addProperty("cpuUsageOfDevcie", AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie);
            this.a.addProperty("memoryRuntimeLevel", AliHAHardware.getInstance().getMemoryInfo().runtimeLevel);
            this.a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
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
            this.b.removeListener(this);
            this.a.removeListener(this);
            this.e.removeListener(this);
            this.c.removeListener(this);
            this.d.removeListener(this);
            this.f.removeListener(this);
            this.h.removeListener(this);
            this.g.removeListener(this);
            j.b.removeListener(this);
            this.a.end();
            super.o();
        }

    }

    public void b(int var1) {
        if (this.b.size() < 200 && this.q) {
            this.b.add(var1);
        }

    }

    public void c(int var1) {
        if (this.q) {
            this.c += var1;
            JankEvent var2 = new JankEvent();
            DumpManager.getInstance().append(var2);
        }

    }

    public void gc() {
        if (this.q) {
            ++this.l;
            GCEvent var1 = new GCEvent();
            DumpManager.getInstance().append(var1);
        }

    }

    public void c(int var1, long var2) {
        HashMap var4;
        if (var1 == 1) {
            var4 = new HashMap(1);
            var4.put("timestamp", var2);
            this.a.event("foreground2Background", var4);
            this.o();
        } else {
            var4 = new HashMap(1);
            var4.put("timestamp", var2);
            this.a.event("background2Foreground", var4);
        }

    }

    public void a(Activity var1, KeyEvent var2, long var3) {
        if (var1 == this.d) {
            int var5 = var2.getAction();
            int var6 = var2.getKeyCode();
            if (var5 == 0 && (var6 == 4 || var6 == 3)) {
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

    public void d(int var1) {
        if (this.q) {
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

    }

    public void e(int var1) {
        if (this.q) {
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

    }

    public void a(Activity var1, Fragment var2, String var3, long var4) {
        if (var2 != null) {
            if (var1 == this.d) {
                String var6 = var2.getClass().getSimpleName();
                String var7 = var6 + "_" + var3;
                Integer var8 = (Integer) this.b.get(var7);
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
