//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.taobao.monitor.impl.common.Global;

public class AppLaunchHelper extends AbstractHelper {
    public AppLaunchHelper() {
    }

    public void lastStartProcessTime(long var1) {
        this.preferences.putLong("lastStartProcessTime", var1);
    }

    public void startProcessSystemTime(long var1) {
        this.preferences.putLong("startProcessSystemTime", var1);
        AppLaunchHelperHolder.lastStartProcessTime(var1);
    }

    public void startProcessSystemClockTime(long var1) {
        this.preferences.putLong("startProcessSystemClockTime", var1);
    }

    public void startAppOnCreateSystemTime(long var1) {
        this.preferences.putLong("startAppOnCreateSystemTime", var1);
    }

    public void startAppOnCreateSystemClockTime(long var1) {
        this.preferences.putLong("startAppOnCreateSystemClockTime", var1);
    }

    public void isFullNewInstall(boolean var1) {
        this.preferences.putBoolean("isFullNewInstall", var1);
    }

    public void isFirstLaunch(boolean var1) {
        this.preferences.putBoolean("isFirstLaunch", var1);
    }

    public void launchType(String var1) {
        this.preferences.putString("launchType", var1);
    }

    public static class AppLaunchHelperHolder {

        public static long lastStartProcessTime() {
            Context var0 = Global.instance().context();
            SharedPreferences var1 = var0.getSharedPreferences("apm", 0);
            return var1.getLong("lastStartProcessTime", -1L);
        }

        public static void lastStartProcessTime(long var0) {
            Context var2 = Global.instance().context();
            SharedPreferences var3 = var2.getSharedPreferences("apm", 0);
            Editor var4 = var3.edit();
            var4.putLong("lastStartProcessTime", var0);
            var4.apply();
        }
    }
}
