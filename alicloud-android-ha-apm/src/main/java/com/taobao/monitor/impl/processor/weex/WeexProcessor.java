package com.taobao.monitor.impl.processor.weex;

import android.app.Activity;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.OnUsableVisibleListener;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.performance.IWXApmAdapter;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: WeexProcessor */
public class WeexProcessor extends AbsProcessor implements OnUsableVisibleListener<Activity>, ApplicationBackgroundChangedDispatcher.BackgroundChangedListener, ApplicationGCDispatcher.GCListener, ApplicationLowMemoryDispatcher.LowMemoryListener, FPSDispatcher.FPSListener, ImageStageDispatcher.StageListener, NetworkStageDispatcher.StageListener, IWXApmAdapter {
    private IDispatcher a;

    /* renamed from: a reason: collision with other field name */
    private IProcedure f39a;
    private IDispatcher b;

    /* renamed from: b reason: collision with other field name */
    private List<Integer> f40b = new ArrayList();
    private int c = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher f41c;
    private IDispatcher d;

    /* renamed from: d reason: collision with other field name */
    private boolean f42d = false;
    private long f;

    /* renamed from: f reason: collision with other field name */
    private IDispatcher f43f;
    private IDispatcher i;
    private int l = 0;
    private int n;
    private int o;
    private int p;
    private int q;

    /* renamed from: q reason: collision with other field name */
    private boolean f44q = true;
    private int r;

    /* renamed from: r reason: collision with other field name */
    private boolean f45r = true;
    private int s;

    /* renamed from: s reason: collision with other field name */
    private boolean f46s = true;
    private int t;

    /* renamed from: t reason: collision with other field name */
    private boolean f47t = true;
    private final String type;
    private int u;

    public WeexProcessor(String str) {
        super(false);
        this.type = str;
    }

    /* access modifiers changed from: protected */
    public void n() {
        super.n();
        this.f = TimeUtils.currentTimeMillis();
        this.f39a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/" + this.type), new Builder().setIndependent(true).setUpload(true).setParentNeedStats(true).setParent(ProcedureManagerProxy.PROXY.getCurrentActivityProcedure()).build());
        this.f39a.begin();
        this.f39a.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.a = getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.b = getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.f41c = getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.d = getDispatcher("APPLICATION_GC_DISPATCHER");
        this.f43f = getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.i = getDispatcher("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        this.d.addListener(this);
        this.b.addListener(this);
        this.a.addListener(this);
        this.f41c.addListener(this);
        this.f43f.addListener(this);
        this.i.addListener(this);
    }

    /* access modifiers changed from: protected */
    public void o() {
        if (!this.f42d) {
            this.f39a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            this.f39a.addStatistic("gcCount", Integer.valueOf(this.l));
            this.f39a.addStatistic("fps", this.f40b.toString());
            this.f39a.addStatistic("jankCount", Integer.valueOf(this.c));
            this.f39a.addStatistic("imgLoadCount", Integer.valueOf(this.n));
            this.f39a.addStatistic("imgLoadSuccessCount", Integer.valueOf(this.o));
            this.f39a.addStatistic("imgLoadFailCount", Integer.valueOf(this.p));
            this.f39a.addStatistic("imgLoadCancelCount", Integer.valueOf(this.q));
            this.f39a.addStatistic("networkRequestCount", Integer.valueOf(this.r));
            this.f39a.addStatistic("networkRequestSuccessCount", Integer.valueOf(this.s));
            this.f39a.addStatistic("networkRequestFailCount", Integer.valueOf(this.t));
            this.f39a.addStatistic("networkRequestCancelCount", Integer.valueOf(this.u));
            this.b.removeListener(this);
            this.a.removeListener(this);
            this.f41c.removeListener(this);
            this.d.removeListener(this);
            this.f43f.removeListener(this);
            this.i.removeListener(this);
            this.f39a.end();
            super.o();
        }
        this.f42d = true;
    }

    public void onStart(String str) {
        n();
        this.f39a.addProperty("instanceId", str);
    }

    public void onEnd() {
        o();
    }

    public void onEvent(String str, Object obj) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, obj);
        this.f39a.event(str, hashMap);
    }

    public void onStage(String str, long j) {
        this.f39a.stage(str, j);
    }

    public void addProperty(String str, Object obj) {
        this.f39a.addProperty(str, obj);
    }

    public void addStatistic(String str, double d2) {
        this.f39a.addStatistic(str, Double.valueOf(d2));
    }

    public void addBiz(String str, Map<String, Object> map) {
        this.f39a.addBiz(str, map);
    }

    public void addBizAbTest(String str, Map<String, Object> map) {
        this.f39a.addBizAbTest(str, map);
    }

    public void addBizStage(String str, Map<String, Object> map) {
        this.f39a.addBizStage(str, map);
    }

    public void onStart() {
        this.f44q = true;
    }

    public void onStop() {
        this.f44q = false;
    }

    public void onLowMemory() {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.f39a.event("onLowMemory", hashMap);
    }

    public void b(int i2) {
        if (this.f40b.size() < 200 && this.f44q) {
            this.f40b.add(Integer.valueOf(i2));
        }
    }

    public void c(int i2) {
        if (this.f44q) {
            this.c += i2;
        }
    }

    public void gc() {
        this.l++;
    }

    public void backgroundChanged(int i2, long j) {
        if (i2 == 1) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("timestamp", Long.valueOf(j));
            this.f39a.event("foreground2Background", hashMap);
            Global.instance().handler().post(new Runnable() {
                public void run() {
                    WeexProcessor.this.o();
                }
            });
            return;
        }
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("timestamp", Long.valueOf(j));
        this.f39a.event("background2Foreground", hashMap2);
    }

    public void imageStage(int i2) {
        if (!this.f44q) {
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
        if (!this.f44q) {
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

    public void a(Activity activity, int i2, long j) {
        if (this.f47t && this.f44q && i2 == 2) {
            this.f39a.addProperty("displayDuration", Long.valueOf(j - this.f));
            this.f39a.stage("displayedTime", j);
            this.f47t = false;
        }
    }

    public void a(Activity activity, int i2, int i3, long j) {
        if (this.f46s && this.f44q && i2 == 2) {
            this.f39a.addProperty("interactiveDuration", Long.valueOf(j - this.f));
            this.f39a.addProperty("loadDuration", Long.valueOf(j - this.f));
            this.f39a.addProperty("usableChangeType", Integer.valueOf(i3));
            this.f39a.stage("interactiveTime", j);
            this.f46s = false;
        }
    }

    /* renamed from: f */
    public void a(Activity activity, long j) {
        if (this.f45r && this.f44q) {
            this.f39a.addProperty("pageInitDuration", Long.valueOf(j - this.f));
            this.f39a.stage("renderStartTime", j);
            this.f45r = false;
        }
    }

    public void a(Activity activity, float f2, long j) {
        if (this.f44q) {
            this.f39a.addProperty("onRenderPercent", Float.valueOf(f2));
            this.f39a.addProperty("drawPercentTime", Long.valueOf(j));
        }
    }
}