package com.taobao.application.common.impl;

import com.taobao.application.common.IPageListener;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: PageListenerGroup */
class PageListenerGroup implements IPageListener, IListenerGroup<IPageListener> {
    /* access modifiers changed from: private */
    public ArrayList<IPageListener> a = new ArrayList<>();

    PageListenerGroup() {
    }

    public void onPageChanged(String pageName, int pageStatus, long timeMillis) {
        final String str2 = pageName;
        final int i2 = pageStatus;
        final long j2 = timeMillis;
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = PageListenerGroup.this.a.iterator();
                while (it.hasNext()) {
                    ((IPageListener) it.next()).onPageChanged(str2, i2, j2);
                }
            }
        });
    }

    /* renamed from: a */
    public void addListener(final IPageListener iPageListener) {
        if (iPageListener == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                if (!PageListenerGroup.this.a.contains(iPageListener)) {
                    PageListenerGroup.this.a.add(iPageListener);
                }
            }
        });
    }

    /* renamed from: b */
    public void removeListener(final IPageListener iPageListener) {
        if (iPageListener == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                PageListenerGroup.this.a.remove(iPageListener);
            }
        });
    }

    private void a(Runnable runnable) {
        ApmImpl.instance().postRunnable(runnable);
    }
}
