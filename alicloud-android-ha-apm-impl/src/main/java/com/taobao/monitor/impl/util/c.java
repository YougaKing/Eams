package com.taobao.monitor.impl.util;

import java.lang.reflect.Field;

/* compiled from: FieldUtils */
public class c {
    public static boolean a(Object obj, Field field, Object obj2, Object obj3) throws IllegalAccessException {
        if (obj2 == obj3) {
            return false;
        }
        field.setAccessible(true);
        field.set(obj, obj3);
        return true;
    }

    public static <T> T a(Object obj, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(obj);
    }
}
