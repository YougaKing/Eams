package com.taobao.monitor.impl.processor.launcher;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/* compiled from: LauncherProcessorFactory */
class LauncherProcessorFactory implements IProcessorFactory<LauncherProcessor> {
    LauncherProcessorFactory() {
    }

    /* renamed from: a */
    public LauncherProcessor createProcessor() {
        if (DynamicConstants.needLauncher) {
            return new LauncherProcessor();
        }
        return null;
    }
}
