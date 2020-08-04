package com.taobao.monitor.adapter;

import com.taobao.monitor.ProcedureGlobal;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: TBAPMAdapterSubTaskManager */
public class TBAPMAdapterSubTaskManager {
    private static Map<String, Property> propertyMap = new HashMap();
    private static Map<String, IProcedure> b = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: b reason: collision with other field name */
    public static boolean f6b = true;

    /* compiled from: TBAPMAdapterSubTaskManager */
    private static class Property {
        /* access modifiers changed from: private */
        public long taskEnd;
        /* access modifiers changed from: private */
        public long cpuEndTime;

        /* renamed from: c reason: collision with other field name */
        private boolean isMainThread;
        /* access modifiers changed from: private */
        public long cpuStartTime;
        private String threadName;
        private long startTime;

        private Property() {
        }
    }

    protected static void a() {
        async(new Runnable() {
            public void run() {
                Iterator it = TBAPMAdapterSubTaskManager.propertyMap.entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    String str = (String) entry.getKey();
                    Property property = (Property) entry.getValue();
                    if (property.taskEnd != 0) {
                        IProcedure createProcedure = ProcedureFactoryProxy.PROXY.createProcedure("/" + str, new Builder().setIndependent(false).setUpload(false).setParentNeedStats(false).setParent(ProcedureManagerProxy.PROXY.getLauncherProcedure()).build());
                        createProcedure.begin();
                        createProcedure.stage("taskStart", property.startTime);
                        createProcedure.stage("cpuStartTime", property.cpuStartTime);
                        createProcedure.addProperty("isMainThread", property.isMainThread);
                        createProcedure.addProperty("threadName", property.threadName);
                        createProcedure.stage("taskEnd", property.taskEnd);
                        createProcedure.stage("cpuEndTime", property.cpuEndTime);
                        createProcedure.end();
                        it.remove();
                    }
                }
                TBAPMAdapterSubTaskManager.f6b = false;
            }
        });
    }

    private static void async(Runnable runnable) {
        ProcedureGlobal.instance().handler().post(runnable);
    }
}
