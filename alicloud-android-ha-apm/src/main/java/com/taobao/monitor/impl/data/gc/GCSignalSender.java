package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;

/* compiled from: GCSignalSender */
class GCSignalSender {
    /* access modifiers changed from: private */
    public static SenderRunnable runnable = new SenderRunnable();

    /* compiled from: GCSignalSender */
    private static class SenderRunnable implements Runnable {
        private SenderRunnable() {
        }

        public void run() {
            Global.instance().handler().removeCallbacks(GCSignalSender.runnable);
            IDispatcher a = APMContext.getDispatcher("APPLICATION_GC_DISPATCHER");
            if (a instanceof ApplicationGCDispatcher) {
                ((ApplicationGCDispatcher) a).gc();
            }
            DataLoggerUtils.log("gc");
        }
    }

    static void k() {
        Global.instance().handler().post(runnable);
    }
}
