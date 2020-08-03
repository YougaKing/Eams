package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;


public class ActivityEventDispatcher extends AbsDispatcher<ActivityEventDispatcher.EventListener> {
    public ActivityEventDispatcher() {
    }

    public void a(final Activity var1, final KeyEvent var2, final long var3) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityEventDispatcher.EventListener>() {
            @Override
            public void run(EventListener eventListener) {
                eventListener.a(var1, var2, var3);
            }
        });
    }

    public void a(final Activity var1, final MotionEvent var2, final long var3) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ActivityEventDispatcher.EventListener>() {
            @Override
            public void run(EventListener eventListener) {
                eventListener.a(var1, var2, var3);
            }
        });
    }

    public interface EventListener {
        void a(Activity var1, KeyEvent var2, long var3);

        void a(Activity var1, MotionEvent var2, long var3);
    }
}
