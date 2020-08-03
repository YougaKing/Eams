package com.taobao.monitor.impl.processor.pageload;

import android.app.Activity;
import android.os.Bundle;

import com.taobao.monitor.impl.processor.IProcessor;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.processor.launcher.LauncherProcessor;
import com.taobao.monitor.impl.processor.launcher.LauncherProcessorFactory;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;


public class PageLoadPopProcessor implements IProcessor.a, ActivityLifeCycleDispatcher.LifeCycleListener {
    private LauncherProcessor launcherProcessor = null;
    private int count = 0;
    private final IProcessorFactory<LauncherProcessor> processorFactory = new LauncherProcessorFactory();

    public PageLoadPopProcessor() {
    }

    public void a(Activity var1, Bundle var2, long var3) {
        if (this.count == 0) {
            this.launcherProcessor = this.processorFactory.createProcessor();
            if (this.launcherProcessor != null) {
                this.launcherProcessor.a(this);
            }
        }

        if (this.launcherProcessor != null) {
            this.launcherProcessor.a(var1, var2, var3);
        }

        ++this.count;
    }

    public void a(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.a(var1, var2);
        }

    }

    public void b(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.b(var1, var2);
        }

    }

    public void c(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.c(var1, var2);
        }

    }

    public void d(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.d(var1, var2);
        }

    }

    public void e(Activity var1, long var2) {
        if (this.launcherProcessor != null) {
            this.launcherProcessor.e(var1, var2);
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
