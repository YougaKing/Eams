package com.taobao.monitor.impl.data.traffic;

import android.net.TrafficStats;
import android.os.Process;

/* compiled from: TrafficTracker */
public class a {
    private static long[] a = new long[2];
    private static int j;
    private static boolean n;

    static {
        boolean z = true;
        j = -1;
        j = Process.myUid();
        a[0] = TrafficStats.getUidRxBytes(j);
        a[1] = TrafficStats.getUidTxBytes(j);
        if (a[0] < 0 || a[1] < 0) {
            z = false;
        }
        n = z;
    }

    public static long[] a() {
        if (!n || j <= 0) {
            return a;
        }
        a[0] = TrafficStats.getUidRxBytes(j);
        a[1] = TrafficStats.getUidTxBytes(j);
        return a;
    }
}
