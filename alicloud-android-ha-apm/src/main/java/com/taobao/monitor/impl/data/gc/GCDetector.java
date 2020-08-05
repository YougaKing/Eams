package com.taobao.monitor.impl.data.gc;

/* compiled from: GCDetector */
public class GCDetector {
    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        GCSignalSender.k();
    }
}
