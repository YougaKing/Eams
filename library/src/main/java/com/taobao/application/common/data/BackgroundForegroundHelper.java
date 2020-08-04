package com.taobao.application.common.data;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2020/8/4 09:37
 * @description:
 */
public class BackgroundForegroundHelper extends AbstractHelper {
    public BackgroundForegroundHelper() {
    }

    public void setInBackground(boolean var1) {
        this.preferences.putBoolean("isInBackground", var1);
    }

    public void setFullInBackground(boolean var1) {
        this.preferences.putBoolean("isFullInBackground", var1);
    }
}