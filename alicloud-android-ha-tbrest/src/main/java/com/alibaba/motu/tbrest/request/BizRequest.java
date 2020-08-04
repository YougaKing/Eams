//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.request;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.alibaba.motu.tbrest.SendService;
import com.alibaba.motu.tbrest.utils.ByteUtils;
import com.alibaba.motu.tbrest.utils.GzipUtils;
import com.alibaba.motu.tbrest.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class BizRequest {
    private static final int HEAD_LENGTH = 8;
    private static final int PAYLOAD_MAX_LENGTH = 16777216;
    private static final byte FLAGS_GZIP = 1;
    private static final byte FLAGS_NO_GZIP = 0;
    private static final byte FLAGS_GZIP_FLUSH_DIC = 2;
    private static final byte FLAGS_KEEP_ALIVE = 8;
    private static final byte FLAGS_REAL_TIME_DEBUG = 16;
    private static final byte FLAGS_GET_CONFIG = 32;
    static boolean needConfigByResponse = false;
    static String mResponseAdditionalData = null;
    private static long mReceivedDataLen = 0L;

    public BizRequest() {
    }

    public static byte[] getPackRequest(Context aContext, Map<String, String> eventMap) throws Exception {
        String appkey = SendService.getInstance().appKey;
        return getPackRequest(appkey, aContext, eventMap, 1);
    }

    static byte[] getPackRequestByRealtime(Context aContext, Map<String, String> eventMap) throws Exception {
        String appkey = SendService.getInstance().appKey;
        return getPackRequest(appkey, aContext, eventMap, 2);
    }

    public static byte[] getPackRequest(String appKey, Context aContext, Map<String, String> eventMap) throws Exception {
        return getPackRequest(appKey, aContext, eventMap, 1);
    }

    static byte[] getPackRequestByRealtime(String appKey, Context aContext, Map<String, String> eventMap) throws Exception {
        return getPackRequest(appKey, aContext, eventMap, 2);
    }

    static byte[] getPackRequest(String appKey, Context aContext, Map<String, String> eventMap, int type) throws Exception {
        byte[] payload = GzipUtils.gzip(getPayload(appKey, aContext, eventMap));
        byte flags = 1;
        byte version = 1;
        if (null != payload && payload.length < 16777216) {
            ByteArrayOutputStream baosRequest = new ByteArrayOutputStream();
            baosRequest.write(version);
            byte[] bytelen = ByteUtils.intToBytes3(payload.length);
            baosRequest.write(bytelen);
            baosRequest.write(type);
            flags = (byte) (flags | 8);
            if (needConfigByResponse) {
                flags = (byte) (flags | 32);
            }

            baosRequest.write(flags);
            baosRequest.write(0);
            baosRequest.write(0);
            baosRequest.write(payload);
            byte[] buf = baosRequest.toByteArray();

            try {
                baosRequest.close();
            } catch (IOException var11) {
                LogUtil.e(var11.toString());
            }

            return buf;
        } else {
            return null;
        }
    }

    private static byte[] getPayload(String appKey, Context aContext, Map<String, String> eventMap) throws Exception {
        ByteArrayOutputStream baosPayload = new ByteArrayOutputStream();
        String head = getHead(appKey, aContext);
        if (head != null && head.length() > 0) {
            baosPayload.write(ByteUtils.intToBytes2(head.getBytes().length));
            baosPayload.write(head.getBytes());
        } else {
            baosPayload.write(ByteUtils.intToBytes2(0));
        }

        if (eventMap != null && eventMap.size() > 0) {
            Iterator var5 = eventMap.keySet().iterator();

            while (var5.hasNext()) {
                String key = (String) var5.next();
                int eventId = Integer.valueOf(key);
                baosPayload.write(ByteUtils.intToBytes4(eventId));
                String eventLogs = (String) eventMap.get(key);
                if (eventLogs != null) {
                    int logLength = eventLogs.getBytes().length;
                    byte[] logLengthByte = ByteUtils.intToBytes4(logLength);
                    baosPayload.write(logLengthByte);
                    baosPayload.write(eventLogs.getBytes());
                } else {
                    baosPayload.write(ByteUtils.intToBytes4(0));
                }
            }
        }

        byte[] buf = baosPayload.toByteArray();

        try {
            baosPayload.close();
        } catch (IOException var11) {
            LogUtil.e(var11.toString());
        }

        return buf;
    }

    public static String getHead(String appKey, Context aContext) {
        String appVersion = SendService.getInstance().appVersion;
        if (null == appVersion) {
            appVersion = "";
        }

        String appVersionSys = "Unknown";

        try {
            PackageInfo packageInfo = aContext.getPackageManager().getPackageInfo(aContext.getPackageName(), 0);
            appVersionSys = packageInfo.versionName;
        } catch (NameNotFoundException var9) {
        }

        String channel = SendService.getInstance().channel;
        if (null == channel) {
            channel = "";
        }

        String utdid = "UTDevice.getUtdid(aContext)";
        String sdkVersion = "6.5.1.3";
        String head = String.format("ak=%s&av=%s&avsys=%s&c=%s&d=%s&sv=%s", appKey, appVersion, appVersionSys, channel, utdid, sdkVersion);
        LogUtil.i("send url :" + head);
        return head;
    }

    static int parseResult(byte[] result) {
        int errCode;
        if (null != result && result.length >= 12) {
            mReceivedDataLen = (long) result.length;
            int len = ByteUtils.bytesToInt(result, 1, 3);
            if (len + 8 != result.length) {
                errCode = -1;
                LogUtil.e("recv len error");
            } else {
                byte flags = result[5];
                boolean gzip = false;
                if (1 == (flags & 1)) {
                    gzip = true;
                }

                errCode = ByteUtils.bytesToInt(result, 8, 4);
                int leftLen = result.length - 12 >= 0 ? result.length - 12 : 0;
                if (leftLen > 0) {
                    if (gzip) {
                        byte[] rawData = new byte[leftLen];
                        System.arraycopy(result, 12, rawData, 0, leftLen);
                        byte[] unGzipData = GzipUtils.unGzip(rawData);
                        mResponseAdditionalData = new String(unGzipData, 0, unGzipData.length);
                    } else {
                        mResponseAdditionalData = new String(result, 12, leftLen);
                    }
                } else {
                    mResponseAdditionalData = null;
                }
            }
        } else {
            errCode = -1;
            LogUtil.e("recv errCode UNKNOWN_ERROR");
        }

        LogUtil.d("errCode:" + errCode);
        return errCode;
    }
}
