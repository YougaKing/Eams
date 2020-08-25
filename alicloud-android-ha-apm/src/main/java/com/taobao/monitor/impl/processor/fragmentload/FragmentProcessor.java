package com.taobao.monitor.impl.processor.fragmentload;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.DisplayedEvent;
import com.ali.ha.fulltrace.event.FPSEvent;
import com.ali.ha.fulltrace.event.FinishLoadPageEvent;
import com.ali.ha.fulltrace.event.OpenPageEvent;
import com.ali.ha.fulltrace.event.UsableEvent;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.OnUsableVisibleListener;
import com.taobao.monitor.impl.data.traffic.TrafficTracker;
import com.taobao.monitor.impl.processor.AbsProcessor;
import com.taobao.monitor.impl.processor.fragmentload.FragmentModelLifecycle.ModelLifecycleListener;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGcDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
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
class FragmentProcessor extends AbsProcessor implements OnUsableVisibleListener<Fragment>,
        ModelLifecycleListener,
        ActivityEventDispatcher.EventListener,
        ApplicationBackgroundChangedDispatcher.BackgroundChangedListener,
        ApplicationGcDispatcher.GcListener,
        ApplicationLowMemoryDispatcher.LowMemoryListener,
        FPSDispatcher.FPSListener,
        ImageStageDispatcher.StageListener,
        NetworkStageDispatcher.StageListener {
    private FPSEvent a = new FPSEvent();

    /* renamed from: a reason: collision with other field name */
    private IDispatcher mActivityEventDispatcher;

    /* renamed from: a reason: collision with other field name */
    private IProcedure mPageLoadProcedure;
    private Fragment b = null;

    /* renamed from: b reason: collision with other field name */
    private IDispatcher mApplicationLowMemoryDispatcher;

    /* renamed from: b reason: collision with other field name */
    private List<Integer> mFpsList = new ArrayList();

    /* renamed from: b reason: collision with other field name */
    private long[] f59b = new long[2];
    private int mJankCount = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher mActivityFpsDispatcher;

    /* renamed from: c reason: collision with other field name */
    private long[] f61c;
    private IDispatcher mApplicationGcDispatcher;
    private IDispatcher mFragmentUsableVisibleDispatcher;
    private long f;

    /* renamed from: f reason: collision with other field name */
    private IDispatcher mApplicationBackgroundChangedDispatcher;
    private long g = -1;

    /* renamed from: g reason: collision with other field name */
    private IDispatcher mNetworkStageDispatcher;
    private long mTotalVisibleDuration = 0;

    /* renamed from: h reason: collision with other field name */
    private IDispatcher mImageStageDispatcher;
    private boolean i = false;
    private int mGcCount = 0;
    private int m = 0;
    private int mImageCount;
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

    public FragmentProcessor() {
        super(false);
    }

    /* access modifiers changed from: protected */
    public void procedureBegin() {
        super.procedureBegin();
        this.mPageLoadProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/pageLoad"), new Builder()
                .setIndependent(false)
                .setUpload(true)
                .setParentNeedStats(true)
                .setParent(null)
                .build());

        this.mPageLoadProcedure.begin();
        this.mActivityEventDispatcher = getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.mApplicationLowMemoryDispatcher = getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.mFragmentUsableVisibleDispatcher = getDispatcher("FRAGMENT_USABLE_VISIBLE_DISPATCHER");
        this.mActivityFpsDispatcher = getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.mApplicationGcDispatcher = getDispatcher("APPLICATION_GC_DISPATCHER");
        this.mApplicationBackgroundChangedDispatcher = getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.mNetworkStageDispatcher = getDispatcher("NETWORK_STAGE_DISPATCHER");
        this.mImageStageDispatcher = getDispatcher("IMAGE_STAGE_DISPATCHER");
        this.mApplicationGcDispatcher.addListener(this);
        this.mApplicationLowMemoryDispatcher.addListener(this);
        this.mActivityEventDispatcher.addListener(this);
        this.mFragmentUsableVisibleDispatcher.addListener(this);
        this.mActivityFpsDispatcher.addListener(this);
        this.mApplicationBackgroundChangedDispatcher.addListener(this);
        this.mNetworkStageDispatcher.addListener(this);
        this.mImageStageDispatcher.addListener(this);
        p();
        this.f59b[0] = 0;
        this.f59b[1] = 0;
    }

    private void p() {
        this.mPageLoadProcedure.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.mPageLoadProcedure.addProperty("errorCode", Integer.valueOf(1));
        this.mPageLoadProcedure.addProperty("installType", GlobalStats.installType);
        this.mPageLoadProcedure.addProperty("leaveType", "other");
    }

    private void o(Fragment fragment) {
        this.pageName = fragment.getClass().getSimpleName();
        this.mPageLoadProcedure.addProperty("pageName", this.pageName);
        this.mPageLoadProcedure.addProperty("fullPageName", fragment.getClass().getName());
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                String dataString = intent.getDataString();
                if (!TextUtils.isEmpty(dataString)) {
                    this.mPageLoadProcedure.addProperty("schemaUrl", dataString);
                }
            }
            this.mPageLoadProcedure.addProperty("activityName", activity.getClass().getSimpleName());
        }
        this.mPageLoadProcedure.addProperty("isInterpretiveExecution", Boolean.valueOf(false));
        this.mPageLoadProcedure.addProperty("isFirstLaunch", Boolean.valueOf(GlobalStats.isFirstLaunch));
        this.mPageLoadProcedure.addProperty("isFirstLoad", Boolean.valueOf(GlobalStats.activityStatusManager.get(fragment.getClass().getName())));
        this.mPageLoadProcedure.addProperty("lastValidTime", Long.valueOf(GlobalStats.lastValidTime));
        this.mPageLoadProcedure.addProperty("lastValidPage", GlobalStats.lastValidPage);
        this.mPageLoadProcedure.addProperty("loadType", "push");
    }

    public void onLowMemory() {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(TimeUtils.currentTimeMillis()));
        this.mPageLoadProcedure.event("onLowMemory", hashMap);
    }

    public void onMotionEvent(Activity activity, MotionEvent motionEvent, long timeMillis) {
        if (this.b != null) {
            try {
                if (activity == this.b.getActivity() && this.f65o) {
                    this.mPageLoadProcedure.stage("firstInteractiveTime", timeMillis);
                    this.mPageLoadProcedure.addProperty("firstInteractiveDuration", Long.valueOf(timeMillis - this.f));
                    this.mPageLoadProcedure.addProperty("leaveType", "touch");
                    this.mPageLoadProcedure.addProperty("errorCode", Integer.valueOf(0));
                    this.f65o = false;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: o */
    public void onResume(Fragment fragment, long timeMillis) {
        if (this.f68r && fragment == this.b) {
            this.mPageLoadProcedure.addProperty("pageInitDuration", Long.valueOf(timeMillis - this.f));
            this.mPageLoadProcedure.stage("renderStartTime", timeMillis);
            this.f68r = false;
        }
    }

    public void visiblePercent(Fragment fragment, float percent, long timeMillis) {
        if (fragment == this.b) {
            this.mPageLoadProcedure.addProperty("onRenderPercent", Float.valueOf(percent));
            this.mPageLoadProcedure.addProperty("drawPercentTime", Long.valueOf(timeMillis));
        }
    }

    public void usable(Fragment fragment, int i2, int usableChangeType, long timeMillis) {
        if (this.f69s && fragment == this.b && i2 == 2) {
            this.mPageLoadProcedure.addProperty("interactiveDuration", Long.valueOf(timeMillis - this.f));
            this.mPageLoadProcedure.addProperty("loadDuration", Long.valueOf(timeMillis - this.f));
            this.mPageLoadProcedure.addProperty("usableChangeType", Integer.valueOf(usableChangeType));
            this.mPageLoadProcedure.stage("interactiveTime", timeMillis);
            this.mPageLoadProcedure.addProperty("errorCode", Integer.valueOf(0));
            this.mPageLoadProcedure.addStatistic("totalRx", Long.valueOf(this.f59b[0]));
            this.mPageLoadProcedure.addStatistic("totalTx", Long.valueOf(this.f59b[1]));
            this.f69s = false;
            UsableEvent usableEvent = new UsableEvent();
            usableEvent.duration = (float) (timeMillis - this.f);
            DumpManager.getInstance().append(usableEvent);
            if (this.mFpsList != null && this.mFpsList.size() != 0) {
                Integer valueOf = Integer.valueOf(0);
                Iterator it = this.mFpsList.iterator();
                while (true) {
                    Integer num = valueOf;
                    if (it.hasNext()) {
                        Integer num2 = (Integer) it.next();
                        valueOf = Integer.valueOf(num2.intValue() + num.intValue());
                    } else {
                        this.a.averageLoadFps = (float) (num.intValue() / this.mFpsList.size());
                        this.m = this.mFpsList.size();
                        return;
                    }
                }
            }
        }
    }

    public void display(Fragment fragment, int i2, long j) {
        if (this.f70t && fragment == this.b && i2 == 2) {
            this.mPageLoadProcedure.addProperty("displayDuration", Long.valueOf(j - this.f));
            this.mPageLoadProcedure.stage("displayedTime", j);
            DumpManager.getInstance().append(new DisplayedEvent());
            this.f70t = false;
        }
    }

    /* access modifiers changed from: protected */
    public void procedureEnd() {
        if (!this.i) {
            this.i = true;
            this.mPageLoadProcedure.addProperty("totalVisibleDuration", this.mTotalVisibleDuration);
            this.mPageLoadProcedure.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            this.mPageLoadProcedure.addStatistic("gcCount", this.mGcCount);
            this.mPageLoadProcedure.addStatistic("fps", this.mFpsList.toString());
            this.mPageLoadProcedure.addStatistic("jankCount", this.mJankCount);
            this.mPageLoadProcedure.addStatistic("image", this.mImageCount);
            this.mPageLoadProcedure.addStatistic("imageOnRequest", Integer.valueOf(this.mImageCount));
            this.mPageLoadProcedure.addStatistic("imageSuccessCount", Integer.valueOf(this.o));
            this.mPageLoadProcedure.addStatistic("imageFailedCount", Integer.valueOf(this.p));
            this.mPageLoadProcedure.addStatistic("imageCanceledCount", Integer.valueOf(this.q));
            this.mPageLoadProcedure.addStatistic("network", Integer.valueOf(this.r));
            this.mPageLoadProcedure.addStatistic("networkOnRequest", Integer.valueOf(this.r));
            this.mPageLoadProcedure.addStatistic("networkSuccessCount", Integer.valueOf(this.s));
            this.mPageLoadProcedure.addStatistic("networkFailedCount", Integer.valueOf(this.t));
            this.mPageLoadProcedure.addStatistic("networkCanceledCount", Integer.valueOf(this.u));
            this.mApplicationLowMemoryDispatcher.removeListener(this);
            this.mActivityEventDispatcher.removeListener(this);
            this.mFragmentUsableVisibleDispatcher.removeListener(this);
            this.mActivityFpsDispatcher.removeListener(this);
            this.mApplicationGcDispatcher.removeListener(this);
            this.mApplicationBackgroundChangedDispatcher.removeListener(this);
            this.mImageStageDispatcher.removeListener(this);
            this.mNetworkStageDispatcher.removeListener(this);
            this.mPageLoadProcedure.end();
            super.procedureEnd();
        }
    }

    public void fps(int i2) {
        if (this.mFpsList.size() < 200 && this.f67q) {
            this.mFpsList.add(Integer.valueOf(i2));
        }
    }

    public void jank(int i2) {
        if (this.f67q) {
            this.mJankCount += i2;
        }
    }

    public void gc() {
        if (this.f67q) {
            this.mGcCount++;
        }
    }

    public void backgroundChanged(int backgroundType, long timeMillis) {
        if (backgroundType == 1) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("timestamp", Long.valueOf(timeMillis));
            this.mPageLoadProcedure.event("foreground2Background", hashMap);
            procedureEnd();
            return;
        }
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("timestamp", Long.valueOf(timeMillis));
        this.mPageLoadProcedure.event("background2Foreground", hashMap2);
    }

    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long timeMillis) {
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
                        this.mPageLoadProcedure.addProperty("leaveType", "home");
                    } else {
                        this.mPageLoadProcedure.addProperty("leaveType", "back");
                    }
                    HashMap hashMap = new HashMap(2);
                    hashMap.put("timestamp", Long.valueOf(timeMillis));
                    hashMap.put("key", Integer.valueOf(keyEvent.getKeyCode()));
                    this.mPageLoadProcedure.event("keyEvent", hashMap);
                }
            }
        }
    }

    public void onFragmentPreAttached(Fragment fragment, long j) {
        procedureBegin();
        ProcedureManagerSetter.instance().setCurrentFragmentProcedure(this.mPageLoadProcedure);
        this.mPageLoadProcedure.stage("loadStartTime", j);
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentPreAttached", hashMap);
        this.b = fragment;
        this.f = j;
        o(fragment);
        this.f61c = TrafficTracker.traffics();
        OpenPageEvent openPageEvent = new OpenPageEvent();
        openPageEvent.pageName = fragment.getClass().getSimpleName();
        DumpManager.getInstance().append(openPageEvent);
    }

    public void onFragmentAttached(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentAttached", hashMap);
    }

    public void onFragmentPreCreated(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentPreCreated", hashMap);
    }

    public void onFragmentCreated(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentCreated", hashMap);
    }

    public void onFragmentActivityCreated(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentActivityCreated", hashMap);
    }

    public void onFragmentViewCreated(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentViewCreated", hashMap);
    }

    public void onFragmentStarted(Fragment fragment, long j) {
        ProcedureManagerSetter.instance().setCurrentFragmentProcedure(this.mPageLoadProcedure);
        this.f67q = true;
        this.g = j;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentStarted", hashMap);
        if (this.f66p) {
            this.f66p = false;
            long[] a2 = TrafficTracker.traffics();
            long[] jArr = this.f59b;
            jArr[0] = jArr[0] + (a2[0] - this.f61c[0]);
            long[] jArr2 = this.f59b;
            jArr2[1] = jArr2[1] + (a2[1] - this.f61c[1]);
        }
        this.f61c = TrafficTracker.traffics();
        GlobalStats.lastValidPage = this.pageName;
        GlobalStats.lastValidTime = j;
    }

    public void onFragmentResumed(Fragment fragment, long j) {
        ProcedureManagerSetter.instance().setCurrentFragmentProcedure(this.mPageLoadProcedure);
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentResumed", hashMap);
    }

    public void onFragmentPaused(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentPaused", hashMap);
    }

    public void onFragmentStopped(Fragment fragment, long j) {
        this.f67q = false;
        this.mTotalVisibleDuration += j - this.g;
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentStopped", hashMap);
        long[] a2 = TrafficTracker.traffics();
        long[] jArr = this.f59b;
        jArr[0] = jArr[0] + (a2[0] - this.f61c[0]);
        long[] jArr2 = this.f59b;
        jArr2[1] = jArr2[1] + (a2[1] - this.f61c[1]);
        this.f61c = a2;
        if (this.mFpsList != null && this.m > this.mFpsList.size()) {
            Integer valueOf = Integer.valueOf(0);
            int i2 = this.m;
            while (true) {
                int i3 = i2;
                if (i3 >= this.mFpsList.size()) {
                    break;
                }
                valueOf = Integer.valueOf(((Integer) this.mFpsList.get(i3)).intValue() + valueOf.intValue());
                i2 = i3 + 1;
            }
            this.a.averageUseFps = (float) (valueOf.intValue() / (this.mFpsList.size() - this.m));
        }
        DumpManager.getInstance().append(this.a);
    }

    public void onFragmentSaveInstanceState(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentSaveInstanceState", hashMap);
    }

    public void onFragmentViewDestroyed(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentViewDestroyed", hashMap);
    }

    public void imageStage(int i2) {
        if (!this.f67q) {
            return;
        }
        if (i2 == 0) {
            this.mImageCount++;
        } else if (i2 == 1) {
            this.o++;
        } else if (i2 == 2) {
            this.p++;
        } else if (i2 == 3) {
            this.q++;
        }
    }

    public void networkStage(int i2) {
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

    public void onFragmentDestroyed(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentDestroyed", hashMap);
    }

    public void onFragmentDetached(Fragment fragment, long j) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("timestamp", Long.valueOf(j));
        this.mPageLoadProcedure.event("onFragmentDetached", hashMap);
        long[] a2 = TrafficTracker.traffics();
        long[] jArr = this.f59b;
        jArr[0] = jArr[0] + (a2[0] - this.f61c[0]);
        long[] jArr2 = this.f59b;
        jArr2[1] = jArr2[1] + (a2[1] - this.f61c[1]);
        FinishLoadPageEvent finishLoadPageEvent = new FinishLoadPageEvent();
        finishLoadPageEvent.pageName = fragment.getClass().getSimpleName();
        DumpManager.getInstance().append(finishLoadPageEvent);
        procedureEnd();
    }
}
