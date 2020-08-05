package com.taobao.monitor.impl.trace;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* compiled from: ActivityEventDispatcher */
public class ActivityEventDispatcher extends AbsDispatcher<ActivityEventDispatcher.EventListener> {

    /* compiled from: ActivityEventDispatcher */
    public interface EventListener {
        void onKeyEvent(Activity activity, KeyEvent keyEvent, long j);

        void onMotionEvent(Activity activity, MotionEvent motionEvent, long j);
    }

    public void onKeyEvent(Activity activity, KeyEvent keyEvent, long j) {
        final Activity activity2 = activity;
        final KeyEvent keyEvent2 = keyEvent;
        final long j2 = j;
        dispatchRunnable(new DispatcherRunnable<EventListener>() {
            /* renamed from: a */
            public void run(EventListener aVar) {
                aVar.onKeyEvent(activity2, keyEvent2, j2);
            }
        });
    }

    public void onMotionEvent(Activity activity, MotionEvent motionEvent, long j) {
        final Activity activity2 = activity;
        final MotionEvent motionEvent2 = motionEvent;
        final long j2 = j;
        dispatchRunnable(new DispatcherRunnable<EventListener>() {
            /* renamed from: a */
            public void run(EventListener aVar) {
                aVar.onMotionEvent(activity2, motionEvent2, j2);
            }
        });
    }
}
