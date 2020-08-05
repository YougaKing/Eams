package com.taobao.monitor.impl.processor.fragmentload;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.DisplayedEvent;
import com.ali.ha.fulltrace.event.FPSEvent;
import com.ali.ha.fulltrace.event.FinishLoadPageEvent;
import com.ali.ha.fulltrace.event.OpenPageEvent;
import com.ali.ha.fulltrace.event.UsableEvent;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.h;
import com.taobao.monitor.impl.processor.a;
import com.taobao.monitor.impl.processor.fragmentload.a.C0002a;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.b;
import com.taobao.monitor.impl.trace.e;
import com.taobao.monitor.impl.trace.f;
import com.taobao.monitor.impl.trace.i;
import com.taobao.monitor.impl.trace.m;
import com.taobao.monitor.impl.trace.n;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: FragmentProcessor */
class d extends a implements h<Fragment>, C0002a, b.a, com.taobao.monitor.impl.trace.d.a, e.a, f.a, i.a, m.a, n.a {
    private FPSEvent a = new FPSEvent();

    /* renamed from: a reason: collision with other field name */
    private IDispatcher f55a;

    /* renamed from: a reason: collision with other field name */
    private IProcedure f56a;
    private Fragment b = null;

    /* renamed from: b reason: collision with other field name */
    private IDispatcher f57b;

    /* renamed from: b reason: collision with other field name */
    private List<Integer> f58b = new ArrayList();

    /* renamed from: b reason: collision with other field name */
    private long[] f59b = new long[2];
    private int c = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher f60c;

    /* renamed from: c reason: collision with other field name */
    private long[] f61c;
    private IDispatcher d;
    private IDispatcher e;
    private long f;

    /* renamed from: f reason: collision with other field name */
    private IDispatcher f62f;
    private long g = -1;

    /* renamed from: g reason: collision with other field name */
    private IDispatcher f63g;
    private long h = 0;

    /* renamed from: h reason: collision with other field name */
    private IDispatcher f64h;
    private boolean i = false;
    private int l = 0;
    private int m = 0;
    private int n;
    private int o;

    /* renamed from: o reason: collision with other field name */
    private boolean f65o = true;
    private int p;

    /* renamed from: p reason: collision with other field name */
    private boolean f66p = true;
    private String pageName;
    private int q;

    /* renamed from: q reason: collision with other field name */
    private boolean f67q = true;
    private int r;

    /* renamed from: r reason: collision with other field name */
    private boolean f68r = true;
    private int s;

    /* renamed from: s reason: collision with other field name */
    private boolean f69s = true;
    private int t;

    /* renamed from: t reason: collision with other field name */
    private boolean f70t = true;
    private int u;

    public d() {
        super(false);
    }

