package com.taobao.monitor.impl.trace;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;

import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.ReceiverLowMemoryEvent;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;

/* compiled from: ApplicationLowMemoryDispatcher */
public class ApplicationLowMemoryDispatcher extends AbsDispatcher<ApplicationLowMemoryDispatcher.LowMemoryListener> implements ComponentCallbacks {

    /* compiled from: ApplicationLowMemoryDispatcher */
    public interface LowMemoryListener {
        void onLowMemory();
    }

    public ApplicationLowMemoryDispatcher() {
        Global.instance().context().registerComponentCallbacks(this);
    }

    public void dispatchOnLowMemory() {
        dispatchRunnable(new DispatcherRunnable<LowMemoryListener>() {
            /* renamed from: a */
            public void run(LowMemoryListener aVar) {
                aVar.onLowMemory();
            }
        });
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override
    public void onLowMemory() {
        DataLoggerUtils.log("ApplicationLowMemory", "onLowMemory");
        dispatchOnLowMemory();
        ReceiverLowMemoryEvent receiverLowMemoryEvent = new ReceiverLowMemoryEvent();
        receiverLowMemoryEvent.level = 1.0f;
        DumpManager.getInstance().append(receiverLowMemoryEvent);
    }
}
