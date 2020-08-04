package com.alibaba.motu.tbrest.request;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.alibaba.motu.tbrest.BuildConfig;
import com.alibaba.motu.tbrest.SendService;
import com.alibaba.motu.tbrest.rest.RestConstants;
import com.alibaba.motu.tbrest.utils.ByteUtils;
import com.alibaba.motu.tbrest.utils.GzipUtils;
import com.alibaba.motu.tbrest.utils.LogUtil;
import com.ut.device.UTDevice;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class BizRequest {
    private static final byte FLAGS_GET_CONFIG = 32;
    private static final byte FLAGS_GZIP = 1;
    private static final byte FLAGS_GZIP_FLUSH_DIC = 2;
    private static final byte FLAGS_KEEP_ALIVE = 8;
    private static final byte FLAGS_NO_GZIP = 0;
    private static final byte FLAGS_REAL_TIME_DEBUG = 16;
    private static final int HEAD_LENGTH = 8;
    private static final int PAYLOAD_MAX_LENGTH = 16777216;
    private static long mReceivedDataLen = 0;
    static String mResponseAdditionalData = null;
    static boolean needConfigByResponse = false;

    public static byte[] getPackRequest(Context aContext, Map<String, String> eventMap) throws Exception {
        return getPackRequest(SendService.getInstance().appKey, aContext, eventMap, 1);
    }

    static byte[] getPackRequestByRealtime(Context aContext, Map<String, String> eventMap) throws Exception {
        return getPackRequest(SendService.getInstance().appKey, aContext, eventMap, 2);
    }

    public static byte[] getPackRequest(String appKey, Context aContext, Map<String, String> eventMap) throws Exception {
        return getPackRequest(appKey, aContext, eventMap, 1);
    }

    static byte[] getPackRequestByRealtime(String appKey, Context aContext, Map<String, String> eventMap) throws Exception {
        return getPackRequest(appKey, aContext, eventMap, 2);
    }

    static byte[] getPackRequest(String appKey, Context aContext, Map<String, String> eventMap, int type) throws Exception {
        byte[] payload = GzipUtils.gzip(getPayload(appKey, aContext, eventMap));
        if (payload == null || payload.length >= PAYLOAD_MAX_LENGTH) {
            return null;
        }
        ByteArrayOutputStream baosRequest = new ByteArrayOutputStream();
        baosRequest.write(1);
        baosRequest.write(ByteUtils.intToBytes3(payload.length));
        baosRequest.write(type);
        byte flags = (byte) 9;
        if (needConfigByResponse) {
            flags = (byte) (flags | FLAGS_GET_CONFIG);
        }
        baosRequest.write(flags);
        baosRequest.write(0);
        baosRequest.write(0);
        baosRequest.write(payload);
        byte[] byteArray = baosRequest.toByteArray();
        try {
            baosRequest.close();
            return byteArray;
        } catch (IOException e) {
            LogUtil.e(e.toString());
            return byteArray;
        }
    }

    private static byte[] getPayload(String appKey, Context aContext, Map<String, String> eventMap) throws Exception {
        ByteArrayOutputStream baosPayload = new ByteArrayOutputStream();
        String head = getHead(appKey, aContext);
        if (head == null || head.length() <= 0) {
            baosPayload.write(ByteUtils.intToBytes2(0));
        } else {
            baosPayload.write(ByteUtils.intToBytes2(head.getBytes().length));
            baosPayload.write(head.getBytes());
        }
        if (eventMap != null && eventMap.size() > 0) {
            for (String key : eventMap.keySet()) {
                baosPayload.write(ByteUtils.intToBytes4(Integer.valueOf(key).intValue()));
                String eventLogs = (String) eventMap.get(key);
                if (eventLogs != null) {
                    baosPayload.write(ByteUtils.intToBytes4(eventLogs.getBytes().length));
                    baosPayload.write(eventLogs.getBytes());
                } else {
                    baosPayload.write(ByteUtils.intToBytes4(0));
                }
            }
        }
        byte[] buf = baosPayload.toByteArray();
        try {
            baosPayload.close();
        } catch (IOException e) {
            LogUtil.e(e.toString());
        }
        return buf;
    }

    public static String getHead(String appKey, Context aContext) {
        Context context = aContext;
        String appVersion = SendService.getInstance().appVersion;
        if (appVersion == null) {
            appVersion = BuildConfig.FLAVOR;
        }
        String appVersionSys = "Unknown";
        try {
            appVersionSys = aContext.getPackageManager().getPackageInfo(aContext.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
        }
        String channel = SendService.getInstance().channel;
        if (channel == null) {
            channel = BuildConfig.FLAVOR;
        }
        String head = String.format("ak=%s&av=%s&avsys=%s&c=%s&d=%s&sv=%s", new Object[]{appKey, appVersion, appVersionSys, channel, UTDevice.getUtdid(aContext), RestConstants.UT_SDK_VRESION});
        LogUtil.i("send url :" + head);
        return head;
    }

    static int parseResult(byte[] result) {
        int errCode;
        int leftLen;
        if (result == null || result.length < 12) {
            errCode = -1;
            LogUtil.e("recv errCode UNKNOWN_ERROR");
        } else {
            mReceivedDataLen = (long) result.length;
            if (ByteUtils.bytesToInt(result, 1, 3) + 8 != result.length) {
                errCode = -1;
                LogUtil.e("recv len error");
            } else {
                boolean gzip = false;
                if (1 == (result[5] & FLAGS_GZIP)) {
                    gzip = true;
                }
                errCode = ByteUtils.bytesToInt(result, 8, 4);
                if (result.length - 12 >= 0) {
                    leftLen = result.length - 12;
                } else {
                    leftLen = 0;
                }
                if (leftLen <= 0) {
                    mResponseAdditionalData = null;
                } else if (gzip) {
                    byte[] rawData = new byte[leftLen];
                    System.arraycopy(result, 12, rawData, 0, leftLen);
                    byte[] unGzipData = GzipUtils.unGzip(rawData);
                    mResponseAdditionalData = new String(unGzipData, 0, unGzipData.length);
                } else {
                    mResponseAdditionalData = new String(result, 12, leftLen);
                }
            }
        }
        LogUtil.d("errCode:" + errCode);
        return errCode;
    }
}
