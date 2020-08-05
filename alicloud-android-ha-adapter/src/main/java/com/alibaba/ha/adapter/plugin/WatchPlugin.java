package com.alibaba.ha.adapter.plugin;

import android.content.Context;

import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.protocol.AliHaParam;
import com.alibaba.ha.protocol.AliHaPlugin;

import java.util.concurrent.atomic.AtomicBoolean;

public class WatchPlugin implements AliHaPlugin {
    AtomicBoolean enabling = new AtomicBoolean(false);

    public String getName() {
        return Plugin.watch.name();
    }

    public void start(AliHaParam aliHaParam) {
        String str = aliHaParam.appVersion;
        Context context = aliHaParam.context;
//        if (context == null || str == null) {
//            Log.e(AliHaAdapter.TAG, "param is unlegal, watch plugin start failure ");
//        } else if (this.enabling.compareAndSet(false, true)) {
//            try {
//                MotuWatch.getInstance().enableWatch(context, str, Boolean.valueOf(false));
//            } catch (Exception e) {
//                Log.e(AliHaAdapter.TAG, "param is unlegal, watch plugin start failure ", e);
//            }
//            WatchService.addWatchListener(new WatchActivityPathCallBack());
//        }
    }
}
