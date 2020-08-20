package com.taobao.monitor.impl.trace;

/* compiled from: ApplicationGCDispatcher */
public class ApplicationGcDispatcher extends AbsDispatcher<ApplicationGcDispatcher.GcListener> {

    /* compiled from: ApplicationGCDispatcher */
    public interface GcListener {
        void gc();
    }

    public void gc() {
        dispatchRunnable(new DispatcherRunnable<GcListener>() {
            /* renamed from: a */
            public void run(GcListener aVar) {
                aVar.gc();
            }
        });
    }
}
