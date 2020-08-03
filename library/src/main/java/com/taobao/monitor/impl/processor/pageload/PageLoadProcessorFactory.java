package com.taobao.monitor.impl.processor.pageload;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: PageLoadProcessorFactory */
class PageLoadProcessorFactory implements IProcessorFactory<PageLoadProcessor> {
    PageLoadProcessorFactory() {
    }

    /* renamed from: a */
    public PageLoadProcessor createProcessor() {
        if (DynamicConstants.needPageLoad) {
            return new PageLoadProcessor();
        }
        return null;
    }
}
