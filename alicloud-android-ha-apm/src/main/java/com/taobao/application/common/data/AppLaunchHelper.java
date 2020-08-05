package com.taobao.application.common.data;

import android.content.SharedPreferences.Editor;

import com.taobao.monitor.impl.common.Global;

/* compiled from: AppLaunchHelper */
public class AppLaunchHelper extends AbstractHelper {

    /* compiled from: AppLaunchHelper */
    public static class AppLaunchHelperHolder {

        public static long lastStartProcessTime() {
            return Global.instance().context().getSharedPreferences("apm", 0).getLong("lastStartProcessTime", -1);
        }

        public static void lastStartProcessTime(long j) {
            Editor edit = Global.instance().context().getSharedPreferences("apm", 0).edit();
            edit.putLong("lastStartProcessTime", j);
            edit.apply();
        }
    }

    public void lastStartProcessTime(long j) {
        this.preferences.putLong("lastStartProcessTime", j);
    }

    public void startProcessSystemTime(long j) {
        this.preferences.putLong("startProcessSystemTime", j);
        AppLaunchHelperHolder.lastStartProcessTime(j);
    }

    public void startProcessSystemClockTime(long j) {
        this.preferences.putLong("startProcessSystemClockTime", j);
    }

    public void startAppOnCreateSystemTime(long j) {
        this.preferences.putLong("startAppOnCreateSystemTime", j);
    }

    public void startAppOnCreateSystemClockTime(long j) {
        this.preferences.putLong("startAppOnCreateSystemClockTime", j);
    }

    public void isFullNewInstall(boolean z) {
        this.preferences.putBoolean("isFullNewInstall", z);
    }

    public void isFirstLaunch(boolean z) {
        this.preferences.putBoolean("isFirstLaunch", z);
    }

    public void launchType(String str) {
        this.preferences.putString("launchType", str);
    }
}
