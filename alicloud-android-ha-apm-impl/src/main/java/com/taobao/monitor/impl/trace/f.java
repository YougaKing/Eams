package com.taobao.monitor.impl.trace;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.ReceiverLowMemoryEvent;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;

/* compiled from: ApplicationLowMemoryDispatcher */
public class f extends a<a> implements ComponentCallbacks {

    /* compiled from: ApplicationLowMemoryDispatcher */
    public interface a {
        void onLowMemory();
    }

    public f() {
        Global.instance().context().registerComponentCallbacks(this);
    }

    public void s() {
        a((C0003a<LISTENER>) new C0003a<a>() {
            /* renamed from: a */
            public void c(a aVar) {
                aVar.onLowMemory();
            }
        });
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onLowMemory() {
        DataLoggerUtils.log("ApplicationLowMemory", "onLowMemory");
        s();
        ReceiverLowMemoryEvent receiverLowMemoryEvent = new ReceiverLowMemoryEvent();
        receiverLowMemoryEvent.level = 1.0f;
        DumpManager.getInstance().append(receiverLowMemoryEvent);
    }
}
