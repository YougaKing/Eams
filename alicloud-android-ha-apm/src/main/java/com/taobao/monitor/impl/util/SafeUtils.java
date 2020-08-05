package com.taobao.monitor.impl.util;

/* compiled from: SafeUtils */
public class SafeUtils {

    public static String transformString(Object obj, String str) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj != null ? obj.toString() : str;
    }
}
