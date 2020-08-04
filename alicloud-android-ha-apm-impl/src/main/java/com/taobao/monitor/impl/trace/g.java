package com.taobao.monitor.impl.trace;

import java.util.HashMap;
import java.util.Map;

/* compiled from: DispatcherManager */
public class g {
    private static Map<String, IDispatcher> e = new HashMap();

    public static void a(String str, IDispatcher iDispatcher) {
        e.put(str, iDispatcher);
    }

    public static IDispatcher a(String str) {
        IDispatcher iDispatcher = (IDispatcher) e.get(str);
        return iDispatcher == null ? h.a : iDispatcher;
    }

    public static boolean a(IDispatcher iDispatcher) {
        return iDispatcher == null || iDispatcher == h.a;
    }
}
