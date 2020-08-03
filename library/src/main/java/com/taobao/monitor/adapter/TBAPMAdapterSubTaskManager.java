package com.taobao.monitor.adapter;

import com.taobao.monitor.ProcedureGlobal;
import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureConfig;
import com.taobao.monitor.procedure.ProcedureConfig.Builder;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TBAPMAdapterSubTaskManager {

    private static Map<String, ApmAdapterSub> apmAdapterSubMap = new HashMap<>();
    private static Map<String, IProcedure> procedureMap = new HashMap<>();
    private static boolean unAsyncTask = true;

    protected static void asyncTask() {
        async(new Runnable() {
            public void run() {
                Iterator iterator = apmAdapterSubMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Entry entry = (Entry) iterator.next();
                    String key = (String) entry.getKey();
                    ApmAdapterSub apmAdapterSub = (ApmAdapterSub) entry.getValue();
                    if (apmAdapterSub.taskEnd != 0L) {
                        IProcedure launcherProcedure = ProcedureManagerProxy.PROXY.getLauncherProcedure();

                        ProcedureConfig procedureConfig = (new Builder())
                                .setIndependent(false)
                                .setUpload(false)
                                .setParentNeedStats(false)
                                .setParent(launcherProcedure)
                                .build();

                        IProcedure procedure = ProcedureFactoryProxy.PROXY.createProcedure("/" + key, procedureConfig);
                        procedure.begin();
                        procedure.stage("taskStart", apmAdapterSub.startTime);
                        procedure.stage("cpuStartTime", apmAdapterSub.cpuStartTime);
                        procedure.addProperty("isMainThread", apmAdapterSub.isMainThread);
                        procedure.addProperty("threadName", apmAdapterSub.threadName);
                        procedure.stage("taskEnd", apmAdapterSub.taskEnd);
                        procedure.stage("cpuEndTime", apmAdapterSub.cpuEndTime);
                        procedure.end();
                        iterator.remove();
                    }
                }

                unAsyncTask = false;
            }
        });
    }

    private static void async(Runnable runnable) {
        ProcedureGlobal.instance().handler().post(runnable);
    }

    private static class ApmAdapterSub {
        private long startTime;
        private long taskEnd;
        private long cpuStartTime;
        private long cpuEndTime;
        private boolean isMainThread;
        private String threadName;

        private ApmAdapterSub() {
        }
    }

}
