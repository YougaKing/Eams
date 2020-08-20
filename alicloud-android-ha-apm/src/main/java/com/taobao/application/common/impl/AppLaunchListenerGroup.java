package com.taobao.application.common.impl;

import com.taobao.application.common.IAppLaunchListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AppLaunchListenerGroup */
class AppLaunchListenerGroup implements IAppLaunchListener, IListenerGroup<IAppLaunchListener> {
    /* access modifiers changed from: private */
    public final List<IAppLaunchListener> mAppLaunchListeners = new ArrayList<>(2);

    AppLaunchListenerGroup() {
    }

    @Override
    public void addListener(final IAppLaunchListener iAppLaunchListener) {
        if (iAppLaunchListener == null) {
            throw new IllegalArgumentException();
        }
        postRunnable((Runnable) new Runnable() {
            public void run() {
                if (!AppLaunchListenerGroup.this.mAppLaunchListeners.contains(iAppLaunchListener)) {
                    AppLaunchListenerGroup.this.mAppLaunchListeners.add(iAppLaunchListener);
                }
            }
        });
    }

    @Override
    public void removeListener(final IAppLaunchListener iAppLaunchListener) {
        if (iAppLaunchListener == null) {
            throw new IllegalArgumentException();
        }
        postRunnable((Runnable) new Runnable() {
            public void run() {
                AppLaunchListenerGroup.this.mAppLaunchListeners.remove(iAppLaunchListener);
            }
        });
    }

    @Override
    public void onLaunchChanged(final int i, final int i2) {
        postRunnable((Runnable) new Runnable() {
            public void run() {
                for (IAppLaunchListener onLaunchChanged : AppLaunchListenerGroup.this.mAppLaunchListeners) {
                    onLaunchChanged.onLaunchChanged(i, i2);
                }
            }
        });
    }

    private void postRunnable(Runnable runnable) {
        ApmImpl.instance().postRunnable(runnable);
    }
}
