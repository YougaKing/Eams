package com.taobao.monitor.impl.trace;

public class ImageStageDispatcher extends AbsDispatcher<ImageStageDispatcher.StageListener> {
    public ImageStageDispatcher() {
    }

    public void f(final int var1) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ImageStageDispatcher.StageListener>() {
            @Override
            public void run(StageListener stageListener) {
                stageListener.d(var1);
            }
        });
    }

    public interface StageListener {
        void d(int var1);
    }
}
