package com.taobao.application.common.impl;

import com.taobao.application.common.IAppLaunchListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AppLaunchListenerGroup */
class AppLaunchListenerGroup implements IAppLaunchListener, IListenerGroup<IAppLaunchListener> {
    /* access modifiers changed from: private */
    public final List<IAppLaunchListener> a = new ArrayList(2);

    AppLaunchListenerGroup() {
    }

    /* renamed from: a */
    public void addListener(final IAppLaunchListener iAppLaunchListener) {
        if (iAppLaunchListener == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                if (!AppLaunchListenerGroup.this.a.contains(iAppLaunchListener)) {
                    AppLaunchListenerGroup.this.a.add(iAppLaunchListener);
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
                AppLaunchListenerGroup.this.a.remove(iAppLaunchListener);
            }
        });
    }

    public void onLaunchChanged(final int i, final int i2) {
        a((Runnable) new Runnable() {
            public void run() {
                for (IAppLaunchListener onLaunchChanged : AppLaunchListenerGroup.this.a) {
                    onLaunchChanged.onLaunchChanged(i, i2);
                }
            }
        });
    }

    private void a(Runnable runnable) {
        ApmImpl.instance().b(runnable);
    }
}
