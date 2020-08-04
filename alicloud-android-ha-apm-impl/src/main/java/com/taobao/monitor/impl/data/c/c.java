package com.taobao.monitor.impl.data.c;

import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.e;

/* compiled from: GCSignalSender */
class c {
    /* access modifiers changed from: private */
    public static a a = new a();

    /* compiled from: GCSignalSender */
    private static class a implements Runnable {
        private a() {
        }

        public void run() {
            Global.instance().handler().removeCallbacks(c.a);
            IDispatcher a = com.taobao.monitor.impl.common.a.a("APPLICATION_GC_DISPATCHER");
            if (a instanceof e) {
                ((e) a).r();
            }
            DataLoggerUtils.log("gc", new Object[0]);
        }
    }

    static void k() {
        Global.instance().handler().post(a);
    }
}
