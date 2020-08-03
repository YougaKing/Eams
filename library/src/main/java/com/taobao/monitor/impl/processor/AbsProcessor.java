package com.taobao.monitor.impl.processor;

import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.trace.IDispatcher;

public class AbsProcessor implements IProcessor {
    private APMContext aPMContext = APMContext.instance();
    private com.taobao.monitor.impl.processor.IProcessor.a a;
    private volatile boolean d = false;

    protected AbsProcessor(boolean var1) {
    }

    protected IDispatcher getDispatcher(String key) {
        APMContext aPMContext = this.aPMContext;
        return APMContext.getDispatcher(key);
    }

    public void a(com.taobao.monitor.impl.processor.IProcessor.a var1) {
        this.a = var1;
    }

    protected void n() {
        if (this.a != null) {
            this.a.a(this);
        }

    }

    protected void o() {
        if (!this.d) {
            this.d = true;
            if (this.a != null) {
                this.a.b(this);
            }
        }
    }
}