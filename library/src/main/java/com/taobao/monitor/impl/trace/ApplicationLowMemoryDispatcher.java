package com.taobao.monitor.impl.trace;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;

import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.event.ReceiverLowMemoryEvent;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.DataLoggerUtils;

public class ApplicationLowMemoryDispatcher extends AbsDispatcher<ApplicationLowMemoryDispatcher.LowMemoryListener> implements ComponentCallbacks {
    public ApplicationLowMemoryDispatcher() {
        Global.instance().context().registerComponentCallbacks(this);
    }

    public void dispatchOnLowMemory() {
        this.dispatchRunnable(new DispatcherRunnable<ApplicationLowMemoryDispatcher.LowMemoryListener>() {
            @Override
            public void run(LowMemoryListener var1) {
                var1.onLowMemory();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration var1) {
    }

    @Override
    public void onLowMemory() {
        DataLoggerUtils.log("ApplicationLowMemory", "onLowMemory");
        this.dispatchOnLowMemory();
        ReceiverLowMemoryEvent var1 = new ReceiverLowMemoryEvent();
        var1.level = 1.0F;
        DumpManager.getInstance().append(var1);
    }

    public interface LowMemoryListener {
        void onLowMemory();
    }
}