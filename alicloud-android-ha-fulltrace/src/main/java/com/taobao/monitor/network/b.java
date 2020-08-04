package com.taobao.monitor.network;

/* compiled from: NetworkSenderProxy */
public class b implements a {
    private a a;

    /* compiled from: NetworkSenderProxy */
    private static class a {
        static final b b = new b();
    }

    private b() {
        this.a = new a() {
            public void b(String str, String str2) {
            }
        };
    }

    public static b a() {
        return a.b;
    }

    public b a(a aVar) {
        this.a = aVar;
        return this;
    }

    public void b(String str, String str2) {
        if (this.a != null) {
            this.a.b(str, str2);
        }
    }
}
