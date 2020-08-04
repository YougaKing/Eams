package com.alibaba.ha.adapter.service.watch;

import com.alibaba.motu.watch.MotuWatch;

public class WatchService {
    private static boolean isValid;

    static {
        isValid = false;
        try {
            Class.forName("com.alibaba.motu.watch.MotuWatch");
            isValid = true;
        } catch (ClassNotFoundException e) {
            isValid = false;
        }
    }

    public static void addWatchListener(WatchListener watchListener) {
        if (isValid) {
            MotuWatch.getInstance().setMyWatchListenerList(new WatchListenerAdapter(watchListener));
        }
    }
}
