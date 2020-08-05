package com.taobao.monitor.impl.trace;

import com.taobao.monitor.impl.data.h;

/* compiled from: UsableVisibleDispatcher */
public class o<T> extends a<h> implements h {
    public void a(Object obj, int i, long j) {
        final Object obj2 = obj;
        final int i2 = i;
        final long j2 = j;
        a((C0003a<LISTENER>) new C0003a<h>() {
            /* renamed from: a */
            public void c(h hVar) {
                hVar.a(obj2, i2, j2);
            }
        });
    }

    public void a(Object obj, int i, int i2, long j) {
        final Object obj2 = obj;
        final int i3 = i;
        final int i4 = i2;
        final long j2 = j;
        a((C0003a<LISTENER>) new C0003a<h>() {
            /* renamed from: a */
            public void c(h hVar) {
                hVar.a(obj2, i3, i4, j2);
            }
        });
    }

    public void a(final Object obj, final long j) {
        a((C0003a<LISTENER>) new C0003a<h>() {
            /* renamed from: a */
            public void c(h hVar) {
                hVar.a(obj, j);
            }
        });
    }

    public void a(Object obj, float f, long j) {
        final Object obj2 = obj;
        final float f2 = f;
        final long j2 = j;
        a((C0003a<LISTENER>) new C0003a<h>() {
            /* renamed from: a */
            public void c(h hVar) {
                hVar.a(obj2, f2, j2);
            }
        });
    }
}
