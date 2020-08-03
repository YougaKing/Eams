package com.taobao.monitor.impl.util;


import java.io.File;

import android.os.Process;

public class ProcessUtils {

    public static long processSystemTime() {
        File file = new File("/proc/" + Process.myPid() + "/comm");
        if (file.exists()) {
            return file.lastModified();
        }
        return -1;
    }
}
