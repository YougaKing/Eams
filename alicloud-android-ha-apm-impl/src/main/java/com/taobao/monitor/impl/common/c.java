package com.taobao.monitor.impl.common;

import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.util.TimeUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: ActivityManagerHook */
class c implements InvocationHandler {
    private final Object a;

    c(Object obj) {
        this.a = obj;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        if (method.getName().contains("startActivity")) {
            GlobalStats.jumpTime = TimeUtils.currentTimeMillis();
        }
        try {
            return method.invoke(this.a, objArr);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
