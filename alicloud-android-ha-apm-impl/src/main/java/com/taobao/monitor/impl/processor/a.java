package com.taobao.monitor.impl.processor;

import com.taobao.monitor.impl.trace.IDispatcher;

/* compiled from: AbsProcessor */
public abstract class a implements IProcessor {
    private com.taobao.monitor.impl.processor.IProcessor.a a;
    private com.taobao.monitor.impl.common.a b = com.taobao.monitor.impl.common.a.a();
    private volatile boolean d = false;

    protected a(boolean z) {
    }

    /* access modifiers changed from: protected */
    public IDispatcher a(String str) {
        com.taobao.monitor.impl.common.a aVar = this.b;
        return com.taobao.monitor.impl.common.a.a(str);
    }

    public void a(com.taobao.monitor.impl.processor.IProcessor.a aVar) {
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    public void n() {
        if (this.a != null) {
            this.a.a(this);
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        if (!this.d) {
            this.d = true;
            if (this.a != null) {
                this.a.b(this);
            }
        }
    }
}
