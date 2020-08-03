//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.adapter;

import android.app.Application;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;

import com.ali.alihadeviceevaluator.AliHAHardware;
import com.ali.ha.datahub.BizSubscriber;
import com.ali.ha.datahub.DataHub;
import com.ali.ha.fulltrace.FulltraceGlobal;
import com.ali.ha.fulltrace.FulltraceLauncher;
import com.taobao.monitor.APMLauncher;
import com.taobao.monitor.ProcedureGlobal;
import com.taobao.monitor.ProcedureLauncher;
import com.taobao.monitor.adapter.constans.TBAPMConstants;
import com.taobao.monitor.adapter.db.TBRestSender;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.impl.processor.pageload.IProcedureManager;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.network.NetworkSenderProxy;
import com.taobao.monitor.procedure.Header;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.util.ThreadUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SimpleApmInitiator implements Serializable {
    private static final String TAG = "TBAPMAdapterLaunchers";
    private long apmStartTime = TimeUtils.currentTimeMillis();
    private long cpuStartTime = SystemClock.currentThreadTimeMillis();

    public SimpleApmInitiator() {
    }

    public void init(Application application, HashMap<String, Object> map) {
        if (!TBAPMConstants.init) {
            Logger.i("TBAPMAdapterLaunchers", "init start");
            TBAPMConstants.open = true;
            this.initAPMFunction(application, map);
            this.initDeviceEvaluation(application);
            Logger.i("TBAPMAdapterLaunchers", "init end");
            TBAPMConstants.init = true;
        }

        Logger.i("TBAPMAdapterLaunchers", "apmStartTime:", TimeUtils.currentTimeMillis() - this.apmStartTime);
    }

    public static void setDebug(boolean debug) {
        Logger.setDebug(debug);
    }

    private void initDeviceEvaluation(Application application) {
        AliHAHardware.getInstance().setUp(application, FulltraceGlobal.instance().dumpHandler());
        ThreadUtils.start(new Runnable() {
            public void run() {
                AliHAHardware.getInstance().getOutlineInfo();
            }
        });
    }

    private void initAPMFunction(Application application, HashMap<String, Object> map) {
        Global.instance().setHandler(ProcedureGlobal.instance().handler());
        this.initAPMLauncher(application, map);
        this.initTbRest(application);
        this.initFulltrace(application);
        this.initDataHub();
        this.initLauncherProcedure();
        this.initWebView();
    }

    private void initTbRest(Application application) {
        NetworkSenderProxy.instance().setNetworkSender(new TBRestSender());
    }

    private void initDataHub() {
        DataHub.getInstance().init(new BizSubscriber() {

            @Override
            public void pub(final String string, final Map<String, Object> map) {
                if ("splash".equals(string)) {
                    GlobalStats.hasSplash = true;
                }

                this.async(new Runnable() {
                    public void run() {
                        IProcedure iProcedure = DataHubProcedureGroupHelper.iProcedure();
                        if (iProcedure != null) {
                            iProcedure.addBiz(string, map);
                        }
                    }
                });
            }

            public void pubAB(final String var1, final Map<String, Object> var2) {
                this.async(new Runnable() {
                    public void run() {
                        IProcedure iProcedure = DataHubProcedureGroupHelper.iProcedure();
                        if (iProcedure != null) {
                            iProcedure.addBizAbTest(var1, var2);
                        }
                    }
                });
            }

            public void onStage(final String var1, final String var2, long var3) {
                final long var5 = TimeUtils.currentTimeMillis();
                this.async(new Runnable() {
                    public void run() {
                        IProcedure iProcedure = DataHubProcedureGroupHelper.iProcedure();
                        if (iProcedure != null) {
                            Map<String, Object> var2x = new HashMap<>();
                            var2x.put(var2, var5);
                            iProcedure.addBizStage(var1, var2x);
                        }

                    }
                });
            }

            public void setMainBiz(final String var1, final String var2) {
                this.async(new Runnable() {
                    public void run() {
                        IProcedure iProcedure = DataHubProcedureGroupHelper.iProcedure();
                        if (iProcedure != null) {
                            iProcedure.addProperty("bizID", var1);
                            if (!TextUtils.isEmpty(var2)) {
                                iProcedure.addProperty("bizCode", var2);
                            }
                        }

                    }
                });
            }

            public void onBizDataReadyStage() {
                IProcedure iProcedure = DataHubProcedureGroupHelper.iProcedure();
                if (iProcedure != null) {
                    iProcedure.stage("onBizDataReadyTime", TimeUtils.currentTimeMillis());
                }

            }

            private void async(Runnable var1) {
                Global.instance().handler().post(var1);
            }
        });
    }

    private void initWebView() {
//        WebViewProxy.INSTANCE.setReal(new AbsWebView() {
//            private String c;
//
//            public boolean isWebView(View var1) {
//                return var1 instanceof WebView;
//            }
//
//            public int getProgress(View var1) {
//                WebView var2 = (WebView) var1;
//                String var3 = var2.getUrl();
//                if (!TextUtils.equals(this.c, var3)) {
//                    this.c = var3;
//                    return 0;
//                } else {
//                    return ((WebView) var1).getProgress();
//                }
//            }
//        });
    }

    private void initLauncherProcedure() {
        ProcedureConfig startupProcedureConfig = (new Builder())
                .setIndependent(false)
                .setUpload(true)
                .setParentNeedStats(false)
                .setParent(null)
                .build();

        IProcedure startupProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/startup"), startupProcedureConfig);
        startupProcedure.begin();
        ProcedureGlobal.PROCEDURE_MANAGER.setLauncherProcedure(startupProcedure);

        ProcedureConfig apmSelfProcedureConfig = (new Builder())
                .setIndependent(false)
                .setUpload(false)
                .setParentNeedStats(false)
                .setParent(startupProcedure)
                .build();

        IProcedure apmSelfProcedure = ProcedureFactoryProxy.PROXY.createProcedure("/APMSelf", apmSelfProcedureConfig);
        apmSelfProcedure.begin();

        boolean isMainThread = Looper.getMainLooper().getThread() == Thread.currentThread();
        apmSelfProcedure.addProperty("isMainThread", isMainThread);
        apmSelfProcedure.addProperty("threadName", Thread.currentThread().getName());
        apmSelfProcedure.stage("taskStart", this.apmStartTime);
        apmSelfProcedure.stage("cpuStartTime", this.cpuStartTime);

        TBAPMAdapterSubTaskManager.asyncTask();
        apmSelfProcedure.stage("taskEnd", TimeUtils.currentTimeMillis());
        apmSelfProcedure.stage("cpuEndTime", SystemClock.currentThreadTimeMillis());
        apmSelfProcedure.end();
    }

    private void initAPMLauncher(Application application, HashMap<String, Object> map) {
        ProcedureLauncher.init(application, map);
        APMLauncher.init(application, map);
        ProcedureManagerSetter.instance().setProxy(new IProcedureManager() {
            public void setCurrentActivityProcedure(IProcedure var1) {
                ProcedureGlobal.PROCEDURE_MANAGER.setCurrentActivityProcedure(var1);
            }

            public void setCurrentFragmentProcedure(IProcedure var1) {
                ProcedureGlobal.PROCEDURE_MANAGER.setCurrentFragmentProcedure(var1);
            }

            public void setCurrentLauncherProcedure(IProcedure var1) {
                ProcedureGlobal.PROCEDURE_MANAGER.setLauncherProcedure(var1);
            }
        });
    }

    private void initFulltrace(final Application var1) {
        ThreadUtils.start(new Runnable() {
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
