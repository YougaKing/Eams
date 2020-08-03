package com.taobao.monitor.impl.data.tracker;


import android.net.TrafficStats;
import android.os.Process;

public class TrafficTracker {
    private static boolean available;
    private static int myUid;
    private static long[] bytes = new long[2];

    public static long[] flowBytes() {
        if (available && myUid > 0) {
            bytes[0] = TrafficStats.getUidRxBytes(myUid);
            bytes[1] = TrafficStats.getUidTxBytes(myUid);
            return bytes;
        } else {
            return bytes;
        }
    }

    static {
        myUid = Process.myUid();
        bytes[0] = TrafficStats.getUidRxBytes(myUid);
        bytes[1] = TrafficStats.getUidTxBytes(myUid);
        available = bytes[0] >= 0L && bytes[1] >= 0L;
    }
}
