//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

import java.util.Map;

class Default implements IProcedure {
    Default() {
    }

    public String topic() {
        return "default";
    }

    public String topicSession() {
        return "no-session";
    }

    public IProcedure begin() {
        return this;
    }

    public IProcedure event(String var1, Map<String, Object> var2) {
        return this;
    }

    public IProcedure stage(String var1, long var2) {
        return this;
    }

    public IProcedure addBiz(String var1, Map<String, Object> var2) {
        return this;
    }

    public IProcedure addBizAbTest(String var1, Map<String, Object> var2) {
        return this;
    }

    public IProcedure addBizStage(String var1, Map<String, Object> var2) {
        return this;
    }

    public IProcedure addProperty(String var1, Object var2) {
        return this;
    }

    public IProcedure addStatistic(String var1, Object var2) {
        return this;
    }

    public boolean isAlive() {
        return false;
    }

    public IProcedure end() {
        return this;
    }

    public IProcedure end(boolean var1) {
        return this;
    }

    public IProcedure parent() {
        return this;
    }
}
