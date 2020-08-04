package com.taobao.monitor.impl.processor.launcher;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: LauncherProcessorFactory */
class c implements IProcessorFactory<b> {
    c() {
    }

    /* renamed from: a */
    public b createProcessor() {
        if (DynamicConstants.needLauncher) {
            return new b();
        }
        return null;
    }
}
