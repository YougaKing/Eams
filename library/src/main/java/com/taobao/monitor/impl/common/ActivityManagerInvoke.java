package com.taobao.monitor.impl.common;

import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.util.TimeUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ActivityManagerInvoke implements InvocationHandler {
    private final Object object;

    ActivityManagerInvoke(Object var1) {
        this.object = var1;
    }

    public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
        String var4 = var2.getName();
        if (var4.contains("startActivity")) {
            GlobalStats.jumpTime = TimeUtils.currentTimeMillis();
        }

        try {
            return var2.invoke(this.object, var3);
        } catch (InvocationTargetException var6) {
            throw var6.getTargetException();
        }
    }
}
