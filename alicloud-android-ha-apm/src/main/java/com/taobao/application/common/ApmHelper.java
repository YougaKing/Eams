package com.taobao.application.common;

import com.taobao.application.common.impl.ApmImpl;

/* compiled from: ApmHelper */
public class ApmHelper {
    public static void instance() {
        ApmManager.setApmDelegate(ApmImpl.instance());
    }
}
