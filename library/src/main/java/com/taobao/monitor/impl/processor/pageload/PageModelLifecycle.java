package com.taobao.monitor.impl.processor.pageload;


import android.app.Activity;
import android.os.Bundle;

import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;

import java.util.HashMap;
import java.util.Map;

public class PageModelLifecycle implements ActivityLifeCycleDispatcher.LifeCycleListener {
    private Map<Activity, ModelLifecycleListener> modelLifecycleListenerMap = new HashMap<>();
    private Map<Activity, ModelLifecyclePairListener> modelLifecyclePairListenerMap = new HashMap<>();
    private Activity activity = null;
    private int count = 0;
    private final IProcessorFactory<PageLoadProcessor> pageLoadProcessorFactory = new PageLoadProcessorFactory();
    private final IProcessorFactory<PageLoadPopProcessor> pageLoadPopProcessorFactory = new PageLoadPopProcessorFactory();

    public PageModelLifecycle() {
    }

    @Override
    public void onActivityCreated(Activity var1, Bundle var2, long var3) {
        PageLoadProcessor pageLoadProcessor = this.pageLoadProcessorFactory.createProcessor();
        if (pageLoadProcessor != null) {
            this.modelLifecycleListenerMap.put(var1, pageLoadProcessor);
            pageLoadProcessor.onActivityCreated(var1, var2, var3);
        }

        this.activity = var1;
    }

    @Override
    public void onActivityStarted(Activity var1, long var2) {
        ++this.count;
        ModelLifecycleListener modelLifecycleListener = this.modelLifecycleListenerMap.get(var1);
        if (modelLifecycleListener != null) {
            modelLifecycleListener.onActivityStarted(var1, var2);
        }

        if (this.modelLifecyclePairListenerMap != var1) {
            PageLoadPopProcessor pageLoadPopProcessor = this.pageLoadPopProcessorFactory.createProcessor();
            if (pageLoadPopProcessor != null) {
                pageLoadPopProcessor.onActivityStarted(var1);
                this.modelLifecyclePairListenerMap.put(var1, pageLoadPopProcessor);
            }
        }

        this.activity = var1;
    }

    @Override
    public void onActivityResumed(Activity var1, long var2) {
        ModelLifecycleListener var4 = this.modelLifecycleListenerMap.get(var1);
        if (var4 != null) {
            var4.onActivityResumed(var1, var2);
        }

    }

    @Override
    public void onActivityPaused(Activity var1, long var2) {
        ModelLifecycleListener var4 = this.modelLifecycleListenerMap.get(var1);
        if (var4 != null) {
            var4.onActivityPaused(var1, var2);
        }

    }

    @Override
    public void onActivityStopped(Activity var1, long var2) {
        --this.count;
        ModelLifecycleListener var4 = this.modelLifecycleListenerMap.get(var1);
        if (var4 != null) {
            var4.onActivityStopped(var1, var2);
        }

        ModelLifecyclePairListener var5 = this.modelLifecyclePairListenerMap.get(var1);
        if (var5 != null) {
            var5.onActivityStopped(var1);
            this.modelLifecyclePairListenerMap.remove(var1);
        }

        if (this.count == 0) {
            this.modelLifecyclePairListenerMap = null;
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity, long var2) {
        ModelLifecycleListener var4 = this.modelLifecycleListenerMap.get(activity);
        if (var4 != null) {
            var4.onActivityDestroyed(activity, var2);
        }

        this.modelLifecycleListenerMap.remove(activity);
        if (this.activity == activity) {
            this.activity = null;
        }

    }

    public interface ModelLifecyclePairListener {
        void onActivityStarted(Activity var1);

        void onActivityStopped(Activity var1);
    }

    public interface ModelLifecycleListener {
        void onActivityStarted(Activity var1, long var2);

        void onActivityResumed(Activity var1, long var2);

        void onActivityPaused(Activity var1, long var2);

        void onActivityStopped(Activity var1, long var2);

        void onActivityDestroyed(Activity var1, long var2);
    }
}
