package com.taobao.monitor.impl.trace;

/* compiled from: FPSDispatcher */
public class FPSDispatcher extends AbsDispatcher<FPSDispatcher.FPSListener> {

    /* compiled from: FPSDispatcher */
    public interface FPSListener {
        void b(int i);

        void c(int i);
    }

    public void b(final int i) {
        dispatchRunnable(new DispatcherRunnable<FPSListener>() {
            /* renamed from: a */
            public void run(FPSListener aVar) {
                aVar.b(i);
            }
        });
    }

    public void c(final int i) {
        dispatchRunnable(new DispatcherRunnable<FPSListener>() {
            /* renamed from: a */
            public void run(FPSListener aVar) {
                aVar.c(i);
            }
        });
    }
}
