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
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;

import com.ali.alihadeviceevaluator.AliHAHardware;
import com.taobao.application.common.data.AppLaunchHelper;
import com.taobao.application.common.data.DeviceHelper;
import com.taobao.application.common.data.c;
import com.taobao.application.common.data.c.a;
import com.taobao.monitor.impl.common.ActivityManagerHook;
import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.AbstractDataCollector;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.collector.ActivityLifecycle;
import com.taobao.monitor.impl.processor.launcher.LauncherProcessor;
import com.taobao.monitor.impl.processor.launcher.b;
import com.taobao.monitor.impl.trace.f;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.trace.i;
import com.taobao.monitor.impl.trace.l;
import com.taobao.monitor.impl.trace.m;
import com.taobao.monitor.impl.trace.n;
import com.taobao.monitor.impl.trace.o;
import com.taobao.monitor.impl.util.SafeUtils;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.d;
import com.taobao.monitor.impl.util.e;
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

    public static void init(Application application, Map<String, Object> map) {
        if (!init) {
            init = true;
            initParams(application, map);
            initHotCold();
            initDispatcher();
            firstAsyncMessage();
            initLifecycle(application);
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
            GlobalStats.appVersion = SafeUtils.instanceofString(var1.get("appVersion"), "unknown");
            Object var3 = var1.get("deviceId");
            if (var3 instanceof String) {
                String var4 = (String) var3;

                try {
                    var4 = URLEncoder.encode(var4, "UTF-8");
                } catch (Exception var9) {
                }

                var2 = "ALI_APM/" + var4 + "/monitor/procedure";
            }
        }

        Global.instance().setContext(var0).setNamespace(var2);
        Context var10 = Global.instance().context();
        SharedPreferences var11 = var10.getSharedPreferences("apm", 0);
        String var5 = var11.getString("appVersion", "");
        Editor var6 = var11.edit();
        boolean var7 = false;
        if (TextUtils.isEmpty(var5)) {
            GlobalStats.isFirstInstall = true;
            GlobalStats.isFirstLaunch = true;
            GlobalStats.installType = "NEW";
            var6.putString("appVersion", GlobalStats.appVersion);
            var7 = true;
        } else {
            GlobalStats.isFirstInstall = false;
            GlobalStats.isFirstLaunch = !var5.equals(GlobalStats.appVersion);
            GlobalStats.installType = "UPDATE";
            if (GlobalStats.isFirstLaunch) {
                var6.putString("appVersion", GlobalStats.appVersion);
                var7 = true;
            }
        }

        GlobalStats.lastTopActivity = var11.getString("LAST_TOP_ACTIVITY", "");
        if (!TextUtils.isEmpty(GlobalStats.lastTopActivity)) {
            var6.putString("LAST_TOP_ACTIVITY", "");
            var7 = true;
        }

        if (var7) {
            var6.apply();
        }

        GlobalStats.lastProcessStartTime = AppLaunchHelper.AppLaunchHelperHolder.lastStartProcessTime();
        launchHelper.isFirstLaunch(GlobalStats.isFirstLaunch);
        launchHelper.isFullNewInstall(GlobalStats.isFirstInstall);
        launchHelper.lastStartProcessTime(GlobalStats.lastProcessStartTime);
        DeviceHelper var8 = new DeviceHelper();
        var8.setMobileModel(Build.MODEL);
    }

    private static void initHotCold() {
        Global.instance().handler().postDelayed(new Runnable() {
            public void run() {
                Looper.getMainLooper();
                Looper.myQueue().addIdleHandler(new IdleHandler() {
                    public boolean queueIdle() {
                        if (GlobalStats.createdPageCount == 0) {
                            LauncherProcessor.c = "HOT";
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
//        if (DynamicConstants.needWeex) {
//            APMAdapterFactoryProxy.instance().setFactory(new com.taobao.monitor.impl.processor.a.a());
//        }
    }

    private static void initHookActivityManager() {
        if (VERSION.SDK_INT <= 28) {
            runInMain(new Runnable() {
                public void run() {
                    ActivityManagerHook.start();
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
            launchHelper.b(System.currentTimeMillis() - (SystemClock.uptimeMillis() - GlobalStats.processStartTime));
        } else {
            long var0 = d.b();
            launchHelper.b(var0);
            if (var0 != -1L) {
                GlobalStats.processStartTime = TimeUtils.currentTimeMillis() - (System.currentTimeMillis() - var0);
            } else {
                GlobalStats.processStartTime = TimeUtils.currentTimeMillis() - Process.getElapsedCpuTime();
            }
        }

        launchHelper.c(GlobalStats.processStartTime);
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
        g.a("APPLICATION_LOW_MEMORY_DISPATCHER", new f());
        g.a("APPLICATION_GC_DISPATCHER", new com.taobao.monitor.impl.trace.e());
        com.taobao.monitor.impl.trace.d var0 = new com.taobao.monitor.impl.trace.d();
        g.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER", var0);
        g.a("ACTIVITY_FPS_DISPATCHER", new i());
        com.taobao.monitor.impl.trace.c var1 = new com.taobao.monitor.impl.trace.c();
        var1.addListener(new com.taobao.monitor.impl.processor.pageload.e());
        var1.addListener(new com.taobao.monitor.impl.processor.launcher.a());
        g.a("ACTIVITY_LIFECYCLE_DISPATCHER", var1);
        g.a("ACTIVITY_EVENT_DISPATCHER", new com.taobao.monitor.impl.trace.b());
        g.a("ACTIVITY_USABLE_VISIBLE_DISPATCHER", new o());
        l var2 = new l();
        var2.addListener(new com.taobao.monitor.impl.processor.fragmentload.a());
        g.a("FRAGMENT_LIFECYCLE_DISPATCHER", var2);
        g.a("FRAGMENT_USABLE_VISIBLE_DISPATCHER", new o());
        g.a("IMAGE_STAGE_DISPATCHER", new m());
        PhenixLifeCycleManager.instance().addLifeCycle(new com.taobao.monitor.impl.data.d.a());
        g.a("NETWORK_STAGE_DISPATCHER", new n());
        NetworkLifecycleManager.instance().setLifecycle(new com.taobao.monitor.impl.data.e.a());
        MtopLifecycleManager.instance().setLifecycle(new com.taobao.monitor.impl.data.e.a());
    }

    private static void initExecutor() {
        com.taobao.monitor.impl.data.c.a var0 = new com.taobao.monitor.impl.data.c.a();
        var0.execute();
    }

    private static void initApmImpl() {
        com.taobao.application.common.a.a();
    }
}