package com.taobao.monitor.impl.trace;

/* compiled from: ApplicationBackgroundChangedDispatcher */
public class d extends a<a> {

    /* compiled from: ApplicationBackgroundChangedDispatcher */
    public interface a {
        void c(int i, long j);
    }

    public void d(final int i, final long j) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.c(i, j);
            }
        });
    }
}
