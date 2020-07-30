package com.example.eams;

import android.app.Application;

import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.ha.adapter.AliHaConfig;
import com.alibaba.ha.adapter.Plugin;

public class YogaEmas {

    private static final String APP_KEY_ID = "28125247";
    private static final String APP_KEY_SECRET = "e4a5d3fd4bb6afd5d8742733a72ad875";
    private static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDvB0ib4dz7UiLGEMuypV5bzlb8IktIUNiccycQ5+gCwji2qZIBH/KbIVbeZQqPHNn+O1rT7iqYxDDmLm59m01Di7Y3h5MubwTCz6fBr3X8x8M+1NO1PH2/RRhyzFfcsXOC+4v9tQ60fxdFr1qCxiQDzZ/mXz3OctpbZ5H2ksEHNQIDAQAB";

    public static void init(Application app, String version, String channel, boolean debug) {
        AliHaConfig config = new AliHaConfig();
        config.appKey = APP_KEY_ID;
        config.appVersion = version + "_" + (debug ? "debug" : "release");
        config.appSecret = APP_KEY_SECRET;
        config.channel = channel;
        config.userNick = null;
        config.application = app;
        config.context = app.getApplicationContext();
        config.isAliyunos = false;
        config.rsaPublicKey = RSA_PUBLIC_KEY;

        AliHaAdapter.getInstance().addPlugin(Plugin.apm);              //性能监控，如不需要可注释掉
        AliHaAdapter.getInstance().openDebug(true);          //调试日志开关
        AliHaAdapter.getInstance().start(config);                     //启动
    }
}