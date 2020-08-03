//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

public class ProcedureFactoryProxy implements IProcedureFactory {
    public static ProcedureFactoryProxy PROXY = new ProcedureFactoryProxy();
    private IProcedureFactory real = new DefaultProcedureFactory();

    private ProcedureFactoryProxy() {
    }

    public ProcedureFactoryProxy setReal(IProcedureFactory var1) {
        this.real = var1;
        return this;
    }

    public IProcedure createProcedure(String var1) {
        return this.real.createProcedure(var1);
    }

    public IProcedure createProcedure(String var1, ProcedureConfig var2) {
        return this.real.createProcedure(var1, var2);
    }
}
