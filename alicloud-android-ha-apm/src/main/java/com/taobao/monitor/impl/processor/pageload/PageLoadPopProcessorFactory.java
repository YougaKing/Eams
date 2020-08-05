package com.taobao.monitor.impl.processor.pageload;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: PageLoadPopProcessorFactory */
class PageLoadPopProcessorFactory implements IProcessorFactory<PageLoadPopProcessor> {
    PageLoadPopProcessorFactory() {
    }

    /* renamed from: a */
    public PageLoadPopProcessor createProcessor() {
        if (DynamicConstants.needPageLoadPop) {
            return new PageLoadPopProcessor();
        }
        return null;
    }
}
