package com.taobao.monitor.impl.processor.launcher;

import android.app.Activity;
import android.os.Bundle;

import com.taobao.monitor.impl.processor.IProcessor;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;

/* compiled from: LauncherModelLifeCycle */
public class LauncherModelLifeCycle implements IProcessor.ProcessorCallback,
        ActivityLifeCycleDispatcher.LifeCycleListener {
    private LauncherProcessor mLauncherProcessor = null;
    private final IProcessorFactory<LauncherProcessor> mLauncherProcessorFactory = new LauncherProcessorFactory();
    private int count = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle, long j) {
        if (this.count == 0) {
            this.mLauncherProcessor = this.mLauncherProcessorFactory.createProcessor();
            if (this.mLauncherProcessor != null) {
                this.mLauncherProcessor.setProcessorCallback(this);
            }
        }
        if (this.mLauncherProcessor != null) {
            this.mLauncherProcessor.onActivityCreated(activity, bundle, j);
        }
        this.count++;
    }

    @Override
    public void onActivityStarted(Activity activity, long j) {
        if (this.mLauncherProcessor != null) {
            this.mLauncherProcessor.onActivityStarted(activity, j);
        }
    }

    @Override
    public void onActivityResumed(Activity activity, long j) {
        if (this.mLauncherProcessor != null) {
            this.mLauncherProcessor.onActivityResumed(activity, j);
        }
    }

    @Override
    public void onActivityPaused(Activity activity, long j) {
        if (this.mLauncherProcessor != null) {
            this.mLauncherProcessor.onActivityPaused(activity, j);
        }
    }

    @Override
    public void onActivityStopped(Activity activity, long j) {
        if (this.mLauncherProcessor != null) {
            this.mLauncherProcessor.onActivityStopped(activity, j);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity, long j) {
        if (this.mLauncherProcessor != null) {
            this.mLauncherProcessor.onActivityDestroyed(activity, j);
        }
        this.count--;
    }

    @Override
    public void onProcedureBegin(IProcessor iProcessor) {
    }

    @Override
    public void onProcedureEnd(IProcessor iProcessor) {
        this.mLauncherProcessor = null;
    }
}
