package com.taobao.monitor.impl.trace;

/* compiled from: FPSDispatcher */
public class FPSDispatcher extends AbsDispatcher<FPSDispatcher.FPSListener> {

    /* compiled from: FPSDispatcher */
    public interface FPSListener {
        void fps(int i);

        void jank(int i);
    }

    public void fps(final int i) {
        dispatchRunnable(new DispatcherRunnable<FPSListener>() {
            /* renamed from: a */
            public void run(FPSListener aVar) {
                aVar.fps(i);
            }
        });
    }

    public void jank(final int i) {
        dispatchRunnable(new DispatcherRunnable<FPSListener>() {
            /* renamed from: a */
            public void run(FPSListener aVar) {
                aVar.jank(i);
            }
        });
    }
}
