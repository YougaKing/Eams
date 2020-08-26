package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.trace.ApplicationBackgroundChangedDispatcher.BackgroundChangedListener;
import com.taobao.monitor.impl.trace.ApplicationGcDispatcher;

/* compiled from: GCSwitcher */
public class GcSwitcher implements BackgroundChangedListener, ApplicationGcDispatcher.GcListener {
    private volatile boolean mGcOpen = false;

    @Override
    public void gc() {
        if (this.mGcOpen) {
            createDetector();
        }
    }

    public void open() {
        if (!this.mGcOpen) {
            this.mGcOpen = true;
            createDetector();
        }
    }

    private void close() {
        this.mGcOpen = false;
    }

    @Override
    public void backgroundChanged(int backgroundType, long timeMillis) {
        if (backgroundType == 0) {
            open();
        } else {
            close();
        }
    }

    private void createDetector() {
        new GcDetector();
    }
}
