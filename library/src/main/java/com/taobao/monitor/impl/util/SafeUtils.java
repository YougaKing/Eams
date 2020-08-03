package com.taobao.monitor.impl.util;


public class SafeUtils {

    public static String instanceofString(Object object, String string) {
        if (object instanceof String) {
            return (String)object;
        } else {
            return object != null ? object.toString() : string;
        }
    }
}
