package com.taobao.monitor.impl.trace;

/* compiled from: ApplicationBackgroundChangedDispatcher */
public class ApplicationBackgroundChangedDispatcher extends AbsDispatcher<ApplicationBackgroundChangedDispatcher.BackgroundChangedListener> {

    /* compiled from: ApplicationBackgroundChangedDispatcher */
    public interface BackgroundChangedListener {
        void backgroundChanged(int backgroundType, long timeMillis);
    }

    public void backgroundChanged(final int backgroundType, final long timeMillis) {
        dispatchRunnable(new DispatcherRunnable<BackgroundChangedListener>() {
            /* renamed from: a */
            public void run(BackgroundChangedListener aVar) {
                aVar.backgroundChanged(backgroundType, timeMillis);
            }
        });
    }
}
