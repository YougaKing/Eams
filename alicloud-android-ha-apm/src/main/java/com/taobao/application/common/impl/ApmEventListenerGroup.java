package com.taobao.application.common.impl;

import com.taobao.application.common.IApmEventListener;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ApmEventListenerGroup */
public class ApmEventListenerGroup implements IApmEventListener, IListenerGroup<IApmEventListener> {
    /* access modifiers changed from: private */
    public final ArrayList<IApmEventListener> a = new ArrayList<>();

    public void onApmEvent(final int apmEventType) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApmEventListenerGroup.this.a.iterator();
                while (it.hasNext()) {
                    ((IApmEventListener) it.next()).onApmEvent(apmEventType);
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
                if (!ApmEventListenerGroup.this.a.contains(iApmEventListener)) {
                    ApmEventListenerGroup.this.a.add(iApmEventListener);
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
                ApmEventListenerGroup.this.a.remove(iApmEventListener);
            }
        });
    }

    private void a(Runnable runnable) {
        ApmImpl.instance().postRunnable(runnable);
    }
}
