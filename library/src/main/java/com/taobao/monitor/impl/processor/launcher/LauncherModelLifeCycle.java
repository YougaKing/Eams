package com.taobao.monitor.impl.processor.launcher;

import android.app.Activity;
import android.os.Bundle;

import com.taobao.monitor.impl.processor.IProcessor;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;

public class LauncherModelLifeCycle implements IProcessor.a, ActivityLifeCycleDispatcher.LifeCycleListener {
    private LauncherProcessor launcherProcessor = null;
    private int count = 0;
    private final IProcessorFactory<LauncherProcessor> launcherProcessorFactory = new LauncherProcessorFactory();

    public LauncherModelLifeCycle() {
    }

    @Override
    public void onActivityCreated(Activity var1, Bundle var2, long var3) {
        if (this.count == 0) {
            this.launcherProcessor = this.launcherProcessorFactory.createProcessor();
            if (this.launcherProcessor != null) {
                this.launcherProcessor.a(this);
            }
        }

        if (this.launcherProcessor != null) {
            this.launcherProcessor.onActivityCreated(var1, var2, var3);
        }

        ++this.count;
    }

    @Override
    public void onActivityStarted(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.onActivityStarted(var1, var2);
        }

    }

    @Override
    public void onActivityResumed(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.onActivityResumed(var1, var2);
        }

    }

    @Override
    public void onActivityPaused(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.onActivityPaused(var1, var2);
        }

    }

    @Override
    public void onActivityStopped(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.onActivityStopped(var1, var2);
        }

    }

    @Override
    public void onActivityDestroyed(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.onActivityDestroyed(var1, var2);
        }

        --this.count;
    }

    @Override
    public void a(IProcessor var1) {
    }

    @Override
    public void b(IProcessor var1) {
        this.launcherProcessor = null;
    }
}
