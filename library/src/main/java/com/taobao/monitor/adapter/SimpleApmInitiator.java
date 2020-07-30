//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.adapter;

import android.app.Application;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import com.ali.alihadeviceevaluator.AliHAHardware;
import com.ali.ha.datahub.BizSubscriber;
import com.ali.ha.datahub.DataHub;
import com.ali.ha.fulltrace.FulltraceGlobal;
import com.ali.ha.fulltrace.FulltraceLauncher;
import com.taobao.monitor.APMLauncher;
import com.taobao.monitor.adapter.a.a;
import com.taobao.monitor.adapter.b.c;
import com.taobao.monitor.d.b;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.AbsWebView;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.WebViewProxy;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.impl.processor.pageload.IProcedureManager;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.procedure.Header;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import java.io.Serializable;
import java.util.HashMap;

public class SimpleApmInitiator implements Serializable {
    private static final String TAG = "TBAPMAdapterLaunchers";
    private long apmStartTime = TimeUtils.currentTimeMillis();
    private long cpuStartTime = SystemClock.currentThreadTimeMillis();

    public SimpleApmInitiator() {
    }

    public void init(Application var1, HashMap<String, Object> var2) {
        if (!a.a) {
            Logger.i("TBAPMAdapterLaunchers", new Object[]{"init start"});
            a.open = true;
            this.initAPMFunction(var1, var2);
            this.initDeviceEvaluation(var1);
            Logger.i("TBAPMAdapterLaunchers", new Object[]{"init end"});
            a.a = true;
        }

        Logger.i("TBAPMAdapterLaunchers", new Object[]{"apmStartTime:", TimeUtils.currentTimeMillis() - this.apmStartTime});
    }

    public static void setDebug(boolean var0) {
        Logger.setDebug(var0);
    }

    private void initDeviceEvaluation(Application var1) {
        AliHAHardware.getInstance().setUp(var1, FulltraceGlobal.instance().dumpHandler());
        com.taobao.monitor.a.a.start(new Runnable() {
            public void run() {
                AliHAHardware.getInstance().getOutlineInfo();
            }
        });
    }

    private void initAPMFunction(Application var1, HashMap<String, Object> var2) {
        Global.instance().setHandler(com.taobao.monitor.a.a().handler());
        this.initAPMLauncher(var1, var2);
        this.initTbRest(var1);
        this.initFulltrace(var1);
        this.initDataHub();
        this.initLauncherProcedure();
        this.initWebView();
    }

    private void initTbRest(Application var1) {
        b.a().a(new c());
    }

    private void initDataHub() {
        DataHub.getInstance().init(new BizSubscriber() {
            public void pub(final String var1, final HashMap<String, String> var2) {
                if ("splash".equals(var1)) {
                    GlobalStats.hasSplash = true;
                }

                this.async(new Runnable() {
                    public void run() {
                        IProcedure var1x = com.taobao.monitor.adapter.a.a();
                        if (var1x != null) {
                            var1x.addBiz(var1, var2);
                        }

                    }
                });
            }

            public void pubAB(final String var1, final HashMap<String, String> var2) {
                this.async(new Runnable() {
                    public void run() {
                        IProcedure var1x = com.taobao.monitor.adapter.a.a();
                        if (var1x != null) {
                            var1x.addBizAbTest(var1, var2);
                        }

                    }
                });
            }

            public void onStage(final String var1, final String var2, long var3) {
                final long var5 = TimeUtils.currentTimeMillis();
                this.async(new Runnable() {
                    public void run() {
                        IProcedure var1x = com.taobao.monitor.adapter.a.a();
                        if (var1x != null) {
                            HashMap var2x = new HashMap();
                            var2x.put(var2, var5);
                            var1x.addBizStage(var1, var2x);
                        }

                    }
                });
            }

            public void setMainBiz(final String var1, final String var2) {
                this.async(new Runnable() {
                    public void run() {
                        IProcedure var1x = com.taobao.monitor.adapter.a.a();
                        if (var1x != null) {
                            var1x.addProperty("bizID", var1);
                            if (!TextUtils.isEmpty(var2)) {
                                var1x.addProperty("bizCode", var2);
                            }
                        }

                    }
                });
            }

            public void onBizDataReadyStage() {
                IProcedure var1 = com.taobao.monitor.adapter.a.a();
                if (var1 != null) {
                    var1.stage("onBizDataReadyTime", TimeUtils.currentTimeMillis());
                }

            }

            private void async(Runnable var1) {
                Global.instance().handler().post(var1);
            }
        });
    }

