//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.datahub;

import java.util.Map;

public interface BizSubscriber {
    void pub(String var1, Map<String, Object> var2);

    void pubAB(String var1, Map<String, Object> var2);

    void onStage(String var1, String var2, long var3);

    void setMainBiz(String var1, String var2);

    void onBizDataReadyStage();
}
