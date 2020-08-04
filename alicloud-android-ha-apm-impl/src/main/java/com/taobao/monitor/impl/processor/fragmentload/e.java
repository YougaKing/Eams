package com.taobao.monitor.impl.processor.fragmentload;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: FragmentProcessorFactory */
class e implements IProcessorFactory<d> {
    e() {
    }

    /* renamed from: a */
    public d createProcessor() {
        if (DynamicConstants.needFragment) {
            return new d();
        }
        return null;
    }
}
