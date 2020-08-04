package com.taobao.monitor.impl.data.gc;


public class GCDetector {

    public GCDetector() {
    }

    protected void finalize() throws Throwable {
        super.finalize();
        GCSignalSender.postRunnable();
    }
}
