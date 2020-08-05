package com.alibaba.ha.adapter.plugin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.protocol.AliHaParam;
import com.alibaba.ha.protocol.AliHaPlugin;
import com.taobao.monitor.adapter.SimpleApmInitiator;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class APMPlugin implements AliHaPlugin {
    AtomicBoolean enabling = new AtomicBoolean(false);

    public String getName() {
        return Plugin.apm.name();
    }

    public void start(AliHaParam aliHaParam) {
        try {
            String str = aliHaParam.appId;
            String str2 = aliHaParam.appKey;
            String str3 = aliHaParam.appVersion;
            Application application = aliHaParam.application;
            Context context = aliHaParam.context;
            if (context == null || application == null || str == null || str2 == null || str3 == null) {
                Log.e(AliHaAdapter.TAG, "param is unlegal, applicationmonitor plugin start failure ");
                return;
            }
            Log.i(AliHaAdapter.TAG, "init apm, appId is " + str + " appKey is " + str2 + " appVersion is " + str3);
            if (this.enabling.compareAndSet(false, true)) {
                initApplicationMonitor(application, aliHaParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initApplicationMonitor(Application application, AliHaParam aliHaParam) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("deviceId", "UTDevice.getUtdid(application.getApplicationContext())");
        hashMap.put("onlineAppKey", aliHaParam.appKey);
        hashMap.put("appVersion", aliHaParam.appVersion);
        hashMap.put("process", application.getApplicationInfo().processName);
        hashMap.put("channel", aliHaParam.channel);
        new SimpleApmInitiator().init(application, hashMap);
    }
}
