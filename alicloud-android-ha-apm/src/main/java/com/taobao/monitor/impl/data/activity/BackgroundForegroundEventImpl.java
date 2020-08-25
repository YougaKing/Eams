package com.taobao.monitor.impl.data.activity;

import com.taobao.application.common.IApmEventListener;
import com.taobao.application.common.data.BackgroundForegroundHelper;
import com.taobao.application.common.impl.ApmImpl;

import static com.taobao.application.common.IApmEventListener.NOTIFY_BACKGROUND_2_FOREGROUND;
import static com.taobao.application.common.IApmEventListener.NOTIFY_FOREGROUND_2_BACKGROUND;
import static com.taobao.application.common.IApmEventListener.NOTIFY_FOR_IN_BACKGROUND;

/* compiled from: BackgroundForegroundEventImpl */
class BackgroundForegroundEventImpl {
    private final BackgroundForegroundHelper mBackgroundForegroundHelper = new BackgroundForegroundHelper();
    private final IApmEventListener mApmEventListener = ApmImpl.instance().apmEventListener();
    private final Runnable mFullInBackgroundRunnable = new Runnable() {
        public void run() {
            if (mBackground) {
                mBackgroundForegroundHelper.isFullInBackground(true);
            }
        }
    };
    private final Runnable mBackgroundRunnable = new Runnable() {
        public void run() {
            if (mBackground) {
                mApmEventListener.onApmEvent(NOTIFY_FOR_IN_BACKGROUND);
            }
        }
    };
    private boolean mBackground = false;

    BackgroundForegroundEventImpl() {
    }

    /* access modifiers changed from: 0000 */
    public void background2Foreground() {
        this.mBackground = false;
        this.mBackgroundForegroundHelper.isInBackground(false);
        this.mBackgroundForegroundHelper.isFullInBackground(false);
        this.mApmEventListener.onApmEvent(NOTIFY_BACKGROUND_2_FOREGROUND);
        ApmImpl.instance().getAsyncHandler().removeCallbacks(this.mFullInBackgroundRunnable);
        ApmImpl.instance().getAsyncHandler().removeCallbacks(this.mBackgroundRunnable);
    }

    /* access modifiers changed from: 0000 */
    public void foreground2Background() {
        this.mBackground = true;
        this.mBackgroundForegroundHelper.isInBackground(true);
        this.mApmEventListener.onApmEvent(NOTIFY_FOREGROUND_2_BACKGROUND);
        ApmImpl.instance().getAsyncHandler().postDelayed(this.mFullInBackgroundRunnable, 300000);
        ApmImpl.instance().getAsyncHandler().postDelayed(this.mBackgroundRunnable, 10000);
    }
}
