//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.network.lifecycle;

import java.util.Map;

public interface IMtopLifecycle {
    void onMtopRequest(String var1, String var2, Map<String, Object> var3);

    void onMtopError(String var1, Map<String, Object> var2);

    void onMtopCancel(String var1, Map<String, Object> var2);

    void onMtopFinished(String var1, Map<String, Object> var2);

    void onMtopEvent(String var1, String var2, Map<String, Object> var3);
}
