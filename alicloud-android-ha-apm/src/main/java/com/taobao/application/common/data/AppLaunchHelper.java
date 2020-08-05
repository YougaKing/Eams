package com.taobao.application.common.data;

import android.content.SharedPreferences.Editor;
import com.taobao.monitor.impl.common.Global;

/* compiled from: AppLaunchHelper */
public class AppLaunchHelper extends AbstractHelper {

    /* compiled from: AppLaunchHelper */
    public static class AppLaunchHelperHolder {
        public static long a() {
            return Global.instance().context().getSharedPreferences("apm", 0).getLong("lastStartProcessTime", -1);
        }

        public static void f(long j) {
            Editor edit = Global.instance().context().getSharedPreferences("apm", 0).edit();
            edit.putLong("lastStartProcessTime", j);
            edit.apply();
        }
    }

    public void a(long j) {
        this.preferences.putLong("lastStartProcessTime", j);
    }

    public void b(long j) {
        this.preferences.putLong("startProcessSystemTime", j);
        AppLaunchHelperHolder.f(j);
    }

    public void c(long j) {
        this.preferences.putLong("startProcessSystemClockTime", j);
    }

    public void d(long j) {
        this.preferences.putLong("startAppOnCreateSystemTime", j);
    }

    public void e(long j) {
        this.preferences.putLong("startAppOnCreateSystemClockTime", j);
    }

    public void a(boolean z) {
        this.preferences.putBoolean("isFullNewInstall", z);
    }

    public void b(boolean z) {
        this.preferences.putBoolean("isFirstLaunch", z);
    }

    public void a(String str) {
        this.preferences.putString("launchType", str);
    }
}
