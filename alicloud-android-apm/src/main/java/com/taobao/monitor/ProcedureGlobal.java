package com.taobao.monitor;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.taobao.monitor.procedure.ProcedureFactory;
import com.taobao.monitor.procedure.ProcedureManager;

/* compiled from: ProcedureGlobal */
public class ProcedureGlobal {
    public static final ProcedureFactory a = new ProcedureFactory();

    /* renamed from: a reason: collision with other field name */
    public static final ProcedureManager f0a = new ProcedureManager();
    private Context context;
    private final Handler handler;

    /* renamed from: com.taobao.monitor.a$a reason: collision with other inner class name */
    /* compiled from: ProcedureGlobal */
    private static class ProcedureGlobalHolder {
        static final ProcedureGlobal PROCEDURE_GLOBAL = new ProcedureGlobal();
    }

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

    /* access modifiers changed from: 0000 */
    public ProcedureGlobal a(Context context2) {
        this.context = context2;
        return this;
    }

    public Handler handler() {
        return this.handler;
    }
}
