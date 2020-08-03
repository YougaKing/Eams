package com.taobao.monitor.impl.processor.launcher;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.processor.IProcessorFactory;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2020/8/3 10:53
 * @description:
 */
public class LauncherProcessorFactory implements IProcessorFactory<LauncherProcessor> {
    LauncherProcessorFactory() {
    }

    public LauncherProcessor createProcessor() {
        return DynamicConstants.needLauncher ? new LauncherProcessor() : null;
    }
}