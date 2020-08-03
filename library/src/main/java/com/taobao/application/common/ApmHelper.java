package com.taobao.application.common;

public class ApmHelper {

    public static void initApmImpl() {
        ApmManager.setApmDelegate(b.a());
    }
}
