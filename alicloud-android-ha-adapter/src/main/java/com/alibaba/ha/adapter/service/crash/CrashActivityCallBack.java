package com.alibaba.ha.adapter.service.crash;

import com.alibaba.ha.adapter.service.activity.ActivityNameManager;
import java.util.HashMap;
import java.util.Map;

public class CrashActivityCallBack implements JavaCrashListener {
    private final String activityListKey = "_controllers";
    private final String activityNameKey = "_controller";

    public Map<String, Object> onCrashCaught(Thread thread, Throwable th) {
        String lastActivity = ActivityNameManager.getInstance().getLastActivity();
        String activityList = ActivityNameManager.getInstance().getActivityList();
        HashMap hashMap = new HashMap();
        if (lastActivity != null) {
            hashMap.put("_controller", lastActivity);
        }
        if (activityList != null) {
            hashMap.put("_controllers", activityList);
        }
        return hashMap;
    }
}
