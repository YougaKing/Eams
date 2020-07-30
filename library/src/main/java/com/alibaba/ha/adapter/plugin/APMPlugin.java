//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.plugin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.protocol.AliHaParam;
import com.alibaba.ha.protocol.AliHaPlugin;
import com.taobao.monitor.adapter.SimpleApmInitiator;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class APMPlugin implements AliHaPlugin {

    private AtomicBoolean enabling = new AtomicBoolean(false);

    public APMPlugin() {
    }

    @Override
    public String getName() {
        return Plugin.apm.name();
    }

    @Override
    public void start(AliHaParam aliHaParam) {
        try {
            String appId = aliHaParam.appId;
            String appKey = aliHaParam.appKey;
            String appVersion = aliHaParam.appVersion;
            Application application = aliHaParam.application;
            Context context = aliHaParam.context;
            if (context == null || application == null || appId == null || appKey == null || appVersion == null) {
                Log.e("AliHaAdapter", "param is unlegal, applicationmonitor plugin start failure ");
                return;
            }

            Log.i("AliHaAdapter", "init apm, appId is " + appId + " appKey is " + appKey + " appVersion is " + appVersion);
            if (this.enabling.compareAndSet(false, true)) {
                this.initApplicationMonitor(application, aliHaParam);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    private void initApplicationMonitor(Application var1, AliHaParam var2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("deviceId", "UTDevice.getUtdid(var1.getApplicationContext())");
        map.put("onlineAppKey", var2.appKey);
        map.put("appVersion", var2.appVersion);
        map.put("process", var1.getApplicationInfo().processName);
        map.put("channel", var2.channel);
        (new SimpleApmInitiator()).init(var1, map);
    }
}
