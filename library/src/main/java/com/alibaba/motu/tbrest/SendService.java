//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest;

import android.content.Context;
import android.util.Log;
import com.alibaba.motu.tbrest.rest.RestReqSend;
import com.alibaba.motu.tbrest.utils.LogUtil;
import java.util.Map;

public class SendService {
    public Context context = null;
    public String appId = null;
    public String appKey = null;
    public String appSecret = null;
    public String appVersion = null;
    public String channel = null;
    public String userNick = null;
    public String host = null;
    public Boolean openHttp = false;
    public String country = null;
    static final SendService instance = new SendService();
    private SendAsyncExecutor sendAsyncExecutor = new SendAsyncExecutor();

    public SendService() {
    }

    public static SendService getInstance() {
        return instance;
    }

    public void init(Context context, String appId, String appKey, String appVersion, String channel, String userNick) {
        this.context = context;
        this.appId = appId;
        this.appKey = appKey;
        this.appVersion = appVersion;
        this.channel = channel;
        this.userNick = userNick;
    }

    public void updateAppVersion(String appVersion) {
        if (appVersion != null) {
            this.appVersion = appVersion;
        }

    }

    public void updateUserNick(String userNick) {
        if (userNick != null) {
            this.userNick = userNick;
        }

    }

    public void updateChannel(String channel) {
        if (channel != null) {
            this.channel = channel;
        }

    }

    public void changeHost(String host) {
        if (host != null) {
            this.host = host;
        }

    }

    public String getChangeHost() {
        return this.host;
    }

    public Boolean sendRequest(String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        if (this.canSend()) {
            if (adashxServerHost == null) {
                if (this.host != null) {
                    adashxServerHost = this.host;
                } else {
                    adashxServerHost = "h-adashx.ut.taobao.com";
                }
            }

            return RestReqSend.sendLog(this.appKey, this.context, adashxServerHost, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
        } else {
            return false;
        }
    }

    public void sendRequestAsyn(String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        if (this.canSend()) {
            if (adashxServerHost == null) {
                if (this.host != null) {
                    adashxServerHost = this.host;
                } else {
                    adashxServerHost = "h-adashx.ut.taobao.com";
                }
            }

            SendService.RestThread restThread = new SendService.RestThread("rest thread", this.appKey, this.context, adashxServerHost, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData, false);
            this.sendAsyncExecutor.start(restThread);
        }

    }

    public void sendRequestAsynByAppkeyAndUrl(String url, String appKey, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        if (this.canSend()) {
            if (url == null) {
                Log.e("RestApi", "need set url");
                return;
            }

            if (appKey == null) {
                appKey = this.appKey;
            }

            SendService.RestThread restThread = new SendService.RestThread("rest thread", appKey, this.context, url, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData, true);
            this.sendAsyncExecutor.start(restThread);
        }

    }

    /** @deprecated */
    @Deprecated
    public String sendRequestByUrl(String url, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        return this.canSend() ? RestReqSend.sendLogByUrl(url, this.appKey, this.context, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData) : null;
    }

    private Boolean canSend() {
        if (this.appId != null && this.appVersion != null && this.appKey != null && this.context != null) {
            return true;
        } else {
            LogUtil.e("have send args is null，you must init first. appId " + this.appId + " appVersion " + this.appVersion + " appKey " + this.appKey);
            return false;
        }
    }

    public class RestThread implements Runnable {
        private String appKey;
        private Context context;
        private String adashxServerHost;
        private Boolean isUrl = false;
        private long aTimestamp;
        private String aPage;
        private int aEventId;
        private Object aArg1;
        private Object aArg2;
        private Object aArg3;
        private Map<String, String> aExtData;

        public RestThread() {
        }

        public RestThread(String name, String appKey, Context context, String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData, Boolean isUrl) {
            this.context = context;
            this.adashxServerHost = adashxServerHost;
            this.aTimestamp = aTimestamp;
            this.aPage = aPage;
            this.aEventId = aEventId;
            this.aArg1 = aArg1;
            this.aArg2 = aArg2;
            this.aArg3 = aArg3;
            this.aExtData = aExtData;
            this.appKey = appKey;
            this.isUrl = isUrl;
        }

        public void run() {
            try {
                if (this.isUrl) {
                    RestReqSend.sendLogByUrl(this.appKey, this.context, this.adashxServerHost, this.aTimestamp, this.aPage, this.aEventId, this.aArg1, this.aArg2, this.aArg3, this.aExtData);
                } else {
                    RestReqSend.sendLog(this.appKey, this.context, this.adashxServerHost, this.aTimestamp, this.aPage, this.aEventId, this.aArg1, this.aArg2, this.aArg3, this.aExtData);
                }
            } catch (Exception var2) {
                LogUtil.e("send log asyn error ", var2);
            }

        }
    }
}
