package com.alibaba.motu.tbrest;

import android.content.Context;
import android.util.Log;
import com.alibaba.motu.tbrest.rest.RestConstants;
import com.alibaba.motu.tbrest.rest.RestReqSend;
import com.alibaba.motu.tbrest.utils.LogUtil;
import java.util.Map;

public class SendService {
    static final SendService instance = new SendService();
    public String appId = null;
    public String appKey = null;
    public String appSecret = null;
    public String appVersion = null;
    public String channel = null;
    public Context context = null;
    public String country = null;
    public String host = null;
    public Boolean openHttp = Boolean.valueOf(false);
    private SendAsyncExecutor sendAsyncExecutor = new SendAsyncExecutor();
    public String userNick = null;

    public class RestThread implements Runnable {
        private Object aArg1;
        private Object aArg2;
        private Object aArg3;
        private int aEventId;
        private Map<String, String> aExtData;
        private String aPage;
        private long aTimestamp;
        private String adashxServerHost;
        private String appKey;
        private Context context;
        private Boolean isUrl = Boolean.valueOf(false);

        public RestThread() {
        }

        public RestThread(String name, String appKey2, Context context2, String adashxServerHost2, long aTimestamp2, String aPage2, int aEventId2, Object aArg12, Object aArg22, Object aArg32, Map<String, String> aExtData2, Boolean isUrl2) {
            this.context = context2;
            this.adashxServerHost = adashxServerHost2;
            this.aTimestamp = aTimestamp2;
            this.aPage = aPage2;
            this.aEventId = aEventId2;
            this.aArg1 = aArg12;
            this.aArg2 = aArg22;
            this.aArg3 = aArg32;
            this.aExtData = aExtData2;
            this.appKey = appKey2;
            this.isUrl = isUrl2;
        }

        public void run() {
            try {
                if (this.isUrl.booleanValue()) {
                    RestReqSend.sendLogByUrl(this.appKey, this.context, this.adashxServerHost, this.aTimestamp, this.aPage, this.aEventId, this.aArg1, this.aArg2, this.aArg3, this.aExtData);
                } else {
                    RestReqSend.sendLog(this.appKey, this.context, this.adashxServerHost, this.aTimestamp, this.aPage, this.aEventId, this.aArg1, this.aArg2, this.aArg3, this.aExtData);
                }
            } catch (Exception e) {
                LogUtil.e("send log asyn error ", e);
            }
        }
    }

    public static SendService getInstance() {
        return instance;
    }

    public void init(Context context2, String appId2, String appKey2, String appVersion2, String channel2, String userNick2) {
        this.context = context2;
        this.appId = appId2;
        this.appKey = appKey2;
        this.appVersion = appVersion2;
        this.channel = channel2;
        this.userNick = userNick2;
    }

    public void updateAppVersion(String appVersion2) {
        if (appVersion2 != null) {
            this.appVersion = appVersion2;
        }
    }

    public void updateUserNick(String userNick2) {
        if (userNick2 != null) {
            this.userNick = userNick2;
        }
    }

    public void updateChannel(String channel2) {
        if (channel2 != null) {
            this.channel = channel2;
        }
    }

    public void changeHost(String host2) {
        if (host2 != null) {
            this.host = host2;
        }
    }

    public String getChangeHost() {
        return this.host;
    }

    public Boolean sendRequest(String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        if (!canSend().booleanValue()) {
            return Boolean.valueOf(false);
        }
        if (adashxServerHost == null) {
            if (this.host != null) {
                adashxServerHost = this.host;
            } else {
                adashxServerHost = RestConstants.G_DEFAULT_ADASHX_HOST;
            }
        }
        return Boolean.valueOf(RestReqSend.sendLog(this.appKey, this.context, adashxServerHost, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData));
    }

    public void sendRequestAsyn(String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        if (canSend().booleanValue()) {
            if (adashxServerHost == null) {
                if (this.host != null) {
                    adashxServerHost = this.host;
                } else {
                    adashxServerHost = RestConstants.G_DEFAULT_ADASHX_HOST;
                }
            }
            this.sendAsyncExecutor.start(new RestThread("rest thread", this.appKey, this.context, adashxServerHost, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData, Boolean.valueOf(false)));
        }
    }

    public void sendRequestAsynByAppkeyAndUrl(String url, String appKey2, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        if (!canSend().booleanValue()) {
            return;
        }
        if (url == null) {
            Log.e(LogUtil.TAG, "need set url");
            return;
        }
        if (appKey2 == null) {
            appKey2 = this.appKey;
        }
        this.sendAsyncExecutor.start(new RestThread("rest thread", appKey2, this.context, url, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData, Boolean.valueOf(true)));
    }

    @Deprecated
    public String sendRequestByUrl(String url, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        if (!canSend().booleanValue()) {
            return null;
        }
        return RestReqSend.sendLogByUrl(url, this.appKey, this.context, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
    }

    private Boolean canSend() {
        if (this.appId != null && this.appVersion != null && this.appKey != null && this.context != null) {
            return Boolean.valueOf(true);
        }
        LogUtil.e("have send args is null，you must init first. appId " + this.appId + " appVersion " + this.appVersion + " appKey " + this.appKey);
        return Boolean.valueOf(false);
    }
}
