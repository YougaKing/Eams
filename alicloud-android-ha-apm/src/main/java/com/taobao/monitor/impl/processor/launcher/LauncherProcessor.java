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
import com.taobao.monitor.impl.trace.ApplicationGcDispatcher;
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
import java.util.Map;

import static com.taobao.application.common.IAppLaunchListener.COLD;
import static com.taobao.application.common.IAppLaunchListener.HOT;
import static com.taobao.application.common.IAppLaunchListener.LAUNCH_COMPLETED;
import static com.taobao.application.common.IAppLaunchListener.LAUNCH_DRAW_START;
import static com.taobao.application.common.IAppLaunchListener.LAUNCH_INTERACTIVE;
import static com.taobao.application.common.IAppLaunchListener.LAUNCH_VISIBLE;

/* compiled from: LauncherProcessor */
public class LauncherProcessor extends AbsProcessor implements OnUsableVisibleListener<Activity>,
        PageModelLifecycle.ModelLifecycleListener,
        ActivityEventDispatcher.EventListener,
        ApplicationBackgroundChangedDispatcher.BackgroundChangedListener,
        ApplicationGcDispatcher.GcListener,
        ApplicationLowMemoryDispatcher.LowMemoryListener,
        FPSDispatcher.FPSListener,
        FragmentFunctionListener,
        ImageStageDispatcher.StageListener,
        NetworkStageDispatcher.StageListener {

    public static volatile String sLaunchType = "COLD";
    public static boolean isBackgroundLaunch = false;
    private IDispatcher<ActivityEventDispatcher.EventListener> mActivityEventDispatcher;

    /* renamed from: a reason: collision with other field name */
    private IProcedure mStartupProcedure;
    private IAppLaunchListener mAppLaunchListener = ApmImpl.instance().appLaunchListener();

    /* renamed from: b reason: collision with other field name */
    private IDispatcher<ApplicationLowMemoryDispatcher.LowMemoryListener> mApplicationLowMemoryDispatcher;

    /* renamed from: b reason: collision with other field name */
    private HashMap<String, Integer> mFragmentLifeMethodName = new HashMap<>();

    /* renamed from: b reason: collision with other field name */
    private List<Integer> mFpsList = new ArrayList<>();

    /* renamed from: c reason: collision with other field name */
    private int mJankCount = 0;

    /* renamed from: c reason: collision with other field name */
    private IDispatcher<FPSDispatcher.FPSListener> mActivityFpsDispatcher;

    /* renamed from: c reason: collision with other field name */
    private List<String> mActivitySimpleNameList = new ArrayList<>(4);

    /* renamed from: c reason: collision with other field name */
    private long[] mTraffics;
    private Activity mLauncherActivity = null;

    /* renamed from: d reason: collision with other field name */
    private IDispatcher<ApplicationGcDispatcher.GcListener> mApplicationGcDispatcher;

    /* renamed from: d reason: collision with other field name */
    private String mCurrentActivityName;
    private IDispatcher<OnUsableVisibleListener> mActivityUsableVisibleDispatcher;

    /* renamed from: e reason: collision with other field name */
    private String mActivityName;
    private IDispatcher<ApplicationBackgroundChangedDispatcher.BackgroundChangedListener> mApplicationBackgroundChangedDispatcher;

    /* renamed from: f reason: collision with other field name */
    private String mLaunchType = sLaunchType;
    private IDispatcher<NetworkStageDispatcher.StageListener> mNetworkStageDispatcher;
    private IDispatcher<ImageStageDispatcher.StageListener> mImageStageDispatcher;
    private long mLaunchTime;

    /* renamed from: i reason: collision with other field name */
    private boolean mEnd = false;
    private int mGcCount = 0;
    private int mImageCount;
    private int mImageSuccessCount;

    /* renamed from: o reason: collision with other field name */
    private boolean mMotionEvent = true;
    private int mImageFailedCount;
    private int mImageCanceledCount;
    private int mNetworkCount;

    /* renamed from: r reason: collision with other field name */
    private boolean mLauncherActivityDestroyed = true;
    private int mNetworkSuccessCount;

    /* renamed from: s reason: collision with other field name */
    private boolean mUsable = true;
    private int mNetworkFailedCount;

    /* renamed from: t reason: collision with other field name */
    private boolean mDisplay = true;
    private int mNetworkCanceledCount;

    /* renamed from: u reason: collision with other field name */
    private boolean mIsLauncherActivity = false;
    private volatile boolean mLaunchCompleted = false;

    public LauncherProcessor() {
        super(false);
    }

    @Override
    public void procedureBegin() {
        super.procedureBegin();
        this.mTraffics = TrafficTracker.traffics();
        new AppLaunchHelper().launchType(this.mLaunchType);
        this.mStartupProcedure = ProcedureManagerProxy.PROXY.getLauncherProcedure();
        if (this.mStartupProcedure == null || !this.mStartupProcedure.isAlive()) {
            this.mStartupProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/startup"), new Builder()
                    .setIndependent(false)
                    .setUpload(true)
                    .setParentNeedStats(true)
                    .setParent(null)
                    .build());
            this.mStartupProcedure.begin();
            ProcedureManagerSetter.instance().setCurrentLauncherProcedure(this.mStartupProcedure);
        }
        this.mStartupProcedure.stage("procedureStartTime", TimeUtils.currentTimeMillis());
        this.mActivityEventDispatcher = getDispatcher("ACTIVITY_EVENT_DISPATCHER");
        this.mApplicationLowMemoryDispatcher = getDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER");
        this.mActivityUsableVisibleDispatcher = getDispatcher("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        this.mActivityFpsDispatcher = getDispatcher("ACTIVITY_FPS_DISPATCHER");
        this.mApplicationGcDispatcher = getDispatcher("APPLICATION_GC_DISPATCHER");
        this.mApplicationBackgroundChangedDispatcher = getDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER");
        this.mNetworkStageDispatcher = getDispatcher("NETWORK_STAGE_DISPATCHER");
        this.mImageStageDispatcher = getDispatcher("IMAGE_STAGE_DISPATCHER");
        this.mApplicationLowMemoryDispatcher.addListener(this);
        this.mActivityFpsDispatcher.addListener(this);
        this.mApplicationGcDispatcher.addListener(this);
        this.mActivityEventDispatcher.addListener(this);
        this.mActivityUsableVisibleDispatcher.addListener(this);
        this.mApplicationBackgroundChangedDispatcher.addListener(this);
        this.mNetworkStageDispatcher.addListener(this);
        this.mImageStageDispatcher.addListener(this);
        FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.addListener(this);
        addBeginProperty();
        StartUpBeginEvent startUpBeginEvent = new StartUpBeginEvent();
        startUpBeginEvent.firstInstall = GlobalStats.isFirstInstall;
        startUpBeginEvent.launchType = sLaunchType;
        startUpBeginEvent.isBackgroundLaunch = isBackgroundLaunch;
        DumpManager.getInstance().append(startUpBeginEvent);
        isBackgroundLaunch = false;
    }

    private void addBeginProperty() {
        this.mLaunchTime = "COLD".equals(sLaunchType) ? GlobalStats.launchStartTime : TimeUtils.currentTimeMillis();
        this.mStartupProcedure.addProperty("errorCode", 1);
        this.mStartupProcedure.addProperty("launchType", sLaunchType);
        this.mStartupProcedure.addProperty("isFirstInstall", GlobalStats.isFirstInstall);
        this.mStartupProcedure.addProperty("isFirstLaunch", GlobalStats.isFirstLaunch);
        this.mStartupProcedure.addProperty("installType", GlobalStats.installType);
        this.mStartupProcedure.addProperty("oppoCPUResource", GlobalStats.oppoCPUResource);
        this.mStartupProcedure.addProperty("leaveType", "other");
        this.mStartupProcedure.addProperty("lastProcessStartTime", GlobalStats.lastProcessStartTime);
        this.mStartupProcedure.addProperty("systemInitDuration", GlobalStats.launchStartTime - GlobalStats.processStartTime);
        this.mStartupProcedure.stage("processStartTime", GlobalStats.processStartTime);
        this.mStartupProcedure.stage("launchStartTime", GlobalStats.launchStartTime);
    }

    public void onActivityCreated(Activity activity, Bundle bundle, long j) {
        String simpleName = ActivityUtils.getSimpleName(activity);
        this.mActivityName = ActivityUtils.getName(activity);
        if (!this.mIsLauncherActivity) {
            this.mLauncherActivity = activity;
            procedureBegin();
            this.mStartupProcedure.addProperty("systemRecovery", Boolean.FALSE);
            if ("COLD".equals(sLaunchType) && this.mActivityName.equals(GlobalStats.lastTopActivity)) {
                this.mStartupProcedure.addProperty("systemRecovery", Boolean.TRUE);
                this.mCurrentActivityName = this.mActivityName;
                this.mActivitySimpleNameList.add(simpleName);
            }
            Intent intent = activity.getIntent();
            if (intent != null) {
                String dataString = intent.getDataString();
                if (!TextUtils.isEmpty(dataString)) {
                    this.mStartupProcedure.addProperty("schemaUrl", dataString);
                    OpenAppFromURL openAppFromURL = new OpenAppFromURL();
                    openAppFromURL.url = dataString;
                    openAppFromURL.time = j;
                    DumpManager.getInstance().append(openAppFromURL);
                }
            }
            this.mStartupProcedure.addProperty("firstPageName", simpleName);
            this.mStartupProcedure.stage("firstPageCreateTime", j);
            this.mLaunchType = sLaunchType;
            sLaunchType = "HOT";
            this.mIsLauncherActivity = true;
        }
        if (this.mActivitySimpleNameList.size() < 10 && TextUtils.isEmpty(this.mCurrentActivityName)) {
            this.mActivitySimpleNameList.add(simpleName);
        }
        if (TextUtils.isEmpty(this.mCurrentActivityName) && (PageList.isWhiteListEmpty() || PageList.inWhiteList(this.mActivityName))) {
            this.mCurrentActivityName = this.mActivityName;
        }
        Map<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("timestamp", j);
        hashMap.put("pageName", simpleName);
        this.mStartupProcedure.event("onActivityCreated", hashMap);
    }

    @Override
    public void onResume(Activity activity, long j) {
        Map<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("timestamp", j);
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.mStartupProcedure.event("onActivityStarted", hashMap);
    }

    @Override
    public void onActivityResumed(Activity activity, long j) {
        Map<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("timestamp", j);
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.mStartupProcedure.event("onActivityResumed", hashMap);
    }

    @Override
    public void onActivityPaused(Activity activity, long j) {
        Map<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("timestamp", j);
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.mStartupProcedure.event("onActivityPaused", hashMap);
    }

    @Override
    public void onActivityStopped(Activity activity, long j) {
        Map<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("timestamp", j);
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.mStartupProcedure.event("onActivityStopped", hashMap);
        if (activity == this.mLauncherActivity) {
            procedureEnd();
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity, long j) {
        Map<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("timestamp", j);
        hashMap.put("pageName", ActivityUtils.getSimpleName(activity));
        this.mStartupProcedure.event("onActivityDestroyed", hashMap);
        if (activity == this.mLauncherActivity) {
            this.mLauncherActivityDestroyed = true;
            procedureEnd();
        }
    }

    @Override
    public void onLowMemory() {
        Map<String, Object> hashMap = new HashMap<>(1);
        hashMap.put("timestamp", TimeUtils.currentTimeMillis());
        this.mStartupProcedure.event("onLowMemory", hashMap);
    }

    @Override
    public void onMotionEvent(Activity activity, MotionEvent motionEvent, long timeMillis) {
        if (this.mMotionEvent && !PageList.inBlackList(ActivityUtils.getName(activity))) {
            if (TextUtils.isEmpty(this.mCurrentActivityName)) {
                this.mCurrentActivityName = ActivityUtils.getName(activity);
            }
            if (activity == this.mLauncherActivity) {
                this.mStartupProcedure.stage("firstInteractiveTime", timeMillis);
                this.mStartupProcedure.addProperty("firstInteractiveDuration", timeMillis - this.mLaunchTime);
                this.mStartupProcedure.addProperty("leaveType", "touch");
                this.mStartupProcedure.addProperty("errorCode", 0);
                DumpManager.getInstance().append(new FirstInteractionEvent());
                this.mMotionEvent = false;
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity, long timeMillis) {
        if (this.mLauncherActivityDestroyed && activity == this.mLauncherActivity) {
            this.mStartupProcedure.addProperty("appInitDuration", timeMillis - this.mLaunchTime);
            this.mStartupProcedure.stage("renderStartTime", timeMillis);
            DumpManager.getInstance().append(new FirstDrawEvent());
            this.mLauncherActivityDestroyed = false;
            this.mAppLaunchListener.onLaunchChanged(getLaunchType(), LAUNCH_DRAW_START);
        }
    }

    private int getLaunchType() {
        return this.mLaunchType.equals("COLD") ? COLD : HOT;
    }

    @Override
    public void visiblePercent(Activity activity, float percent, long timeMillis) {
        if (activity == this.mLauncherActivity) {
            this.mStartupProcedure.addProperty("onRenderPercent", percent);
            this.mStartupProcedure.addProperty("drawPercentTime", timeMillis);
        }
    }

    @Override
    public void usable(Activity activity, int i2, int i3, long j) {
        if (this.mUsable && activity == this.mLauncherActivity && i2 == 2) {
            this.mStartupProcedure.addProperty("errorCode", 0);
            this.mStartupProcedure.addProperty("interactiveDuration", j - this.mLaunchTime);
            this.mStartupProcedure.addProperty("launchDuration", j - this.mLaunchTime);
            this.mStartupProcedure.addProperty("deviceLevel", AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
            this.mStartupProcedure.addProperty("runtimeLevel", AliHAHardware.getInstance().getOutlineInfo().runtimeLevel);
            this.mStartupProcedure.addProperty("cpuUsageOfDevcie", AliHAHardware.getInstance().getCpuInfo().cpuUsageOfDevcie);
            this.mStartupProcedure.addProperty("memoryRuntimeLevel", AliHAHardware.getInstance().getMemoryInfo().runtimeLevel);
            this.mStartupProcedure.addProperty("usableChangeType", i3);
            this.mStartupProcedure.stage("interactiveTime", j);
            LauncherUsableEvent launcherUsableEvent = new LauncherUsableEvent();
            launcherUsableEvent.duration = (float) (j - this.mLaunchTime);
            DumpManager.getInstance().append(launcherUsableEvent);
            this.mAppLaunchListener.onLaunchChanged(getLaunchType(), LAUNCH_INTERACTIVE);
            onLaunchCompleted();
            this.mUsable = false;
        }
    }

    @Override
    public void display(Activity activity, int i2, long j) {
        if (this.mDisplay) {
            if (i2 == 2 && !PageList.inBlackList(this.mActivityName) && TextUtils.isEmpty(this.mCurrentActivityName)) {
                this.mCurrentActivityName = this.mActivityName;
            }
            if (activity == this.mLauncherActivity && i2 == 2) {
                this.mStartupProcedure.addProperty("displayDuration", j - this.mLaunchTime);
                this.mStartupProcedure.stage("displayedTime", j);
                DumpManager.getInstance().append(new DisplayedEvent());
                this.mAppLaunchListener.onLaunchChanged(getLaunchType(), LAUNCH_VISIBLE);
                this.mDisplay = false;
            }
        }
    }

    @Override
    public void procedureEnd() {
        if (!this.mEnd) {
            this.mEnd = true;
            onLaunchCompleted();
            if (!TextUtils.isEmpty(this.mCurrentActivityName)) {
                this.mStartupProcedure.addProperty("currentPageName", this.mCurrentActivityName.substring(this.mCurrentActivityName.lastIndexOf(".") + 1));
                this.mStartupProcedure.addProperty("fullPageName", this.mCurrentActivityName);
            }
            this.mStartupProcedure.addProperty("linkPageName", this.mActivitySimpleNameList.toString());
            this.mActivitySimpleNameList.clear();
            this.mStartupProcedure.addProperty("hasSplash", GlobalStats.hasSplash);
            this.mStartupProcedure.addStatistic("gcCount", this.mGcCount);
            this.mStartupProcedure.addStatistic("fps", this.mFpsList.toString());
            this.mStartupProcedure.addStatistic("jankCount", this.mJankCount);
            this.mStartupProcedure.addStatistic("image", this.mImageCount);
            this.mStartupProcedure.addStatistic("imageOnRequest", this.mImageCount);
            this.mStartupProcedure.addStatistic("imageSuccessCount", this.mImageSuccessCount);
            this.mStartupProcedure.addStatistic("imageFailedCount", this.mImageFailedCount);
            this.mStartupProcedure.addStatistic("imageCanceledCount", this.mImageCanceledCount);
            this.mStartupProcedure.addStatistic("network", this.mNetworkCount);
            this.mStartupProcedure.addStatistic("networkOnRequest", this.mNetworkCount);
            this.mStartupProcedure.addStatistic("networkSuccessCount", this.mNetworkSuccessCount);
            this.mStartupProcedure.addStatistic("networkFailedCount", this.mNetworkFailedCount);
            this.mStartupProcedure.addStatistic("networkCanceledCount", this.mNetworkCanceledCount);
            long[] traffics = TrafficTracker.traffics();
            this.mStartupProcedure.addStatistic("totalRx", traffics[0] - this.mTraffics[0]);
            this.mStartupProcedure.addStatistic("totalTx", traffics[1] - this.mTraffics[1]);
            this.mStartupProcedure.stage("procedureEndTime", TimeUtils.currentTimeMillis());
            GlobalStats.hasSplash = false;
            this.mApplicationBackgroundChangedDispatcher.removeListener(this);
            this.mApplicationLowMemoryDispatcher.removeListener(this);
            this.mApplicationGcDispatcher.removeListener(this);
            this.mActivityFpsDispatcher.removeListener(this);
            this.mActivityEventDispatcher.removeListener(this);
            this.mActivityUsableVisibleDispatcher.removeListener(this);
            this.mImageStageDispatcher.removeListener(this);
            this.mNetworkStageDispatcher.removeListener(this);
            FragmentFunctionDispatcher.FRAGMENT_FUNCTION_DISPATCHER.removeListener(this);
            this.mStartupProcedure.end();
            DumpManager.getInstance().append(new StartUpEndEvent());
            super.procedureEnd();
        }
    }

    @Override
    public void fps(int fps) {
        if (this.mFpsList.size() < 200) {
            this.mFpsList.add(fps);
        }
    }

    @Override
    public void jank(int jankCount) {
        this.mJankCount += jankCount;
    }

    @Override
    public void gc() {
        this.mGcCount++;
    }

    @Override
    public void backgroundChanged(int i2, long j) {
        if (i2 == 1) {
            Map<String, Object> hashMap = new HashMap<>(1);
            hashMap.put("timestamp", j);
            this.mStartupProcedure.event("foreground2Background", hashMap);
            procedureEnd();
        }
    }

    private void onLaunchCompleted() {
        if (!this.mLaunchCompleted) {
            IAppLaunchListener iAppLaunchListener = this.mAppLaunchListener;
            int launchType;
            if (this.mLaunchType.equals("COLD")) {
                launchType = 0;
            } else {
                launchType = 1;
            }
            iAppLaunchListener.onLaunchChanged(launchType, LAUNCH_COMPLETED);
            this.mLaunchCompleted = true;
        }
    }

    @Override
    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long timeMillis) {
        if (!PageList.inBlackList(ActivityUtils.getName(activity)) && activity == this.mLauncherActivity) {
            int action = keyEvent.getAction();
            int keyCode = keyEvent.getKeyCode();
            if (action != 0) {
                return;
            }
            if (keyCode == 4 || keyCode == 3) {
                if (TextUtils.isEmpty(this.mCurrentActivityName)) {
                    this.mCurrentActivityName = ActivityUtils.getName(activity);
                }
                if (keyCode == 3) {
                    this.mStartupProcedure.addProperty("leaveType", "home");
                } else {
                    this.mStartupProcedure.addProperty("leaveType", "back");
                }
                Map<String, Object> hashMap = new HashMap<>(2);
                hashMap.put("timestamp", timeMillis);
                hashMap.put("key", keyEvent.getKeyCode());
                this.mStartupProcedure.event("keyEvent", hashMap);
            }
        }
    }

    @Override
    public void imageStage(int i2) {
        if (i2 == 0) {
            this.mImageCount++;
        } else if (i2 == 1) {
            this.mImageSuccessCount++;
        } else if (i2 == 2) {
            this.mImageFailedCount++;
        } else if (i2 == 3) {
            this.mImageCanceledCount++;
        }
    }

    @Override
    public void networkStage(int i2) {
        if (i2 == 0) {
            this.mNetworkCount++;
        } else if (i2 == 1) {
            this.mNetworkSuccessCount++;
        } else if (i2 == 2) {
            this.mNetworkFailedCount++;
        } else if (i2 == 3) {
            this.mNetworkCanceledCount++;
        }
    }

    @Override
    public void onFragmentAttached(Activity activity, Fragment fragment, String methodName, long timeMillis) {
        Integer valueOf;
        if (fragment != null && activity != null && activity == this.mLauncherActivity) {
            String lifeMethodName = fragment.getClass().getSimpleName() + "_" + methodName;
            Integer num = this.mFragmentLifeMethodName.get(lifeMethodName);
            if (num == null) {
                valueOf = 0;
            } else {
                valueOf = num + 1;
            }
            this.mFragmentLifeMethodName.put(lifeMethodName, valueOf);
            this.mStartupProcedure.stage(lifeMethodName + valueOf, timeMillis);
        }
    }
}
