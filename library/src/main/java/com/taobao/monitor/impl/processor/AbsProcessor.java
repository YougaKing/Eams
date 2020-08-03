package com.taobao.monitor.impl.processor;

import com.taobao.monitor.impl.trace.IDispatcher;

public class AbsProcessor implements IProcessor {
    private com.taobao.monitor.impl.common.a b = com.taobao.monitor.impl.common.a.a();
    private com.taobao.monitor.impl.processor.IProcessor.a a;
    private volatile boolean d = false;

    protected AbsProcessor(boolean var1) {
    }

    protected IDispatcher a(String var1) {
        com.taobao.monitor.impl.common.a var10000 = this.b;
        return com.taobao.monitor.impl.common.a.a(var1);
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