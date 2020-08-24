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

import static com.taobao.application.common.IPageListener.DRAW_START;
import static com.taobao.application.common.IPageListener.INIT_TIME;
import static com.taobao.application.common.IPageListener.INTERACTIVE;
import static com.taobao.application.common.IPageListener.VISIBLE;

/* compiled from: AbstractDataCollector */
public class AbstractDataCollector<T> implements PageLoadCalculate.PageLoadCalculateListener, SimplePageLoadCalculate.PageLoadCalculateListener, Runnable {
    private float mPercent = 0.0f;

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
    private volatile boolean mResume = false;
    private final IPageListener mPageListener = ApmImpl.instance().pageListener();

    /* renamed from: b reason: collision with other field name */
    private IExecutor mSimplePageLoadCalculate;

    /* renamed from: b reason: collision with other field name */
    private final T mT;

    /* renamed from: b reason: collision with other field name */
    private boolean mUsable = false;
    private boolean c = false;
    private int count = 0;
    private boolean d = false;
    private final boolean mIsActivity;
    private final String pageName;

    protected AbstractDataCollector(T t) {
        if ((t instanceof Activity) || (t instanceof Fragment)) {
            this.mT = t;
            this.mIsActivity = t instanceof Activity;
            this.pageName = t.getClass().getName();
            this.mPageListener.onPageChanged(this.pageName, INIT_TIME, TimeUtils.currentTimeMillis());
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
        if (!this.mResume) {
            if (!DispatcherManager.isEmpty(this.mUsableVisibleDispatcher)) {
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
            this.mPageListener.onPageChanged(this.pageName, DRAW_START, TimeUtils.currentTimeMillis());
            this.mResume = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        stop();
        this.d = !this.mIsActivity;
    }

    /* access modifiers changed from: protected */
    public void usable(int usableChangeType, long timeMillis) {
        if (!this.mUsable && !this.d) {
            DataLoggerUtils.log("AbstractDataCollector", "usable", this.pageName);
            Logger.i("AbstractDataCollector", this.pageName, " usable", timeMillis);
            if (!DispatcherManager.isEmpty(this.mUsableVisibleDispatcher)) {
                this.mUsableVisibleDispatcher.usable(this.mT, 2, usableChangeType, timeMillis);
            }
            stop();
            this.mPageListener.onPageChanged(this.pageName, INTERACTIVE, timeMillis);
            this.mUsable = true;
        }
    }

    @Override
    public void visiblePercent(float percent) {
        Logger.i("AbstractDataCollector", "visiblePercent", percent, this.pageName);
        if (Math.abs(percent - this.mPercent) > 0.05f || percent > 0.8f) {
            if (!DispatcherManager.isEmpty(this.mUsableVisibleDispatcher)) {
                this.mUsableVisibleDispatcher.visiblePercent(this.mT, percent, TimeUtils.currentTimeMillis());
            }
            DataLoggerUtils.log("AbstractDataCollector", "visiblePercent", percent, this.pageName);
            if (percent > 0.8f) {
                display(TimeUtils.currentTimeMillis());
                run();
            }
            this.mPercent = percent;
        }
    }

    @Override
    public void pageDisplay(long j) {
        display(j);
    }

    private void display(long j) {
        if (!this.c && !this.d) {
            if (!DispatcherManager.isEmpty(this.mUsableVisibleDispatcher)) {
                Logger.i("AbstractDataCollector", this.pageName, " visible", j);
                this.mUsableVisibleDispatcher.display((Object) this.mT, 2, j);
            }
            this.mPageListener.onPageChanged(this.pageName, VISIBLE, j);
            stop();
            this.c = true;
        }
    }

    @Override
    public void pageUsable(int usableChangeType, long timeMillis) {
        usable(usableChangeType, timeMillis);
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
