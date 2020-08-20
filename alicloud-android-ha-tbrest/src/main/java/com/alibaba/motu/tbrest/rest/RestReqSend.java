//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.rest;

import android.content.Context;
import com.alibaba.motu.tbrest.SendService;
import com.alibaba.motu.tbrest.request.BizRequest;
import com.alibaba.motu.tbrest.request.BizResponse;
import com.alibaba.motu.tbrest.request.UrlWrapper;
import com.alibaba.motu.tbrest.utils.LogUtil;
import com.alibaba.motu.tbrest.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RestReqSend {
    public RestReqSend() {
    }

    public static boolean sendLog(Context aContext, String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        String appkey = SendService.getInstance().appKey;
        return sendLog(appkey, aContext, adashxServerHost, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
    }

    public static boolean sendLog(String appKey, Context aContext, String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        try {
            LogUtil.i("RestAPI start send log!");
            String requestData = RestReqDataBuilder.buildRequestData(appKey, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
            if (StringUtils.isNotBlank(requestData)) {
                LogUtil.i("RestAPI build data succ!");
                Map<String, String> eventMap = new HashMap(1);
                eventMap.put(String.valueOf(aEventId), requestData);
                byte[] packRequest = null;

                try {
                    packRequest = BizRequest.getPackRequest(appKey, aContext, eventMap);
                } catch (Exception var15) {
                    LogUtil.e(var15.toString());
                }

                if (packRequest != null) {
                    LogUtil.i("packRequest success!");
                    BizResponse bizResponse = UrlWrapper.sendRequest(adashxServerHost, packRequest);
                    return bizResponse.isSuccess();
                }
            } else {
                LogUtil.i("UTRestAPI build data failure!");
            }
        } catch (Throwable var16) {
            LogUtil.e("system error!", var16);
        }

        return false;
    }

    public static boolean sendLogByUrl(String appKey, Context aContext, String url, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        try {
            LogUtil.i("RestAPI start send log by url!");
            String requestData = RestReqDataBuilder.buildRequestData(appKey, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
            if (StringUtils.isNotBlank(requestData)) {
                LogUtil.i("RestAPI build data succ by url!");
                Map<String, String> eventMap = new HashMap(1);
                eventMap.put(String.valueOf(aEventId), requestData);
                byte[] packRequest = null;

                try {
                    packRequest = BizRequest.getPackRequest(appKey, aContext, eventMap);
                } catch (Exception var15) {
                    LogUtil.e(var15.toString());
                }

                if (packRequest != null) {
                    LogUtil.i("packRequest success by url!");
                    BizResponse bizResponse = UrlWrapper.sendRequest(appKey, url, packRequest);
                    return bizResponse.isSuccess();
                }
            } else {
                LogUtil.i("UTRestAPI build data failure by url!");
            }
        } catch (Throwable var16) {
            LogUtil.e("system error by url!", var16);
        }

        return false;
    }

    /** @deprecated */
    @Deprecated
    public static String sendLogByUrl(String url, Context aContext, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        String appkey = SendService.getInstance().appKey;
        return sendLogByUrl(url, appkey, aContext, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
    }

    /** @deprecated */
    @Deprecated
    public static String sendLogByUrl(String url, String appKey, Context aContext, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        try {
            LogUtil.i("sendLogByUrl RestAPI start send log!");
            RestReqDataBuildResult lRQBR = RestReqDataBuilder.buildMonkeyPostReqDataObj(appKey, url, aContext, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
            if (null != lRQBR) {
                LogUtil.i("sendLogByUrl RestAPI build data succ!");
                Map<String, Object> lPostReqData = lRQBR.getPostReqData();
                if (null == lPostReqData) {
                    LogUtil.i("sendLogByUrl postReqData is null!");
                    return null;
                }

                String lReqUrl = lRQBR.getReqUrl();
                if (StringUtils.isEmpty(lReqUrl)) {
                    LogUtil.i("sendLogByUrl reqUrl is null!");
                    return null;
                }

                byte[] lREPData = RestHttpUtils.sendRequest(2, lReqUrl, lPostReqData, true);
                if (null != lREPData) {
                    try {
                        String lStr = new String(lREPData, "UTF-8");
                        if (!StringUtils.isEmpty(lStr)) {
                            return lStr;
                        }
                    } catch (UnsupportedEncodingException var16) {
                        LogUtil.e("sendLogByUrl result encoding UTF-8 error!", var16);
                    }
                }
            } else {
                LogUtil.i("sendLogByUrl UTRestAPI build data failure!");
            }
        } catch (Throwable var17) {
            LogUtil.e("sendLogByUrl system error!", var17);
        }

        return null;
    }
}
