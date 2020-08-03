//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.network.lifecycle;

import java.util.Map;

public interface INetworkLifecycle {
    void onRequest(String var1, String var2, Map<String, Object> var3);

    void onValidRequest(String var1, String var2, Map<String, Object> var3);

    void onError(String var1, Map<String, Object> var2);

    void onCancel(String var1, Map<String, Object> var2);

    void onFinished(String var1, Map<String, Object> var2);

    void onEvent(String var1, String var2, Map<String, Object> var3);
}
