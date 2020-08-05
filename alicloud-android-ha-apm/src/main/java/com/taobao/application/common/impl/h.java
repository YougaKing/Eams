package com.taobao.application.common.impl;

import com.taobao.application.common.IPageListener;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: PageListenerGroup */
class h implements IPageListener, f<IPageListener> {
    /* access modifiers changed from: private */
    public ArrayList<IPageListener> a = new ArrayList<>();

    h() {
    }

    public void onPageChanged(String str, int i, long j) {
        final String str2 = str;
        final int i2 = i;
        final long j2 = j;
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = h.this.a.iterator();
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
                if (!h.this.a.contains(iPageListener)) {
                    h.this.a.add(iPageListener);
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
                h.this.a.remove(iPageListener);
            }
        });
    }

    private void a(Runnable runnable) {
        b.a().b(runnable);
    }
}
