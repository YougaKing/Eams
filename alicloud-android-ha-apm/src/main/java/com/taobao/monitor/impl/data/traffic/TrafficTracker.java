package com.taobao.monitor.impl.data.traffic;

import android.net.TrafficStats;
import android.os.Process;

/* compiled from: TrafficTracker */
public class TrafficTracker {
    private static long[] mArrays = new long[2];
    private static int myUid;
    private static boolean mAvailable;

    static {
        boolean available = true;
        myUid = -1;
        myUid = Process.myUid();
        mArrays[0] = TrafficStats.getUidRxBytes(myUid);
        mArrays[1] = TrafficStats.getUidTxBytes(myUid);
        if (mArrays[0] < 0 || mArrays[1] < 0) {
            available = false;
        }
        mAvailable = available;
    }

    public static long[] traffics() {
        if (!mAvailable || myUid <= 0) {
            return mArrays;
        }
        mArrays[0] = TrafficStats.getUidRxBytes(myUid);
        mArrays[1] = TrafficStats.getUidTxBytes(myUid);
        return mArrays;
    }
}
