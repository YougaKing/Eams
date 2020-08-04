package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.support.v4.app.Fragment;

/* compiled from: FragmentFunctionDispatcher */
public class j extends a<k> implements k {
    public static final j b = new j();

    public void a(Activity activity, Fragment fragment, String str, long j) {
        final Activity activity2 = activity;
        final Fragment fragment2 = fragment;
        final String str2 = str;
        final long j2 = j;
        a((C0003a<LISTENER>) new C0003a<k>() {
            /* renamed from: a */
            public void c(k kVar) {
                kVar.a(activity2, fragment2, str2, j2);
            }
        });
    }
}
