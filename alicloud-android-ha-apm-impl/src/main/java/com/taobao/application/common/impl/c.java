package com.taobao.application.common.impl;

import com.taobao.application.common.IAppLaunchListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AppLaunchListenerGroup */
class c implements IAppLaunchListener, f<IAppLaunchListener> {
    /* access modifiers changed from: private */
    public final List<IAppLaunchListener> a = new ArrayList(2);

    c() {
    }

    /* renamed from: a */
    public void addListener(final IAppLaunchListener iAppLaunchListener) {
        if (iAppLaunchListener == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                if (!c.this.a.contains(iAppLaunchListener)) {
                    c.this.a.add(iAppLaunchListener);
                }
            }
        });
    }

    /* renamed from: b */
    public void removeListener(final IAppLaunchListener iAppLaunchListener) {
        if (iAppLaunchListener == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                c.this.a.remove(iAppLaunchListener);
            }
        });
    }

    public void onLaunchChanged(final int i, final int i2) {
        a((Runnable) new Runnable() {
            public void run() {
                for (IAppLaunchListener onLaunchChanged : c.this.a) {
                    onLaunchChanged.onLaunchChanged(i, i2);
                }
            }
        });
    }

    private void a(Runnable runnable) {
        b.a().b(runnable);
    }
}
