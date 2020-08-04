package com.taobao.monitor.impl.processor.pageload;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: PageLoadPopProcessorFactory */
class b implements IProcessorFactory<a> {
    b() {
    }

    /* renamed from: a */
    public a createProcessor() {
        if (DynamicConstants.needPageLoadPop) {
            return new a();
        }
        return null;
    }
}
