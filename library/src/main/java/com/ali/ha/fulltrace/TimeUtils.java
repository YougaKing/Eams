//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace;

import android.os.SystemClock;

public class TimeUtils {
    static long dTime = -1L;

    public TimeUtils() {
    }

    public static long currentTimeMillis() {
        return SystemClock.elapsedRealtime() + dTime;
    }

    static {
        dTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
    }
}
