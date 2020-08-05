package com.taobao.monitor.impl.data.a;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window.Callback;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* compiled from: WindowCallbackProxy */
class d implements InvocationHandler {
    private final Callback a;

    /* renamed from: a reason: collision with other field name */
    final a f31a;

    /* compiled from: WindowCallbackProxy */
    public interface a {
        void a(KeyEvent keyEvent);

        void a(MotionEvent motionEvent);
    }

    d(Callback callback, a aVar) {
        this.a = callback;
        this.f31a = aVar;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        String name = method.getName();
        if ("dispatchTouchEvent".equals(name)) {
            if (!(this.f31a == null || objArr == null || objArr.length < 1)) {
                MotionEvent motionEvent = objArr[0];
                if (motionEvent instanceof MotionEvent) {
                    this.f31a.a(motionEvent);
                }
            }
        } else if ("dispatchKeyEvent".equals(name) && this.f31a != null && objArr != null && objArr.length >= 1) {
            KeyEvent keyEvent = objArr[0];
            if (keyEvent instanceof KeyEvent) {
                this.f31a.a(keyEvent);
            }
        }
        return method.invoke(this.a, objArr);
    }
}
