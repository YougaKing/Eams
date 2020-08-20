package com.taobao.monitor.impl.common;

import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;

/* compiled from: APMContext */
public class APMContext {

    /* renamed from: com.taobao.monitor.impl.common.a$a reason: collision with other inner class name */
    /* compiled from: APMContext */
    private static class APMContextHolder {
        /* access modifiers changed from: private */
        public static final APMContext APM_CONTEXT = new APMContext();
    }

    private APMContext() {
    }

    public static APMContext instance() {
        return APMContextHolder.APM_CONTEXT;
    }

    public static IDispatcher getDispatcher(String key) {
        return DispatcherManager.getDispatcher(key);
    }
}
