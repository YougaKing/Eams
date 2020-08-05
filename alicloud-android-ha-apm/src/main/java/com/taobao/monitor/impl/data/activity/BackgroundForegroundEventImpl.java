package com.taobao.monitor.impl.data.activity;

import com.taobao.application.common.IApmEventListener;
import com.taobao.application.common.data.AppLaunchHelper;
import com.taobao.application.common.data.d;

/* compiled from: BackgroundForegroundEventImpl */
class BackgroundForegroundEventImpl {
    private final d a = new d();
    private final IApmEventListener b = b.a().a();
    private final Runnable d = new Runnable() {
        public void run() {
            if (BackgroundForegroundEventImpl.a(BackgroundForegroundEventImpl.this)) {
                BackgroundForegroundEventImpl.a(BackgroundForegroundEventImpl.this).d(true);
            }
        }
    };
    private final Runnable e = new Runnable() {
        public void run() {
            if (BackgroundForegroundEventImpl.a(BackgroundForegroundEventImpl.this)) {
                BackgroundForegroundEventImpl.a(BackgroundForegroundEventImpl.this).onEvent(50);
            }
        }
    };
    private boolean l = false;
    private final AppLaunchHelper launchHelper = new AppLaunchHelper();

    BackgroundForegroundEventImpl() {
    }

    /* access modifiers changed from: 0000 */
    public void i() {
        this.l = false;
        this.a.c(false);
        this.a.d(false);
        this.b.onEvent(2);
        b.a().a().removeCallbacks(this.d);
        b.a().a().removeCallbacks(this.e);
    }

    /* access modifiers changed from: 0000 */
    public void j() {
        this.l = true;
        this.a.c(true);
        this.b.onEvent(1);
        b.a().a().postDelayed(this.d, 300000);
        b.a().a().postDelayed(this.e, 10000);
    }
}
