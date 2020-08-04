package com.taobao.monitor.impl.data;


import android.content.res.Resources;
import android.view.View;

import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.Logger;

import java.lang.ref.WeakReference;

public class PageLoadCalculate implements IExecutor, Runnable {
    private PageLoadCalculateListener calculateListener;
    private final WeakReference<View> viewWeakReference;
    private volatile boolean i = false;

    public PageLoadCalculate(View var1) {
        this.viewWeakReference = new WeakReference<>(var1);
    }

    public PageLoadCalculate a(PageLoadCalculateListener var1) {
        this.calculateListener = var1;
        return this;
    }

    @Override
    public void execute() {
        Global.instance().getAsyncUiHandler().postDelayed(this, 50L);
    }

    @Override
    public void stop() {
        this.i = true;
        Global.instance().getAsyncUiHandler().removeCallbacks(this);
        Global.instance().handler().post(new Runnable() {
            public void run() {
                PageLoadCalculate.this.calculateListener = null;
            }
        });
    }

    @Override
    public void run() {
        if (!this.i) {
            this.g();
            Global.instance().getAsyncUiHandler().postDelayed(this, 75L);
        }

    }

    private void g() {
        View var1 = this.viewWeakReference.get();
        if (var1 == null) {
            this.stop();
            Logger.d("PageLoadCalculate", "check root view null, stop");
        } else {
            try {
                Resources var2 = var1.getResources();
                int var3 = var2.getIdentifier("content", "id", "android");
                View var4 = var1.findViewById(var3);
                if (var4 == null) {
                    var4 = var1;
                }

                if (var4.getHeight() * var4.getWidth() == 0) {
                    Logger.d("PageLoadCalculate", "check not draw");
                    return;
                }

                this.a(var4, var1);
            } catch (NullPointerException var5) {
                Logger.w("PageLoadCalculate", "check exception: " + var5.getMessage());
            }

        }
    }

    private void a(View var1, View var2) {
        if (this.calculateListener != null) {
            CanvasCalculator var3 = new CanvasCalculator(var1, var2);
            float var4 = var3.a();
            Logger.d("PageLoadCalculate", "calculateDraw percent: " + var4);
            this.calculateListener.a(var4);
        }

    }

    public interface PageLoadCalculateListener {
        void a(float var1);
    }
}
