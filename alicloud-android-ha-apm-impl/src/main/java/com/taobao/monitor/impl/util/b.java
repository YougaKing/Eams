package com.taobao.monitor.impl.util;

import com.taobao.monitor.impl.common.Global;

/* compiled from: DeviceUtils */
public class b {
    public static int a(int i) {
        return (int) ((Global.instance().context().getResources().getDisplayMetrics().density * ((float) i)) + 0.5f);
    }
}
