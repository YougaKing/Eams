package com.taobao.monitor.impl.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import com.taobao.application.common.IPageListener;
import com.taobao.application.common.impl.b;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.impl.processor.launcher.PageList;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.trace.o;
import com.taobao.monitor.impl.util.TimeUtils;

/* compiled from: AbstractDataCollector */
public class a<T> implements com.taobao.monitor.impl.data.i.a, com.taobao.monitor.impl.data.j.a, Runnable {
    private float a = 0.0f;

    /* renamed from: a reason: collision with other field name */
    private f f20a;

    /* renamed from: a reason: collision with other field name */
    private o f21a = null;

    /* renamed from: a reason: collision with other field name */
    private final Runnable f22a = new Runnable() {
        public void run() {
            a.this.c();
        }
    };

    /* renamed from: a reason: collision with other field name */
    private volatile boolean f23a = false;
    private final IPageListener b = b.a().a();

    /* renamed from: b reason: collision with other field name */
    private f f24b;

    /* renamed from: b reason: collision with other field name */
    private final T f25b;

    /* renamed from: b reason: collision with other field name */
    private boolean f26b = false;
    private boolean c = false;
    private int count = 0;
    private boolean d = false;
    private final boolean e;
    private final String pageName;

    protected a(T t) {
        if ((t instanceof Activity) || (t instanceof Fragment)) {
            this.f25b = t;
            this.e = t instanceof Activity;
            this.pageName = t.getClass().getName();
            this.b.onPageChanged(this.pageName, 0, TimeUtils.currentTimeMillis());
            Logger.i("AbstractDataCollector", "visibleStart", this.pageName);
            return;
        }
        throw new IllegalArgumentException();
    }

    public void run() {
        this.count++;
        if (this.count > 2) {
            a(1, TimeUtils.currentTimeMillis());
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.removeCallbacks(this);
        handler.postDelayed(this, 16);
    }

    /* access modifiers changed from: protected */
    public void initDispatcher() {
        IDispatcher a2;
        if (this.f25b instanceof Activity) {
            a2 = com.taobao.monitor.impl.common.a.a("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        } else {
            a2 = com.taobao.monitor.impl.common.a.a("FRAGMENT_USABLE_VISIBLE_DISPATCHER");
        }
        if (a2 instanceof o) {
            this.f21a = (o) a2;
        }
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        this.d = false;
        if (!this.f23a) {
            if (!g.a((IDispatcher) this.f21a)) {
                this.f21a.a(this.f25b, TimeUtils.currentTimeMillis());
            }
            this.f20a = new i(view);
            ((i) this.f20a).a(this);
            this.f20a.execute();
            if (!PageList.inComplexPage(this.f25b.getClass().getName()) && VERSION.SDK_INT >= 16) {
                this.f24b = new j(view, this);
                this.f24b.execute();
            }
            Global.instance().handler().postDelayed(this.f22a, 20000);
            this.b.onPageChanged(this.pageName, 1, TimeUtils.currentTimeMillis());
            this.f23a = true;
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        c();
        this.d = !this.e;
    }

    /* access modifiers changed from: protected */
    public void a(int i, long j) {
        if (!this.f26b && !this.d) {
            DataLoggerUtils.log("AbstractDataCollector", "usable", this.pageName);
            Logger.i("AbstractDataCollector", this.pageName, " usable", Long.valueOf(j));
            if (!g.a((IDispatcher) this.f21a)) {
                this.f21a.a(this.f25b, 2, i, j);
            }
            c();
            this.b.onPageChanged(this.pageName, 3, j);
            this.f26b = true;
        }
    }

    public void a(float f) {
        Logger.i("AbstractDataCollector", "visiblePercent", Float.valueOf(f), this.pageName);
        if (Math.abs(f - this.a) > 0.05f || f > 0.8f) {
            if (!g.a((IDispatcher) this.f21a)) {
                this.f21a.a((Object) this.f25b, f, TimeUtils.currentTimeMillis());
            }
            DataLoggerUtils.log("AbstractDataCollector", "visiblePercent", Float.valueOf(f), this.pageName);
            if (f > 0.8f) {
                h(TimeUtils.currentTimeMillis());
                run();
            }
            this.a = f;
        }
    }

    public void g(long j) {
        h(j);
    }

    private void h(long j) {
        if (!this.c && !this.d) {
            if (!g.a((IDispatcher) this.f21a)) {
                Logger.i("AbstractDataCollector", this.pageName, " visible", Long.valueOf(j));
                this.f21a.a((Object) this.f25b, 2, j);
            }
            this.b.onPageChanged(this.pageName, 2, j);
            c();
            this.c = true;
        }
    }

    public void b(int i, long j) {
        a(i, j);
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.f20a != null) {
            synchronized (this) {
                if (!(this.f20a == null && this.f24b == null)) {
                    Global.instance().handler().removeCallbacks(this.f22a);
                    if (this.f20a != null) {
                        this.f20a.stop();
                    }
                    if (this.f24b != null) {
                        this.f24b.stop();
                    }
                    d();
                    this.f20a = null;
                    this.f24b = null;
                }
            }
        }
    }

    private void d() {
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(Global.instance().context());
        Intent intent = new Intent("ACTIVITY_FRAGMENT_VISIBLE_ACTION");
        intent.putExtra("page_name", this.pageName);
        if (this.f25b instanceof Activity) {
            intent.putExtra("type", "activity");
        } else if (this.f25b instanceof Fragment) {
            intent.putExtra("type", "fragment");
        } else {
            intent.putExtra("type", "unknown");
        }
        intent.putExtra("status", 1);
        instance.sendBroadcastSync(intent);
        Logger.i("AbstractDataCollector", "doSendPageFinishedEvent:" + this.pageName);
    }

    /* access modifiers changed from: protected */
    public void e() {
        if (this.f24b instanceof j) {
            ((j) this.f24b).e();
        }
    }
}
