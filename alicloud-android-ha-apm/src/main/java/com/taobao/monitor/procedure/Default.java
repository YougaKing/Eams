package com.taobao.monitor.procedure;

import java.util.Map;

/* compiled from: IProcedure */
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

    public IProcedure event(String key, Map<String, Object> map) {
        return this;
    }

    public IProcedure stage(String key, long timestamp) {
        return this;
    }

    public IProcedure addBiz(String str, Map<String, Object> map) {
        return this;
    }

    public IProcedure addBizAbTest(String str, Map<String, Object> map) {
        return this;
    }

    public IProcedure addBizStage(String str, Map<String, Object> map) {
        return this;
    }

    public IProcedure addProperty(String str, Object obj) {
        return this;
    }

    public IProcedure addStatistic(String str, Object obj) {
        return this;
    }

    public boolean isAlive() {
        return false;
    }

    public IProcedure end() {
        return this;
    }

    public IProcedure end(boolean z) {
        return this;
    }

    public IProcedure parent() {
        return this;
    }
}
