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
    private volatile boolean i = false;

    /* compiled from: PageLoadCalculate */
    public interface PageLoadCalculateListener {
        void visiblePercent(float f);
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
        this.i = true;
        Global.instance().getAsyncUiHandler().removeCallbacks(this);
        Global.instance().handler().post(new Runnable() {
            public void run() {
                PageLoadCalculate.this.mPageLoadCalculateListener = null;
            }
        });
    }

    @Override
    public void run() {
        if (!this.i) {
            g();
            Global.instance().getAsyncUiHandler().postDelayed(this, 75);
        }
    }

    private void g() {
        View view = (View) this.mViewWeakReference.get();
        if (view == null) {
            stop();
            Logger.d("PageLoadCalculate", "check root view null, stop");
            return;
        }
        try {
            View findViewById = view.findViewById(view.getResources().getIdentifier("content", "id", "android"));
            if (findViewById == null) {
                findViewById = view;
            }
            if (findViewById.getHeight() * findViewById.getWidth() == 0) {
                Logger.d("PageLoadCalculate", "check not draw");
                return;
            }
            a(findViewById, view);
        } catch (NullPointerException e) {
            Logger.w("PageLoadCalculate", "check exception: " + e.getMessage());
        }
    }

    private void a(View view, View view2) {
        if (this.mPageLoadCalculateListener != null) {
            float a2 = new CanvasCalculator(view, view2).a();
            Logger.d("PageLoadCalculate", "calculateDraw percent: " + a2);
            this.mPageLoadCalculateListener.visiblePercent(a2);
        }
    }
}
