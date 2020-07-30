//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure.model;

import java.util.HashMap;
import java.util.Map;

public class Biz {
    private final String bizID;
    private Map<String, Object> properties;
    private Map<String, Object> abTest;
    private Map<String, Object> stages;

    public Biz(String var1, Map<String, Object> var2) {
        this.bizID = var1;
        this.properties = var2;
    }

    public String bizID() {
        return this.bizID;
    }

    public Map<String, Object> stages() {
        return this.stages;
    }

    public Map<String, Object> abTest() {
        return this.abTest;
    }

    public Map<String, Object> properties() {
        return this.properties;
    }

    public Biz addProperties(Map<String, Object> var1) {
        if (var1 == null) {
            return this;
        } else {
            if (this.properties == null) {
                this.properties = new HashMap();
            }

            this.properties.putAll(var1);
            return this;
        }
    }

    public Biz addProperties(String var1, Object var2) {
        if (this.properties == null) {
            this.properties = new HashMap();
        }

        this.properties.put(var1, var2);
        return this;
    }

    public Biz addAbTest(Map<String, Object> var1) {
        if (var1 == null) {
            return this;
        } else {
            if (this.abTest == null) {
                this.abTest = new HashMap();
            }

            this.abTest.putAll(var1);
            return this;
        }
    }

    public Biz addStage(Map<String, Object> var1) {
        if (var1 == null) {
            return this;
        } else {
            if (this.stages == null) {
                this.stages = new HashMap();
            }

            this.stages.putAll(var1);
            return this;
        }
    }

    public String toString() {
        return this.bizID;
    }
}
