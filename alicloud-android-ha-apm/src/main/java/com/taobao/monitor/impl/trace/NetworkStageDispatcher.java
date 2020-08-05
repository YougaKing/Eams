package com.taobao.monitor.impl.trace;

/* compiled from: NetworkStageDispatcher */
public class NetworkStageDispatcher extends AbsDispatcher<NetworkStageDispatcher.StageListener> {

    /* compiled from: NetworkStageDispatcher */
    public interface StageListener {
        void networkStage(int i);
    }

    public void networkStage(final int i) {
        dispatchRunnable(new DispatcherRunnable<StageListener>() {
            /* renamed from: a */
            public void run(StageListener aVar) {
                aVar.networkStage(i);
            }
        });
    }
}
