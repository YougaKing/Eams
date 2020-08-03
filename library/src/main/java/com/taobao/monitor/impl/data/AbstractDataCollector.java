//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
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

public class AbstractDataCollector<T> implements com.taobao.monitor.impl.data.i.a, com.taobao.monitor.impl.data.j.a, Runnable {
    private final T b;
    private final String pageName;
    private o a = null;
    private volatile boolean a = false;
    private int count = 0;
    private float a = 0.0F;
    private f a;
    private f b;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private final boolean e;
    private final IPageListener b = com.taobao.application.common.impl.b.a().a();
    private final Runnable a = new Runnable() {
        public void run() {
            a.this.c();
        }
    };

    protected a(T var1) {
        if (!(var1 instanceof Activity) && !(var1 instanceof Fragment)) {
            throw new IllegalArgumentException();
        } else {
            this.b = var1;
            this.e = var1 instanceof Activity;
            this.pageName = var1.getClass().getName();
            this.b.onPageChanged(this.pageName, 0, TimeUtils.currentTimeMillis());
            Logger.i("AbstractDataCollector", new Object[]{"visibleStart", this.pageName});
        }
    }

    public void run() {
        ++this.count;
        if (this.count > 2) {
            this.a(1, TimeUtils.currentTimeMillis());
        } else {
            Handler var1 = new Handler(Looper.getMainLooper());
            var1.removeCallbacks(this);
            var1.postDelayed(this, 16L);
        }
    }

    protected void initDispatcher() {
        IDispatcher var1 = this.b instanceof Activity ? com.taobao.monitor.impl.common.a.a("ACTIVITY_USABLE_VISIBLE_DISPATCHER") : com.taobao.monitor.impl.common.a.a("FRAGMENT_USABLE_VISIBLE_DISPATCHER");
        if (var1 instanceof o) {
            this.a = (o)var1;
        }

    }

    protected void a(View var1) {
        this.d = false;
        if (!this.a) {
            if (!g.a(this.a)) {
                this.a.a(this.b, TimeUtils.currentTimeMillis());
            }

            this.a = new i(var1);
            ((i)this.a).a(this);
            this.a.execute();
            if (!PageList.inComplexPage(this.b.getClass().getName()) && VERSION.SDK_INT >= 16) {
                this.b = new j(var1, this);
                this.b.execute();
            }

            Global.instance().handler().postDelayed(this.a, 20000L);
            this.b.onPageChanged(this.pageName, 1, TimeUtils.currentTimeMillis());
            this.a = true;
        }

    }

    protected void b() {
        this.c();
        this.d = !this.e;
    }

    protected void a(int var1, long var2) {
        if (!this.b && !this.d) {
            DataLoggerUtils.log("AbstractDataCollector", new Object[]{"usable", this.pageName});
            Logger.i("AbstractDataCollector", new Object[]{this.pageName, " usable", var2});
            if (!g.a(this.a)) {
                this.a.a(this.b, 2, var1, var2);
            }

            this.c();
            this.b.onPageChanged(this.pageName, 3, var2);
            this.b = true;
        }

    }

    public void a(float var1) {
        Logger.i("AbstractDataCollector", new Object[]{"visiblePercent", var1, this.pageName});
        if (Math.abs(var1 - this.a) > 0.05F || var1 > 0.8F) {
            if (!g.a(this.a)) {
                this.a.a(this.b, var1, TimeUtils.currentTimeMillis());
            }

            DataLoggerUtils.log("AbstractDataCollector", new Object[]{"visiblePercent", var1, this.pageName});
            if (var1 > 0.8F) {
                this.h(TimeUtils.currentTimeMillis());
                this.run();
            }

            this.a = var1;
        }

    }

    public void g(long var1) {
        this.h(var1);
    }

    private void h(long var1) {
        if (!this.c && !this.d) {
            if (!g.a(this.a)) {
                Logger.i("AbstractDataCollector", new Object[]{this.pageName, " visible", var1});
                this.a.a(this.b, 2, var1);
            }

            this.b.onPageChanged(this.pageName, 2, var1);
            this.c();
            this.c = true;
        }

    }

    public void b(int var1, long var2) {
        this.a(var1, var2);
    }

    private void c() {
        if (this.a != null) {
            synchronized(this) {
                if (this.a != null || this.b != null) {
                    Global.instance().handler().removeCallbacks(this.a);
                    if (this.a != null) {
                        this.a.stop();
                    }

                    if (this.b != null) {
                        this.b.stop();
                    }

                    this.d();
                    this.a = null;
                    this.b = null;
                }
            }
        }

    }

    private void d() {
        LocalBroadcastManager var1 = LocalBroadcastManager.getInstance(Global.instance().context());
        Intent var2 = new Intent("ACTIVITY_FRAGMENT_VISIBLE_ACTION");
        var2.putExtra("page_name", this.pageName);
        if (this.b instanceof Activity) {
            var2.putExtra("type", "activity");
        } else if (this.b instanceof Fragment) {
            var2.putExtra("type", "fragment");
        } else {
            var2.putExtra("type", "unknown");
        }

        var2.putExtra("status", 1);
        var1.sendBroadcastSync(var2);
        Logger.i("AbstractDataCollector", new Object[]{"doSendPageFinishedEvent:" + this.pageName});
    }

    protected void e() {
        if (this.b instanceof j) {
            ((j)this.b).e();
        }

    }
}
