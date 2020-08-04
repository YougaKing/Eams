package com.alibaba.ha.adapter.service.telescope;

import com.ali.telescope.api.Telescope;
import com.ali.telescope.data.AppConfig;
import com.ali.telescope.interfaces.OnAccurateBootListener;
import com.ali.telescope.interfaces.TelescopeErrReporter;
import com.ali.telescope.interfaces.TelescopeEventData;
import java.lang.reflect.Method;

public class TelescopeService {
    private static boolean isValid;
    public static Method mAnetworkEnd = null;
    public static Method mAnetworkStart = null;
    public static boolean sIsInTaobao = true;
    public static boolean sSdCardEnable;

    static {
        isValid = false;
        try {
            Class.forName("com.ali.telescope.api.Telescope");
            isValid = true;
        } catch (ClassNotFoundException e) {
            isValid = false;
        }
    }

    public static void setBootPath(String[] strArr, long j) {
        if (isValid && strArr != null) {
            for (String split : strArr) {
                String[] split2 = split.split("\\.");
                int length = split2.length;
                if (length > 1) {
                    addBootActivityName(split2[length - 1]);
                }
            }
        }
    }

    private static void addBootActivityName(String str) {
        AppConfig.bootActivityNameList.add(str);
    }

    public static void addOnAccurateBootListener(OnAccurateBootListener onAccurateBootListener) {
        if (isValid) {
            Telescope.addOnAccurateBootListener(onAccurateBootListener);
        }
    }

    public static void addTelescopeErrorReporter(TelescopeErrReporter telescopeErrReporter) {
        if (isValid) {
            Telescope.addTelescopeErrorReporter(telescopeErrReporter);
        }
    }

    public static void addTelescopeDataListener(TelescopeEventData telescopeEventData) {
        if (isValid) {
            Telescope.addTelescopeEventDataListener(telescopeEventData);
        }
    }
}
