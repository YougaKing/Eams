package com.taobao.monitor.procedure;

import com.taobao.monitor.a;
import com.taobao.monitor.d.c;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;

public class ProcedureFactory implements IProcedureFactory {
    public IProcedure createProcedure(String str) {
        return createProcedure(str, new Builder().setUpload(false).setIndependent(true).setParentNeedStats(true).setParent(a.f0a.getCurrentProcedure()).build());
    }

    public IProcedure createProcedure(String str, ProcedureConfig procedureConfig) {
        if (procedureConfig == null) {
            procedureConfig = new Builder().setUpload(false).setIndependent(true).setParentNeedStats(true).setParent(a.f0a.getCurrentProcedure()).build();
        }
        return new ProcedureProxy(createProcedureImpl(str, procedureConfig));
    }

    private ProcedureImpl createProcedureImpl(String str, ProcedureConfig procedureConfig) {
        IProcedure parent = procedureConfig.getParent();
        if (parent == IProcedure.DEFAULT) {
            parent = a.f0a.getCurrentProcedure();
        }
        ProcedureImpl procedureImpl = new ProcedureImpl(str, parent, procedureConfig.isIndependent(), procedureConfig.isParentNeedStats());
        if (procedureConfig.isUpload()) {
            procedureImpl.setLifeCycle(new c());
        }
        return procedureImpl;
    }
}
