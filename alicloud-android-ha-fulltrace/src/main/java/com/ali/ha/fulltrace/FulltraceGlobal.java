package com.ali.ha.fulltrace;

import android.os.Handler;
import android.os.HandlerThread;

public class FulltraceGlobal {
    private final Handler dumpHandler;
    private final Handler uploadHandler;

    private static class Holder {
        static final FulltraceGlobal INSTANCE = new FulltraceGlobal();

        private Holder() {
        }
    }

    private FulltraceGlobal() {
        HandlerThread handlerThread1 = new HandlerThread("APM-FulltraceDump");
        handlerThread1.start();
        this.dumpHandler = new Handler(handlerThread1.getLooper());
        HandlerThread handlerThread2 = new HandlerThread("APM-FulltraceUpload");
        handlerThread2.start();
        this.uploadHandler = new Handler(handlerThread2.getLooper());
    }

    public static FulltraceGlobal instance() {
        return Holder.INSTANCE;
    }

    public Handler dumpHandler() {
        return this.dumpHandler;
    }

    public Handler uploadHandler() {
        return this.uploadHandler;
    }
}
