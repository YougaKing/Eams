package com.taobao.phenix.lifecycle;

import java.util.Map;

public interface IPhenixLifeCycle {
    void onCancel(String str, String str2, Map<String, Object> map);

    void onError(String str, String str2, Map<String, Object> map);

    void onEvent(String str, String str2, Map<String, Object> map);

    void onFinished(String str, String str2, Map<String, Object> map);

    void onRequest(String str, String str2, Map<String, Object> map);
}
