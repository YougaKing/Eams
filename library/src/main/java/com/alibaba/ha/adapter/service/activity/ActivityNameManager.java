//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityNameManager {
    private List<String> activityNameList;
    private int activityListMaxLength;

    private ActivityNameManager() {
        this.activityNameList = new ArrayList<>();
        this.activityListMaxLength = 20;
    }

    public static synchronized ActivityNameManager getInstance() {
        return ActivityNameManager.InstanceCreater.instance;
    }

    public void addActivityName(String activityName) {
        try {
            if (activityName != null) {
                if (this.activityNameList.size() < this.activityListMaxLength) {
                    this.activityNameList.add(activityName);
                } else {
                    this.activityNameList.remove(0);
                    if (this.activityNameList.size() < this.activityListMaxLength) {
                        this.activityNameList.add(activityName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getActivityList() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            for(int i = 0; i < this.activityNameList.size(); ++i) {
                String activityName = this.activityNameList.get(i);
                stringBuilder.append(activityName);
                if (i < this.activityNameList.size() - 1) {
                    stringBuilder.append("+");
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public String getLastActivity() {
        String activityName = null;

        try {
            int lastIndex = this.activityNameList.size() - 1;
            if (lastIndex >= 0) {
                activityName = this.activityNameList.get(lastIndex);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return activityName;
    }

    private static class InstanceCreater {
        private static ActivityNameManager instance = new ActivityNameManager();

        private InstanceCreater() {
        }
    }
}
