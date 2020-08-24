package com.taobao.monitor.impl.trace;

import com.taobao.monitor.impl.data.OnUsableVisibleListener;

/* compiled from: UsableVisibleDispatcher */
public class UsableVisibleDispatcher<T> extends AbsDispatcher<OnUsableVisibleListener> implements OnUsableVisibleListener {
    public void display(Object obj, int i, long j) {
        final Object obj2 = obj;
        final int i2 = i;
        final long j2 = j;
        dispatchRunnable(new DispatcherRunnable<OnUsableVisibleListener>() {
            /* renamed from: a */
            public void run(OnUsableVisibleListener hVar) {
                hVar.display(obj2, i2, j2);
            }
        });
    }

    public void usable(final Object obj, final int i, final int usableChangeType, final long timeMillis) {
        dispatchRunnable(new DispatcherRunnable<OnUsableVisibleListener>() {
            /* renamed from: a */
            public void run(OnUsableVisibleListener hVar) {
                hVar.usable(obj, i, usableChangeType, timeMillis);
            }
        });
    }

    public void onResume(final Object obj, final long timeMillis) {
        dispatchRunnable(new DispatcherRunnable<OnUsableVisibleListener>() {
            /* renamed from: a */
            public void run(OnUsableVisibleListener hVar) {
                hVar.onResume(obj, timeMillis);
            }
        });
    }

    public void visiblePercent(final Object obj, final float percent, final long timeMillis) {
        dispatchRunnable(new DispatcherRunnable<OnUsableVisibleListener>() {
            /* renamed from: a */
            public void run(OnUsableVisibleListener hVar) {
                hVar.visiblePercent(obj, percent, timeMillis);
            }
        });
    }
}
