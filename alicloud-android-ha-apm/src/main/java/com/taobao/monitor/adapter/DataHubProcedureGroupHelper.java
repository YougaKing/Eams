//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.adapter;

import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

class DataHubProcedureGroupHelper {

    private static final DataHubProcedureGroup DATA_HUB_PROCEDURE_GROUP = new DataHubProcedureGroup();

    public static IProcedure instance() {
        DATA_HUB_PROCEDURE_GROUP.clear();
        DATA_HUB_PROCEDURE_GROUP.addSubProcedure(ProcedureManagerProxy.PROXY.getLauncherProcedure());
        DATA_HUB_PROCEDURE_GROUP.addSubProcedure(ProcedureManagerProxy.PROXY.getCurrentActivityProcedure());
        DATA_HUB_PROCEDURE_GROUP.addSubProcedure(ProcedureManagerProxy.PROXY.getCurrentFragmentProcedure());
        return DATA_HUB_PROCEDURE_GROUP;
    }

    public static class DataHubProcedureGroup implements IProcedure {
        final ArrayList<IProcedure> mProcedures = new ArrayList();

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

        public IProcedure event(String key, Map<String, Object> var2) {
            Iterator var3 = this.mProcedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.event(key, var2);
            }

            return this;
        }

        public IProcedure stage(String key, long timestamp) {
            Iterator var4 = this.mProcedures.iterator();

            while(var4.hasNext()) {
                IProcedure var5 = (IProcedure)var4.next();
                var5.stage(key, timestamp);
            }

            return this;
        }

        public IProcedure addBiz(String var1, Map<String, Object> var2) {
            Iterator var3 = this.mProcedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addBiz(var1, var2);
            }

            return this;
        }

        public IProcedure addBizAbTest(String var1, Map<String, Object> var2) {
            Iterator var3 = this.mProcedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addBizAbTest(var1, var2);
            }

            return this;
        }

        public IProcedure addBizStage(String var1, Map<String, Object> var2) {
            Iterator var3 = this.mProcedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addBizStage(var1, var2);
            }

            return this;
        }

        public IProcedure addProperty(String var1, Object var2) {
            Iterator var3 = this.mProcedures.iterator();

            while(var3.hasNext()) {
                IProcedure var4 = (IProcedure)var3.next();
                var4.addProperty(var1, var2);
            }

            return this;
        }

        public IProcedure addStatistic(String var1, Object var2) {
            Iterator var3 = this.mProcedures.iterator();

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
                this.mProcedures.add(var1);
            }

        }

        private void clear() {
            this.mProcedures.clear();
        }
    }
}
