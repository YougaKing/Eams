//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.plugin.factory;

import android.util.Log;

import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.adapter.plugin.APMPlugin;
import com.alibaba.ha.protocol.AliHaPlugin;

public class PluginFactory {
    public PluginFactory() {
    }

    public static AliHaPlugin createPlugin(Plugin var0) {
        AliHaPlugin aliHaPlugin = null;
        switch (var0) {
            case ut:
//                aliHaPlugin = new UtPlugin();
                break;
            case tlog:
//                aliHaPlugin = new TLogPlugin();
                break;
            case bizErrorReporter:
//                aliHaPlugin = new BizErrorPlugin();
                break;
            case watch:
//                aliHaPlugin = new WatchPlugin();
                break;
            case telescope:
//                aliHaPlugin = new TelescopePlugin();
                break;
            case onlineMonitor:
//                aliHaPlugin = new OnLineMonitorPlugin();
                break;
            case crashreporter:
//                aliHaPlugin = new CrashReporterPlugin();
                break;
            case apm:
                aliHaPlugin = new APMPlugin();
                break;
            default:
                Log.w("AliHaAdapter", "plugin not exist! ");
        }

        return aliHaPlugin;
    }
}
