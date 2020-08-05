package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* compiled from: ActivityEventDispatcher */
public class b extends a<a> {

    /* compiled from: ActivityEventDispatcher */
    public interface a {
        void a(Activity activity, KeyEvent keyEvent, long j);

        void a(Activity activity, MotionEvent motionEvent, long j);
    }

    public void a(Activity activity, KeyEvent keyEvent, long j) {
        final Activity activity2 = activity;
        final KeyEvent keyEvent2 = keyEvent;
        final long j2 = j;
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.a(activity2, keyEvent2, j2);
            }
        });
    }

    public void a(Activity activity, MotionEvent motionEvent, long j) {
        final Activity activity2 = activity;
        final MotionEvent motionEvent2 = motionEvent;
        final long j2 = j;
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.a(activity2, motionEvent2, j2);
            }
        });
    }
}
