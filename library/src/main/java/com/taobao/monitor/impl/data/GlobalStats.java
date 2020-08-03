//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.data;

import java.util.HashMap;

public class GlobalStats {
    public static boolean isDebug = true;
    public static boolean isBackground;
    public static boolean isFirstInstall;
    public static volatile boolean hasSplash = false;
    public static boolean isFirstLaunch = false;
    public static String lastTopActivity = "";
    public static int createdPageCount = 0;
    public static String installType = "unknown";
    public static String appVersion = "unknown";
    public static long processStartTime = -1L;
    public static long launchStartTime = -1L;
    public static long lastProcessStartTime = -1L;
    public static String oppoCPUResource = "false";
    public static long jumpTime = -1L;
    public static long lastValidTime = -1L;
    public static String lastValidPage = "background";
    public static GlobalStats.a activityStatusManager = new GlobalStats.a();

    public GlobalStats() {
    }

    public static class a {
        HashMap<String, Boolean> a = new HashMap();

        public a() {
        }

        public boolean a(String var1) {
            Boolean var2 = (Boolean)this.a.get(var1);
            return var2 == null ? true : var2;
        }

        public void b(String var1) {
            if (this.a.get(var1) == null) {
                this.a.put(var1, true);
            } else {
                this.a.put(var1, false);
            }

        }
    }
}
