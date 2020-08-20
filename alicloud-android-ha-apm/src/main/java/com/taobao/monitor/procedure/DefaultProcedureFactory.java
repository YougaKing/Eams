package com.taobao.monitor.procedure;

/* compiled from: ProcedureFactoryProxy */
class DefaultProcedureFactory implements IProcedureFactory {
    DefaultProcedureFactory() {
    }

    public IProcedure createProcedure(String topic) {
        return IProcedure.DEFAULT;
    }

    public IProcedure createProcedure(String topic, ProcedureConfig procedureConfig) {
        return IProcedure.DEFAULT;
    }
}
