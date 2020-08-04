package com.taobao.monitor.impl.data.gc;


import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.ApplicationGCDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.IDispatcher;

public class GCSignalSender {

    private static SignalSenderRunnable runnable = new SignalSenderRunnable();

    static void postRunnable() {
        Global.instance().handler().post(runnable);
    }

    private static class SignalSenderRunnable implements Runnable {
        private SignalSenderRunnable() {
        }

        public void run() {
            Global.instance().handler().removeCallbacks(GCSignalSender.runnable);
            IDispatcher var1 = DispatcherManager.getDispatcher("APPLICATION_GC_DISPATCHER");
            if (var1 instanceof ApplicationGCDispatcher) {
                ((ApplicationGCDispatcher) var1).dispatchGc();
            }

            DataLoggerUtils.log("gc");
        }
    }
}
