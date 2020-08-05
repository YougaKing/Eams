package com.taobao.monitor.impl.processor.launcher;

import android.app.Activity;
import android.os.Bundle;
import com.taobao.monitor.impl.processor.IProcessor;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: LauncherModelLifeCycle */
public class a implements com.taobao.monitor.impl.processor.IProcessor.a, com.taobao.monitor.impl.trace.c.a {
    private b a = null;
    private final IProcessorFactory<b> c = new c();
    private int count = 0;

    public void a(Activity activity, Bundle bundle, long j) {
        if (this.count == 0) {
            this.a = (b) this.c.createProcessor();
            if (this.a != null) {
                this.a.a((com.taobao.monitor.impl.processor.IProcessor.a) this);
            }
        }
        if (this.a != null) {
            this.a.a(activity, bundle, j);
        }
        this.count++;
    }

    public void a(Activity activity, long j) {
        if (this.a != null) {
            this.a.a(activity, j);
        }
    }

    public void b(Activity activity, long j) {
        if (this.a != null) {
            this.a.b(activity, j);
        }
    }

    public void c(Activity activity, long j) {
        if (this.a != null) {
            this.a.c(activity, j);
        }
    }

    public void d(Activity activity, long j) {
        if (this.a != null) {
            this.a.d(activity, j);
        }
    }

    public void e(Activity activity, long j) {
        if (this.a != null) {
            this.a.e(activity, j);
        }
        this.count--;
    }

    public void a(IProcessor iProcessor) {
    }

    public void b(IProcessor iProcessor) {
        this.a = null;
    }
}
