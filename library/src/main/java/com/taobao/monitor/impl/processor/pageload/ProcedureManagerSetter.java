//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.pageload;

import com.taobao.monitor.procedure.IProcedure;

public class ProcedureManagerSetter implements IProcedureManager {
    private IProcedureManager proxy;

    private ProcedureManagerSetter() {
        this.proxy = new ProcedureManagerSetter.a();
    }

    public static ProcedureManagerSetter instance() {
        return ProcedureManagerSetter.b.a;
    }

    public ProcedureManagerSetter setProxy(IProcedureManager var1) {
        this.proxy = var1;
        return this;
    }

    public void setCurrentActivityProcedure(IProcedure var1) {
        this.proxy.setCurrentActivityProcedure(var1);
    }

    public void setCurrentFragmentProcedure(IProcedure var1) {
        this.proxy.setCurrentFragmentProcedure(var1);
    }

    public void setCurrentLauncherProcedure(IProcedure var1) {
        this.proxy.setCurrentLauncherProcedure(var1);
    }

    private static class a implements IProcedureManager {
        private a() {
        }

        public void setCurrentActivityProcedure(IProcedure var1) {
        }

        public void setCurrentFragmentProcedure(IProcedure var1) {
        }

        public void setCurrentLauncherProcedure(IProcedure var1) {
        }
    }

    private static class b {
        static final ProcedureManagerSetter a = new ProcedureManagerSetter();
    }
}
