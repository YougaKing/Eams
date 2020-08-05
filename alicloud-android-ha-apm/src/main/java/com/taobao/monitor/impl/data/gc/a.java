package com.taobao.monitor.impl.data.gc;

import com.taobao.monitor.impl.trace.DispatcherManager;

/* compiled from: GCCollector */
public class a {
    public void execute() {
        d dVar = new d();
        DispatcherManager.getDispatcher("APPLICATION_GC_DISPATCHER").addListener(dVar);
        dVar.open();
    }
}
