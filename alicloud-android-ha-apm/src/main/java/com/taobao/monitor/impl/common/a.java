package com.taobao.monitor.impl.common;

import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.g;

/* compiled from: APMContext */
public class a {

    /* renamed from: com.taobao.monitor.impl.common.a$a reason: collision with other inner class name */
    /* compiled from: APMContext */
    private static class C0000a {
        /* access modifiers changed from: private */
        public static final a a = new a();
    }

    private a() {
    }

    public static a a() {
        return C0000a.a;
    }

    public static IDispatcher a(String str) {
        return g.a(str);
    }
}
