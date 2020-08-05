package com.taobao.monitor.impl.data;

import java.util.HashMap;

public class GlobalStats {
    public static ActivityStatusManager activityStatusManager = new ActivityStatusManager();
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

    public static class ActivityStatusManager {
        HashMap<String, Boolean> mMap = new HashMap<>();

        public boolean get(String str) {
            Boolean bool = this.mMap.get(str);
            if (bool == null) {
                return true;
            }
            return bool;
        }

        public void put(String str) {
            if (this.mMap.get(str) == null) {
                this.mMap.put(str, Boolean.TRUE);
            } else {
                this.mMap.put(str, Boolean.FALSE);
            }
        }
    }
}
