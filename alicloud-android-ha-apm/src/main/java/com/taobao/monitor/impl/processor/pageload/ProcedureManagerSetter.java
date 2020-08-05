package com.taobao.monitor.impl.processor.pageload;

import com.taobao.monitor.procedure.IProcedure;

public class ProcedureManagerSetter implements IProcedureManager {
    private IProcedureManager proxy;

    private static class a implements IProcedureManager {
        private a() {
        }

        public void setCurrentActivityProcedure(IProcedure iProcedure) {
        }

        public void setCurrentFragmentProcedure(IProcedure iProcedure) {
        }

        public void setCurrentLauncherProcedure(IProcedure iProcedure) {
        }
    }

    private static class b {
        static final ProcedureManagerSetter a = new ProcedureManagerSetter();
    }

    private ProcedureManagerSetter() {
        this.proxy = new a();
    }

    public static ProcedureManagerSetter instance() {
        return b.a;
    }

    public ProcedureManagerSetter setProxy(IProcedureManager iProcedureManager) {
        this.proxy = iProcedureManager;
        return this;
    }

    public void setCurrentActivityProcedure(IProcedure iProcedure) {
        this.proxy.setCurrentActivityProcedure(iProcedure);
    }

    public void setCurrentFragmentProcedure(IProcedure iProcedure) {
        this.proxy.setCurrentFragmentProcedure(iProcedure);
    }

    public void setCurrentLauncherProcedure(IProcedure iProcedure) {
        this.proxy.setCurrentLauncherProcedure(iProcedure);
    }
}
