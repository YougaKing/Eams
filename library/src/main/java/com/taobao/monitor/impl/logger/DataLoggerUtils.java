//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.logger;

public class DataLoggerUtils {
    private static IDataLogger dataLogger;

    public DataLoggerUtils() {
    }

    public static void log(String var0, Object... var1) {
        if (dataLogger != null) {
            dataLogger.log(var0, var1);
        }

    }

    public static void setDataLogger(IDataLogger var0) {
        dataLogger = var0;
    }
}
