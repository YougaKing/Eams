package com.taobao.monitor.impl.trace;

/* compiled from: ImageStageDispatcher */
public class ImageStageDispatcher extends AbsDispatcher<ImageStageDispatcher.StageListener> {

    /* compiled from: ImageStageDispatcher */
    public interface StageListener {
        void imageStage(int i);
    }

    public void imageStage(final int i) {
        dispatchRunnable(new DispatcherRunnable<StageListener>() {
            /* renamed from: a */
            public void run(StageListener aVar) {
                aVar.imageStage(i);
            }
        });
    }
}
