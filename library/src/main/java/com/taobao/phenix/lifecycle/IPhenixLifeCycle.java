//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.phenix.lifecycle;

import java.util.Map;

public interface IPhenixLifeCycle {
    void onRequest(String var1, String var2, Map<String, Object> var3);

    void onError(String var1, String var2, Map<String, Object> var3);

    void onCancel(String var1, String var2, Map<String, Object> var3);

    void onFinished(String var1, String var2, Map<String, Object> var3);

    void onEvent(String var1, String var2, Map<String, Object> var3);
}
