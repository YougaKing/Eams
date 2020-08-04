package com.taobao.monitor.impl.data;

import android.annotation.TargetApi;
import android.view.ViewTreeObserver.OnDrawListener;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.trace.i;
import com.taobao.monitor.impl.util.TimeUtils;

@TargetApi(16)
/* compiled from: DrawTimeCollector */
public class d implements OnDrawListener {
    private i a;
    private long b = TimeUtils.currentTimeMillis();
    private int c = 0;

    /* renamed from: c reason: collision with other field name */
    private long f32c = 0;
    private int d = 0;

    /* renamed from: d reason: collision with other field name */
    private long f33d;

    public d() {
        IDispatcher a2 = g.a("ACTIVITY_FPS_DISPATCHER");
        if (a2 instanceof i) {
            this.a = (i) a2;
        }
    }

    public void f() {
        this.f33d = TimeUtils.currentTimeMillis();
    }

    public void onDraw() {
        long currentTimeMillis = TimeUtils.currentTimeMillis();
        if (currentTimeMillis - this.f33d <= 2000) {
            long j = currentTimeMillis - this.b;
            if (j < 200) {
                this.f32c += j;
                this.d++;
                if (j > 32) {
                    this.c++;
                }
                if (this.f32c > 1000) {
                    if (this.d > 60) {
                        this.d = 60;
                    }
                    if (!g.a((IDispatcher) this.a)) {
                        this.a.b(this.d);
                        this.a.c(this.c);
                    }
                    this.f32c = 0;
                    this.d = 0;
                    this.c = 0;
                }
            }
            this.b = currentTimeMillis;
        }
    }
}
