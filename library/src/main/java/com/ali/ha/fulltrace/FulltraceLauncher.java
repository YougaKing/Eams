//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace;

import android.app.Application;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.upload.UploadManager;

import java.util.HashMap;

public class FulltraceLauncher {

    public FulltraceLauncher() {
    }

    public static void init(final Application application, HashMap<String, String> headDatas) {
        FTHeader.appVersion = (String)headDatas.get("appVersion");
        FTHeader.appBuild = (String)headDatas.get("appBuild");
        FTHeader.appId = (String)headDatas.get("appId");
        FTHeader.appKey = (String)headDatas.get("appKey");
        FTHeader.channel = (String)headDatas.get("channel");
        FTHeader.utdid = (String)headDatas.get("utdid");
        FTHeader.userId = (String)headDatas.get("userId");
        FTHeader.userNick = (String)headDatas.get("userNick");
        FTHeader.ttid = (String)headDatas.get("ttid");
        FTHeader.apmVersion = (String)headDatas.get("apmVersion");
        FTHeader.brand = (String)headDatas.get("brand");
        FTHeader.deviceModel = (String)headDatas.get("deviceModel");
        FTHeader.clientIp = (String)headDatas.get("clientIp");
        FTHeader.os = (String)headDatas.get("os");
        FTHeader.osVersion = (String)headDatas.get("osVersion");
        FTHeader.processName = (String)headDatas.get("processName");
        FulltraceGlobal.instance().dumpHandler().post(new Runnable() {
            public void run() {
                HashMap<String, String> appInfo = new HashMap();
                appInfo.put("appVersion", FTHeader.appVersion);
                appInfo.put("appBuild", FTHeader.appBuild);
                appInfo.put("appId", FTHeader.appId);
                appInfo.put("appKey", FTHeader.appKey);
                appInfo.put("channel", FTHeader.channel);
                appInfo.put("utdid", FTHeader.utdid);
                appInfo.put("userId", FTHeader.userId);
                appInfo.put("userNick", FTHeader.userNick);
                appInfo.put("ttid", FTHeader.ttid);
                appInfo.put("apmVersion", FTHeader.apmVersion);
                appInfo.put("session", FTHeader.session);
                appInfo.put("processName", FTHeader.processName);
                HashMap<String, String> deviceInfo = new HashMap();
                deviceInfo.put("brand", FTHeader.brand);
                deviceInfo.put("deviceModel", FTHeader.deviceModel);
                deviceInfo.put("clientIp", FTHeader.clientIp);
                deviceInfo.put("os", FTHeader.os);
                deviceInfo.put("osVersion", FTHeader.osVersion);
                DumpManager.getInstance().initTraceLog(application, appInfo, deviceInfo);
                UploadManager.getInstance().init(application);
            }
        });
    }
}
