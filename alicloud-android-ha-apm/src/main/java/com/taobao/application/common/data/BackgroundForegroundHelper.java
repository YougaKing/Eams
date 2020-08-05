package com.taobao.application.common.data;

/* compiled from: BackgroundForegroundHelper */
public class BackgroundForegroundHelper extends AbstractHelper {
    public void c(boolean z) {
        this.preferences.putBoolean("isInBackground", z);
    }

    public void d(boolean z) {
        this.preferences.putBoolean("isFullInBackground", z);
    }
}
