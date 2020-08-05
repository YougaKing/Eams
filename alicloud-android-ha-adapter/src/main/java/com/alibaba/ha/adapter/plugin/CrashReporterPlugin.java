package com.alibaba.ha.adapter.plugin;

import android.content.Context;
import android.util.Log;

import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.protocol.AliHaParam;
import com.alibaba.ha.protocol.AliHaPlugin;

import java.util.concurrent.atomic.AtomicBoolean;

public class CrashReporterPlugin implements AliHaPlugin {
    AtomicBoolean enabling = new AtomicBoolean(false);

    public String getName() {
        return Plugin.crashreporter.name();
    }

    public void start(AliHaParam aliHaParam) {
        String str = aliHaParam.appId;
        String str2 = aliHaParam.appKey;
        String str3 = aliHaParam.appSecret;
        String str4 = aliHaParam.appVersion;
        Context context = aliHaParam.context;
        if (context == null || str == null || str2 == null || str4 == null) {
            Log.e(AliHaAdapter.TAG, "param is unlegal, crashreporter plugin start failure ");
            return;
        }
        String str5 = aliHaParam.channel;
        String str6 = aliHaParam.userNick;
        Log.i(AliHaAdapter.TAG, "init crashreporter, appId is " + str + " appKey is " + str2 + " appVersion is " + str4 + " channel is " + str5 + " userNick is " + str6);
//        if (this.enabling.compareAndSet(false, true)) {
//            ReporterConfigure reporterConfigure = new ReporterConfigure();
//            reporterConfigure.setEnableDumpSysLog(true);
//            reporterConfigure.setEnableDumpRadioLog(true);
//            reporterConfigure.setEnableDumpEventsLog(true);
//            reporterConfigure.setEnableCatchANRException(true);
//            reporterConfigure.enableDeduplication = false;
//            try {
//                MotuCrashReporter.getInstance().enable(context, str, str2, str3, str4, str5, str6, reporterConfigure);
//            } catch (Exception e) {
//                Log.e(AliHaAdapter.TAG, "crashreporter plugin start failure ", e);
//            }
//            CrashService.addJavaCrashListener(new CrashActivityCallBack());
//        }
    }
}
