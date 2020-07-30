//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.plugin.factory;

import android.util.Log;
import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.adapter.plugin.APMPlugin;
import com.alibaba.ha.adapter.plugin.BizErrorPlugin;
import com.alibaba.ha.adapter.plugin.CrashReporterPlugin;
import com.alibaba.ha.adapter.plugin.OnLineMonitorPlugin;
import com.alibaba.ha.adapter.plugin.TLogPlugin;
import com.alibaba.ha.adapter.plugin.TelescopePlugin;
import com.alibaba.ha.adapter.plugin.UtPlugin;
import com.alibaba.ha.adapter.plugin.WatchPlugin;
import com.alibaba.ha.protocol.AliHaPlugin;

public class PluginFactory {
    public PluginFactory() {
    }

    public static AliHaPlugin createPlugin(Plugin var0) {
        Object var1 = null;
        switch(var0) {
            case ut:
                var1 = new UtPlugin();
                break;
            case tlog:
                var1 = new TLogPlugin();
                break;
            case bizErrorReporter:
                var1 = new BizErrorPlugin();
                break;
            case watch:
                var1 = new WatchPlugin();
                break;
            case telescope:
                var1 = new TelescopePlugin();
                break;
            case onlineMonitor:
                var1 = new OnLineMonitorPlugin();
                break;
            case crashreporter:
                var1 = new CrashReporterPlugin();
                break;
            case apm:
                var1 = new APMPlugin();
                break;
            default:
                Log.w("AliHaAdapter", "plugin not exist! ");
        }

        return (AliHaPlugin)var1;
    }
}
