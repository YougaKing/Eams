package com.taobao.monitor.adapter;

import com.taobao.monitor.procedure.IProcedure;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* compiled from: DataHubProcedureGroupHelper */
class a {
    private static final C0001a a = new C0001a();

    /* renamed from: com.taobao.monitor.adapter.a$a reason: collision with other inner class name */
    /* compiled from: DataHubProcedureGroupHelper */
    public static class C0001a implements IProcedure {
        final ArrayList<IProcedure> a = new ArrayList<>();

        public String topic() {
            throw new UnsupportedOperationException();
        }

        public String topicSession() {
            throw new UnsupportedOperationException();
        }

        public IProcedure begin() {
            throw new UnsupportedOperationException();
        }

        public IProcedure event(String str, Map<String, Object> map) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IProcedure) it.next()).event(str, map);
            }
            return this;
        }

        public IProcedure stage(String str, long j) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IProcedure) it.next()).stage(str, j);
            }
            return this;
        }

        public IProcedure addBiz(String str, Map<String, Object> map) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IProcedure) it.next()).addBiz(str, map);
            }
            return this;
        }

        public IProcedure addBizAbTest(String str, Map<String, Object> map) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IProcedure) it.next()).addBizAbTest(str, map);
            }
            return this;
        }

        public IProcedure addBizStage(String str, Map<String, Object> map) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IProcedure) it.next()).addBizStage(str, map);
            }
            return this;
        }

        public IProcedure addProperty(String str, Object obj) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IProcedure) it.next()).addProperty(str, obj);
            }
            return this;
        }

        public IProcedure addStatistic(String str, Object obj) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IProcedure) it.next()).addStatistic(str, obj);
            }
            return this;
        }

        public boolean isAlive() {
            throw new UnsupportedOperationException();
        }

        public IProcedure end() {
            throw new UnsupportedOperationException();
        }

        public IProcedure end(boolean z) {
            throw new UnsupportedOperationException();
        }

        public IProcedure parent() {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: private */
        public void addSubProcedure(IProcedure iProcedure) {
            if (iProcedure != null) {
                this.a.add(iProcedure);
            }
        }

        /* access modifiers changed from: private */
        public void clear() {
            this.a.clear();
        }
    }

    public static IProcedure a() {
        a.clear();
        a.addSubProcedure(ProcedureManagerProxy.PROXY.getLauncherProcedure());
        a.addSubProcedure(ProcedureManagerProxy.PROXY.getCurrentActivityProcedure());
        a.addSubProcedure(ProcedureManagerProxy.PROXY.getCurrentFragmentProcedure());
        return a;
    }
}
