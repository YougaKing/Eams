package com.taobao.monitor;

import android.app.Application;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import android.os.Process;
import android.os.SystemClock;
import com.ali.alihadeviceevaluator.AliHAHardware;
import com.taobao.application.common.data.DeviceHelper;
import com.taobao.application.common.data.c;
import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.processor.a.a;
import com.taobao.monitor.impl.processor.launcher.b;
import com.taobao.monitor.impl.trace.e;
import com.taobao.monitor.impl.trace.f;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.trace.i;
import com.taobao.monitor.impl.trace.l;
import com.taobao.monitor.impl.trace.m;
import com.taobao.monitor.impl.trace.n;
import com.taobao.monitor.impl.trace.o;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.d;
import com.taobao.monitor.performance.APMAdapterFactoryProxy;
import com.taobao.network.lifecycle.MtopLifecycleManager;
import com.taobao.network.lifecycle.NetworkLifecycleManager;
import com.taobao.phenix.lifecycle.PhenixLifeCycleManager;
import java.util.Map;

public class APMLauncher {
    private static final String TAG = "APMLauncher";
    private static boolean init = false;
    /* access modifiers changed from: private */
    public static final c launchHelper = new c();

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

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0100  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void initParams(android.app.Application r6, java.util.Map<java.lang.String, java.lang.Object> r7) {
        /*
            r3 = 0
            r2 = 1
            long r0 = com.taobao.monitor.impl.util.TimeUtils.currentTimeMillis()
            com.taobao.monitor.impl.data.GlobalStats.launchStartTime = r0
            com.taobao.application.common.data.c r0 = launchHelper
            java.lang.String r1 = "COLD"
            r0.a(r1)
            com.taobao.application.common.data.c r0 = launchHelper
            long r4 = android.os.SystemClock.uptimeMillis()
            r0.e(r4)
            com.taobao.application.common.data.c r0 = launchHelper
            long r4 = java.lang.System.currentTimeMillis()
            r0.d(r4)
            java.lang.String r1 = "ALI_APM/device-id/monitor/procedure"
            if (r7 == 0) goto L_0x0102
            java.lang.String r0 = "appVersion"
            java.lang.Object r0 = r7.get(r0)
            java.lang.String r4 = "unknown"
            java.lang.String r0 = com.taobao.monitor.impl.util.e.a(r0, r4)
            com.taobao.monitor.impl.data.GlobalStats.appVersion = r0
            java.lang.String r0 = "deviceId"
            java.lang.Object r0 = r7.get(r0)
            boolean r4 = r0 instanceof java.lang.String
            if (r4 == 0) goto L_0x0102
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "UTF-8"
            java.lang.String r0 = java.net.URLEncoder.encode(r0, r1)     // Catch:{ Exception -> 0x00fd }
        L_0x0045:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "ALI_APM/"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "/monitor/procedure"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
        L_0x005e:
            com.taobao.monitor.impl.common.Global r1 = com.taobao.monitor.impl.common.Global.instance()
            com.taobao.monitor.impl.common.Global r1 = r1.setContext(r6)
            r1.setNamespace(r0)
            com.taobao.monitor.impl.common.Global r0 = com.taobao.monitor.impl.common.Global.instance()
            android.content.Context r0 = r0.context()
            java.lang.String r1 = "apm"
            android.content.SharedPreferences r1 = r0.getSharedPreferences(r1, r3)
            java.lang.String r0 = "appVersion"
            java.lang.String r4 = ""
            java.lang.String r0 = r1.getString(r0, r4)
            android.content.SharedPreferences$Editor r4 = r1.edit()
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 == 0) goto L_0x00dd
            com.taobao.monitor.impl.data.GlobalStats.isFirstInstall = r2
            com.taobao.monitor.impl.data.GlobalStats.isFirstLaunch = r2
            java.lang.String r0 = "NEW"
            com.taobao.monitor.impl.data.GlobalStats.installType = r0
            java.lang.String r0 = "appVersion"
            java.lang.String r3 = com.taobao.monitor.impl.data.GlobalStats.appVersion
            r4.putString(r0, r3)
            r3 = r2
        L_0x0099:
            java.lang.String r0 = "LAST_TOP_ACTIVITY"
            java.lang.String r5 = ""
            java.lang.String r0 = r1.getString(r0, r5)
            com.taobao.monitor.impl.data.GlobalStats.lastTopActivity = r0
            java.lang.String r0 = com.taobao.monitor.impl.data.GlobalStats.lastTopActivity
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0100
            java.lang.String r0 = "LAST_TOP_ACTIVITY"
            java.lang.String r1 = ""
            r4.putString(r0, r1)
        L_0x00b2:
            if (r2 == 0) goto L_0x00b7
            r4.apply()
        L_0x00b7:
            long r0 = com.taobao.application.common.data.c.a.a()
            com.taobao.monitor.impl.data.GlobalStats.lastProcessStartTime = r0
            com.taobao.application.common.data.c r0 = launchHelper
            boolean r1 = com.taobao.monitor.impl.data.GlobalStats.isFirstLaunch
            r0.b(r1)
            com.taobao.application.common.data.c r0 = launchHelper
            boolean r1 = com.taobao.monitor.impl.data.GlobalStats.isFirstInstall
            r0.a(r1)
            com.taobao.application.common.data.c r0 = launchHelper
            long r2 = com.taobao.monitor.impl.data.GlobalStats.lastProcessStartTime
            r0.a(r2)
            com.taobao.application.common.data.DeviceHelper r0 = new com.taobao.application.common.data.DeviceHelper
            r0.<init>()
            java.lang.String r1 = android.os.Build.MODEL
            r0.setMobileModel(r1)
            return
        L_0x00dd:
            com.taobao.monitor.impl.data.GlobalStats.isFirstInstall = r3
            java.lang.String r5 = com.taobao.monitor.impl.data.GlobalStats.appVersion
            boolean r0 = r0.equals(r5)
            if (r0 != 0) goto L_0x00fb
            r0 = r2
        L_0x00e8:
            com.taobao.monitor.impl.data.GlobalStats.isFirstLaunch = r0
            java.lang.String r0 = "UPDATE"
            com.taobao.monitor.impl.data.GlobalStats.installType = r0
            boolean r0 = com.taobao.monitor.impl.data.GlobalStats.isFirstLaunch
            if (r0 == 0) goto L_0x0099
            java.lang.String r0 = "appVersion"
            java.lang.String r3 = com.taobao.monitor.impl.data.GlobalStats.appVersion
            r4.putString(r0, r3)
            r3 = r2
            goto L_0x0099
        L_0x00fb:
            r0 = r3
            goto L_0x00e8
        L_0x00fd:
            r1 = move-exception
            goto L_0x0045
        L_0x0100:
            r2 = r3
            goto L_0x00b2
        L_0x0102:
            r0 = r1
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.monitor.APMLauncher.initParams(android.app.Application, java.util.Map):void");
    }

    private static void initHotCold() {
        Global.instance().handler().postDelayed(new Runnable() {
            public void run() {
                Looper.getMainLooper();
                Looper.myQueue().addIdleHandler(new IdleHandler() {
                    public boolean queueIdle() {
                        if (GlobalStats.createdPageCount == 0) {
                            b.c = "HOT";
                            b.isBackgroundLaunch = true;
                            APMLauncher.launchHelper.a("HOT");
                        }
                        return false;
                    }
                });
            }
        }, 3000);
    }

    private static void initLifecycle(Application application) {
        application.registerActivityLifecycleCallbacks(new com.taobao.monitor.impl.data.a.b());
    }

    /* access modifiers changed from: private */
    public static void initWeex() {
        if (DynamicConstants.needWeex) {
            APMAdapterFactoryProxy.instance().setFactory(new a());
        }
    }

    private static void initHookActivityManager() {
        if (VERSION.SDK_INT <= 28) {
            runInMain(new Runnable() {
                public void run() {
                    com.taobao.monitor.impl.common.b.start();
                }
            });
        }
    }

    private static void runInMain(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    /* access modifiers changed from: private */
    public static void initProcessStartTime() {
        if (VERSION.SDK_INT >= 24) {
            GlobalStats.processStartTime = (TimeUtils.currentTimeMillis() + Process.getStartUptimeMillis()) - SystemClock.uptimeMillis();
            launchHelper.b(System.currentTimeMillis() - (SystemClock.uptimeMillis() - GlobalStats.processStartTime));
        } else {
            long b = d.b();
            launchHelper.b(b);
            if (b != -1) {
                GlobalStats.processStartTime = TimeUtils.currentTimeMillis() - (System.currentTimeMillis() - b);
            } else {
                GlobalStats.processStartTime = TimeUtils.currentTimeMillis() - Process.getElapsedCpuTime();
            }
        }
        launchHelper.c(GlobalStats.processStartTime);
    }

    /* access modifiers changed from: private */
    public static void initOppoCPUResource() {
        GlobalStats.oppoCPUResource = System.getProperty("oppoCPUResource", "false");
    }

    private static void firstAsyncMessage() {
        Global.instance().handler().post(new Runnable() {
            public void run() {
                APMLauncher.initOppoCPUResource();
                APMLauncher.initExecutor();
                APMLauncher.initWeex();
                APMLauncher.initProcessStartTime();
                DeviceHelper deviceHelper = new DeviceHelper();
                deviceHelper.setDeviceLevel(AliHAHardware.getInstance().getOutlineInfo().deviceLevel);
                deviceHelper.setCpuScore(AliHAHardware.getInstance().getCpuInfo().deviceLevel);
                deviceHelper.setMemScore(AliHAHardware.getInstance().getMemoryInfo().deviceLevel);
            }
        });
    }

    private static void initDispatcher() {
        g.a("APPLICATION_LOW_MEMORY_DISPATCHER", new f());
        g.a("APPLICATION_GC_DISPATCHER", new e());
        g.a("APPLICATION_BACKGROUND_CHANGED_DISPATCHER", new com.taobao.monitor.impl.trace.d());
        g.a("ACTIVITY_FPS_DISPATCHER", new i());
        com.taobao.monitor.impl.trace.c cVar = new com.taobao.monitor.impl.trace.c();
        cVar.addListener(new com.taobao.monitor.impl.processor.pageload.e());
        cVar.addListener(new com.taobao.monitor.impl.processor.launcher.a());
        g.a("ACTIVITY_LIFECYCLE_DISPATCHER", cVar);
        g.a("ACTIVITY_EVENT_DISPATCHER", new com.taobao.monitor.impl.trace.b());
        g.a("ACTIVITY_USABLE_VISIBLE_DISPATCHER", new o());
        l lVar = new l();
        lVar.addListener(new com.taobao.monitor.impl.processor.fragmentload.a());
        g.a("FRAGMENT_LIFECYCLE_DISPATCHER", lVar);
        g.a("FRAGMENT_USABLE_VISIBLE_DISPATCHER", new o());
        g.a("IMAGE_STAGE_DISPATCHER", new m());
        PhenixLifeCycleManager.instance().addLifeCycle(new com.taobao.monitor.impl.data.d.a());
        g.a("NETWORK_STAGE_DISPATCHER", new n());
        NetworkLifecycleManager.instance().setLifecycle(new com.taobao.monitor.impl.data.e.a());
        MtopLifecycleManager.instance().setLifecycle(new com.taobao.monitor.impl.data.e.a());
    }

    /* access modifiers changed from: private */
    public static void initExecutor() {
        new com.taobao.monitor.impl.data.c.a().execute();
    }

    private static void initApmImpl() {
        com.taobao.application.common.a.a();
    }
}
