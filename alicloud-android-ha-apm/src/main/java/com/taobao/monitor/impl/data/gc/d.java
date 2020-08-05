package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher.BackgroundChangedListener;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;

/* compiled from: GCSwitcher */
public class d implements BackgroundChangedListener, ApplicationGCDispatcher.GCListener {
    private volatile boolean m = false;

    public void gc() {
        if (this.m) {
            l();
        }
    }

    public void open() {
        if (!this.m) {
            this.m = true;
            l();
        }
    }

    public void close() {
        this.m = false;
    }

    public void backgroundChanged(int i, long j) {
        if (i == 0) {
            open();
        } else {
            close();
        }
    }

    private void l() {
        new b();
    }
}
