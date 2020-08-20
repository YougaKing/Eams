package com.taobao.monitor.procedure;

import java.util.Map;

public interface IProcedure {
    IProcedure DEFAULT = new Default();

    IProcedure addBiz(String str, Map<String, Object> map);

    IProcedure addBizAbTest(String str, Map<String, Object> map);

    IProcedure addBizStage(String str, Map<String, Object> map);

    IProcedure addProperty(String str, Object obj);

    IProcedure addStatistic(String str, Object obj);

    IProcedure begin();

    IProcedure end();

    IProcedure end(boolean z);

    IProcedure event(String key, Map<String, Object> map);

    boolean isAlive();

    IProcedure parent();

    IProcedure stage(String key, long timestamp);

    String topic();

    String topicSession();
}
