//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.ha.adapter.plugin.factory.PluginFactory;
import com.alibaba.ha.adapter.service.activity.AdapterActivityLifeCycle;
import com.alibaba.ha.adapter.service.apm.APMService;
import com.alibaba.ha.adapter.service.appstatus.AppStatusRegHelper;
import com.alibaba.ha.adapter.service.appstatus.Event1010Handler;
import com.alibaba.ha.core.AliHaCore;
import com.alibaba.ha.protocol.AliHaParam;
import com.alibaba.ha.protocol.AliHaPlugin;
import com.alibaba.motu.tbrest.SendService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AliHaAdapter {
    private static final String mUniversalHost = "adash-emas.cn-hangzhou.aliyuncs.com";
    private static final String mHATLogHost = "tlog-emas.aliyuncs.com";
    private static final String mHAOSSBucketName = "emasha-online";
    public static final String TAG = "AliHaAdapter";
    private List<Plugin> plugins;
    public Context context;

    private AliHaAdapter() {
        this.plugins = new ArrayList<>();
        this.context = null;
    }

    public static synchronized AliHaAdapter getInstance() {
        return AliHaAdapter.InstanceCreater.instance;
    }

    public void addPlugin(Plugin plugin) {
        if (plugin != null && !this.plugins.contains(plugin)) {
            Log.w("AliHaAdapter", "plugin add to list success, plugin name is " + plugin.name());
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

    public void removePlugin(Plugin var1) {
        if (var1 != null) {
            Log.w("AliHaAdapter", "plugin remove from list success, plugin name is " + var1.name());
            this.plugins.remove(var1);
        }

    }

    public Boolean startWithPlugins(AliHaConfig var1, List<Plugin> var2) {
        if (!this.isLegal(var1)) {
            return false;
        } else {
            if (var2 != null && var2.size() > 0) {
                this.plugins.addAll(var2);
                if (this.plugins.contains(Plugin.tlog) || this.plugins.contains(Plugin.crashreporter)) {
                    this.addPlugin(Plugin.telescope);
                }

                if (this.plugins.contains(Plugin.crashreporter)) {
                    this.addPlugin(Plugin.watch);
                    this.addPlugin(Plugin.bizErrorReporter);
                }
            }

            return this.start(var1);
        }
    }

    private Boolean startWithPlugin(AliHaConfig var1, Plugin var2) {
        if (!this.isLegal(var1)) {
            return false;
        } else {
            AliHaParam var3 = this.buildParam(var1);
            AliHaPlugin var4 = PluginFactory.createPlugin(var2);
            if (var4 != null) {
                AliHaCore.getInstance().startWithPlugin(var3, var4);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean startCrashReport(AliHaConfig var1) {
        this.addPlugin(Plugin.crashreporter);
        this.addPlugin(Plugin.telescope);
        this.addPlugin(Plugin.watch);
        this.addPlugin(Plugin.bizErrorReporter);
        return this.start(var1);
    }

    public Boolean start(AliHaConfig aliHaConfig) {
        if (!this.isLegal(aliHaConfig)) {
            return false;
        } else {
            this.openPublishEmasHa();
            AliHaParam aliHaParam = this.buildParam(aliHaConfig);

            try {
                if (this.plugins.contains(Plugin.crashreporter)) {
//                    CrashReporterPlugin crashReporterPlugin = new CrashReporterPlugin();
//                    AliHaCore.getInstance().startWithPlugin(aliHaParam, crashReporterPlugin);
                } else {
                    SendService.getInstance().init(aliHaParam.context, aliHaParam.appId, aliHaParam.appKey, aliHaParam.appVersion, aliHaParam.channel, aliHaParam.userNick);
                    SendService.getInstance().appSecret = aliHaParam.appSecret;
                    Log.i("AliHaAdapter", "init send service success, appId is " + aliHaParam.appId + " appKey is " + aliHaParam.appKey + " appVersion is " + aliHaParam.appVersion + " channel is " + aliHaParam.channel + " userNick is " + aliHaParam.userNick);
                }

                AliHaPlugin aliHaPlugin;
                if (this.plugins.contains(Plugin.ut)) {
//                    aliHaPlugin = PluginFactory.createPlugin(Plugin.ut);
//                    AliHaCore.getInstance().registPlugin(aliHaPlugin);
                } else {
                    this.printPluginAdded(Plugin.ut.name());
                }

                if (this.plugins.contains(Plugin.bizErrorReporter)) {
//                    aliHaPlugin = PluginFactory.createPlugin(Plugin.bizErrorReporter);
//                    AliHaCore.getInstance().registPlugin(aliHaPlugin);
                } else {
                    this.printPluginAdded(Plugin.bizErrorReporter.name());
                }

                if (this.plugins.contains(Plugin.onlineMonitor)) {
//                    aliHaPlugin = PluginFactory.createPlugin(Plugin.onlineMonitor);
//                    AliHaCore.getInstance().registPlugin(aliHaPlugin);
                } else {
                    this.printPluginAdded(Plugin.onlineMonitor.name());
                }

                if (this.plugins.contains(Plugin.telescope)) {
//                    aliHaPlugin = PluginFactory.createPlugin(Plugin.telescope);
//                    AliHaCore.getInstance().registPlugin(aliHaPlugin);
                } else {
                    this.printPluginAdded(Plugin.telescope.name());
                }

                if (this.plugins.contains(Plugin.tlog)) {
//                    aliHaPlugin = PluginFactory.createPlugin(Plugin.tlog);
//                    AliHaCore.getInstance().registPlugin(aliHaPlugin);
//                    TLogService.changeRasPublishKey(aliHaConfig.rsaPublicKey);
                } else {
                    this.printPluginAdded(Plugin.tlog.name());
                }

                if (this.plugins.contains(Plugin.watch)) {
//                    aliHaPlugin = PluginFactory.createPlugin(Plugin.watch);
//                    AliHaCore.getInstance().registPlugin(aliHaPlugin);
                } else {
                    this.printPluginAdded(Plugin.watch.name());
                }

                if (this.plugins.contains(Plugin.apm)) {
                    aliHaPlugin = PluginFactory.createPlugin(Plugin.apm);
                    AliHaCore.getInstance().registPlugin(aliHaPlugin);
                } else {
                    this.printPluginAdded(Plugin.apm.name());
                }

                AliHaCore.getInstance().start(aliHaParam);
                if (VERSION.SDK_INT >= 14) {
                    aliHaParam.application.registerActivityLifecycleCallbacks(new AdapterActivityLifeCycle());
                } else {
                    Log.w("AliHaAdapter", String.format("build version %s not suppert, registerActivityLifecycleCallbacks failed", VERSION.SDK_INT));
                }

                this.initAppStatus(aliHaConfig);
                return true;
            } catch (Exception var4) {
                Log.e("AliHaAdapter", "start plugin error ", var4);
                return false;
            }
        }
    }

    private String getBizId() {
        String bizId = "";
        if (this.plugins.contains(Plugin.crashreporter)) {
            bizId = "ha-crash";
        }

        if (this.plugins.contains(Plugin.apm)) {
            if (bizId.length() != 0) {
                bizId = bizId + "_";
            }

            bizId = bizId + "ha-apm";
        }

        if (this.plugins.contains(Plugin.tlog)) {
            if (bizId.length() != 0) {
                bizId = bizId + "_";
            }

            bizId = bizId + "ha-tlog";
        }

        return bizId;
    }

    private void initAppStatus(AliHaConfig aliHaConfig) {
        String bizId = this.getBizId();
        if (bizId != null && aliHaConfig != null) {
            AppStatusRegHelper.registeActivityLifecycleCallbacks(aliHaConfig.application);
            HashMap<String, String> map = new HashMap<>();
            map.put("_aliyun_biz_id", bizId);
            Event1010Handler event1010Handler = Event1010Handler.getInstance();
            event1010Handler.init(aliHaConfig.application, map);
            AppStatusRegHelper.registerAppStatusCallbacks(event1010Handler);
        }

    }

    private AliHaParam buildParam(AliHaConfig var1) {
        AliHaParam var2 = new AliHaParam();
        var2.application = var1.application;
        var2.context = var1.context;
        var2.appKey = var1.appKey;
        var2.appSecret = var1.appSecret;
        if (var1.isAliyunos) {
            var2.appId = var2.appKey + "@aliyunos";
        } else {
            var2.appId = var2.appKey + "@android";
        }

        var2.appVersion = var1.appVersion;
        var2.channel = var1.channel;
        var2.userNick = var1.userNick;
        return var2;
    }

    private Boolean isLegal(AliHaConfig var1) {
        if (var1 == null) {
            Log.e("AliHaAdapter", "config is null ");
            return false;
        } else if (var1.application == null) {
            Log.e("AliHaAdapter", "application is null ");
            return false;
        } else if (var1.context == null) {
            Log.e("AliHaAdapter", "context is null ");
            return false;
        } else if (var1.appKey != null && var1.appSecret != null && var1.appVersion != null) {
            if (this.plugins.contains(Plugin.apm) && TextUtils.isEmpty(var1.rsaPublicKey)) {
                Log.e("AliHaAdapter", "rsaPublicKey is empty ");
                return false;
            } else {
                this.context = var1.context;
                return true;
            }
        } else {
            Log.e("AliHaAdapter", "config is unlegal, ha plugin start failure  appKey is " + var1.appKey + " appVersion is " + var1.appVersion + " appSecret is " + var1.appSecret);
            return false;
        }
    }

    private void printPluginAdded(String var1) {
        Log.w("AliHaAdapter", "plugin " + var1 + " in plugin list, add success! ");
    }

    public void openDebug(Boolean var1) {
//        TLogService.OpenDebug(var1);
        APMService.openDebug(var1);
    }

    public void updateVersion(String var1) {
//        CrashService.updateApppVersion(var1);
    }

    public void updateUserNick(String var1) {
//        CrashService.updateUserNick(var1);
    }

    public void updateChannel(String var1) {
//        CrashService.updateChannel(var1);
    }

    private void changeHost(String var1) {
        if (var1 != null) {
//            CrashService.changeHost(var1);
            SendService.getInstance().changeHost(var1);
        }

    }

    private void changeTLogHost() {
//        TLogService.changeHost("tlog-emas.aliyuncs.com");
    }

    private void changeTLogBucketName() {
//        TLogService.changeBucketName("emasha-online");
    }

    public void setBootPath(String[] var1, long var2) {
//        TelescopeService.setBootPath(var1, var2);
    }

    public void openHttp(Boolean var1) {
        if (var1 != null) {
            SendService.getInstance().openHttp = var1;
        }

    }

    public void openPublishEmasHa() {
        this.changeHost("adash-emas.cn-hangzhou.aliyuncs.com");
        this.changeTLogHost();
        this.changeTLogBucketName();
    }

    public void changeAppSecretKey(String var1) {
        if (var1 != null) {
            SendService.getInstance().appSecret = var1;
        }

    }

    private static class InstanceCreater {
        private static AliHaAdapter instance = new AliHaAdapter();

        private InstanceCreater() {
        }
    }
}
