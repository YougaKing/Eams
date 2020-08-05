package com.taobao.monitor.impl.data;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnDrawListener;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.util.TimeUtils;

@TargetApi(16)
/* compiled from: SimplePageLoadCalculate */
public class j implements OnDrawListener, f {
    private final a a;
    private long b;

    /* renamed from: b reason: collision with other field name */
    private final Handler f35b = new Handler(Looper.getMainLooper());

    /* renamed from: b reason: collision with other field name */
    private final Runnable f36b = new Runnable() {
        public void run() {
            j.a(j.this);
            j.a(j.this).g(j.a(j.this));
            if (j.b(j.this) > j.a(j.this)) {
                j.a(j.this).b(2, j.b(j.this));
                j.this.stop();
            }
        }
    };
    private final Runnable c = new Runnable() {
        @UiThread
        public void run() {
            j.a(j.this);
            if (j.b(j.this) > 2) {
                j.this.e = TimeUtils.currentTimeMillis();
                return;
            }
            j.a(j.this).removeCallbacks(this);
            j.a(j.this).postDelayed(this, 16);
        }
    };
    private final View d;

    /* renamed from: d reason: collision with other field name */
    private volatile boolean f37d = false;
    /* access modifiers changed from: private */
    public long e;
    private int f = 0;
    private volatile boolean j = false;

    /* compiled from: SimplePageLoadCalculate */
    public interface a {
        void b(int i, long j);

        void g(long j);
    }

    public j(View view, a aVar) {
        if (view == null || aVar == null) {
            throw new IllegalArgumentException();
        }
        this.d = view;
        this.a = aVar;
    }

    public void execute() {
        this.f35b.post(new Runnable() {
            public void run() {
                ViewTreeObserver viewTreeObserver = j.a(j.this).getViewTreeObserver();
                if (viewTreeObserver != null) {
                    viewTreeObserver.addOnDrawListener(j.this);
                }
            }
        });
        Global.instance().handler().postDelayed(this.f36b, 3000);
    }

    public void stop() {
        if (!this.f37d) {
            this.f37d = true;
            h();
            this.f35b.removeCallbacks(this.c);
        }
    }

    public void e() {
        this.a.g(this.b);
        if (this.e > this.b) {
            this.a.b(4, this.e);
            stop();
        }
    }

    private void h() {
        if (!this.j) {
            this.j = true;
            this.f35b.post(new Runnable() {
                public void run() {
                    ViewTreeObserver viewTreeObserver = j.a(j.this).getViewTreeObserver();
                    if (viewTreeObserver != null) {
                        viewTreeObserver.removeOnDrawListener(j.this);
                    }
                }
            });
            Global.instance().handler().removeCallbacks(this.f36b);
        }
    }

    public void onDraw() {
        this.b = TimeUtils.currentTimeMillis();
        this.f = 0;
        Global.instance().handler().removeCallbacks(this.f36b);
        Global.instance().handler().postDelayed(this.f36b, 3000);
        this.f35b.removeCallbacks(this.c);
        this.f35b.postDelayed(this.c, 16);
    }
}
