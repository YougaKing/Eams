package com.taobao.monitor.impl.processor.fragmentload;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: FragmentPopProcessorFactory */
class FragmentPopProcessorFactory implements IProcessorFactory<FragmentPopProcessor> {
    FragmentPopProcessorFactory() {
    }

    /* renamed from: a */
    public FragmentPopProcessor createProcessor() {
        if (DynamicConstants.needFragmentPop) {
            return new FragmentPopProcessor();
        }
        return null;
    }
}
