package com.taobao.application.common.data;

/* compiled from: ActivityCountHelper */
public class ActivityCountHelper extends AbstractHelper {

    public void aliveActivityCount(int i) {
        this.preferences.putInt("aliveActivityCount", i);
    }
}
