package com.taobao.application.common.impl;

import com.taobao.application.common.IApmEventListener;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ApmEventListenerGroup */
public class a implements IApmEventListener, f<IApmEventListener> {
    /* access modifiers changed from: private */
    public final ArrayList<IApmEventListener> a = new ArrayList<>();

    public void onEvent(final int i) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = a.this.a.iterator();
                while (it.hasNext()) {
                    ((IApmEventListener) it.next()).onEvent(i);
                }
            }
        });
    }

    /* renamed from: a */
    public void addListener(final IApmEventListener iApmEventListener) {
        if (iApmEventListener == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                if (!a.this.a.contains(iApmEventListener)) {
                    a.this.a.add(iApmEventListener);
                }
            }
        });
    }

    /* renamed from: b */
    public void removeListener(final IApmEventListener iApmEventListener) {
        if (iApmEventListener == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                a.this.a.remove(iApmEventListener);
            }
        });
    }

    private void a(Runnable runnable) {
        ApmImpl.instance().b(runnable);
    }
}
