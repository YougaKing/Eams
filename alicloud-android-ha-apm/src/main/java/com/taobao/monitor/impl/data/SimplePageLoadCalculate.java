package com.taobao.monitor.impl.data;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnDrawListener;

import androidx.annotation.UiThread;

import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.util.TimeUtils;

@TargetApi(16)
/* compiled from: SimplePageLoadCalculate */
public class SimplePageLoadCalculate implements OnDrawListener, IExecutor {
    private final PageLoadCalculateListener mPageLoadCalculateListener;
    private long mDrawTime;

    /* renamed from: b reason: collision with other field name */
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    /* renamed from: b reason: collision with other field name */
    private final Runnable mRunnable = new Runnable() {
        public void run() {
            removeOnDrawListener();
            mPageLoadCalculateListener.pageDisplay(mDrawTime);
            if (mDrawTwiceTime > mDrawTime) {
                mPageLoadCalculateListener.pageUsable(2, mDrawTwiceTime);
                stop();
            }
        }
    };
    private final Runnable mUiRunnable = new Runnable() {
        @UiThread
        public void run() {
            mDrawCount++;
            if (mDrawCount > 2) {
                mDrawTwiceTime = TimeUtils.currentTimeMillis();
                return;
            }
            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, 16);
        }
    };
    private final View mView;

    /* renamed from: d reason: collision with other field name */
    private volatile boolean mStop = false;
    /* access modifiers changed from: private */
    public long mDrawTwiceTime;
    private int mDrawCount = 0;
    private volatile boolean mRemoveDraw = false;

    /* compiled from: SimplePageLoadCalculate */
    public interface PageLoadCalculateListener {
        void pageUsable(int usableChangeType, long timeMillis);

        void pageDisplay(long timeMillis);
    }

    public SimplePageLoadCalculate(View view, PageLoadCalculateListener aVar) {
        if (view == null || aVar == null) {
            throw new IllegalArgumentException();
        }
        this.mView = view;
        this.mPageLoadCalculateListener = aVar;
    }

    @Override
    public void execute() {
        this.mHandler.post(new Runnable() {
            public void run() {
                ViewTreeObserver viewTreeObserver = mView.getViewTreeObserver();
                if (viewTreeObserver != null) {
                    viewTreeObserver.addOnDrawListener(SimplePageLoadCalculate.this);
                }
            }
        });
        Global.instance().handler().postDelayed(this.mRunnable, 3000);
    }

    @Override
    public void stop() {
        if (!this.mStop) {
            this.mStop = true;
            removeOnDrawListener();
            this.mHandler.removeCallbacks(this.mUiRunnable);
        }
    }

    public void onActivityStopped() {
        this.mPageLoadCalculateListener.pageDisplay(this.mDrawTime);
        if (this.mDrawTwiceTime > this.mDrawTime) {
            this.mPageLoadCalculateListener.pageUsable(4, this.mDrawTwiceTime);
            stop();
        }
    }

    private void removeOnDrawListener() {
        if (!this.mRemoveDraw) {
            this.mRemoveDraw = true;
            this.mHandler.post(new Runnable() {
                public void run() {
                    ViewTreeObserver viewTreeObserver = mView.getViewTreeObserver();
                    if (viewTreeObserver != null) {
                        viewTreeObserver.removeOnDrawListener(SimplePageLoadCalculate.this);
                    }
                }
            });
            Global.instance().handler().removeCallbacks(this.mRunnable);
        }
    }

    @Override
    public void onDraw() {
        this.mDrawTime = TimeUtils.currentTimeMillis();
        this.mDrawCount = 0;
        Global.instance().handler().removeCallbacks(this.mRunnable);
        Global.instance().handler().postDelayed(this.mRunnable, 3000);
        this.mHandler.removeCallbacks(this.mUiRunnable);
        this.mHandler.postDelayed(this.mUiRunnable, 16);
    }
}
