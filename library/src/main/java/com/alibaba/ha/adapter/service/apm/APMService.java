//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.apm;

import com.taobao.monitor.adapter.SimpleApmInitiator;

public class APMService {
    private static boolean isValid = false;

    public APMService() {
    }

    public static void openDebug(Boolean debug) {
        if (isValid) {
            SimpleApmInitiator.setDebug(debug);
        }
    }

    static {
        try {
            Class.forName("com.taobao.monitor.adapter.SimpleApmInitiator");
            isValid = true;
        } catch (ClassNotFoundException var1) {
            isValid = false;
        }

    }
}
