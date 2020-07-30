//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

import java.util.Map;

public interface IProcedure {
    IProcedure DEFAULT = new Default();

    String topic();

    String topicSession();

    IProcedure begin();

    IProcedure event(String var1, Map<String, Object> var2);

    IProcedure stage(String var1, long var2);

    IProcedure addBiz(String var1, Map<String, Object> var2);

    IProcedure addBizAbTest(String var1, Map<String, Object> var2);

    IProcedure addBizStage(String var1, Map<String, Object> var2);

    IProcedure addProperty(String var1, Object var2);

    IProcedure addStatistic(String var1, Object var2);

    boolean isAlive();

    IProcedure end();

    IProcedure end(boolean var1);

    IProcedure parent();
}
