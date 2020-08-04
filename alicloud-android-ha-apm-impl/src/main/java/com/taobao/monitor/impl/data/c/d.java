package com.taobao.monitor.impl.data.c;

import com.taobao.monitor.impl.trace.d.a;
import com.taobao.monitor.impl.trace.e;

/* compiled from: GCSwitcher */
public class d implements a, e.a {
    private volatile boolean m = false;

    public void gc() {
        if (this.m) {
            l();
        }
    }

    public void open() {
        if (!this.m) {
            this.m = true;
            l();
        }
    }

    public void close() {
        this.m = false;
    }

    public void c(int i, long j) {
        if (i == 0) {
            open();
        } else {
            close();
        }
    }

    private void l() {
        new b();
    }
}
