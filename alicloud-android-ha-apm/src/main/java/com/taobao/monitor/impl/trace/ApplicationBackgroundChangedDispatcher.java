package com.taobao.monitor.impl.trace;

/* compiled from: ApplicationBackgroundChangedDispatcher */
public class ApplicationBackgroundChangedDispatcher extends AbsDispatcher<ApplicationBackgroundChangedDispatcher.BackgroundChangedListener> {

    /* compiled from: ApplicationBackgroundChangedDispatcher */
    public interface BackgroundChangedListener {
        void backgroundChanged(int i, long j);
    }

    public void backgroundChanged(final int i, final long j) {
        dispatchRunnable(new DispatcherRunnable<BackgroundChangedListener>() {
            /* renamed from: a */
            public void run(BackgroundChangedListener aVar) {
                aVar.backgroundChanged(i, j);
            }
        });
    }
}
