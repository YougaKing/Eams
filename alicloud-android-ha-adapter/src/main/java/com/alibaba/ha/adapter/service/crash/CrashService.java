package com.alibaba.ha.adapter.service.crash;

import com.alibaba.motu.crashreporter.MotuCrashReporter;

public class CrashService {
    private static boolean isValid;

    static {
        isValid = false;
        try {
            Class.forName("com.alibaba.motu.crashreporter.MotuCrashReporter");
            isValid = true;
        } catch (ClassNotFoundException e) {
            isValid = false;
        }
    }

    public static void addJavaCrashListener(JavaCrashListener javaCrashListener) {
        if (isValid) {
            MotuCrashReporter.getInstance().setCrashCaughtListener(new JavaCrashListenerAdapter(javaCrashListener));
        }
    }

    public static void changeHost(String str) {
        if (isValid && str != null) {
            MotuCrashReporter.getInstance().changeHost(str);
        }
    }

    public static void updateApppVersion(String str) {
        if (isValid) {
            MotuCrashReporter.getInstance().setAppVersion(str);
        }
    }

    public static void updateUserNick(String str) {
        if (isValid) {
            MotuCrashReporter.getInstance().setUserNick(str);
        }
    }

    public static void updateChannel(String str) {
        if (isValid) {
            MotuCrashReporter.getInstance().setTTid(str);
        }
    }

    public static void addNativeHeaderInfo(String str, String str2) {
        if (isValid) {
            MotuCrashReporter.getInstance().addNativeHeaderInfo(str, str2);
        }
    }
}
