package com.taobao.application.common.data;

public class ActivityCountHelper extends AbstractHelper {

    public ActivityCountHelper() {
    }

    public void setAliveActivityCount(int var1) {
        this.preferences.putInt("aliveActivityCount", var1);
    }
}