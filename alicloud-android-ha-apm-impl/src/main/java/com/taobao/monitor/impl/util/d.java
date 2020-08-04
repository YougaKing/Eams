package com.taobao.monitor.impl.util;

import android.os.Process;
import java.io.File;

/* compiled from: ProcessUtils */
public class d {
    public static long b() {
        File file = new File("/proc/" + Process.myPid() + "/comm");
        if (file.exists()) {
            return file.lastModified();
        }
        return -1;
    }
}
