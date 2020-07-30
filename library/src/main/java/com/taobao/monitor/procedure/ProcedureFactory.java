//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

import com.taobao.monitor.ProcedureGlobal;
import com.taobao.monitor.network.ProcedureLifecycleImpl;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;

public class ProcedureFactory implements IProcedureFactory {
    public ProcedureFactory() {
    }

    public IProcedure createProcedure(String var1) {
        Builder var2 = (new Builder()).setUpload(false).setIndependent(true).setParentNeedStats(true).setParent(ProcedureGlobal.PROCEDURE_MANAGER.getCurrentProcedure());
        return this.createProcedure(var1, var2.build());
    }

    public IProcedure createProcedure(String var1, ProcedureConfig var2) {
        if (var2 == null) {
            var2 = (new Builder()).setUpload(false).setIndependent(true).setParentNeedStats(true).setParent(ProcedureGlobal.PROCEDURE_MANAGER.getCurrentProcedure()).build();
        }

        ProcedureImpl procedureImpl = this.createProcedureImpl(var1, var2);
        return new ProcedureProxy(procedureImpl);
    }

    private ProcedureImpl createProcedureImpl(String var1, ProcedureConfig procedureConfig) {
        IProcedure iProcedure = procedureConfig.getParent();
        if (iProcedure == IProcedure.DEFAULT) {
            iProcedure = ProcedureGlobal.PROCEDURE_MANAGER.getCurrentProcedure();
        }

        ProcedureImpl procedure = new ProcedureImpl(var1, iProcedure, procedureConfig.isIndependent(), procedureConfig.isParentNeedStats());
        if (procedureConfig.isUpload()) {
            procedure.setLifeCycle(new ProcedureLifecycleImpl());
        }

        return procedure;
    }
}
