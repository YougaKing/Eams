package com.alibaba.ha.adapter.service.tlog;


public class TLogService {
    private static boolean isValid;

    static {
        isValid = false;
        try {
            Class.forName("com.taobao.tao.log.TLog");
            isValid = true;
        } catch (ClassNotFoundException e) {
            isValid = false;
        }
    }

    public static void OpenDebug(Boolean bool) {
        if (isValid) {
//            TLogInitializer.getInstance().setDebugMode(bool.booleanValue());
        }
    }

    public static void changeBucketName(String str) {
        if (isValid && str != null) {
            changeRemoteDebugOssBucket(str);
        }
    }

    public static void changeHost(String str) {
        if (isValid && str != null) {
            changeRemoteDebugHost(str);
        }
    }

    public static void logv(String str, String str2, String str3) {
        if (isValid) {
//            TLog.logv(str, str2, str3);
        }
    }

    public static void logd(String str, String str2, String str3) {
        if (isValid) {
//            TLog.logd(str, str2, str3);
        }
    }

    public static void logi(String str, String str2, String str3) {
        if (isValid) {
//            TLog.logi(str, str2, str3);
        }
    }

    public static void logw(String str, String str2, String str3) {
        if (isValid) {
//            TLog.logw(str, str2, str3);
        }
    }

    public static void logw(String str, String str2, Throwable th) {
        if (isValid) {
//            TLog.logw(str, str2, th);
        }
    }

    public static void loge(String str, String str2, String str3) {
        if (isValid) {
//            TLog.loge(str, str2, str3);
        }
    }

    public static void loge(String str, String str2, Throwable th) {
        if (isValid) {
//            TLog.loge(str, str2, th);
        }
    }

    public static void traceLog(String str, String str2) {
        if (isValid) {
//            TLog.traceLog(str, str2);
        }
    }

    public static void updateLogLevel(TLogLevel tLogLevel) {
        if (isValid) {
//            TLogInitializer.getInstance().updateLogLevel(tLogLevel.name());
        }
    }

    public static void changeRemoteDebugHost(String str) {
        if (str != null) {
//            TLogInitializer.getInstance().messageHostName = str;
        }
    }

    public static void changeRemoteDebugOssBucket(String str) {
        if (str != null) {
//            TLogInitializer.getInstance().ossBucketName = str;
        }
    }

    public static void changeAccsServiceId(String str) {
        if (str != null) {
//            TLogInitializer.getInstance().accsServiceId = str;
        }
    }

    public static void changeAccsTag(String str) {
        if (str != null) {
//            TLogInitializer.getInstance().accsTag = str;
        }
    }

    public static void changeRasPublishKey(String str) {
        if (str != null) {
//            TLogInitializer.getInstance().changeRsaPublishKey(str);
        }
    }
}
