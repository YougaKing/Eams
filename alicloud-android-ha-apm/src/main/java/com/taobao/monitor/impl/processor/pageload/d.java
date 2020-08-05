package com.taobao.monitor.impl.processor.pageload;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: PageLoadProcessorFactory */
class d implements IProcessorFactory<c> {
    d() {
    }

    /* renamed from: a */
    public c createProcessor() {
        if (DynamicConstants.needPageLoad) {
            return new c();
        }
        return null;
    }
}
