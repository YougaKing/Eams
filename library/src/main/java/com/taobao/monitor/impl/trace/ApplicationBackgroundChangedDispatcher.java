package com.taobao.monitor.impl.trace;

public class ApplicationBackgroundChangedDispatcher extends AbsDispatcher<ApplicationBackgroundChangedDispatcher.BackgroundChangedListener> {

    public ApplicationBackgroundChangedDispatcher() {
    }

    public void dispatchBackgroundChanged(final int var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ApplicationBackgroundChangedDispatcher.BackgroundChangedListener>() {
            @Override
            public void run(BackgroundChangedListener backgroundChangedListener) {
                backgroundChangedListener.backgroundChanged(var1, var2);
            }
        });
    }

    public interface BackgroundChangedListener {
        void backgroundChanged(int var1, long var2);
    }
}
