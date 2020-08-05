package com.taobao.application.common;

import com.taobao.application.common.impl.ApmImpl;

/* compiled from: ApmHelper */
public class a {
    public static void a() {
        ApmManager.setApmDelegate(ApmImpl.instance());
    }
}
