package com.taobao.monitor.impl.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.taobao.application.common.IPageListener;
import com.taobao.application.common.impl.ApmImpl;
import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.impl.processor.launcher.PageList;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.UsableVisibleDispatcher;
import com.taobao.monitor.impl.util.TimeUtils;

/* compiled from: AbstractDataCollector */
public class AbstractDataCollector<T> implements PageLoadCalculate.PageLoadCalculateListener, SimplePageLoadCalculate.PageLoadCalculateListener, Runnable {
    private float a = 0.0f;

    /* renamed from: a reason: collision with other field name */
    private IExecutor mPageLoadCalculate;

    /* renamed from: a reason: collision with other field name */
    private UsableVisibleDispatcher mUsableVisibleDispatcher = null;

    /* renamed from: a reason: collision with other field name */
    private final Runnable mRunnable = new Runnable() {
        public void run() {
            AbstractDataCollector.this.stop();
        }
    };

    /* renamed from: a reason: collision with other field name */
    private volatile boolean f23a = false;
    private final IPageListener mPageListener = ApmImpl.instance().m4a();

    /* renamed from: b reason: collision with other field name */
    private IExecutor mSimplePageLoadCalculate;

    /* renamed from: b reason: collision with other field name */
    private final T mT;

    /* renamed from: b reason: collision with other field name */
    private boolean f26b = false;
    private boolean c = false;
    private int count = 0;
    private boolean d = false;
    private final boolean e;
    private final String pageName;

    protected AbstractDataCollector(T t) {
        if ((t instanceof Activity) || (t instanceof Fragment)) {
            this.mT = t;
            this.e = t instanceof Activity;
            this.pageName = t.getClass().getName();
            this.mPageListener.onPageChanged(this.pageName, 0, TimeUtils.currentTimeMillis());
            Logger.i("AbstractDataCollector", "visibleStart", this.pageName);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void run() {
        this.count++;
        if (this.count > 2) {
            usable(1, TimeUtils.currentTimeMillis());
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.removeCallbacks(this);
        handler.postDelayed(this, 16);
    }

    /* access modifiers changed from: protected */
    public void initDispatcher() {
        IDispatcher a2;
        if (this.mT instanceof Activity) {
            a2 = APMContext.getDispatcher("ACTIVITY_USABLE_VISIBLE_DISPATCHER");
        } else {
            a2 = APMContext.getDispatcher("FRAGMENT_USABLE_VISIBLE_DISPATCHER");
        }
        if (a2 instanceof UsableVisibleDispatcher) {
            this.mUsableVisibleDispatcher = (UsableVisibleDispatcher) a2;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume(View view) {
        this.d = false;
        if (!this.f23a) {
            if (!DispatcherManager.isEmpty((IDispatcher) this.mUsableVisibleDispatcher)) {
                this.mUsableVisibleDispatcher.onResume(this.mT, TimeUtils.currentTimeMillis());
            }
            this.mPageLoadCalculate = new PageLoadCalculate(view);
            ((PageLoadCalculate) this.mPageLoadCalculate).setPageLoadCalculateListener(this);
            this.mPageLoadCalculate.execute();
            if (!PageList.inComplexPage(this.mT.getClass().getName()) && VERSION.SDK_INT >= 16) {
                this.mSimplePageLoadCalculate = new SimplePageLoadCalculate(view, this);
                this.mSimplePageLoadCalculate.execute();
            }
            Global.instance().handler().postDelayed(this.mRunnable, 20000);
            this.mPageListener.onPageChanged(this.pageName, 1, TimeUtils.currentTimeMillis());
            this.f23a = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        stop();
        this.d = !this.e;
    }

    /* access modifiers changed from: protected */
    public void usable(int i, long j) {
        if (!this.f26b && !this.d) {
            DataLoggerUtils.log("AbstractDataCollector", "usable", this.pageName);
            Logger.i("AbstractDataCollector", this.pageName, " usable", Long.valueOf(j));
            if (!DispatcherManager.isEmpty((IDispatcher) this.mUsableVisibleDispatcher)) {
                this.mUsableVisibleDispatcher.usable(this.mT, 2, i, j);
            }
            stop();
            this.mPageListener.onPageChanged(this.pageName, 3, j);
            this.f26b = true;
        }
    }

    @Override
    public void visiblePercent(float f) {
        Logger.i("AbstractDataCollector", "visiblePercent", Float.valueOf(f), this.pageName);
        if (Math.abs(f - this.a) > 0.05f || f > 0.8f) {
            if (!DispatcherManager.isEmpty((IDispatcher) this.mUsableVisibleDispatcher)) {
                this.mUsableVisibleDispatcher.visiblePercent((Object) this.mT, f, TimeUtils.currentTimeMillis());
            }
            DataLoggerUtils.log("AbstractDataCollector", "visiblePercent", Float.valueOf(f), this.pageName);
            if (f > 0.8f) {
                display(TimeUtils.currentTimeMillis());
                run();
            }
            this.a = f;
        }
    }

    public void pageDisplay(long j) {
        display(j);
    }

    private void display(long j) {
        if (!this.c && !this.d) {
            if (!DispatcherManager.isEmpty((IDispatcher) this.mUsableVisibleDispatcher)) {
                Logger.i("AbstractDataCollector", this.pageName, " visible", Long.valueOf(j));
                this.mUsableVisibleDispatcher.display((Object) this.mT, 2, j);
            }
            this.mPageListener.onPageChanged(this.pageName, 2, j);
            stop();
            this.c = true;
        }
    }

    public void pageUsable(int i, long j) {
        usable(i, j);
    }

    /* access modifiers changed from: private */
    public void stop() {
        if (this.mPageLoadCalculate != null) {
            synchronized (this) {
                if (!(this.mPageLoadCalculate == null && this.mSimplePageLoadCalculate == null)) {
                    Global.instance().handler().removeCallbacks(this.mRunnable);
                    if (this.mPageLoadCalculate != null) {
                        this.mPageLoadCalculate.stop();
                    }
                    if (this.mSimplePageLoadCalculate != null) {
                        this.mSimplePageLoadCalculate.stop();
                    }
                    sendBroadcast();
                    this.mPageLoadCalculate = null;
                    this.mSimplePageLoadCalculate = null;
                }
            }
        }
    }

    private void sendBroadcast() {
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(Global.instance().context());
        Intent intent = new Intent("ACTIVITY_FRAGMENT_VISIBLE_ACTION");
        intent.putExtra("page_name", this.pageName);
        if (this.mT instanceof Activity) {
            intent.putExtra("type", "activity");
        } else if (this.mT instanceof Fragment) {
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
        if (this.mSimplePageLoadCalculate instanceof SimplePageLoadCalculate) {
            ((SimplePageLoadCalculate) this.mSimplePageLoadCalculate).e();
        }
    }
}
