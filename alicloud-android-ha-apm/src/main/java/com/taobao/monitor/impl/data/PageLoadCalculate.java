package com.taobao.monitor.impl.data;

import android.view.View;

import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.Logger;

import java.lang.ref.WeakReference;

/* compiled from: PageLoadCalculate */
public class PageLoadCalculate implements IExecutor, Runnable {
    /* access modifiers changed from: private */
    public PageLoadCalculateListener mPageLoadCalculateListener;

    /* renamed from: a reason: collision with other field name */
    private final WeakReference<View> mViewWeakReference;
    private volatile boolean mStop = false;

    /* compiled from: PageLoadCalculate */
    public interface PageLoadCalculateListener {
        void visiblePercent(float percent);
    }

    public PageLoadCalculate(View view) {
        this.mViewWeakReference = new WeakReference<>(view);
    }

    public PageLoadCalculate setPageLoadCalculateListener(PageLoadCalculateListener aVar) {
        this.mPageLoadCalculateListener = aVar;
        return this;
    }

    @Override
    public void execute() {
        Global.instance().getAsyncUiHandler().postDelayed(this, 50);
    }

    @Override
    public void stop() {
        this.mStop = true;
        Global.instance().getAsyncUiHandler().removeCallbacks(this);
        Global.instance().handler().post(new Runnable() {
            public void run() {
                PageLoadCalculate.this.mPageLoadCalculateListener = null;
            }
        });
    }

    @Override
    public void run() {
        if (!this.mStop) {
            visiblePercent();
            Global.instance().getAsyncUiHandler().postDelayed(this, 75);
        }
    }

    private void visiblePercent() {
        View decorView = this.mViewWeakReference.get();
        if (decorView == null) {
            stop();
            Logger.d("PageLoadCalculate", "check root decorView null, stop");
            return;
        }
        try {
            View contentView = decorView.findViewById(decorView.getResources().getIdentifier("content", "id", "android"));
            if (contentView == null) {
                contentView = decorView;
            }
            if (contentView.getHeight() * contentView.getWidth() == 0) {
                Logger.d("PageLoadCalculate", "check not draw");
                return;
            }
            visiblePercent(contentView, decorView);
        } catch (NullPointerException e) {
            Logger.w("PageLoadCalculate", "check exception: " + e.getMessage());
        }
    }

    private void visiblePercent(View contentView, View decorView) {
        if (this.mPageLoadCalculateListener != null) {
            float percent = new CanvasCalculator(contentView, decorView).calculateViewPercent();
            Logger.d("PageLoadCalculate", "calculateDraw percent: " + percent);
            this.mPageLoadCalculateListener.visiblePercent(percent);
        }
    }
}
