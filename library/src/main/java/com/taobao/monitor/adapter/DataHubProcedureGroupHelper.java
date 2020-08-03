package com.taobao.monitor.adapter;

import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DataHubProcedureGroupHelper {

    private static final DataHubProcedureGroup DATA_HUB_PROCEDURE_GROUP = new DataHubProcedureGroup();

    public static IProcedure iProcedure() {
        DATA_HUB_PROCEDURE_GROUP.clear();
        DATA_HUB_PROCEDURE_GROUP.addSubProcedure(ProcedureManagerProxy.PROXY.getLauncherProcedure());
        DATA_HUB_PROCEDURE_GROUP.addSubProcedure(ProcedureManagerProxy.PROXY.getCurrentActivityProcedure());
        DATA_HUB_PROCEDURE_GROUP.addSubProcedure(ProcedureManagerProxy.PROXY.getCurrentFragmentProcedure());
        return DATA_HUB_PROCEDURE_GROUP;
    }

    public static class DataHubProcedureGroup implements IProcedure {
        final ArrayList<IProcedure> procedures = new ArrayList<>();

        public DataHubProcedureGroup() {
        }

        public String topic() {
            throw new UnsupportedOperationException();
        }

        public String topicSession() {
            throw new UnsupportedOperationException();
        }

        public IProcedure begin() {
            throw new UnsupportedOperationException();
        }

        public IProcedure event(String var1, Map<String, Object> var2) {
            Iterator var3 = this.procedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.event(var1, var2);
            }

            return this;
        }

        public IProcedure stage(String var1, long var2) {
            Iterator var4 = this.procedures.iterator();

            while(var4.hasNext()) {
                IProcedure var5 = (IProcedure)var4.next();
                var5.stage(var1, var2);
            }

            return this;
        }

        public IProcedure addBiz(String var1, Map<String, Object> var2) {
            Iterator var3 = this.procedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addBiz(var1, var2);
            }

            return this;
        }

        public IProcedure addBizAbTest(String var1, Map<String, Object> var2) {
            Iterator var3 = this.procedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addBizAbTest(var1, var2);
            }

            return this;
        }

        public IProcedure addBizStage(String var1, Map<String, Object> var2) {
            Iterator var3 = this.procedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addBizStage(var1, var2);
            }

            return this;
        }

        public IProcedure addProperty(String var1, Object var2) {
            Iterator var3 = this.procedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addProperty(var1, var2);
            }

            return this;
        }

        public IProcedure addStatistic(String var1, Object var2) {
            Iterator var3 = this.procedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addStatistic(var1, var2);
            }

            return this;
        }

        public boolean isAlive() {
            throw new UnsupportedOperationException();
        }

        public IProcedure end() {
            throw new UnsupportedOperationException();
        }

        public IProcedure end(boolean var1) {
            throw new UnsupportedOperationException();
        }

        public IProcedure parent() {
            throw new UnsupportedOperationException();
        }

        private void addSubProcedure(IProcedure var1) {
            if (var1 != null) {
                this.procedures.add(var1);
            }

        }

        private void clear() {
            this.procedures.clear();
        }
    }
}
