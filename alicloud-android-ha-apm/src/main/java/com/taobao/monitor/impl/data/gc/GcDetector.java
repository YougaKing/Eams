package com.taobao.monitor.impl.data.gc;

/* compiled from: GCDetector */
public class GcDetector {
    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        GcSignalSender.runGcRunnable();
    }
}
