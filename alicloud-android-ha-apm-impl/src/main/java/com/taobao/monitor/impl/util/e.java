package com.taobao.monitor.impl.util;

/* compiled from: SafeUtils */
public class e {
    public static String a(Object obj, String str) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj != null ? obj.toString() : str;
    }
}
