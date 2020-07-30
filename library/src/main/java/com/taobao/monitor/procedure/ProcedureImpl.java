//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

import com.taobao.monitor.ProcedureGlobal;
import com.taobao.monitor.exception.ProcedureException;
import com.taobao.monitor.log.Logger;
import com.taobao.monitor.procedure.model.Event;
import com.taobao.monitor.procedure.model.Stage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProcedureImpl implements IProcedureGroup, IValueCallback {
    private static final String TAG = "ProcedureImpl";
    private String topic;
    private static volatile long count = System.currentTimeMillis();
    private final String session;
    private final IProcedure parent;
    private final Value value;
    private ProcedureImpl.Status status;
    private List<IProcedure> subProcedures;
    private ProcedureImpl.IProcedureLifeCycle lifeCycle;
    private final boolean independent;

    ProcedureImpl(String var1, IProcedure var2, boolean var3, boolean var4) {
        this.session = String.valueOf((long)(count++));
        this.status = ProcedureImpl.Status.INIT;
        this.topic = var1;
        this.parent = var2;
        this.independent = var3;
        this.value = new Value(var1, var3, var4);
        if (var2 != null) {
            this.value.addProperty("parentSession", var2.topicSession());
        }

        this.value.addProperty("session", this.session);
    }

    public String topic() {
        return this.topic;
    }

    public String topicSession() {
        return this.session;
    }

    public IProcedure begin() {
        if (this.status == ProcedureImpl.Status.INIT) {
            this.status = ProcedureImpl.Status.RUNNING;
            if (this.parent instanceof IProcedureGroup) {
                ((IProcedureGroup)this.parent).addSubProcedure(this);
            }

            this.subProcedures = new LinkedList();
            Logger.i("ProcedureImpl", this.parent, this.topic, "begin()");
            if (this.lifeCycle != null) {
                this.lifeCycle.begin(this.value);
            }
        }

        return this;
    }

    public IProcedure event(String var1, Map<String, Object> var2) {
        if (var1 != null && this.isAlive()) {
            Event var3 = new Event(var1, var2);
            this.value.event(var3);
            if (this.lifeCycle != null) {
                this.lifeCycle.event(this.value, var3);
            }

            Logger.i("ProcedureImpl", new Object[]{this.parent, this.topic, var1});
        }

        return this;
    }

    public IProcedure stage(String var1, long var2) {
        if (var1 != null && this.isAlive()) {
            Stage var4 = new Stage(var1, var2);
            this.value.stage(var4);
            if (this.lifeCycle != null) {
                this.lifeCycle.stage(this.value, var4);
            }

            Logger.i("ProcedureImpl", this.parent, this.topic, var4);
        }

        return this;
    }

    public IProcedure addBiz(String var1, Map<String, Object> var2) {
        if (var1 != null && this.isAlive()) {
            this.value.addBiz(var1, var2);
            Logger.i("ProcedureImpl", this.parent, this.topic, var1);
        }

        return this;
    }

    public IProcedure addBizAbTest(String var1, Map<String, Object> var2) {
        if (var1 != null && this.isAlive()) {
            this.value.addBizAbTest(var1, var2);
            Logger.i("ProcedureImpl", this.parent, this.topic, var1);
        }

        return this;
    }

    public IProcedure addBizStage(String var1, Map<String, Object> var2) {
        if (var1 != null && this.isAlive()) {
            this.value.addBizStage(var1, var2);
            Logger.i("ProcedureImpl", this.parent, this.topic, var1);
        }

        return this;
    }

    public IProcedure addProperty(String var1, Object var2) {
        if (this.isAlive()) {
            this.value.addProperty(var1, var2);
        }

        return this;
    }

    public IProcedure addStatistic(String var1, Object var2) {
        if (this.isAlive()) {
            this.value.addStatistic(var1, var2);
        }

        return this;
    }

    public boolean isAlive() {
        return ProcedureImpl.Status.STOPPED != this.status;
    }

    public IProcedure end() {
        return this.end(false);
    }

    public IProcedure end(boolean var1) {
        if (this.status == ProcedureImpl.Status.RUNNING) {
            synchronized(this.subProcedures) {
                Iterator var3 = this.subProcedures.iterator();

                label61:
                while(true) {
                    IProcedure var5;
                    ProcedureImpl var6;
                    label59:
                    do {
                        while(true) {
                            while(true) {
                                if (!var3.hasNext()) {
                                    break label61;
                                }

                                IProcedure var4 = (IProcedure)var3.next();
                                if (var4 instanceof ProcedureProxy) {
                                    var5 = ((ProcedureProxy)var4).base();
                                    if (var5 instanceof ProcedureImpl) {
                                        var6 = (ProcedureImpl)var5;
                                        if (var6.isAlive()) {
                                            this.value.addSubValue(var6.value4Parent());
                                        }
                                        continue label59;
                                    }

                                    var5.end(var1);
                                } else {
                                    var4.end(var1);
                                }
                            }
                        }
                    } while(var6.independent && !var1);

                    var5.end(var1);
                }
            }

            if (this.parent instanceof IProcedureGroup) {
                ProcedureGlobal.instance().handler().post(new Runnable() {
                    public void run() {
                        ((IProcedureGroup)ProcedureImpl.this.parent).removeSubProcedure(ProcedureImpl.this);
                    }
                });
            }

            if (this.parent instanceof IValueCallback) {
                ((IValueCallback)this.parent).callback(this.value4Parent());
            }

            if (this.lifeCycle != null) {
                this.lifeCycle.end(this.value);
            }

            this.status = ProcedureImpl.Status.STOPPED;
            Logger.i("ProcedureImpl", this.parent, this.topic, "end()");
        }

        return this;
    }

    protected Value value4Parent() {
        return this.value.summary();
    }

    public void addSubProcedure(IProcedure var1) {
        if (var1 != null && this.isAlive()) {
            synchronized(this.subProcedures) {
                this.subProcedures.add(var1);
            }
        }

    }

    public IProcedure parent() {
        return this.parent;
    }

    public void removeSubProcedure(IProcedure var1) {
        if (var1 != null) {
            synchronized(this.subProcedures) {
                this.subProcedures.remove(var1);
            }
        }

    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.status == ProcedureImpl.Status.RUNNING) {
            Logger.throwException(new ProcedureException("Please call end function first!"));
        }

    }

    public Value value() {
        return this.value;
    }

    public ProcedureImpl setLifeCycle(ProcedureImpl.IProcedureLifeCycle var1) {
        this.lifeCycle = var1;
        return this;
    }

    public String toString() {
        return this.topic;
    }

    public void callback(Value var1) {
        if (this.isAlive()) {
            this.value.addSubValue(var1);
        }

    }

    public interface IProcedureLifeCycle {
        void begin(Value var1);

        void event(Value var1, Event var2);

        void stage(Value var1, Stage var2);

        void end(Value var1);
    }

    private static enum Status {
        INIT,
        RUNNING,
        STOPPED;

        private Status() {
        }
    }
}
