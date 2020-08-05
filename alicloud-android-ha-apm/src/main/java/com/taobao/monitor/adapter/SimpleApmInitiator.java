package com.taobao.monitor.adapter;

import android.app.Application;
import android.content.Context;
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
import com.taobao.monitor.ProcedureGlobal;
import com.taobao.monitor.ProcedureLauncher;
import com.taobao.monitor.adapter.constants.TBAPMConstants;
import com.taobao.monitor.adapter.db.TBRestSender;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.data.AbsWebView;
import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.data.WebViewProxy;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.impl.processor.pageload.IProcedureManager;
import com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter;
import com.taobao.monitor.impl.util.TimeUtils;
import com.taobao.monitor.impl.util.TopicUtils;
import com.taobao.monitor.network.NetworkSenderProxy;
import com.taobao.monitor.procedure.Header;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.thread.ThreadUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SimpleApmInitiator implements Serializable {
    private static final String TAG = "TBAPMAdapterLaunchers";
    private long apmStartTime = TimeUtils.currentTimeMillis();
    private long cpuStartTime = SystemClock.currentThreadTimeMillis();

    public void init(Application application, HashMap<String, Object> hashMap) {
        if (!TBAPMConstants.init) {
            Logger.i(TAG, "init start");
            TBAPMConstants.open = true;
            initAPMFunction(application, hashMap);
            initDeviceEvaluation(application);
            Logger.i(TAG, "init end");
            TBAPMConstants.init = true;
        }
        Logger.i(TAG, "apmStartTime:", TimeUtils.currentTimeMillis() - this.apmStartTime);
    }

    public static void setDebug(boolean z) {
        Logger.setDebug(z);
    }

    private void initDeviceEvaluation(Application application) {
        AliHAHardware.getInstance().setUp(application, FulltraceGlobal.instance().dumpHandler());
        ThreadUtils.start(new Runnable() {
            public void run() {
                AliHAHardware.getInstance().getOutlineInfo();
            }
        });
    }

    private void initAPMFunction(Application application, HashMap<String, Object> hashMap) {
        Global.instance().setHandler(ProcedureGlobal.instance().handler());
        initAPMLauncher(application, hashMap);
        initTbRest(application);
        initFulltrace(application);
        initDataHub();
        initLauncherProcedure();
        initWebView();
    }

    private void initTbRest(Application application) {
        NetworkSenderProxy.instance().setNetworkSender(new TBRestSender());
    }

    private void initDataHub() {
        DataHub.getInstance().init(new BizSubscriber() {
            public void pub(final String str, final Map<String, Object> hashMap) {
                if ("splash".equals(str)) {
                    GlobalStats.hasSplash = true;
                }
                async(new Runnable() {
                    public void run() {
                        IProcedure a2 = DataHubProcedureGroupHelper.instance();
                        if (a2 != null) {
                            a2.addBiz(str, hashMap);
                        }
                    }
                });
            }

            public void pubAB(final String str, final Map<String, Object> hashMap) {
                async(new Runnable() {
                    public void run() {
                        IProcedure a2 = DataHubProcedureGroupHelper.instance();
                        if (a2 != null) {
                            a2.addBizAbTest(str, hashMap);
                        }
                    }
                });
            }

            public void onStage(String str, String str2, long j) {
                final long currentTimeMillis = TimeUtils.currentTimeMillis();
                final String str3 = str2;
                final String str4 = str;
                async(new Runnable() {
                    public void run() {
                        IProcedure a2 = DataHubProcedureGroupHelper.instance();
                        if (a2 != null) {
                            HashMap hashMap = new HashMap();
                            hashMap.put(str3, Long.valueOf(currentTimeMillis));
                            a2.addBizStage(str4, hashMap);
                        }
                    }
                });
            }

            public void setMainBiz(final String str, final String str2) {
                async(new Runnable() {
                    public void run() {
                        IProcedure a2 = DataHubProcedureGroupHelper.instance();
                        if (a2 != null) {
                            a2.addProperty("bizID", str);
                            if (!TextUtils.isEmpty(str2)) {
                                a2.addProperty("bizCode", str2);
                            }
                        }
                    }
                });
            }

            public void onBizDataReadyStage() {
                IProcedure a2 = DataHubProcedureGroupHelper.instance();
                if (a2 != null) {
                    a2.stage("onBizDataReadyTime", TimeUtils.currentTimeMillis());
                }
            }

            private void async(Runnable runnable) {
                Global.instance().handler().post(runnable);
            }
        });
    }

    private void initWebView() {
        WebViewProxy.INSTANCE.setReal(new AbsWebView() {
            private String c;

            public boolean isWebView(View view) {
                return view instanceof WebView;
            }

            public int getProgress(View view) {
                String url = ((WebView) view).getUrl();
                if (TextUtils.equals(this.c, url)) {
                    return ((WebView) view).getProgress();
                }
                this.c = url;
                return 0;
            }
        });
    }

    private void initLauncherProcedure() {
        boolean isMainThread = true;
        IProcedure startupProcedure = ProcedureFactoryProxy.PROXY.createProcedure(TopicUtils.getFullTopic("/startup"), new Builder()
                .setIndependent(false)
                .setUpload(true)
                .setParentNeedStats(false)
                .setParent(null)
                .build());

        startupProcedure.begin();
        ProcedureGlobal.PROCEDURE_MANAGER.setLauncherProcedure(startupProcedure);

        IProcedure aPMSelfProcedure = ProcedureFactoryProxy.PROXY.createProcedure("/APMSelf", new Builder()
                .setIndependent(false)
                .setUpload(false)
                .setParentNeedStats(false)
                .setParent(startupProcedure)
                .build());

        aPMSelfProcedure.begin();
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            isMainThread = false;
        }
        aPMSelfProcedure.addProperty("isMainThread", isMainThread);
        aPMSelfProcedure.addProperty("threadName", Thread.currentThread().getName());
        aPMSelfProcedure.stage("taskStart", this.apmStartTime);
        aPMSelfProcedure.stage("cpuStartTime", this.cpuStartTime);
        TBAPMAdapterSubTaskManager.asyncRunnable();
        aPMSelfProcedure.stage("taskEnd", TimeUtils.currentTimeMillis());
        aPMSelfProcedure.stage("cpuEndTime", SystemClock.currentThreadTimeMillis());
        aPMSelfProcedure.end();
    }

    private void initAPMLauncher(Application application, HashMap<String, Object> hashMap) {
        ProcedureLauncher.init((Context) application, (Map<String, Object>) hashMap);
        APMLauncher.init(application, hashMap);
        ProcedureManagerSetter.instance().setProxy(new IProcedureManager() {
            public void setCurrentActivityProcedure(IProcedure iProcedure) {
                ProcedureGlobal.PROCEDURE_MANAGER.setCurrentActivityProcedure(iProcedure);
            }

            public void setCurrentFragmentProcedure(IProcedure iProcedure) {
                ProcedureGlobal.PROCEDURE_MANAGER.setCurrentFragmentProcedure(iProcedure);
            }

            public void setCurrentLauncherProcedure(IProcedure iProcedure) {
                ProcedureGlobal.PROCEDURE_MANAGER.setLauncherProcedure(iProcedure);
            }
        });
    }

    private void initFulltrace(final Application application) {
        ThreadUtils.start(new Runnable() {
            public void run() {
                HashMap hashMap = new HashMap();
                hashMap.put("appVersion", Header.appVersion);
                hashMap.put("session", Header.session);
                hashMap.put("apmVersion", Header.apmVersion);
                hashMap.put("ttid", Header.ttid);
                hashMap.put("userNick", Header.userNick);
                hashMap.put("userId", Header.userId);
                hashMap.put("osVersion", Header.osVersion);
                hashMap.put("os", Header.os);
                hashMap.put("appChannelVersion", Header.channel);
                hashMap.put("deviceModel", Header.deviceModel);
                hashMap.put("brand", Header.brand);
                hashMap.put("utdid", Header.utdid);
                hashMap.put("appKey", Header.appKey);
                hashMap.put("appId", Header.appId);
                hashMap.put("appBuild", Header.appBuild);
                hashMap.put("processName", Header.processName);
                FulltraceLauncher.init(application, hashMap);
            }
        });
    }
}
