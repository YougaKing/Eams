package com.taobao.monitor.impl.processor;

public interface IProcessorFactory<T extends IProcessor> {
    T createProcessor();
}
