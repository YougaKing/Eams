package com.taobao.application.common.impl;

import com.taobao.application.common.IApmEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ApmEventListenerGroup implements IApmEventListener, IListenerGroup<IApmEventListener> {
    private final ArrayList<IApmEventListener> eventListeners = new ArrayList<>();

    public ApmEventListenerGroup() {
    }

    public void onEvent(final int var1) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApmEventListenerGroup.this.eventListeners.iterator();

                while (var1x.hasNext()) {
                    IApmEventListener var2 = (IApmEventListener) var1x.next();
                    var2.onEvent(var1);
                }

            }
        });
    }

    @Override
    public void addListener(final IApmEventListener var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.postRunnable(new Runnable() {
                public void run() {
                    if (!ApmEventListenerGroup.this.eventListeners.contains(var1)) {
                        ApmEventListenerGroup.this.eventListeners.add(var1);
                    }

                }
            });
        }
    }

    @Override
    public void removeListener(final IApmEventListener var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.postRunnable(new Runnable() {
                public void run() {
                    ApmEventListenerGroup.this.eventListeners.remove(var1);
                }
            });
        }
    }

    private void postRunnable(Runnable var1) {
        ApmImpl.instance().postRunnable(var1);
    }
}
