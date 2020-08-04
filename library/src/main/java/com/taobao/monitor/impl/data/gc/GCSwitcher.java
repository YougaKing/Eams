package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;

public class GCSwitcher implements ApplicationBackgroundChangedDispatcher.BackgroundChangedListener,
        ApplicationGCDispatcher.GCListener {
    private volatile boolean m = false;

    public GCSwitcher() {
    }

    @Override
    public void gc() {
        if (this.m) {
            this.l();
        }

    }

    public void open() {
        if (!this.m) {
            this.m = true;
            this.l();
        }

    }

    public void close() {
        this.m = false;
    }

    @Override
    public void backgroundChanged(int var1, long var2) {
        if (var1 == 0) {
            this.open();
        } else {
            this.close();
        }

    }

    private void l() {
        new GCDetector();
    }
}
