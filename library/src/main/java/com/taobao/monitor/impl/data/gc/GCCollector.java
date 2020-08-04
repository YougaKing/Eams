package com.taobao.monitor.impl.data.gc;


import com.taobao.monitor.impl.trace.DispatcherManager;

public class GCCollector {

    public void execute() {
        GCSwitcher gcSwitcher = new GCSwitcher();
        DispatcherManager.getDispatcher("APPLICATION_GC_DISPATCHER").addListener(gcSwitcher);
        gcSwitcher.open();
    }
}
