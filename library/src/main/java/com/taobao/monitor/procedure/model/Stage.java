//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure.model;

public class Stage {
    private final String name;
    private final long timestamp;

    public Stage(String var1, long var2) {
        this.name = var1;
        this.timestamp = var2;
    }

    public String name() {
        return this.name;
    }

    public long timestamp() {
        return this.timestamp;
    }

    public String toString() {
        return this.name;
    }
}
