package com.taobao.monitor.impl.data;

import java.util.HashMap;

public class GlobalStats {
    public static a activityStatusManager = new a();
    public static String appVersion = "unknown";
    public static int createdPageCount = 0;
    public static volatile boolean hasSplash = false;
    public static String installType = "unknown";
    public static boolean isBackground;
    public static boolean isDebug = true;
    public static boolean isFirstInstall;
    public static boolean isFirstLaunch = false;
    public static long jumpTime = -1;
    public static long lastProcessStartTime = -1;
    public static String lastTopActivity = "";
    public static String lastValidPage = "background";
    public static long lastValidTime = -1;
    public static long launchStartTime = -1;
    public static String oppoCPUResource = "false";
    public static long processStartTime = -1;

    public static class a {
        HashMap<String, Boolean> a = new HashMap<>();

        public boolean a(String str) {
            Boolean bool = (Boolean) this.a.get(str);
            if (bool == null) {
                return true;
            }
            return bool.booleanValue();
        }

        public void b(String str) {
            if (this.a.get(str) == null) {
                this.a.put(str, Boolean.valueOf(true));
            } else {
                this.a.put(str, Boolean.valueOf(false));
            }
        }
    }
}
