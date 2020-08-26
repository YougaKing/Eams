package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.trace.DispatcherManager;

/* compiled from: GCCollector */
public class GcCollector {

    public void execute() {
        GcSwitcher gcSwitcher = new GcSwitcher();
        DispatcherManager.getDispatcher("APPLICATION_GC_DISPATCHER").addListener(gcSwitcher);
        gcSwitcher.open();
    }
}
