//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

import com.taobao.monitor.ProcedureGlobal;
import java.util.Map;

public class ProcedureProxy implements IProcedureGroup, IValueCallback {
    private final ProcedureImpl base;

    public ProcedureProxy(ProcedureImpl var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.base = var1;
        }
    }

    public String topic() {
        return this.base.topic();
    }

    public String topicSession() {
        return this.base.topicSession();
    }

    public IProcedure begin() {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.begin();
            }
        });
        return this;
    }

    public IProcedure event(final String var1, final Map<String, Object> var2) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.event(var1, var2);
            }
        });
        return this;
    }

    public IProcedure stage(final String var1, final long var2) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.stage(var1, var2);
            }
        });
        return this;
    }

    public IProcedure addBiz(final String var1, final Map<String, Object> var2) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.addBiz(var1, var2);
            }
        });
        return this;
    }

    public IProcedure addBizAbTest(final String var1, final Map<String, Object> var2) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.addBizAbTest(var1, var2);
            }
        });
        return this;
    }

    public IProcedure addBizStage(final String var1, final Map<String, Object> var2) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.addBizStage(var1, var2);
            }
        });
        return this;
    }

    public IProcedure addProperty(final String var1, final Object var2) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.addProperty(var1, var2);
            }
        });
        return this;
    }

    public IProcedure addStatistic(final String var1, final Object var2) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.addStatistic(var1, var2);
            }
        });
        return this;
    }

    public boolean isAlive() {
        return this.base.isAlive();
    }

    public IProcedure end() {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.end();
            }
        });
        return this;
    }

    public IProcedure end(final boolean var1) {
        this.async(new Runnable() {
            public void run() {
                ProcedureProxy.this.base.end(var1);
            }
        });
        return this;
    }

    public IProcedure base() {
        return this.base;
    }

    public void removeSubProcedure(IProcedure var1) {
        this.base.removeSubProcedure(var1);
    }

    public void addSubProcedure(IProcedure var1) {
        this.base.addSubProcedure(var1);
    }

    public IProcedure parent() {
        return this.base.parent();
    }

    private void async(Runnable var1) {
        ProcedureGlobal.instance().handler().post(var1);
    }

    public void callback(Value var1) {
        this.base.callback(var1);
    }
}
