package com.taobao.monitor.impl.data;

import android.annotation.TargetApi;
import android.view.ViewTreeObserver.OnDrawListener;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.FPSDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;

@TargetApi(16)
/* compiled from: DrawTimeCollector */
public class DrawTimeCollector implements OnDrawListener {
    private FPSDispatcher mFPSDispatcher;
    private long mLastTime = TimeUtils.currentTimeMillis();
    private int mJankCount = 0;

    /* renamed from: c reason: collision with other field name */
    private long mTempTime = 0;
    private int mFpsCount = 0;

    /* renamed from: d reason: collision with other field name */
    private long mStartTime;

    public DrawTimeCollector() {
        IDispatcher a2 = DispatcherManager.getDispatcher("ACTIVITY_FPS_DISPATCHER");
        if (a2 instanceof FPSDispatcher) {
            this.mFPSDispatcher = (FPSDispatcher) a2;
        }
    }

    public void mOnTouchEvent() {
        this.mStartTime = TimeUtils.currentTimeMillis();
    }

    @Override
    public void onDraw() {
        long currentTimeMillis = TimeUtils.currentTimeMillis();
        if (currentTimeMillis - this.mStartTime <= 2000) {
            long j = currentTimeMillis - this.mLastTime;
            if (j < 200) {
                this.mTempTime += j;
                this.mFpsCount++;
                if (j > 32) {
                    this.mJankCount++;
                }
                if (this.mTempTime > 1000) {
                    if (this.mFpsCount > 60) {
                        this.mFpsCount = 60;
                    }
                    if (!DispatcherManager.isEmpty((IDispatcher) this.mFPSDispatcher)) {
                        this.mFPSDispatcher.fps(this.mFpsCount);
                        this.mFPSDispatcher.jank(this.mJankCount);
                    }
                    this.mTempTime = 0;
                    this.mFpsCount = 0;
                    this.mJankCount = 0;
                }
            }
            this.mLastTime = currentTimeMillis;
        }
    }
}
