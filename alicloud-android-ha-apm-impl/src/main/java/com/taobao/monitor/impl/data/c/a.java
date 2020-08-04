package com.taobao.monitor.impl.data.c;

import com.taobao.monitor.impl.trace.g;

/* compiled from: GCCollector */
public class a {
    public void execute() {
        d dVar = new d();
        g.a("APPLICATION_GC_DISPATCHER").addListener(dVar);
        dVar.open();
    }
}
