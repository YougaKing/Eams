package com.taobao.application.common.data;

/* compiled from: BackgroundForegroundHelper */
public class BackgroundForegroundHelper extends AbstractHelper {
    public void isInBackground(boolean isInBackground) {
        this.preferences.putBoolean("isInBackground", isInBackground);
    }

    public void isFullInBackground(boolean isFullInBackground) {
        this.preferences.putBoolean("isFullInBackground", isFullInBackground);
    }
}
