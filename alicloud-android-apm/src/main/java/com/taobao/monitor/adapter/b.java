package com.taobao.monitor.adapter;

import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: TBAPMAdapterSubTaskManager */
public class b {
    private static Map<String, a> a = new HashMap();
    private static Map<String, IProcedure> b = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: b reason: collision with other field name */
    public static boolean f6b = true;

    /* compiled from: TBAPMAdapterSubTaskManager */
    private static class a {
        /* access modifiers changed from: private */
        public long b;
        /* access modifiers changed from: private */
        public long c;

        /* renamed from: c reason: collision with other field name */
        private boolean f7c;
        /* access modifiers changed from: private */
        public long cpuStartTime;
        private String d;
        private long startTime;

        private a() {
        }
    }

    protected static void a() {
        async(new Runnable() {
            public void run() {
                Iterator it = b.a().entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    String str = (String) entry.getKey();
                    a aVar = (a) entry.getValue();
                    if (aVar.b != 0) {
                        IProcedure createProcedure = ProcedureFactoryProxy.PROXY.createProcedure("/" + str, new Builder().setIndependent(false).setUpload(false).setParentNeedStats(false).setParent(ProcedureManagerProxy.PROXY.getLauncherProcedure()).build());
                        createProcedure.begin();
                        createProcedure.stage("taskStart", a.a(aVar));
                        createProcedure.stage("cpuStartTime", aVar.cpuStartTime);
                        createProcedure.addProperty("isMainThread", Boolean.valueOf(a.a(aVar)));
                        createProcedure.addProperty("threadName", a.a(aVar));
                        createProcedure.stage("taskEnd", aVar.b);
                        createProcedure.stage("cpuEndTime", aVar.c);
                        createProcedure.end();
                        it.remove();
                    }
                }
                b.f6b = false;
            }
        });
    }

    private static void async(Runnable runnable) {
        com.taobao.monitor.a.a().handler().post(runnable);
    }
}
