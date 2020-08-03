package com.taobao.monitor.impl.trace;

public class ApplicationGCDispatcher extends AbsDispatcher<ApplicationGCDispatcher.GCListener> {

    public ApplicationGCDispatcher() {
    }

    public void dispatchGc() {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<ApplicationGCDispatcher.GCListener>() {
            @Override
            public void run(GCListener var1) {
                var1.gc();
            }
        });
    }

    public interface GCListener {
        void gc();
    }
}
