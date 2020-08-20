package com.taobao.monitor.impl.util;

import android.os.Process;
import java.io.File;

/* compiled from: ProcessUtils */
public class ProcessUtils {

    public static long getProcessSystemTime() {
        File file = new File("/proc/" + Process.myPid() + "/comm");
        if (file.exists()) {
            return file.lastModified();
        }
        return -1;
    }
}
