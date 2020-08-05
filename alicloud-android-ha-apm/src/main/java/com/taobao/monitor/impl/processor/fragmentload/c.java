package com.taobao.monitor.impl.processor.fragmentload;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: FragmentPopProcessorFactory */
class c implements IProcessorFactory<b> {
    c() {
    }

    /* renamed from: a */
    public b createProcessor() {
        if (DynamicConstants.needFragmentPop) {
            return new b();
        }
        return null;
    }
}
