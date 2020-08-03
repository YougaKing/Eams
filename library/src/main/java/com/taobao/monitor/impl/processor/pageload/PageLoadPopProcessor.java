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
import com.taobao.monitor.impl.processor.pageload.e.b;
import com.taobao.monitor.impl.trace.IDispatcher;
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
public class PageLoadPopProcessor extends com.taobao.monitor.impl.processor.a implements b, com.taobao.monitor.impl.trace.b.a, com.taobao.monitor.impl.trace.e.a, com.taobao.monitor.impl.trace.f.a, com.taobao.monitor.impl.trace.i.a {
    private IProcedure a;
    private long f;
    private Activity d = null;
    private String pageName;
    private IDispatcher a;
    private IDispatcher b;
    private IDispatcher c;
    private IDispatcher d;
    private long g = -1L;
    private long h = 0L;
    private long[] b = new long[2];
    private List<Integer> b = new ArrayList();
    private int c = 0;
    private int l = 0;
    private boolean o = true;

    public a() {
        super(false);
    }

    protected void n() {
        super.n();
        ProcedureConfig var1 = (new Builder()).setIndependent(false).setUpload(true).setParentNeedStats(false).setParent((IProcedure)null).build();
        this.a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), var1);
        this.a.begin();
        this.a = this.a("ACTIVITY_EVENT_DISPATCHER");
        this.b = this.a("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.c = this.a("ACTIVITY_FPS_DISPATCHER");
        this.d = this.a("APPLICATION_GC_DISPATCHER");
        this.d.addListener(this);
        this.b.addListener(this);
        this.a.addListener(this);
        this.c.addListener(this);
        this.p();
    }

    private void p() {
        this.a.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.a.addProperty("errorCode", 1);
        this.a.addProperty("installType", GlobalStats.installType);
    }

    private void b(Activity var1) {
        this.pageName = com.taobao.monitor.impl.util.a.b(var1);
        this.a.addProperty("pageName", this.pageName);
        this.a.addProperty("fullPageName", var1.getClass().getName());
        Intent var2 = var1.getIntent();
        if (var2 != null) {
            String var3 = var2.getDataString();
            if (TextUtils.isEmpty(var3)) {
                this.a.addProperty("schemaUrl", var3);
            }
        }

        this.a.addProperty("isInterpretiveExecution", false);
        this.a.addProperty("isFirstLaunch", GlobalStats.isFirstLaunch);
        this.a.addProperty("isFirstLoad", GlobalStats.activityStatusManager.a(com.taobao.monitor.impl.util.a.a(var1)));
        this.a.addProperty("jumpTime", GlobalStats.jumpTime);
        this.a.addProperty("lastValidTime", GlobalStats.lastValidTime);
        this.a.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.a.addProperty("loadType", "pop");
    }

    public void onActivityStarted(Activity var1) {
        this.n();
        this.f = TimeUtils.currentTimeMillis();
        this.b(var1);
        this.g = this.f;
        HashMap var2 = new HashMap(1);
        var2.put("timestamp", TimeUtils.currentTimeMillis());
        this.a.event("onActivityStarted", var2);
        long[] var3 = com.taobao.monitor.impl.data.f.a.a();
        this.b[0] = var3[0];
        this.b[1] = var3[1];
        this.a.stage("loadStartTime", this.f);
        long var4 = TimeUtils.currentTimeMillis();
        this.a.addProperty("pageInitDuration", var4 - this.f);
        this.a.stage("renderStartTime", var4);
        long var6 = TimeUtils.currentTimeMillis();
        this.a.addProperty("interactiveDuration", var6 - this.f);
        this.a.addProperty("loadDuration", var6 - this.f);
        this.a.stage("interactiveTime", var6);
        long var8 = TimeUtils.currentTimeMillis();
        this.a.addProperty("displayDuration", var8 - this.f);
        this.a.stage("displayedTime", this.f);
    }

    public void onActivityStopped(Activity var1) {
        this.h += TimeUtils.currentTimeMillis() - this.g;
        HashMap var2 = new HashMap(1);
        var2.put("timestamp", TimeUtils.currentTimeMillis());
        this.a.event("onActivityStopped", var2);
        long[] var3 = com.taobao.monitor.impl.data.f.a.a();
        this.b[0] = var3[0] - this.b[0];
        this.b[1] = var3[1] - this.b[1];
        this.a.addProperty("totalVisibleDuration", this.h);
        this.a.addProperty("errorCode", 0);
        this.a.addStatistic("totalRx", this.b[0]);
        this.a.addStatistic("totalTx", this.b[1]);
        this.o();
    }

    public void onLowMemory() {
        HashMap var1 = new HashMap(1);
        var1.put("timestamp", TimeUtils.currentTimeMillis());
        this.a.event("onLowMemory", var1);
    }

    public void a(Activity var1, MotionEvent var2, long var3) {
        if (var1 == this.d && this.o) {
            this.a.stage("firstInteractiveTime", var3);
            this.a.addProperty("firstInteractiveDuration", var3 - this.f);
            this.o = false;
        }

    }

    protected void o() {
        this.a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
        this.a.addStatistic("gcCount", this.l);
        this.a.addStatistic("fps", this.b.toString());
        this.a.addStatistic("jankCount", this.c);
        this.b.removeListener(this);
        this.a.removeListener(this);
        this.c.removeListener(this);
        this.d.removeListener(this);
        this.a.end();
        super.o();
    }

    public void b(int var1) {
        if (this.b.size() < 60) {
            this.b.add(var1);
        }

    }

    public void c(int var1) {
        this.c += var1;
    }

    public void gc() {
        ++this.l;
    }

    public void a(Activity var1, KeyEvent var2, long var3) {
        if (var2.getAction() == 0 && (var2.getKeyCode() == 4 || var2.getKeyCode() == 3)) {
            HashMap var5 = new HashMap(2);
            var5.put("timestamp", var3);
            var5.put("key", var2.getKeyCode());
            this.a.event("keyEvent", var5);
        }

    }
}
