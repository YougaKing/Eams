package com.taobao.monitor.procedure;

import com.taobao.monitor.ProcedureGlobal;
import com.taobao.monitor.network.ProcedureLifecycleImpl;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;

public class ProcedureFactory implements IProcedureFactory {

    public IProcedure createProcedure(String topic) {
        return createProcedure(topic, new Builder()
                .setUpload(false)
                .setIndependent(true)
                .setParentNeedStats(true)
                .setParent(ProcedureGlobal.PROCEDURE_MANAGER.getCurrentProcedure())
                .build());
    }

    public IProcedure createProcedure(String topic, ProcedureConfig procedureConfig) {
        if (procedureConfig == null) {
            procedureConfig = new Builder()
                    .setUpload(false)
                    .setIndependent(true)
                    .setParentNeedStats(true)
                    .setParent(ProcedureGlobal.PROCEDURE_MANAGER.getCurrentProcedure())
                    .build();
        }
        return new ProcedureProxy(createProcedureImpl(topic, procedureConfig));
    }

    private ProcedureImpl createProcedureImpl(String topic, ProcedureConfig procedureConfig) {
        IProcedure parent = procedureConfig.getParent();
        if (parent == IProcedure.DEFAULT) {
            parent = ProcedureGlobal.PROCEDURE_MANAGER.getCurrentProcedure();
        }
        ProcedureImpl procedureImpl = new ProcedureImpl(topic, parent, procedureConfig.isIndependent(), procedureConfig.isParentNeedStats());
        if (procedureConfig.isUpload()) {
            procedureImpl.setLifeCycle(new ProcedureLifecycleImpl());
        }
        return procedureImpl;
    }
}
