package com.ali.ha.datahub;

import java.util.Map;

public interface BizSubscriber {
    void onBizDataReadyStage();

    void onStage(String str, String str2, long j);

    void pub(String str, Map<String, Object> hashMap);

    void pubAB(String str, Map<String, Object> hashMap);

    void setMainBiz(String str, String str2);
}
