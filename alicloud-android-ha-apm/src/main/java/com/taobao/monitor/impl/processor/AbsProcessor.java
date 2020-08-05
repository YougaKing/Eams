package com.taobao.monitor.impl.processor;

import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.trace.IDispatcher;

/* compiled from: AbsProcessor */
public abstract class AbsProcessor implements IProcessor {
    private com.taobao.monitor.impl.processor.IProcessor.a a;
    private APMContext mAPMContext = APMContext.instance();
    private volatile boolean d = false;

    protected AbsProcessor(boolean z) {
    }

    /* access modifiers changed from: protected */
    public IDispatcher getDispatcher(String str) {
        APMContext aVar = this.mAPMContext;
        return APMContext.getDispatcher(str);
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
