package com.taobao.monitor.impl.trace;

import com.taobao.monitor.impl.data.OnUsableVisibleListener;

public class UsableVisibleDispatcher extends AbsDispatcher<OnUsableVisibleListener> implements OnUsableVisibleListener {
    public UsableVisibleDispatcher() {
    }

    public void a(final Object var1, final int var2, final long var3) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<OnUsableVisibleListener>() {
            @Override
            public void run(OnUsableVisibleListener onUsableVisibleListener) {
                onUsableVisibleListener.a(var1, var2, var3);
            }
        });
    }

    public void a(final Object var1, final int var2, final int var3, final long var4) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<OnUsableVisibleListener>() {
            @Override
            public void run(OnUsableVisibleListener onUsableVisibleListener) {
                onUsableVisibleListener.a(var1, var2, var3, var4);
            }
        });
    }

    public void a(final Object var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<OnUsableVisibleListener>() {
            @Override
            public void run(OnUsableVisibleListener onUsableVisibleListener) {
                onUsableVisibleListener.a(var1, var2);
            }
        });
    }

    public void a(final Object var1, final float var2, final long var3) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<OnUsableVisibleListener>() {
            @Override
            public void run(OnUsableVisibleListener onUsableVisibleListener) {
                onUsableVisibleListener.a(var1, var2, var3);
            }
        });
    }
}
