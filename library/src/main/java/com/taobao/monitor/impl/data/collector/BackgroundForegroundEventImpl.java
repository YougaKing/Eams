package com.taobao.monitor.impl.data.collector;

import com.taobao.application.common.IApmEventListener;
import com.taobao.application.common.data.BackgroundForegroundHelper;
import com.taobao.application.common.impl.ApmImpl;

public class BackgroundForegroundEventImpl {

    private final BackgroundForegroundHelper backgroundForegroundHelper = new BackgroundForegroundHelper();
    private final IApmEventListener eventListener = ApmImpl.instance().apmEventListener();
    private boolean inBackground = false;

    private final Runnable backgroundForegroundRunnable = new Runnable() {
        @Override
        public void run() {
            if (BackgroundForegroundEventImpl.this.inBackground) {
                BackgroundForegroundEventImpl.this.backgroundForegroundHelper.setFullInBackground(true);
            }

        }
    };
    private final Runnable eventRunnable = new Runnable() {
        @Override
        public void run() {
            if (BackgroundForegroundEventImpl.this.inBackground) {
                BackgroundForegroundEventImpl.this.eventListener.onEvent(50);
            }

        }
    };

    BackgroundForegroundEventImpl() {
    }

    void i() {
        this.inBackground = false;
        this.backgroundForegroundHelper.setInBackground(false);
        this.backgroundForegroundHelper.setFullInBackground(false);
        this.eventListener.onEvent(2);
        ApmImpl.instance().getAsyncHandler().removeCallbacks(this.backgroundForegroundRunnable);
        ApmImpl.instance().getAsyncHandler().removeCallbacks(this.eventRunnable);
    }

    void j() {
        this.inBackground = true;
        this.backgroundForegroundHelper.setInBackground(true);
        this.eventListener.onEvent(1);
        ApmImpl.instance().getAsyncHandler().postDelayed(this.backgroundForegroundRunnable, 300000L);
        ApmImpl.instance().getAsyncHandler().postDelayed(this.eventRunnable, 10000L);
    }
}
