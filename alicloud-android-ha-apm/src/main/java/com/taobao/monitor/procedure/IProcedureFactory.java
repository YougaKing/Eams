package com.taobao.monitor.procedure;

public interface IProcedureFactory {
    IProcedure createProcedure(String topic);

    IProcedure createProcedure(String topic, ProcedureConfig procedureConfig);
}
