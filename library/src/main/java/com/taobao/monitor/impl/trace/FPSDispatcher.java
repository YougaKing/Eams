package com.taobao.monitor.impl.trace;

public class FPSDispatcher extends AbsDispatcher<FPSDispatcher.FPSListener> {

    public FPSDispatcher() {
    }

    public void b(final int var1) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FPSDispatcher.FPSListener>() {
            @Override
            public void run(FPSListener fpsListener) {
                fpsListener.b(var1);
            }
        });
    }

    public void c(final int var1) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FPSDispatcher.FPSListener>() {
            @Override
            public void run(FPSListener fpsListener) {
                fpsListener.c(var1);
            }
        });
    }

    public interface FPSListener {
        void b(int var1);

        void c(int var1);
    }
}
