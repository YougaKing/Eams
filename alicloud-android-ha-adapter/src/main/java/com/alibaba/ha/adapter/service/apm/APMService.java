package com.alibaba.ha.adapter.service.apm;

import com.taobao.monitor.adapter.SimpleApmInitiator;

public class APMService {
    private static boolean isValid;

    static {
        isValid = false;
        try {
            Class.forName("com.taobao.monitor.adapter.SimpleApmInitiator");
            isValid = true;
        } catch (ClassNotFoundException e) {
            isValid = false;
        }
    }

    public static void openDebug(Boolean bool) {
        if (isValid) {
            SimpleApmInitiator.setDebug(bool.booleanValue());
        }
    }
}
