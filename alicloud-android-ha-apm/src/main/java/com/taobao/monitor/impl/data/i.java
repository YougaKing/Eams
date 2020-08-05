package com.taobao.monitor.impl.data;

import android.view.View;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.Logger;
import java.lang.ref.WeakReference;

/* compiled from: PageLoadCalculate */
public class i implements f, Runnable {
    /* access modifiers changed from: private */
    public a a;

    /* renamed from: a reason: collision with other field name */
    private final WeakReference<View> f34a;
    private volatile boolean i = false;

    /* compiled from: PageLoadCalculate */
    public interface a {
        void a(float f);
    }

    public i(View view) {
        this.f34a = new WeakReference<>(view);
    }

    public i a(a aVar) {
        this.a = aVar;
        return this;
    }

    public void execute() {
        Global.instance().getAsyncUiHandler().postDelayed(this, 50);
    }

    public void stop() {
        this.i = true;
        Global.instance().getAsyncUiHandler().removeCallbacks(this);
        Global.instance().handler().post(new Runnable() {
            public void run() {
                i.this.a = null;
            }
        });
    }

    public void run() {
        if (!this.i) {
            g();
            Global.instance().getAsyncUiHandler().postDelayed(this, 75);
        }
    }

    private void g() {
        View view = (View) this.f34a.get();
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
        if (this.a != null) {
            float a2 = new b(view, view2).a();
            Logger.d("PageLoadCalculate", "calculateDraw percent: " + a2);
            this.a.a(a2);
        }
    }
}
