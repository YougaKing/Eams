package com.taobao.monitor.impl.trace;


import java.util.HashMap;
import java.util.Map;

public class DispatcherManager {
    private static Map<String, IDispatcher> dispatcherMap = new HashMap<>();

    public static void putDispatcher(String str, IDispatcher iDispatcher) {
        dispatcherMap.put(str, iDispatcher);
    }

    public static IDispatcher getDispatcher(String str) {
        IDispatcher iDispatcher = dispatcherMap.get(str);
        return iDispatcher == null ? EmptyDispatcher.emptyDispatcher : iDispatcher;
    }

    public static boolean isEmpty(IDispatcher iDispatcher) {
        return iDispatcher == null || iDispatcher == EmptyDispatcher.emptyDispatcher;
    }
}
