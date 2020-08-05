//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.os.MessageQueue.IdleHandler;
import android.text.TextUtils;
import com.ali.alihadeviceevaluator.AliHAHardware;
import com.taobao.application.common.ApmHelper;
import com.taobao.application.common.data.DeviceHelper;
import com.taobao.application.common.data.AppLaunchHelper;
import com.taobao.application.common.data.AppLaunchHelper.AppLaunchHelperHolder;
import com.taobao.monitor.impl.common.ActivityManagerInvoke;
import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.activity.ActivityLifecycle;
import com.taobao.monitor.impl.data.phenix.PhenixLifeCycleImpl;
import com.taobao.monitor.impl.processor.fragmentload.FragmentModelLifecycle;
import com.taobao.monitor.impl.processor.launcher.LauncherModelLifeCycle;
import com.taobao.monitor.impl.processor.launcher.LauncherProcessor;
import com.taobao.monitor.impl.processor.pageload.PageModelLifecycle;
import com.taobao.monitor.impl.processor.weex.WeexApmAdapterFactory;
import com.taobao.monitor.impl.trace.ActivityEventDispatcher;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;
import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.ApplicationLowMemoryDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.trace.FragmentLifecycleDispatcher;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
import com.taobao.monitor.impl.trace.UsableVisibleDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.d;
import com.taobao.monitor.impl.util.SafeUtils;
import com.taobao.monitor.performance.APMAdapterFactoryProxy;
import com.taobao.network.lifecycle.MtopLifecycleManager;
import com.taobao.network.lifecycle.NetworkLifecycleManager;
import com.taobao.phenix.lifecycle.PhenixLifeCycleManager;
import java.net.URLEncoder;
import java.util.Map;

public class APMLauncher {
    private static final String TAG = "APMLauncher";
    private static boolean init = false;
    private static final AppLaunchHelper launchHelper = new AppLaunchHelper();

    private APMLauncher() {
    }

    public static void init(Application var0, Map<String, Object> var1) {
        if (!init) {
            init = true;
            initParams(var0, var1);
            initHotCold();
            initDispatcher();
            firstAsyncMessage();
            initLifecycle(var0);
            initHookActivityManager();
            initApmImpl();
        }

    }

    private static void initParams(Application var0, Map<String, Object> var1) {
        GlobalStats.launchStartTime = TimeUtils.currentTimeMillis();
        launchHelper.launchType("COLD");
        launchHelper.startAppOnCreateSystemClockTime(SystemClock.uptimeMillis());
        launchHelper.startAppOnCreateSystemTime(System.currentTimeMillis());
        String var2 = "ALI_APM/device-id/monitor/procedure";
        if (var1 != null) {
            GlobalStats.appVersion = SafeUtils.transformString(var1.get("appVersion"), "unknown");
            Object var3 = var1.get("deviceId");
            if (var3 instanceof String) {
                String var4 = (String)var3;

                try {
                    var4 = URLEncoder.encode(var4, "UTF-8");
                } catch (Exception var9) {
                }

                var2 = "ALI_APM/" + var4 + "/monitor/procedure";
            }
        }

        Global.instance().setContext(var0).setNamespace(var2);
        Context var10 = Global.instance().context();
        SharedPreferences sharedPreferences = var10.getSharedPreferences("apm", 0);
        String var5 = sharedPreferences.getString("appVersion", "");
        Editor editor = sharedPreferences.edit();
        boolean var7 = false;
        if (TextUtils.isEmpty(var5)) {
            GlobalStats.isFirstInstall = true;
            GlobalStats.isFirstLaunch = true;
            GlobalStats.installType = "NEW";
            editor.putString("appVersion", GlobalStats.appVersion);
            var7 = true;
        } else {
            GlobalStats.isFirstInstall = false;
            GlobalStats.isFirstLaunch = !var5.equals(GlobalStats.appVersion);
            GlobalStats.installType = "UPDATE";
            if (GlobalStats.isFirstLaunch) {
                editor.putString("appVersion", GlobalStats.appVersion);
                var7 = true;
            }
        }

        GlobalStats.lastTopActivity = sharedPreferences.getString("LAST_TOP_ACTIVITY", "");
        if (!TextUtils.isEmpty(GlobalStats.lastTopActivity)) {
            editor.putString("LAST_TOP_ACTIVITY", "");
            var7 = true;
        }

        if (var7) {
            editor.apply();
        }

        GlobalStats.lastProcessStartTime = AppLaunchHelperHolder.lastStartProcessTime();
        launchHelper.isFirstLaunch(GlobalStats.isFirstLaunch);
        launchHelper.isFullNewInstall(GlobalStats.isFirstInstall);
        launchHelper.lastStartProcessTime(GlobalStats.lastProcessStartTime);
        DeviceHelper deviceHelper = new DeviceHelper();
        deviceHelper.setMobileModel(Build.MODEL);
    }

