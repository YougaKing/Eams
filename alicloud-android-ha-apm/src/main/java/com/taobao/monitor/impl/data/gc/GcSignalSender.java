package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ApplicationGcDispatcher;

/* compiled from: GCSignalSender */
class GcSignalSender {
    /* access modifiers changed from: private */
    public static SenderRunnable runnable = new SenderRunnable();

    /* compiled from: GCSignalSender */
    private static class SenderRunnable implements Runnable {
        private SenderRunnable() {
        }

        @Override
        public void run() {
            Global.instance().handler().removeCallbacks(GcSignalSender.runnable);
            IDispatcher a = APMContext.getDispatcher("APPLICATION_GC_DISPATCHER");
            if (a instanceof ApplicationGcDispatcher) {
                ((ApplicationGcDispatcher) a).gc();
            }
            DataLoggerUtils.log("gc");
        }
    }

    static void runGcRunnable() {
        Global.instance().handler().post(runnable);
    }
}
