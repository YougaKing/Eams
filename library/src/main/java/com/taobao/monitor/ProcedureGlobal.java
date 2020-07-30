package com.taobao.monitor;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.taobao.monitor.procedure.ProcedureFactory;
import com.taobao.monitor.procedure.ProcedureManager;

public class ProcedureGlobal {

    private Context context;
    private final Handler handler;
    public static final ProcedureManager PROCEDURE_MANAGER = new ProcedureManager();
    public static final ProcedureFactory PROCEDURE_FACTORY = new ProcedureFactory();

    private ProcedureGlobal() {
        HandlerThread handlerThread = new HandlerThread("APM-Procedure");
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper());
    }

    public static ProcedureGlobal instance() {
        return ProcedureGlobalHolder.PROCEDURE_GLOBAL;
    }

    public Context context() {
        return this.context;
    }

    public ProcedureGlobal setContext(Context context) {
        this.context = context;
        return this;
    }

    public Handler handler() {
        return this.handler;
    }

    private static class ProcedureGlobalHolder {
        static final ProcedureGlobal PROCEDURE_GLOBAL = new ProcedureGlobal();
    }
}
