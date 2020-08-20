package com.taobao.monitor.impl.processor;

public interface IProcessor {

    interface ProcessorCallback {
        void onProcedureBegin(IProcessor iProcessor);

        void onProcedureEnd(IProcessor iProcessor);
    }
}
