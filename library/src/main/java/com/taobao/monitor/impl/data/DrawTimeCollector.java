package com.taobao.monitor.impl.data;

import android.view.ViewTreeObserver;

import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;

public class DrawTimeCollector implements ViewTreeObserver.OnDrawListener {
    private long b = TimeUtils.currentTimeMillis();
    private long c = 0L;
    private int c = 0;
    private int d = 0;
    private long d;
    private i a;

    public DrawTimeCollector() {
        IDispatcher var1 = g.a("ACTIVITY_FPS_DISPATCHER");
        if (var1 instanceof i) {
            this.a = (i)var1;
        }

    }

    public void f() {
        this.d = TimeUtils.currentTimeMillis();
    }

    public void onDraw() {
        long var1 = TimeUtils.currentTimeMillis();
        if (var1 - this.d <= 2000L) {
            long var3 = var1 - this.b;
            if (var3 < 200L) {
                this.c += var3;
                ++this.d;
                if (var3 > 32L) {
                    ++this.c;
                }

                if (this.c > 1000L) {
                    if (this.d > 60) {
                        this.d = 60;
                    }

                    if (!g.a(this.a)) {
                        this.a.b(this.d);
                        this.a.c(this.c);
                    }

                    this.c = 0L;
                    this.d = 0;
                    this.c = 0;
                }
            }

            this.b = var1;
        }
    }
}
