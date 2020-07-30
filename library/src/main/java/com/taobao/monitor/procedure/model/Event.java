//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure.model;

import java.util.Map;

public class Event {
    private final String name;
    private final long timestamp;
    private Map<String, Object> properties;

    public Event(String var1, Map<String, Object> var2) {
        this(var1, System.currentTimeMillis());
        this.properties = var2;
    }

    public Event(String var1, long var2) {
        this.name = var1;
        this.timestamp = var2;
    }

    public String name() {
        return this.name;
    }

    public long timestamp() {
        return this.timestamp;
    }

    public Map<String, Object> properties() {
        return this.properties;
    }

    public Event setProperties(Map<String, Object> var1) {
        this.properties = var1;
        return this;
    }

    public String toString() {
        return "Event{name='" + this.name + '\'' + ", timestamp=" + this.timestamp + '}';
    }
}
