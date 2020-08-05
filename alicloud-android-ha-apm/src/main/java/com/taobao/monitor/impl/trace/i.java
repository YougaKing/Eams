package com.taobao.monitor.impl.trace;

/* compiled from: FPSDispatcher */
public class i extends a<a> {

    /* compiled from: FPSDispatcher */
    public interface a {
        void b(int i);

        void c(int i);
    }

    public void b(final int i) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.b(i);
            }
        });
    }

    public void c(final int i) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.c(i);
            }
        });
    }
}
