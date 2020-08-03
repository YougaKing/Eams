package com.taobao.monitor.impl.trace;

public class NetworkStageDispatcher extends AbsDispatcher<NetworkStageDispatcher.StageListener> {
    public NetworkStageDispatcher() {
    }

    public void networkStage(final int var1) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<NetworkStageDispatcher.StageListener>() {
            @Override
            public void run(StageListener stageListener) {
                stageListener.networkStage(var1);
            }
        });
    }

    public interface StageListener {
        void networkStage(int var1);
    }
}
