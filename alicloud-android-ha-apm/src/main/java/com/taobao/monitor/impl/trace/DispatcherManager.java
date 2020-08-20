package com.taobao.monitor.impl.trace;

import java.util.HashMap;
import java.util.Map;

/* compiled from: DispatcherManager */
public class DispatcherManager {
    private static Map<String, IDispatcher> dispatcherMap = new HashMap<>();

    public static void putDispatcher(String key, IDispatcher iDispatcher) {
        dispatcherMap.put(key, iDispatcher);
    }

    public static IDispatcher getDispatcher(String key) {
        IDispatcher iDispatcher = dispatcherMap.get(key);
        return iDispatcher == null ? EmptyDispatcher.emptyDispatcher : iDispatcher;
    }

    public static boolean isEmpty(IDispatcher iDispatcher) {
        return iDispatcher == null || iDispatcher == EmptyDispatcher.emptyDispatcher;
    }
}
