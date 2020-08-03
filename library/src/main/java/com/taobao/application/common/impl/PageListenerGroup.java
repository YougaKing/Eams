package com.taobao.application.common.impl;

import com.taobao.application.common.IPageListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PageListenerGroup implements IPageListener, IListenerGroup<IPageListener> {
    private ArrayList<IPageListener> pageListeners = new ArrayList();

    PageListenerGroup() {
    }

    public void onPageChanged(final String var1, final int var2, final long var3) {
        this.a(new Runnable() {
            public void run() {
                Iterator var1x = PageListenerGroup.this.pageListeners.iterator();

                while (var1x.hasNext()) {
                    IPageListener var2x = (IPageListener) var1x.next();
                    var2x.onPageChanged(var1, var2, var3);
                }

            }
        });
    }

    @Override
    public void addListener(final IPageListener var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.a(new Runnable() {
                public void run() {
                    if (!PageListenerGroup.this.pageListeners.contains(var1)) {
                        PageListenerGroup.this.pageListeners.add(var1);
                    }

                }
            });
        }
    }

    @Override
    public void removeListener(final IPageListener var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.a(new Runnable() {
                public void run() {
                    PageListenerGroup.this.pageListeners.remove(var1);
                }
            });
        }
    }

    private void a(Runnable var1) {
        ApmImpl.instance().postRunnable(var1);
    }
}
