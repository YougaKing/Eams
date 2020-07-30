//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.core;

import android.util.Log;
import com.alibaba.ha.protocol.AliHaParam;
import com.alibaba.ha.protocol.AliHaPlugin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AliHaCore {
    private List<AliHaPlugin> plugins;
    private final String TAG;

    private AliHaCore() {
        this.plugins = new ArrayList<>();
        this.TAG = "AliHaCore";
    }

    public static synchronized AliHaCore getInstance() {
        return AliHaCoreHolder.aliHaCore;
    }

    public void registPlugin(AliHaPlugin aliHaPlugin) throws Exception {
        if (aliHaPlugin != null) {
            this.plugins.add(aliHaPlugin);
        }
    }

    public void start(AliHaParam aliHaParam) throws Exception {
        Iterator iterator = this.plugins.iterator();

        while(iterator.hasNext()) {
            AliHaPlugin var3 = (AliHaPlugin)iterator.next();
            this.startWithPlugin(aliHaParam, var3);
        }

    }

    public void startWithPlugin(AliHaParam aliHaParam, AliHaPlugin aliHaPlugin) {
        if (aliHaParam != null && aliHaPlugin != null) {
            String pluginName = aliHaPlugin.getName();
            Long currentTimeMillis = System.currentTimeMillis();
            if (pluginName == null) {
                pluginName = "Unknown";
            }

            Log.i("AliHaCore", "start init plugin " + pluginName);
            aliHaPlugin.start(aliHaParam);
            Log.i("AliHaCore", "end init plugin " + pluginName + " " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
        }

    }

    private static class AliHaCoreHolder {
        private static AliHaCore aliHaCore = new AliHaCore();
    }
}
