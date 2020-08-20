package com.taobao.monitor.procedure;

public class ProcedureFactoryProxy implements IProcedureFactory {
    public static ProcedureFactoryProxy PROXY = new ProcedureFactoryProxy();
    private IProcedureFactory real = new DefaultProcedureFactory();

    private ProcedureFactoryProxy() {
    }

    public ProcedureFactoryProxy setReal(IProcedureFactory iProcedureFactory) {
        this.real = iProcedureFactory;
        return this;
    }

    public IProcedure createProcedure(String topic) {
        return this.real.createProcedure(topic);
    }

    public IProcedure createProcedure(String topic, ProcedureConfig procedureConfig) {
        return this.real.createProcedure(topic, procedureConfig);
    }
}