    /* access modifiers changed from: protected */
    public void n() {
        super.n();
        this.f56a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), new Builder().setIndependent(false).setUpload(true).setParentNeedStats(true).setParent(null).build());
        this.f56a.begin();
        this.f55a = a("ACTIVITY_EVENT_DISPATCHER");
        this.f57b = a("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.e = a("FRAGMENT_USABLE_VISIBLE_DISPATCHER");
        this.f60c = a("ACTIVITY_FPS_DISPATCHER");
        this.d = a("APPLICATION_GC_DISPATCHER");
        this.f62f = a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.f63g = a("NETWORK_STAGE_DISPATCHER");
        this.f64h = a("IMAGE_STAGE_DISPATCHER");
        this.d.addListener(this);
        this.f57b.addListener(this);
        this.f55a.addListener(this);
        this.e.addListener(this);
        this.f60c.addListener(this);
        this.f62f.addListener(this);
        this.f63g.addListener(this);
        this.f64h.addListener(this);
        p();
        this.f59b[0] = 0;
        this.f59b[1] = 0;
    }

    private void p() {
        this.f56a.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.f56a.addProperty("errorCode", Integer.valueOf(1));
        this.f56a.addProperty("installType", GlobalStats.installType);
        this.f56a.addProperty("leaveType", "other");
    }

    private void o(Fragment fragment) {
        this.pageName = fragment.getClass().getSimpleName();
        this.f56a.addProperty("pageName", this.pageName);
        this.f56a.addProperty("fullPageName", fragment.getClass().getName());
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                String dataString = intent.getDataString();
                if (!TextUtils.isEmpty(dataString)) {
                    this.f56a.addProperty("schemaUrl", dataString);
                }
            }
            this.f56a.addProperty("activityName", activity.getClass().getSimpleName());
        }
        this.f56a.addProperty("isInterpretiveExecution", Boolean.valueOf(false));
        this.f56a.addProperty("isFirstLaunch", Boolean.valueOf(GlobalStats.isFirstLaunch));
        this.f56a.addProperty("isFirstLoad", Boolean.valueOf(GlobalStats.activityStatusManager.a(fragment.getClass().getName())));
        this.f56a.addProperty("lastValidTime", Long.valueOf(GlobalStats.lastValidTime));
        this.f56a.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.f56a.addProperty("loadType", "push");
    }

    public void onLowMemory() {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.f56a.event("onLowMemory", hashMap);
    }

    public void a(Activity activity, MotionEvent motionEvent, long j) {
        if (this.b != null) {
            try {
                if (activity == this.b.getActivity() && this.f65o) {
                    this.f56a.stage("firstInteractiveTime", j);
                    this.f56a.addProperty("firstInteractiveDuration", Long.valueOf(j - this.f));
                    this.f56a.addProperty("leaveType", "touch");
                    this.f56a.addProperty("errorCode", Integer.valueOf(0));
                    this.f65o = false;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: o */
    public void a(Fragment fragment, long j) {
        if (this.f68r && fragment == this.b) {
            this.f56a.addProperty("pageInitDuration", Long.valueOf(j - this.f));
            this.f56a.stage("renderStartTime", j);
            this.f68r = false;
        }
    }

    public void a(Fragment fragment, float f2, long j) {
        if (fragment == this.b) {
            this.f56a.addProperty("onRenderPercent", Float.valueOf(f2));
            this.f56a.addProperty("drawPercentTime", Long.valueOf(j));
        }
    }

    public void a(Fragment fragment, int i2, int i3, long j) {
        if (this.f69s && fragment == this.b && i2 == 2) {
            this.f56a.addProperty("interactiveDuration", Long.valueOf(j - this.f));
            this.f56a.addProperty("loadDuration", Long.valueOf(j - this.f));
            this.f56a.addProperty("usableChangeType", Integer.valueOf(i3));
            this.f56a.stage("interactiveTime", j);
            this.f56a.addProperty("errorCode", Integer.valueOf(0));
            this.f56a.addStatistic("totalRx", Long.valueOf(this.f59b[0]));
            this.f56a.addStatistic("totalTx", Long.valueOf(this.f59b[1]));
            this.f69s = false;
            UsableEvent usableEvent = new UsableEvent();
            usableEvent.duration = (float) (j - this.f);
            DumpManager.getInstance().append(usableEvent);
            if (this.f58b != null && this.f58b.size() != 0) {
                Integer valueOf = Integer.valueOf(0);
                Iterator it = this.f58b.iterator();
                while (true) {
                    Integer num = valueOf;
                    if (it.hasNext()) {
                        Integer num2 = (Integer) it.next();
                        valueOf = Integer.valueOf(num2.intValue() + num.intValue());
                    } else {
                        this.a.averageLoadFps = (float) (num.intValue() / this.f58b.size());
                        this.m = this.f58b.size();
                        return;
                    }
                }
            }
        }
    }

    public void a(Fragment fragment, int i2, long j) {
        if (this.f70t && fragment == this.b && i2 == 2) {
            this.f56a.addProperty("displayDuration", Long.valueOf(j - this.f));
            this.f56a.stage("displayedTime", j);
            DumpManager.getInstance().append(new DisplayedEvent());
            this.f70t = false;
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        if (!this.i) {
            this.i = true;
            this.f56a.addProperty("totalVisibleDuration", Long.valueOf(this.h));
            this.f56a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            this.f56a.addStatistic("gcCount", Integer.valueOf(this.l));
            this.f56a.addStatistic("fps", this.f58b.toString());
            this.f56a.addStatistic("jankCount", Integer.valueOf(this.c));
            this.f56a.addStatistic("image", Integer.valueOf(this.n));
            this.f56a.addStatistic("imageOnRequest", Integer.valueOf(this.n));
            this.f56a.addStatistic("imageSuccessCount", Integer.valueOf(this.o));
            this.f56a.addStatistic("imageFailedCount", Integer.valueOf(this.p));
            this.f56a.addStatistic("imageCanceledCount", Integer.valueOf(this.q));
            this.f56a.addStatistic("network", Integer.valueOf(this.r));
            this.f56a.addStatistic("networkOnRequest", Integer.valueOf(this.r));
            this.f56a.addStatistic("networkSuccessCount", Integer.valueOf(this.s));
            this.f56a.addStatistic("networkFailedCount", Integer.valueOf(this.t));
            this.f56a.addStatistic("networkCanceledCount", Integer.valueOf(this.u));
            this.f57b.removeListener(this);
            this.f55a.removeListener(this);
            this.e.removeListener(this);
            this.f60c.removeListener(this);
            this.d.removeListener(this);
            this.f62f.removeListener(this);
            this.f64h.removeListener(this);
            this.f63g.removeListener(this);
            this.f56a.end();
            super.o();
        }
    }

    public void b(int i2) {
        if (this.f58b.size() < 200 && this.f67q) {
            this.f58b.add(Integer.valueOf(i2));
        }
    }

    public void c(int i2) {
        if (this.f67q) {
            this.c += i2;
        }
    }

    public void gc() {
        if (this.f67q) {
            this.l++;
        }
    }

    public void c(int i2, long j) {
        if (i2 == 1) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("timestamp", Long.valueOf(j));
            this.f56a.event("foreground2Background", hashMap);
            o();
            return;
        }
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("timestamp", Long.valueOf(j));
        this.f56a.event("background2Foreground", hashMap2);
    }

    public void a(Activity activity, KeyEvent keyEvent, long j) {
        if (this.b != null) {
            FragmentActivity fragmentActivity = null;
            try {
                fragmentActivity = this.b.getActivity();
            } catch (Exception e2) {
            }
            if (activity == fragmentActivity) {
                int action = keyEvent.getAction();
                int keyCode = keyEvent.getKeyCode();
                if (action != 0) {
                    return;
                }
                if (keyCode == 4 || keyCode == 3) {
                    if (keyCode == 3) {
                        this.f56a.addProperty("leaveType", "home");
                    } else {
                        this.f56a.addProperty("leaveType", "back");
                    }
                    HashMap hashMap = new HashMap(2);
                    hashMap.put("timestamp", Long.valueOf(j));
                    hashMap.put("key", Integer.valueOf(keyEvent.getKeyCode()));
                    this.f56a.event("keyEvent", hashMap);
                }
            }
        }
    }

    public void a(Fragment fragment, long j) {
        n();
        ProcedureManagerSetter.instance().setCurrentFragmentProcedure(this.f56a);
        this.f56a.stage("loadStartTime", j);
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentPreAttached", hashMap);
        this.b = fragment;
        this.f = j;
        o(fragment);
        this.f61c = com.taobao.monitor.impl.data.f.a.a();
        OpenPageEvent openPageEvent = new OpenPageEvent();
        openPageEvent.pageName = fragment.getClass().getSimpleName();
        DumpManager.getInstance().append(openPageEvent);
    }

    public void b(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentAttached", hashMap);
    }

    public void c(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentPreCreated", hashMap);
    }

    public void d(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentCreated", hashMap);
    }

    public void e(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentActivityCreated", hashMap);
    }

    public void f(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentViewCreated", hashMap);
    }

    public void g(Fragment fragment, long j) {
        ProcedureManagerSetter.instance().setCurrentFragmentProcedure(this.f56a);
        this.f67q = true;
        this.g = j;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentStarted", hashMap);
        if (this.f66p) {
            this.f66p = false;
            long[] a2 = com.taobao.monitor.impl.data.f.a.a();
            long[] jArr = this.f59b;
            jArr[0] = jArr[0] + (a2[0] - this.f61c[0]);
            long[] jArr2 = this.f59b;
            jArr2[1] = jArr2[1] + (a2[1] - this.f61c[1]);
        }
        this.f61c = com.taobao.monitor.impl.data.f.a.a();
        GlobalStats.lastValidPage = this.pageName;
        GlobalStats.lastValidTime = j;
    }

    public void h(Fragment fragment, long j) {
        ProcedureManagerSetter.instance().setCurrentFragmentProcedure(this.f56a);
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentResumed", hashMap);
    }

    public void i(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentPaused", hashMap);
    }

    public void j(Fragment fragment, long j) {
        this.f67q = false;
        this.h += j - this.g;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentStopped", hashMap);
        long[] a2 = com.taobao.monitor.impl.data.f.a.a();
        long[] jArr = this.f59b;
        jArr[0] = jArr[0] + (a2[0] - this.f61c[0]);
        long[] jArr2 = this.f59b;
        jArr2[1] = jArr2[1] + (a2[1] - this.f61c[1]);
        this.f61c = a2;
        if (this.f58b != null && this.m > this.f58b.size()) {
            Integer valueOf = Integer.valueOf(0);
            int i2 = this.m;
            while (true) {
                int i3 = i2;
                if (i3 >= this.f58b.size()) {
                    break;
                }
                valueOf = Integer.valueOf(((Integer) this.f58b.get(i3)).intValue() + valueOf.intValue());
                i2 = i3 + 1;
            }
            this.a.averageUseFps = (float) (valueOf.intValue() / (this.f58b.size() - this.m));
        }
        DumpManager.getInstance().append(this.a);
    }

    public void k(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentSaveInstanceState", hashMap);
    }

    public void l(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentViewDestroyed", hashMap);
    }

    public void d(int i2) {
        if (!this.f67q) {
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

    public void e(int i2) {
        if (!this.f67q) {
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

    public void m(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentDestroyed", hashMap);
    }

    public void n(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.f56a.event("onFragmentDetached", hashMap);
        long[] a2 = com.taobao.monitor.impl.data.f.a.a();
        long[] jArr = this.f59b;
        jArr[0] = jArr[0] + (a2[0] - this.f61c[0]);
        long[] jArr2 = this.f59b;
        jArr2[1] = jArr2[1] + (a2[1] - this.f61c[1]);
        FinishLoadPageEvent finishLoadPageEvent = new FinishLoadPageEvent();
        finishLoadPageEvent.pageName = fragment.getClass().getSimpleName();
        DumpManager.getInstance().append(finishLoadPageEvent);
        o();
    }
}
