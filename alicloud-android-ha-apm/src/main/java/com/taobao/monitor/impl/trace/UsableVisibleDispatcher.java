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

    public void usable(Object obj, int i, int i2, long j) {
        final Object obj2 = obj;
        final int i3 = i;
        final int i4 = i2;
        final long j2 = j;
        dispatchRunnable(new DispatcherRunnable<OnUsableVisibleListener>() {
            /* renamed from: a */
            public void run(OnUsableVisibleListener hVar) {
                hVar.usable(obj2, i3, i4, j2);
            }
        });
    }

    public void onResume(final Object obj, final long j) {
        dispatchRunnable(new DispatcherRunnable<OnUsableVisibleListener>() {
            /* renamed from: a */
            public void run(OnUsableVisibleListener hVar) {
                hVar.onResume(obj, j);
            }
        });
    }

    public void visiblePercent(Object obj, float percent, long timeMillis) {
        final Object obj2 = obj;
        final float f2 = percent;
        final long j2 = timeMillis;
        dispatchRunnable(new DispatcherRunnable<OnUsableVisibleListener>() {
            /* renamed from: a */
            public void run(OnUsableVisibleListener hVar) {
                hVar.visiblePercent(obj2, f2, j2);
            }
        });
    }
}
