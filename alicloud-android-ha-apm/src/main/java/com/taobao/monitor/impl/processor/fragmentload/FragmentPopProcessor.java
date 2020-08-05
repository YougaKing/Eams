package com.taobao.monitor.impl.processor.fragmentload;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.IExecutor;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.util.ActivityUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: FragmentPopProcessor */
class FragmentPopProcessor extends AbsProcessor implements FragmentModelLifecycle.b, ActivityEventDispatcher.EventListener, ApplicationGCDispatcher.GCListener, ApplicationLowMemoryDispatcher.LowMemoryListener, FPSDispatcher.FPSListener {
    private IDispatcher a;

    /* renamed from: a reason: collision with other field name */
    private IProcedure f50a;
    private Fragment b = null;

    /* renamed from: b reason: collision with other field name */
    private IDispatcher f51b;

    /* renamed from: b reason: collision with other field name */
    private List<Integer> f52b = new ArrayList();

    /* renamed from: b reason: collision with other field name */
    private long[] f53b = new long[2];
    private int c = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher f54c;
    private IDispatcher d;
    private long f;
    private long g = -1;
    private long h = 0;
    private int l = 0;
    private boolean o = true;
    private String pageName;

    public FragmentPopProcessor() {
        super(false);
    }

    /* access modifiers changed from: protected */
    public void n() {
        super.n();
        this.f50a = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), new Builder().setIndependent(false).setUpload(true).setParentNeedStats(false).setParent(null).build());
        this.f50a.begin();
        this.a = getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.f51b = getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.f54c = getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.d = getDispatcher("APPLICATION_GC_DISPATCHER");
        this.d.addListener(this);
        this.f51b.addListener(this);
        this.a.addListener(this);
        this.f54c.addListener(this);
        p();
    }

    private void p() {
        this.f50a.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.f50a.addProperty("errorCode", Integer.valueOf(1));
        this.f50a.addProperty("installType", GlobalStats.installType);
    }

    private void o(Fragment fragment) {
        this.pageName = fragment.getClass().getSimpleName();
        this.f50a.addProperty("pageName", this.pageName);
        this.f50a.addProperty("fullPageName", fragment.getClass().getName());
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                String dataString = intent.getDataString();
                if (TextUtils.isEmpty(dataString)) {
                    this.f50a.addProperty("schemaUrl", dataString);
                }
            }
        }
        this.f50a.addProperty("isInterpretiveExecution", Boolean.valueOf(false));
        this.f50a.addProperty("isFirstLaunch", Boolean.valueOf(GlobalStats.isFirstLaunch));
        this.f50a.addProperty("isFirstLoad", Boolean.valueOf(GlobalStats.activityStatusManager.a(ActivityUtils.getName(activity))));
        this.f50a.addProperty("jumpTime", Long.valueOf(GlobalStats.jumpTime));
        this.f50a.addProperty("lastValidTime", Long.valueOf(GlobalStats.lastValidTime));
        this.f50a.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.f50a.addProperty("loadType", "pop");
    }

    public void g(Fragment fragment) {
        n();
        o(fragment);
        this.f = TimeUtils.currentTimeMillis();
        this.g = this.f;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.f50a.event("onFragmentStarted", hashMap);
        long[] a2 = IExecutor.a.a();
        this.f53b[0] = a2[0];
        this.f53b[1] = a2[1];
        this.f50a.stage("loadStartTime", this.f);
        long currentTimeMillis = TimeUtils.currentTimeMillis();
        this.f50a.addProperty("pageInitDuration", Long.valueOf(currentTimeMillis - this.f));
        this.f50a.stage("renderStartTime", currentTimeMillis);
        long currentTimeMillis2 = TimeUtils.currentTimeMillis();
        this.f50a.addProperty("interactiveDuration", Long.valueOf(currentTimeMillis2 - this.f));
        this.f50a.addProperty("loadDuration", Long.valueOf(currentTimeMillis2 - this.f));
        this.f50a.stage("interactiveTime", currentTimeMillis2);
        this.f50a.addProperty("displayDuration", Long.valueOf(TimeUtils.currentTimeMillis() - this.f));
        this.f50a.stage("displayedTime", this.f);
    }

    public void j(Fragment fragment) {
        this.h += TimeUtils.currentTimeMillis() - this.g;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.f50a.event("onFragmentStopped", hashMap);
        long[] a2 = IExecutor.a.a();
        this.f53b[0] = a2[0] - this.f53b[0];
        this.f53b[1] = a2[1] - this.f53b[1];
        this.f50a.addProperty("totalVisibleDuration", Long.valueOf(this.h));
        this.f50a.addProperty("errorCode", Integer.valueOf(0));
        this.f50a.addStatistic("totalRx", Long.valueOf(this.f53b[0]));
        this.f50a.addStatistic("totalTx", Long.valueOf(this.f53b[1]));
        o();
    }

    public void onLowMemory() {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.f50a.event("onLowMemory", hashMap);
    }

    public void onMotionEvent(Activity activity, MotionEvent motionEvent, long j) {
        if (this.b != null && activity == this.b.getActivity() && this.o) {
            this.f50a.stage("firstInteractiveTime", j);
            this.f50a.addProperty("firstInteractiveDuration", Long.valueOf(j - this.f));
            this.o = false;
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        this.f50a.stage("procedureEndTime", TimeUtils.currentTimeMillis());
        this.f50a.addStatistic("gcCount", Integer.valueOf(this.l));
        this.f50a.addStatistic("fps", this.f52b.toString());
        this.f50a.addStatistic("jankCount", Integer.valueOf(this.c));
        this.f51b.removeListener(this);
        this.a.removeListener(this);
        this.f54c.removeListener(this);
        this.d.removeListener(this);
        this.f50a.end();
        super.o();
    }

    public void b(int i) {
        if (this.f52b.size() < 60) {
            this.f52b.add(Integer.valueOf(i));
        }
    }

    public void c(int i) {
        this.c += i;
    }

    public void gc() {
        this.l++;
    }

    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long j) {
        if (keyEvent.getAction() != 0) {
            return;
        }
        if (keyEvent.getKeyCode() == 4 || keyEvent.getKeyCode() == 3) {
            HashMap hashMap = new HashMap(2);
            hashMap.put("timestamp", Long.valueOf(j));
            hashMap.put("key", Integer.valueOf(keyEvent.getKeyCode()));
            this.f50a.event("keyEvent", hashMap);
        }
    }
}
