package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.trace.DispatcherManager;

/* compiled from: GCCollector */
public class GCCollector {
    public void execute() {
        GCSwitcher dVar = new GCSwitcher();
        DispatcherManager.getDispatcher("APPLICATION_GC_DISPATCHER").addListener(dVar);
        dVar.open();
    }
}
