package com.taobao.monitor.impl.data;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.taobao.monitor.impl.util.TimeUtils;

public abstract class AbsWebView implements IWebView {
    private int hashCode = 0;
    /* access modifiers changed from: private */
    public int progress = 0;
    /* access modifiers changed from: private */
    public long progressEndTime;
    private long startTime;

    public abstract int getProgress(View view);

    public abstract boolean isWebView(View view);

    public boolean isWebViewLoadFinished(final View view) {
        if (view.hashCode() != this.hashCode) {
            this.hashCode = view.hashCode();
            this.progress = 0;
            this.startTime = TimeUtils.currentTimeMillis();
            this.progressEndTime = 0;
            return false;
        }
        if (this.progress != 100) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    try {
                        AbsWebView.this.progress = AbsWebView.this.getProgress(view);
                        if (AbsWebView.this.progress == 100) {
                            AbsWebView.this.progressEndTime = TimeUtils.currentTimeMillis();
                        }
                    } catch (Exception e) {
                        AbsWebView.this.progress = 0;
                    }
                }
            });
        }
        long currentTimeMillis = TimeUtils.currentTimeMillis();
        if (this.progressEndTime == 0 || ((double) (currentTimeMillis - this.progressEndTime)) <= 1.5d * ((double) (this.progressEndTime - this.startTime)) || this.progress != 100) {
            return false;
        }
        return true;
    }
}
