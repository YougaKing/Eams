package com.taobao.monitor.impl.processor;

import com.taobao.monitor.impl.common.APMContext;
import com.taobao.monitor.impl.trace.IDispatcher;

/* compiled from: AbsProcessor */
public abstract class AbsProcessor implements IProcessor {
    private ProcessorCallback mProcessorCallback;
    private volatile boolean mEnd = false;

    protected AbsProcessor(boolean z) {
    }

    /* access modifiers changed from: protected */
    public <T> IDispatcher<T> getDispatcher(String key) {
        return APMContext.getDispatcher(key);
    }

    public void setProcessorCallback(ProcessorCallback processorCallback) {
        this.mProcessorCallback = processorCallback;
    }

    /* access modifiers changed from: protected */
    public void procedureBegin() {
        if (this.mProcessorCallback != null) {
            this.mProcessorCallback.onProcedureBegin(this);
        }
    }

    /* access modifiers changed from: protected */
    public void procedureEnd() {
        if (!this.mEnd) {
            this.mEnd = true;
            if (this.mProcessorCallback != null) {
                this.mProcessorCallback.onProcedureEnd(this);
            }
        }
    }
}
