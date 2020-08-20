package com.taobao.monitor.impl.trace;

import java.util.HashMap;
import java.util.Map;

/* compiled from: DispatcherManager */
public class DispatcherManager {

    private static Map<String, IDispatcher> dispatcherMap = new HashMap<>();

    public static <T> void putDispatcher(String key, IDispatcher<T> iDispatcher) {
        dispatcherMap.put(key, iDispatcher);
    }

    public static <T> IDispatcher<T> getDispatcher(String key) {
        IDispatcher<T> iDispatcher = dispatcherMap.get(key);
        return iDispatcher == null ? EmptyDispatcher.emptyDispatcher : iDispatcher;
    }

    public static <T> boolean isEmpty(IDispatcher<T> iDispatcher) {
        return iDispatcher == null || iDispatcher == EmptyDispatcher.emptyDispatcher;
    }
}
