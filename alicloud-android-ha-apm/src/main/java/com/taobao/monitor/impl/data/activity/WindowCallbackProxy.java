package com.taobao.monitor.impl.data.activity;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window.Callback;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* compiled from: WindowCallbackProxy */
class WindowCallbackProxy implements InvocationHandler {
    private final Callback mCallback;

    /* renamed from: a reason: collision with other field name */
    final CallbackListener mCallbackListener;

    /* compiled from: WindowCallbackProxy */
    public interface CallbackListener {
        void dispatchKeyEvent(KeyEvent keyEvent);

        void dispatchTouchEvent(MotionEvent motionEvent);
    }

    WindowCallbackProxy(Callback callback, CallbackListener aVar) {
        this.mCallback = callback;
        this.mCallbackListener = aVar;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        String name = method.getName();
        if ("dispatchTouchEvent".equals(name)) {
            if (!(this.mCallbackListener == null || objArr == null || objArr.length < 1)) {
                Object motionEvent = objArr[0];
                if (motionEvent instanceof MotionEvent) {
                    this.mCallbackListener.dispatchTouchEvent((MotionEvent) motionEvent);
                }
            }
        } else if ("dispatchKeyEvent".equals(name) && this.mCallbackListener != null && objArr != null && objArr.length >= 1) {
            Object keyEvent = objArr[0];
            if (keyEvent instanceof KeyEvent) {
                this.mCallbackListener.dispatchKeyEvent((KeyEvent) keyEvent);
            }
        }
        return method.invoke(this.mCallback, objArr);
    }
}
