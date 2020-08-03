package com.taobao.application.common.impl;

import com.taobao.application.common.IAppLaunchListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppLaunchListenerGroup implements IAppLaunchListener, IListenerGroup<IAppLaunchListener> {
    private final List<IAppLaunchListener> launchListeners = new ArrayList<>(2);

    AppLaunchListenerGroup() {
    }

    @Override
    public void addListener(final IAppLaunchListener var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.postRunnable(new Runnable() {
                public void run() {
                    if (!AppLaunchListenerGroup.this.launchListeners.contains(var1)) {
                        AppLaunchListenerGroup.this.launchListeners.add(var1);
                    }

                }
            });
        }
    }

    @Override
    public void removeListener(final IAppLaunchListener var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.postRunnable(new Runnable() {
                public void run() {
                    AppLaunchListenerGroup.this.launchListeners.remove(var1);
                }
            });
        }
    }

    public void onLaunchChanged(final int var1, final int var2) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = AppLaunchListenerGroup.this.launchListeners.iterator();

                while (var1x.hasNext()) {
                    IAppLaunchListener var2x = (IAppLaunchListener) var1x.next();
                    var2x.onLaunchChanged(var1, var2);
                }

            }
        });
    }

    private void postRunnable(Runnable var1) {
        ApmImpl.instance().postRunnable(var1);
    }
}
