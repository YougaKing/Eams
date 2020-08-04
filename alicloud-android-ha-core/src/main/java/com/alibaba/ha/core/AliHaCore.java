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
        this.plugins = new ArrayList();
        this.TAG = "AliHaCore";
    }

    public static synchronized AliHaCore getInstance() {
        return AliHaCore.a.a;
    }

    public void registPlugin(AliHaPlugin var1) throws Exception {
        if (var1 != null) {
            this.plugins.add(var1);
        }

    }

    public void start(AliHaParam var1) throws Exception {
        Iterator var2 = this.plugins.iterator();

        while(var2.hasNext()) {
            AliHaPlugin var3 = (AliHaPlugin)var2.next();
            this.startWithPlugin(var1, var3);
        }

    }

    public void startWithPlugin(AliHaParam var1, AliHaPlugin var2) {
        if (var1 != null && var2 != null) {
            String var3 = var2.getName();
            Long var4 = System.currentTimeMillis();
            if (var3 == null) {
                var3 = "Unknown";
            }

            Log.i("AliHaCore", "start init plugin " + var3);
            var2.start(var1);
            Log.i("AliHaCore", "end init plugin " + var3 + " " + (System.currentTimeMillis() - var4) + "ms");
        }

    }

    private static class a {
        private static AliHaCore a = new AliHaCore();
    }
}
