package com.alibaba.ha.adapter;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.ha.adapter.plugin.CrashReporterPlugin;
import com.alibaba.ha.adapter.plugin.factory.PluginFactory;
import com.alibaba.ha.adapter.service.activity.AdapterActivityLifeCycle;
import com.alibaba.ha.adapter.service.apm.APMService;
import com.alibaba.ha.adapter.service.appstatus.AppStatusRegHelper;
import com.alibaba.ha.adapter.service.appstatus.Event1010Handler;
import com.alibaba.ha.adapter.service.crash.CrashService;
import com.alibaba.ha.adapter.service.telescope.TelescopeService;
import com.alibaba.ha.adapter.service.tlog.TLogService;
import com.alibaba.ha.core.AliHaCore;
import com.alibaba.ha.protocol.AliHaParam;
import com.alibaba.ha.protocol.AliHaPlugin;
import com.alibaba.motu.tbrest.SendService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AliHaAdapter {
    public static final String TAG = "AliHaAdapter";
    private static final String mHAOSSBucketName = "emasha-online";
    private static final String mHATLogHost = "tlog-emas.aliyuncs.com";
    private static final String mUniversalHost = "adash-emas.cn-hangzhou.aliyuncs.com";
    public Context context;
    private List<Plugin> plugins;

    private static class InstanceCreater {
        /* access modifiers changed from: private */
        public static AliHaAdapter instance = new AliHaAdapter();

        private InstanceCreater() {
        }
    }

    private AliHaAdapter() {
        this.plugins = new ArrayList();
        this.context = null;
    }

    public static synchronized AliHaAdapter getInstance() {
        AliHaAdapter access$100;
        synchronized (AliHaAdapter.class) {
            access$100 = InstanceCreater.instance;
        }
        return access$100;
    }

    public void addPlugin(Plugin plugin) {
        if (plugin != null && !this.plugins.contains(plugin)) {
            Log.w(TAG, "plugin add to list success, plugin name is " + plugin.name());
            this.plugins.add(plugin);
            if (Plugin.tlog.equals(plugin) && !this.plugins.contains(Plugin.telescope)) {
                this.plugins.add(Plugin.telescope);
            }
            if (Plugin.crashreporter.equals(plugin)) {
                if (!this.plugins.contains(Plugin.telescope)) {
                    this.plugins.add(Plugin.telescope);
                }
                if (!this.plugins.contains(Plugin.watch)) {
                    this.plugins.add(Plugin.watch);
                }
                if (!this.plugins.contains(Plugin.bizErrorReporter)) {
                    this.plugins.add(Plugin.bizErrorReporter);
                }
            }
        }
    }

    public void removePlugin(Plugin plugin) {
        if (plugin != null) {
            Log.w(TAG, "plugin remove from list success, plugin name is " + plugin.name());
            this.plugins.remove(plugin);
        }
    }

    public Boolean startWithPlugins(AliHaConfig aliHaConfig, List<Plugin> list) {
        if (!isLegal(aliHaConfig).booleanValue()) {
            return Boolean.valueOf(false);
        }
        if (list != null && list.size() > 0) {
            this.plugins.addAll(list);
            if (this.plugins.contains(Plugin.tlog) || this.plugins.contains(Plugin.crashreporter)) {
                addPlugin(Plugin.telescope);
            }
            if (this.plugins.contains(Plugin.crashreporter)) {
                addPlugin(Plugin.watch);
                addPlugin(Plugin.bizErrorReporter);
            }
        }
        return start(aliHaConfig);
    }

    private Boolean startWithPlugin(AliHaConfig aliHaConfig, Plugin plugin) {
        if (!isLegal(aliHaConfig).booleanValue()) {
            return Boolean.valueOf(false);
        }
        AliHaParam buildParam = buildParam(aliHaConfig);
        AliHaPlugin createPlugin = PluginFactory.createPlugin(plugin);
        if (createPlugin == null) {
            return Boolean.valueOf(false);
        }
        AliHaCore.getInstance().startWithPlugin(buildParam, createPlugin);
        return Boolean.valueOf(true);
    }

    public boolean startCrashReport(AliHaConfig aliHaConfig) {
        addPlugin(Plugin.crashreporter);
        addPlugin(Plugin.telescope);
        addPlugin(Plugin.watch);
        addPlugin(Plugin.bizErrorReporter);
        return start(aliHaConfig).booleanValue();
    }

    public Boolean start(AliHaConfig aliHaConfig) {
        if (!isLegal(aliHaConfig).booleanValue()) {
            return Boolean.valueOf(false);
        }
        openPublishEmasHa();
        AliHaParam buildParam = buildParam(aliHaConfig);
        try {
            if (this.plugins.contains(Plugin.crashreporter)) {
                AliHaCore.getInstance().startWithPlugin(buildParam, new CrashReporterPlugin());
            } else {
                SendService.getInstance().init(buildParam.context, buildParam.appId, buildParam.appKey, buildParam.appVersion, buildParam.channel, buildParam.userNick);
                SendService.getInstance().appSecret = buildParam.appSecret;
                Log.i(TAG, "init send service success, appId is " + buildParam.appId + " appKey is " + buildParam.appKey + " appVersion is " + buildParam.appVersion + " channel is " + buildParam.channel + " userNick is " + buildParam.userNick);
            }
            if (this.plugins.contains(Plugin.ut)) {
                AliHaCore.getInstance().registPlugin(PluginFactory.createPlugin(Plugin.ut));
            } else {
                printPluginAdded(Plugin.ut.name());
            }
            if (this.plugins.contains(Plugin.bizErrorReporter)) {
                AliHaCore.getInstance().registPlugin(PluginFactory.createPlugin(Plugin.bizErrorReporter));
            } else {
                printPluginAdded(Plugin.bizErrorReporter.name());
            }
            if (this.plugins.contains(Plugin.onlineMonitor)) {
                AliHaCore.getInstance().registPlugin(PluginFactory.createPlugin(Plugin.onlineMonitor));
            } else {
                printPluginAdded(Plugin.onlineMonitor.name());
            }
            if (this.plugins.contains(Plugin.telescope)) {
                AliHaCore.getInstance().registPlugin(PluginFactory.createPlugin(Plugin.telescope));
            } else {
                printPluginAdded(Plugin.telescope.name());
            }
            if (this.plugins.contains(Plugin.tlog)) {
                AliHaCore.getInstance().registPlugin(PluginFactory.createPlugin(Plugin.tlog));
                TLogService.changeRasPublishKey(aliHaConfig.rsaPublicKey);
            } else {
                printPluginAdded(Plugin.tlog.name());
            }
            if (this.plugins.contains(Plugin.watch)) {
                AliHaCore.getInstance().registPlugin(PluginFactory.createPlugin(Plugin.watch));
            } else {
                printPluginAdded(Plugin.watch.name());
            }
            if (this.plugins.contains(Plugin.apm)) {
                AliHaCore.getInstance().registPlugin(PluginFactory.createPlugin(Plugin.apm));
            } else {
                printPluginAdded(Plugin.apm.name());
            }
            AliHaCore.getInstance().start(buildParam);
            if (VERSION.SDK_INT >= 14) {
                buildParam.application.registerActivityLifecycleCallbacks(new AdapterActivityLifeCycle());
            } else {
                Log.w(TAG, String.format("build version %s not suppert, registerActivityLifecycleCallbacks failed", new Object[]{Integer.valueOf(VERSION.SDK_INT)}));
            }
            initAppStatus(aliHaConfig);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            Log.e(TAG, "start plugin error ", e);
            return Boolean.valueOf(false);
        }
    }

    private String getBizId() {
        String str = "";
        if (this.plugins.contains(Plugin.crashreporter)) {
            str = "ha-crash";
        }
        if (this.plugins.contains(Plugin.apm)) {
            if (str.length() != 0) {
                str = str + "_";
            }
            str = str + "ha-apm";
        }
        if (!this.plugins.contains(Plugin.tlog)) {
            return str;
        }
        if (str.length() != 0) {
            str = str + "_";
        }
        return str + "ha-tlog";
    }

    private void initAppStatus(AliHaConfig aliHaConfig) {
        String bizId = getBizId();
        if (bizId != null && aliHaConfig != null) {
            AppStatusRegHelper.registeActivityLifecycleCallbacks(aliHaConfig.application);
            HashMap hashMap = new HashMap();
            hashMap.put("_aliyun_biz_id", bizId);
            Event1010Handler instance = Event1010Handler.getInstance();
            instance.init(aliHaConfig.application, hashMap);
            AppStatusRegHelper.registerAppStatusCallbacks(instance);
        }
    }

    private AliHaParam buildParam(AliHaConfig aliHaConfig) {
        AliHaParam aliHaParam = new AliHaParam();
        aliHaParam.application = aliHaConfig.application;
        aliHaParam.context = aliHaConfig.context;
        aliHaParam.appKey = aliHaConfig.appKey;
        aliHaParam.appSecret = aliHaConfig.appSecret;
        if (aliHaConfig.isAliyunos.booleanValue()) {
            aliHaParam.appId = aliHaParam.appKey + "@aliyunos";
        } else {
            aliHaParam.appId = aliHaParam.appKey + "@android";
        }
        aliHaParam.appVersion = aliHaConfig.appVersion;
        aliHaParam.channel = aliHaConfig.channel;
        aliHaParam.userNick = aliHaConfig.userNick;
        return aliHaParam;
    }

    private Boolean isLegal(AliHaConfig aliHaConfig) {
        if (aliHaConfig == null) {
            Log.e(TAG, "config is null ");
            return Boolean.valueOf(false);
        } else if (aliHaConfig.application == null) {
            Log.e(TAG, "application is null ");
            return Boolean.valueOf(false);
        } else if (aliHaConfig.context == null) {
            Log.e(TAG, "context is null ");
            return Boolean.valueOf(false);
        } else if (aliHaConfig.appKey == null || aliHaConfig.appSecret == null || aliHaConfig.appVersion == null) {
            Log.e(TAG, "config is unlegal, ha plugin start failure  appKey is " + aliHaConfig.appKey + " appVersion is " + aliHaConfig.appVersion + " appSecret is " + aliHaConfig.appSecret);
            return Boolean.valueOf(false);
        } else if (!this.plugins.contains(Plugin.apm) || !TextUtils.isEmpty(aliHaConfig.rsaPublicKey)) {
            this.context = aliHaConfig.context;
            return Boolean.valueOf(true);
        } else {
            Log.e(TAG, "rsaPublicKey is empty ");
            return Boolean.valueOf(false);
        }
    }

    private void printPluginAdded(String str) {
        Log.w(TAG, "plugin " + str + " in plugin list, add success! ");
    }

    public void openDebug(Boolean bool) {
        TLogService.OpenDebug(bool);
        APMService.openDebug(bool);
    }

    public void updateVersion(String str) {
        CrashService.updateApppVersion(str);
    }

    public void updateUserNick(String str) {
        CrashService.updateUserNick(str);
    }

    public void updateChannel(String str) {
        CrashService.updateChannel(str);
    }

    private void changeHost(String str) {
        if (str != null) {
            CrashService.changeHost(str);
            SendService.getInstance().changeHost(str);
        }
    }

    private void changeTLogHost() {
        TLogService.changeHost(mHATLogHost);
    }

    private void changeTLogBucketName() {
        TLogService.changeBucketName(mHAOSSBucketName);
    }

    public void setBootPath(String[] strArr, long j) {
        TelescopeService.setBootPath(strArr, j);
    }

    public void openHttp(Boolean bool) {
        if (bool != null) {
            SendService.getInstance().openHttp = bool;
        }
    }

    public void openPublishEmasHa() {
        changeHost(mUniversalHost);
        changeTLogHost();
        changeTLogBucketName();
    }

    public void changeAppSecretKey(String str) {
        if (str != null) {
            SendService.getInstance().appSecret = str;
        }
    }
}
