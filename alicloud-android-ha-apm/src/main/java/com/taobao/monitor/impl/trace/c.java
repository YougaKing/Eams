package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.os.Bundle;

/* compiled from: ActivityLifeCycleDispatcher */
public class c extends a<a> {

    /* compiled from: ActivityLifeCycleDispatcher */
    public interface a {
        void a(Activity activity, long j);

        void a(Activity activity, Bundle bundle, long j);

        void b(Activity activity, long j);

        void c(Activity activity, long j);

        void d(Activity activity, long j);

        void e(Activity activity, long j);
    }

    public void a(Activity activity, Bundle bundle, long j) {
        final Activity activity2 = activity;
        final Bundle bundle2 = bundle;
        final long j2 = j;
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.a(activity2, bundle2, j2);
            }
        });
    }

    public void a(final Activity activity, final long j) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.a(activity, j);
            }
        });
    }

    public void b(final Activity activity, final long j) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.b(activity, j);
            }
        });
    }

    public void c(final Activity activity, final long j) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.c(activity, j);
            }
        });
    }

    public void d(final Activity activity, final long j) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.d(activity, j);
            }
        });
    }

    public void e(final Activity activity, final long j) {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.e(activity, j);
            }
        });
    }
}