    private static void initHotCold() {
        Global.instance().handler().postDelayed(new Runnable() {
            public void run() {
                Looper.getMainLooper();
                Looper.myQueue().addIdleHandler(new IdleHandler() {
                    public boolean queueIdle() {
                        if (GlobalStats.createdPageCount == 0) {
                            LauncherProcessor.sLaunchType = "HOT";
                            LauncherProcessor.isBackgroundLaunch = true;
                            APMLauncher.launchHelper.launchType("HOT");
                        }

                        return false;
                    }
                });
            }
        }, 3000L);
    }

    private static void initLifecycle(Application var0) {
        var0.registerActivityLifecycleCallbacks(new ActivityLifecycle());
    }

    private static void initWeex() {
        if (DynamicConstants.needWeex) {
            APMAdapterFactoryProxy.instance().setFactory(new WeexApmAdapterFactory());
        }

    }

    private static void initHookActivityManager() {
        if (VERSION.SDK_INT <= 28) {
            runInMain(new Runnable() {
                public void run() {
                    ActivityManagerInvoke.start();
                }
            });
        }

    }

    private static void runInMain(Runnable var0) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            var0.run();
        } else {
            Handler var1 = new Handler(Looper.getMainLooper());
            var1.post(var0);
        }

    }

    private static void initProcessStartTime() {
        if (VERSION.SDK_INT >= 24) {
            GlobalStats.processStartTime = TimeUtils.currentTimeMillis() + Process.getStartUptimeMillis() - SystemClock.uptimeMillis();
            launchHelper.startProcessSystemTime(System.currentTimeMillis() - (SystemClock.uptimeMillis() - GlobalStats.processStartTime));
        } else {
            long var0 = d.b();
            launchHelper.startProcessSystemTime(var0);
            if (var0 != -1L) {
                GlobalStats.processStartTime = TimeUtils.currentTimeMillis() - (System.currentTimeMillis() - var0);
            } else {
                GlobalStats.processStartTime = TimeUtils.currentTimeMillis() - Process.getElapsedCpuTime();
            }
        }

        launchHelper.startProcessSystemClockTime(GlobalStats.processStartTime);
    }

    private static void initOppoCPUResource() {
        String var0 = System.getProperty("oppoCPUResource", "false");
        GlobalStats.oppoCPUResource = var0;
    }

    private static void firstAsyncMessage() {
        Global.instance().handler().post(new Runnable() {
            public void run() {
                APMLauncher.initOppoCPUResource();
                APMLauncher.initExecutor();
                APMLauncher.initWeex();
                APMLauncher.initProcessStartTime();
                DeviceHelper var1 = new DeviceHelper();
                var1.setDeviceLevel(AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
                var1.setCpuScore(AliHAHardware.getInstance().getCpuInfo().deviceLevel);
                var1.setMemScore(AliHAHardware.getInstance().getMemoryInfo().deviceLevel);
            }
        });
    }

    private static void initDispatcher() {
        DispatcherManager.putDispatcher("APPLICATION_LOW_MEMORY_DISPATCHER", new ApplicationLowMemoryDispatcher());
        DispatcherManager.putDispatcher("APPLICATION_GC_DISPATCHER", new ApplicationGCDispatcher());
        ApplicationBackgroundChangedDispatcher var0 = new ApplicationBackgroundChangedDispatcher();
        DispatcherManager.putDispatcher("APPLICATION_BACKGROUND_CHANGED_DISPATCHER", var0);
        DispatcherManager.putDispatcher("ACTIVITY_FPS_DISPATCHER", new FPSDispatcher());
        ActivityLifeCycleDispatcher var1 = new ActivityLifeCycleDispatcher();
        var1.addListener(new PageModelLifecycle());
        var1.addListener(new LauncherModelLifeCycle());
        DispatcherManager.putDispatcher("ACTIVITY_LIFECYCLE_DISPATCHER", var1);
        DispatcherManager.putDispatcher("ACTIVITY_EVENT_DISPATCHER", new ActivityEventDispatcher());
        DispatcherManager.putDispatcher("ACTIVITY_USABLE_VISIBLE_DISPATCHER", new UsableVisibleDispatcher());
        FragmentLifecycleDispatcher var2 = new FragmentLifecycleDispatcher();
        var2.addListener(new FragmentModelLifecycle());
        DispatcherManager.putDispatcher("FRAGMENT_LIFECYCLE_DISPATCHER", var2);
        DispatcherManager.putDispatcher("FRAGMENT_USABLE_VISIBLE_DISPATCHER", new UsableVisibleDispatcher());
        DispatcherManager.putDispatcher("IMAGE_STAGE_DISPATCHER", new ImageStageDispatcher());
        PhenixLifeCycleManager.instance().addLifeCycle(new PhenixLifeCycleImpl());
        DispatcherManager.putDispatcher("NETWORK_STAGE_DISPATCHER", new NetworkStageDispatcher());
        NetworkLifecycleManager.instance().setLifecycle(new com.taobao.monitor.impl.data.network.a());
        MtopLifecycleManager.instance().setLifecycle(new com.taobao.monitor.impl.data.network.a());
    }

    private static void initExecutor() {
        com.taobao.monitor.impl.data.gc.a var0 = new com.taobao.monitor.impl.data.gc.a();
        var0.execute();
    }

    private static void initApmImpl() {
        ApmHelper.instance();
    }
}
