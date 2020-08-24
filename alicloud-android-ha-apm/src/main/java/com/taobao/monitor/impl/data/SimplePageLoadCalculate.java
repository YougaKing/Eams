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
    private long b;

    /* renamed from: b reason: collision with other field name */
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    /* renamed from: b reason: collision with other field name */
    private final Runnable mRunnable = new Runnable() {
        public void run() {
            h();
            mPageLoadCalculateListener.pageDisplay(b);
            if (e > b) {
                mPageLoadCalculateListener.pageUsable(2, e);
                stop();
            }
        }
    };
    private final Runnable mUiRunnable = new Runnable() {
        @UiThread
        public void run() {
            mDrawCount++;
            if (mDrawCount > 2) {
                e = TimeUtils.currentTimeMillis();
                return;
            }
            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, 16);
        }
    };
    private final View mView;

    /* renamed from: d reason: collision with other field name */
    private volatile boolean f37d = false;
    /* access modifiers changed from: private */
    public long e;
    private int mDrawCount = 0;
    private volatile boolean j = false;

    /* compiled from: SimplePageLoadCalculate */
    public interface PageLoadCalculateListener {
        void pageUsable(int usableChangeType, long timeMillis);

        void pageDisplay(long j);
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
        if (!this.f37d) {
            this.f37d = true;
            h();
            this.mHandler.removeCallbacks(this.mUiRunnable);
        }
    }

    public void e() {
        this.mPageLoadCalculateListener.pageDisplay(this.b);
        if (this.e > this.b) {
            this.mPageLoadCalculateListener.pageUsable(4, this.e);
            stop();
        }
    }

    private void h() {
        if (!this.j) {
            this.j = true;
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
        this.b = TimeUtils.currentTimeMillis();
        this.mDrawCount = 0;
        Global.instance().handler().removeCallbacks(this.mRunnable);
        Global.instance().handler().postDelayed(this.mRunnable, 3000);
        this.mHandler.removeCallbacks(this.mUiRunnable);
        this.mHandler.postDelayed(this.mUiRunnable, 16);
    }
}