    private void initWebView() {
        WebViewProxy.INSTANCE.setReal(new AbsWebView() {
            private String c;

            public boolean isWebView(View var1) {
                return var1 instanceof WebView;
            }

            public int getProgress(View var1) {
                WebView var2 = (WebView)var1;
                String var3 = var2.getUrl();
                if (!TextUtils.equals(this.c, var3)) {
                    this.c = var3;
                    return 0;
                } else {
                    return ((WebView)var1).getProgress();
                }
            }
        });
    }

    private void initLauncherProcedure() {
        ProcedureConfig var1 = (new Builder()).setIndependent(false).setUpload(true).setParentNeedStats(false).setParent((IProcedure)null).build();
        IProcedure var2 = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/startup"), var1);
        var2.begin();
        com.taobao.monitor.a.a.setLauncherProcedure(var2);
        ProcedureConfig var3 = (new Builder()).setIndependent(false).setUpload(false).setParentNeedStats(false).setParent(var2).build();
        IProcedure var4 = ProcedureFactoryProxy.PROXY.createProcedure("/APMSelf", var3);
        var4.begin();
        boolean var5 = Looper.getMainLooper().getThread() == Thread.currentThread();
        var4.addProperty("isMainThread", var5);
        var4.addProperty("threadName", Thread.currentThread().getName());
        var4.stage("taskStart", this.apmStartTime);
        var4.stage("cpuStartTime", this.cpuStartTime);
        com.taobao.monitor.adapter.b.a();
        var4.stage("taskEnd", TimeUtils.currentTimeMillis());
        var4.stage("cpuEndTime", SystemClock.currentThreadTimeMillis());
        var4.end();
    }

    private void initAPMLauncher(Application var1, HashMap<String, Object> var2) {
        com.taobao.monitor.b.a(var1, var2);
        APMLauncher.init(var1, var2);
        ProcedureManagerSetter.instance().setProxy(new IProcedureManager() {
            public void setCurrentActivityProcedure(IProcedure var1) {
                com.taobao.monitor.a.a.setCurrentActivityProcedure(var1);
            }

            public void setCurrentFragmentProcedure(IProcedure var1) {
                com.taobao.monitor.a.a.setCurrentFragmentProcedure(var1);
            }

            public void setCurrentLauncherProcedure(IProcedure var1) {
                com.taobao.monitor.a.a.setLauncherProcedure(var1);
            }
        });
    }

    private void initFulltrace(final Application var1) {
        com.taobao.monitor.a.a.start(new Runnable() {
            public void run() {
                HashMap var1x = new HashMap();
                var1x.put("appVersion", Header.appVersion);
                var1x.put("session", Header.session);
                var1x.put("apmVersion", Header.apmVersion);
                var1x.put("ttid", Header.ttid);
                var1x.put("userNick", Header.userNick);
                var1x.put("userId", Header.userId);
                var1x.put("osVersion", Header.osVersion);
                var1x.put("os", Header.os);
                var1x.put("appChannelVersion", Header.channel);
                var1x.put("deviceModel", Header.deviceModel);
                var1x.put("brand", Header.brand);
                var1x.put("utdid", Header.utdid);
                var1x.put("appKey", Header.appKey);
                var1x.put("appId", Header.appId);
                var1x.put("appBuild", Header.appBuild);
                var1x.put("processName", Header.processName);
                FulltraceLauncher.init(var1, var1x);
            }
        });
    }
}
