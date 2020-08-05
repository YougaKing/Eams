package com.taobao.monitor.impl.data;

/* compiled from: OnUsableVisibleListener */
public interface OnUsableVisibleListener<T> {
    void visiblePercent(T t, float f, long j);

    void usable(T t, int i, int i2, long j);

    void display(T t, int i, long j);

    void onResume(T t, long j);
}
