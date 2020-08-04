package com.alibaba.motu.tbrest.rest;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.alibaba.motu.tbrest.BuildConfig;
import com.alibaba.motu.tbrest.SendService;
import com.alibaba.motu.tbrest.utils.DeviceUtils;
import com.alibaba.motu.tbrest.utils.LogUtil;
import com.alibaba.motu.tbrest.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class RestReqDataBuilder {
    private static long s_session_start_timestamp = System.currentTimeMillis();

    private static String _fixVariableValue(String value) {
        if (StringUtils.isBlank(value)) {
            return "-";
        }
        if (value == null || BuildConfig.FLAVOR.equals(value)) {
            return value;
        }
        StringBuilder strNoBlank = new StringBuilder(value.length());
        char[] str = value.toCharArray();
        for (int i = 0; i < str.length; i++) {
            if (!(str[i] == 10 || str[i] == 13 || str[i] == 9 || str[i] == '|')) {
                strNoBlank.append(str[i]);
            }
        }
        return strNoBlank.toString();
    }

    public static String buildRequestData(long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        return buildRequestData(SendService.getInstance().appKey, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, extData);
    }

    public static String buildRequestData(String userAppKey, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        if (aEventId == 0) {
            return null;
        }
        try {
            String utdid = DeviceUtils.getUtdid(SendService.getInstance().context);
            if (utdid == null) {
                LogUtil.e("get utdid failure, so build report failure, now return");
                return null;
            }
            String[] networkStatus = DeviceUtils.getNetworkType(SendService.getInstance().context);
            String accessName = networkStatus[0];
            String accessSubTypeName = null;
            if (networkStatus.length > 1 && accessName != null && !DeviceUtils.NETWORK_CLASS_WIFI.equals(accessName)) {
                accessSubTypeName = networkStatus[1];
            }
            String lRecordTimestamp = BuildConfig.FLAVOR + (aTimestamp > 0 ? aTimestamp : System.currentTimeMillis());
            String lPage = _fixVariableValue(aPage);
            String lEventId = _fixVariableValue(String.valueOf(aEventId));
            String lArg1 = _fixVariableValue(StringUtils.convertObjectToString(aArg1));
            String lArg2 = _fixVariableValue(StringUtils.convertObjectToString(aArg2));
            String lArg3 = _fixVariableValue(StringUtils.convertObjectToString(aArg3));
            String lArgs = _fixVariableValue(StringUtils.convertMapToString(extData));
            String str = "5.0.1";
            String imei = _fixVariableValue(DeviceUtils.getImei(SendService.getInstance().context));
            String imsi = _fixVariableValue(DeviceUtils.getImsi(SendService.getInstance().context));
            String brand = _fixVariableValue(Build.BRAND);
            String _fixVariableValue = _fixVariableValue(DeviceUtils.getCpuName());
            String _fixVariableValue2 = _fixVariableValue(imei);
            String deviceModel = _fixVariableValue(Build.MODEL);
            String resolution = _fixVariableValue(DeviceUtils.getResolution(SendService.getInstance().context));
            String carrier = _fixVariableValue(DeviceUtils.getCarrier(SendService.getInstance().context));
            String access = _fixVariableValue(accessName);
            String accessSubType = _fixVariableValue(accessSubTypeName);
            String appKey = _fixVariableValue(userAppKey);
            String appVersion = _fixVariableValue(SendService.getInstance().appVersion);
            String channel = _fixVariableValue(SendService.getInstance().channel);
            String longLoginUserNick = _fixVariableValue(SendService.getInstance().userNick);
            String userNick = _fixVariableValue(SendService.getInstance().userNick);
            String str2 = "-";
            String _fixVariableValue3 = _fixVariableValue(DeviceUtils.getCountry());
            String language = _fixVariableValue(DeviceUtils.getLanguage());
            String appId = SendService.getInstance().appId;
            String os = "a";
            String osVersion = _fixVariableValue(VERSION.RELEASE);
            String sdkType = "mini";
            String sdkReleaseVersion = BuildConfig.VERSION_NAME;
            String str3 = BuildConfig.FLAVOR + s_session_start_timestamp;
            String reserve2 = _fixVariableValue(utdid);
            String reserve3 = "-";
            String reserve4 = "-";
            String reserve5 = "-";
            String reserves = _fixVariableValue(SendService.getInstance().country);
            String str4 = "-";
            String lBundleVersion = BuildConfig.FLAVOR;
            if (!StringUtils.isBlank(lBundleVersion)) {
                String reserve6 = lBundleVersion;
            }
            if (appId != null && appId.contains("aliyunos")) {
                os = "y";
            }
            Map<String, String> aLogMap = new HashMap<>();
            aLogMap.put(RestFieldsScheme.IMEI.toString(), imei);
            aLogMap.put(RestFieldsScheme.IMSI.toString(), imsi);
            aLogMap.put(RestFieldsScheme.BRAND.toString(), brand);
            aLogMap.put(RestFieldsScheme.DEVICE_MODEL.toString(), deviceModel);
            aLogMap.put(RestFieldsScheme.RESOLUTION.toString(), resolution);
            aLogMap.put(RestFieldsScheme.CARRIER.toString(), carrier);
            aLogMap.put(RestFieldsScheme.ACCESS.toString(), access);
            aLogMap.put(RestFieldsScheme.ACCESS_SUBTYPE.toString(), accessSubType);
            aLogMap.put(RestFieldsScheme.CHANNEL.toString(), channel);
            aLogMap.put(RestFieldsScheme.APPKEY.toString(), appKey);
            aLogMap.put(RestFieldsScheme.APPVERSION.toString(), appVersion);
            aLogMap.put(RestFieldsScheme.LL_USERNICK.toString(), longLoginUserNick);
            aLogMap.put(RestFieldsScheme.USERNICK.toString(), userNick);
            aLogMap.put(RestFieldsScheme.LL_USERID.toString(), "-");
            aLogMap.put(RestFieldsScheme.USERID.toString(), "-");
            aLogMap.put(RestFieldsScheme.LANGUAGE.toString(), language);
            aLogMap.put(RestFieldsScheme.OS.toString(), os);
            aLogMap.put(RestFieldsScheme.OSVERSION.toString(), osVersion);
            aLogMap.put(RestFieldsScheme.SDKVERSION.toString(), sdkReleaseVersion);
            aLogMap.put(RestFieldsScheme.START_SESSION_TIMESTAMP.toString(), BuildConfig.FLAVOR + s_session_start_timestamp);
            aLogMap.put(RestFieldsScheme.UTDID.toString(), reserve2);
            aLogMap.put(RestFieldsScheme.SDKTYPE.toString(), sdkType);
            aLogMap.put(RestFieldsScheme.RESERVE2.toString(), reserve2);
            aLogMap.put(RestFieldsScheme.RESERVE3.toString(), reserve3);
            aLogMap.put(RestFieldsScheme.RESERVE4.toString(), reserve4);
            aLogMap.put(RestFieldsScheme.RESERVE5.toString(), reserve5);
            aLogMap.put(RestFieldsScheme.RESERVES.toString(), reserves);
            aLogMap.put(RestFieldsScheme.RECORD_TIMESTAMP.toString(), lRecordTimestamp);
            aLogMap.put(RestFieldsScheme.PAGE.toString(), lPage);
            aLogMap.put(RestFieldsScheme.EVENTID.toString(), lEventId);
            aLogMap.put(RestFieldsScheme.ARG1.toString(), lArg1);
            aLogMap.put(RestFieldsScheme.ARG2.toString(), lArg2);
            aLogMap.put(RestFieldsScheme.ARG3.toString(), lArg3);
            aLogMap.put(RestFieldsScheme.ARGS.toString(), lArgs);
            return assembleWithFullFields(aLogMap);
        } catch (Exception e) {
            LogUtil.e("UTRestAPI buildTracePostReqDataObj catch!", e);
            return BuildConfig.FLAVOR;
        }
    }

    public static String assembleWithFullFields(Map<String, String> aLogMap) {
        RestFieldsScheme[] values;
        Map<String, String> lLogMapNew = aLogMap;
        StringBuffer lSb = new StringBuffer();
        for (RestFieldsScheme lEnumKey : RestFieldsScheme.values()) {
            if (lEnumKey == RestFieldsScheme.ARGS) {
                break;
            }
            String lV = null;
            if (lLogMapNew.containsKey(lEnumKey.toString())) {
                lV = StringUtils.convertObjectToString(lLogMapNew.get(lEnumKey.toString()));
                lLogMapNew.remove(lEnumKey.toString());
            }
            lSb.append(_fixVariableValue(lV)).append("||");
        }
        boolean lIsFirstArgFlag = true;
        if (lLogMapNew.containsKey(RestFieldsScheme.ARGS.toString())) {
            lSb.append(_fixVariableValue(StringUtils.convertObjectToString(lLogMapNew.get(RestFieldsScheme.ARGS.toString()))));
            lLogMapNew.remove(RestFieldsScheme.ARGS.toString());
            lIsFirstArgFlag = false;
        }
        for (String lKey : lLogMapNew.keySet()) {
            String lV2 = null;
            if (lLogMapNew.containsKey(lKey)) {
                lV2 = StringUtils.convertObjectToString(lLogMapNew.get(lKey));
            }
            if (lIsFirstArgFlag) {
                if ("StackTrace".equals(lKey)) {
                    lSb.append("StackTrace=====>").append(lV2);
                } else {
                    lSb.append(_fixVariableValue(lKey)).append("=").append(lV2);
                }
                lIsFirstArgFlag = false;
            } else if ("StackTrace".equals(lKey)) {
                lSb.append(",").append("StackTrace=====>").append(lV2);
            } else {
                lSb.append(",").append(_fixVariableValue(lKey)).append("=").append(lV2);
            }
        }
        String lLogResult = lSb.toString();
        if (StringUtils.isEmpty(lLogResult) || !lLogResult.endsWith("||")) {
            return lLogResult;
        }
        return lLogResult + "-";
    }

    public static Map<String, Object> buildPostRequestMap(String lReqData) {
        if (StringUtils.isEmpty(lReqData)) {
            return null;
        }
        Map<String, String> newReqData = new HashMap<>();
        newReqData.put("stm_x", lReqData);
        return buildPostRequestMap(newReqData);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0062 A[SYNTHETIC, Splitter:B:24:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0067 A[Catch:{ Exception -> 0x006b }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0018 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.String, java.lang.Object> buildPostRequestMap(java.util.Map<java.lang.String, java.lang.String> r13) {
        /*
            r10 = 0
            if (r13 == 0) goto L_0x0009
            int r11 = r13.size()
            if (r11 > 0) goto L_0x000b
        L_0x0009:
            r2 = r10
        L_0x000a:
            return r2
        L_0x000b:
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ Exception -> 0x006b }
            r2.<init>()     // Catch:{ Exception -> 0x006b }
            java.util.Set r11 = r13.keySet()     // Catch:{ Exception -> 0x006b }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ Exception -> 0x006b }
        L_0x0018:
            boolean r12 = r11.hasNext()     // Catch:{ Exception -> 0x006b }
            if (r12 == 0) goto L_0x000a
            java.lang.Object r8 = r11.next()     // Catch:{ Exception -> 0x006b }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x006b }
            java.lang.Object r9 = r13.get(r8)     // Catch:{ Exception -> 0x006b }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Exception -> 0x006b }
            boolean r12 = com.alibaba.motu.tbrest.utils.StringUtils.isEmpty(r8)     // Catch:{ Exception -> 0x006b }
            if (r12 != 0) goto L_0x0018
            boolean r12 = com.alibaba.motu.tbrest.utils.StringUtils.isEmpty(r9)     // Catch:{ Exception -> 0x006b }
            if (r12 != 0) goto L_0x0018
            r3 = 0
            r6 = 0
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0073 }
            r4.<init>()     // Catch:{ IOException -> 0x0073 }
            java.util.zip.GZIPOutputStream r7 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x0075 }
            r7.<init>(r4)     // Catch:{ IOException -> 0x0075 }
            java.lang.String r12 = "UTF-8"
            byte[] r12 = r9.getBytes(r12)     // Catch:{ IOException -> 0x005d }
            r7.write(r12)     // Catch:{ IOException -> 0x005d }
            r7.flush()     // Catch:{ IOException -> 0x005d }
            r7.close()     // Catch:{ IOException -> 0x005d }
            byte[] r5 = r4.toByteArray()     // Catch:{ IOException -> 0x005d }
            byte[] r1 = com.alibaba.motu.tbrest.utils.RC4.rc4(r5)     // Catch:{ IOException -> 0x005d }
            r2.put(r8, r1)     // Catch:{ IOException -> 0x005d }
            goto L_0x0018
        L_0x005d:
            r0 = move-exception
            r6 = r7
            r3 = r4
        L_0x0060:
            if (r6 == 0) goto L_0x0065
            r6.close()     // Catch:{ Exception -> 0x006b }
        L_0x0065:
            if (r3 == 0) goto L_0x0018
            r3.close()     // Catch:{ Exception -> 0x006b }
            goto L_0x0018
        L_0x006b:
            r0 = move-exception
            java.lang.String r11 = "buildPostRequestMap"
            com.alibaba.motu.tbrest.utils.LogUtil.e(r11, r0)
            r2 = r10
            goto L_0x000a
        L_0x0073:
            r0 = move-exception
            goto L_0x0060
        L_0x0075:
            r0 = move-exception
            r3 = r4
            goto L_0x0060
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.motu.tbrest.rest.RestReqDataBuilder.buildPostRequestMap(java.util.Map):java.util.Map");
    }

    public static RestReqDataBuildResult buildMonkeyPostReqDataObj(String URL, Context aContext, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        return buildMonkeyPostReqDataObj(SendService.getInstance().appKey, URL, aContext, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, extData);
    }

    public static RestReqDataBuildResult buildMonkeyPostReqDataObj(String userKey, String URL, Context aContext, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        if (aEventId == 0) {
            return null;
        }
        try {
            String utdid = DeviceUtils.getUtdid(SendService.getInstance().context);
            if (utdid == null) {
                LogUtil.e("get utdid failure, so build report failure, now return");
                return null;
            }
            String[] networkStatus = DeviceUtils.getNetworkType(SendService.getInstance().context);
            String accessName = networkStatus[0];
            String accessSubTypeName = null;
            if (networkStatus.length > 1 && accessName != null && !DeviceUtils.NETWORK_CLASS_WIFI.equals(accessName)) {
                accessSubTypeName = networkStatus[1];
            }
            long lTimestamp = aTimestamp > 0 ? aTimestamp : System.currentTimeMillis();
            String lRecordTimestamp = BuildConfig.FLAVOR + lTimestamp;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lRecordDate = simpleDateFormat.format(Long.valueOf(lTimestamp));
            String lPage = _fixVariableValue(aPage);
            String lEventId = _fixVariableValue(String.valueOf(aEventId));
            String lArg1 = _fixVariableValue(StringUtils.convertObjectToString(aArg1));
            String lArg2 = _fixVariableValue(StringUtils.convertObjectToString(aArg2));
            String lArg3 = _fixVariableValue(StringUtils.convertObjectToString(aArg3));
            String lArgs = _fixVariableValue(StringUtils.convertMapToString(extData));
            String ndkReleaseVersion = "5.0.1";
            String imei = _fixVariableValue(DeviceUtils.getImei(SendService.getInstance().context));
            String imsi = _fixVariableValue(DeviceUtils.getImsi(SendService.getInstance().context));
            String brand = _fixVariableValue(Build.BRAND);
            String cpu = _fixVariableValue(DeviceUtils.getCpuName());
            String deviceId = _fixVariableValue(imei);
            String deviceModel = _fixVariableValue(Build.MODEL);
            String resolution = _fixVariableValue(DeviceUtils.getResolution(SendService.getInstance().context));
            String carrier = _fixVariableValue(DeviceUtils.getCarrier(SendService.getInstance().context));
            String access = _fixVariableValue(accessName);
            String accessSubType = _fixVariableValue(accessSubTypeName);
            String appKey = _fixVariableValue(userKey);
            String appVersion = _fixVariableValue(SendService.getInstance().appVersion);
            String channel = _fixVariableValue(SendService.getInstance().channel);
            String longLoginUserNick = _fixVariableValue(SendService.getInstance().userNick);
            String userNick = _fixVariableValue(SendService.getInstance().userNick);
            String phoneNumber = "-";
            String country = _fixVariableValue(DeviceUtils.getCountry());
            String language = _fixVariableValue(DeviceUtils.getLanguage());
            String appId = SendService.getInstance().appId;
            String os = "Android";
            if (appId != null) {
                if (appId.contains("aliyunos")) {
                    os = "aliyunos";
                }
            }
            String osVersion = _fixVariableValue(VERSION.RELEASE);
            String sdkType = "mini";
            String sdkReleaseVersion = BuildConfig.VERSION_NAME;
            String reserve1 = BuildConfig.FLAVOR + s_session_start_timestamp;
            String reserve2 = _fixVariableValue(utdid);
            String reserve3 = "-";
            String reserve4 = "-";
            String reserve5 = "-";
            String str = "-";
            String lBundleVersion = BuildConfig.FLAVOR;
            if (!StringUtils.isBlank(lBundleVersion)) {
                String reserve6 = lBundleVersion;
            }
            StringBuffer lSb = new StringBuffer();
            String split = "||";
            lSb.append(ndkReleaseVersion).append(split);
            lSb.append(imei).append(split);
            lSb.append(imsi).append(split);
            lSb.append(brand).append(split);
            lSb.append(cpu).append(split);
            lSb.append(deviceId).append(split);
            lSb.append(deviceModel).append(split);
            lSb.append(resolution).append(split);
            lSb.append(carrier).append(split);
            lSb.append(access).append(split);
            lSb.append(accessSubType).append(split);
            lSb.append(channel).append(split);
            lSb.append(appKey).append(split);
            lSb.append(appVersion).append(split);
            lSb.append(longLoginUserNick).append(split);
            lSb.append(userNick).append(split);
            lSb.append(phoneNumber).append(split);
            lSb.append(country).append(split);
            lSb.append(language).append(split);
            lSb.append(os).append(split);
            lSb.append(osVersion).append(split);
            lSb.append(sdkType).append(split);
            lSb.append(sdkReleaseVersion).append(split);
            lSb.append(reserve1).append(split);
            lSb.append(reserve2).append(split);
            lSb.append(reserve3).append(split);
            lSb.append(reserve4).append(split);
            lSb.append(reserve5).append(split);
            lSb.append("-").append(split);
            lSb.append(lRecordDate).append(split);
            lSb.append(lRecordTimestamp).append(split);
            lSb.append(lPage).append(split);
            lSb.append(lEventId).append(split);
            lSb.append(lArg1).append(split);
            lSb.append(lArg2).append(split);
            lSb.append(lArg3).append(split);
            lSb.append(lArgs);
            String lReqData = lSb.toString();
            Map<String, Object> lBReqData = new HashMap<>();
            lBReqData.put("stm_x", lReqData.getBytes());
            RestReqDataBuildResult lResult = new RestReqDataBuildResult();
            lResult.setReqUrl(RestUrlWrapper.getSignedTransferUrl(URL, null, lBReqData, aContext, appKey, channel, appVersion, os, BuildConfig.FLAVOR, reserve2));
            lResult.setPostReqData(lBReqData);
            return lResult;
        } catch (Exception e) {
            LogUtil.e("UTRestAPI buildTracePostReqDataObj catch!", e);
            return null;
        }
    }
}
