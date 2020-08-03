package com.taobao.monitor.impl.common;

import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.IDispatcher;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2020/8/3 11:37
 * @description:
 */
public class APMContext {
    private APMContext() {
    }

    public static APMContext instance() {
        return APMContextHolder.APM_CONTEXT;
    }

    public static IDispatcher getDispatcher(String key) {
        return DispatcherManager.getDispatcher(key);
    }

    private static class APMContextHolder {
        private static final APMContext APM_CONTEXT = new APMContext();
    }
}
