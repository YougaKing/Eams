package com.taobao.monitor.impl.trace;

/* compiled from: ApplicationGCDispatcher */
public class ApplicationGCDispatcher extends AbsDispatcher<ApplicationGCDispatcher.GCListener> {

    /* compiled from: ApplicationGCDispatcher */
    public interface GCListener {
        void gc();
    }

    public void gc() {
        dispatchRunnable(new DispatcherRunnable<GCListener>() {
            /* renamed from: a */
            public void run(GCListener aVar) {
                aVar.gc();
            }
        });
    }
}
