package com.taobao.monitor.impl.data.activity;

import com.taobao.application.common.IApmEventListener;
import com.taobao.application.common.data.AppLaunchHelper;
import com.taobao.application.common.data.BackgroundForegroundHelper;
import com.taobao.application.common.impl.ApmImpl;

/* compiled from: BackgroundForegroundEventImpl */
class BackgroundForegroundEventImpl {
    private final BackgroundForegroundHelper mBackgroundForegroundHelper = new BackgroundForegroundHelper();
    private final IApmEventListener mApmEventListener = ApmImpl.instance().apmEventListener();
    private final Runnable d = new Runnable() {
        public void run() {
            if (l) {
                mBackgroundForegroundHelper.d(true);
            }
        }
    };
    private final Runnable e = new Runnable() {
        public void run() {
            if (l) {
                mApmEventListener.onEvent(50);
            }
        }
    };
    private boolean l = false;
    private final AppLaunchHelper launchHelper = new AppLaunchHelper();

    BackgroundForegroundEventImpl() {
    }

    /* access modifiers changed from: 0000 */
    public void i() {
        this.l = false;
        this.mBackgroundForegroundHelper.c(false);
        this.mBackgroundForegroundHelper.d(false);
        this.mApmEventListener.onEvent(2);
        ApmImpl.instance().getAsyncHandler().removeCallbacks(this.d);
        ApmImpl.instance().getAsyncHandler().removeCallbacks(this.e);
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        this.l = true;
        this.mBackgroundForegroundHelper.c(true);
        this.mApmEventListener.onEvent(1);
        ApmImpl.instance().getAsyncHandler().postDelayed(this.d, 300000);
        ApmImpl.instance().getAsyncHandler().postDelayed(this.e, 10000);
    }
}
