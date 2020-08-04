//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.rest;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.alibaba.motu.tbrest.SendService;
import com.alibaba.motu.tbrest.utils.DeviceUtils;
import com.alibaba.motu.tbrest.utils.LogUtil;
import com.alibaba.motu.tbrest.utils.RC4;
import com.alibaba.motu.tbrest.utils.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class RestReqDataBuilder {
    private static long s_session_start_timestamp = System.currentTimeMillis();

    public RestReqDataBuilder() {
    }

    private static String _fixVariableValue(String value) {
        if (StringUtils.isBlank(value)) {
            return "-";
        } else if (value != null && !"".equals(value)) {
            StringBuilder strNoBlank = new StringBuilder(value.length());
            char[] str = value.toCharArray();

            for(int i = 0; i < str.length; ++i) {
                if (str[i] != '\n' && str[i] != '\r' && str[i] != '\t' && str[i] != '|') {
                    strNoBlank.append(str[i]);
                }
            }

            return strNoBlank.toString();
        } else {
            return value;
        }
    }

    public static String buildRequestData(long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        String appkey = SendService.getInstance().appKey;
        return buildRequestData(appkey, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, extData);
    }

    public static String buildRequestData(String userAppKey, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        try {
            if (0 == aEventId) {
                return null;
            } else {
                String utdid = DeviceUtils.getUtdid(SendService.getInstance().context);
                if (utdid == null) {
                    LogUtil.e("get utdid failure, so build report failure, now return");
                    return null;
                } else {
                    String[] networkStatus = DeviceUtils.getNetworkType(SendService.getInstance().context);
                    String accessName = networkStatus[0];
                    String accessSubTypeName = null;
                    if (networkStatus.length > 1 && accessName != null && !"Wi-Fi".equals(accessName)) {
                        accessSubTypeName = networkStatus[1];
                    }

                    long lTimestamp = aTimestamp > 0L ? aTimestamp : System.currentTimeMillis();
                    String lRecordTimestamp = "" + lTimestamp;
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
                    String appKey = _fixVariableValue(userAppKey);
                    String appVersion = _fixVariableValue(SendService.getInstance().appVersion);
                    String channel = _fixVariableValue(SendService.getInstance().channel);
                    String longLoginUserNick = _fixVariableValue(SendService.getInstance().userNick);
                    String userNick = _fixVariableValue(SendService.getInstance().userNick);
                    String phoneNumber = "-";
                    String country = _fixVariableValue(DeviceUtils.getCountry());
                    String language = _fixVariableValue(DeviceUtils.getLanguage());
                    String appId = SendService.getInstance().appId;
                    String os = "a";
                    String osVersion = _fixVariableValue(VERSION.RELEASE);
                    String sdkType = "mini";
                    String sdkReleaseVersion = "1.0";
                    String reserve1 = "" + s_session_start_timestamp;
                    String reserve2 = _fixVariableValue(utdid);
                    String reserve3 = "-";
                    String reserve4 = "-";
                    String reserve5 = "-";
                    String reserves = _fixVariableValue(SendService.getInstance().country);
                    String reserve6 = "-";
                    String lBundleVersion = "";
                    if (!StringUtils.isBlank(lBundleVersion)) {
                        ;
                    }

                    if (appId != null && appId.contains("aliyunos")) {
                        os = "y";
                    }

                    Map<String, String> aLogMap = new HashMap();
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
                    aLogMap.put(RestFieldsScheme.START_SESSION_TIMESTAMP.toString(), "" + s_session_start_timestamp);
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
                }
            }
        } catch (Exception var55) {
            LogUtil.e("UTRestAPI buildTracePostReqDataObj catch!", var55);
            return "";
        }
    }

    public static String assembleWithFullFields(Map<String, String> aLogMap) {
        Map<String, String> lLogMapNew = aLogMap;
        StringBuffer lSb = new StringBuffer();
        RestFieldsScheme[] var3 = RestFieldsScheme.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            RestFieldsScheme lEnumKey = var3[var5];
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
            String lArgs = StringUtils.convertObjectToString(lLogMapNew.get(RestFieldsScheme.ARGS.toString()));
            lSb.append(_fixVariableValue(lArgs));
            lLogMapNew.remove(RestFieldsScheme.ARGS.toString());
            lIsFirstArgFlag = false;
        }

        Iterator lItorKeys = lLogMapNew.keySet().iterator();

        String lKey;
        while(lItorKeys.hasNext()) {
            lKey = (String)lItorKeys.next();
            String lV = null;
            if (lLogMapNew.containsKey(lKey)) {
                lV = StringUtils.convertObjectToString(lLogMapNew.get(lKey));
            }

            if (lIsFirstArgFlag) {
                if ("StackTrace".equals(lKey)) {
                    lSb.append("StackTrace=====>").append(lV);
                } else {
                    lSb.append(_fixVariableValue(lKey)).append("=").append(lV);
                }

                lIsFirstArgFlag = false;
            } else if ("StackTrace".equals(lKey)) {
                lSb.append(",").append("StackTrace=====>").append(lV);
            } else {
                lSb.append(",").append(_fixVariableValue(lKey)).append("=").append(lV);
            }
        }

        lKey = lSb.toString();
        if (!StringUtils.isEmpty(lKey) && lKey.endsWith("||")) {
            lKey = lKey + "-";
        }

        return lKey;
    }

    public static Map<String, Object> buildPostRequestMap(String lReqData) {
        if (StringUtils.isEmpty(lReqData)) {
            return null;
        } else {
            Map<String, String> newReqData = new HashMap();
            newReqData.put("stm_x", lReqData);
            return buildPostRequestMap((Map)newReqData);
        }
    }

    public static Map<String, Object> buildPostRequestMap(Map<String, String> lReqData) {
        if (null != lReqData && lReqData.size() > 0) {
            try {
                Map<String, Object> lBReqData = new HashMap();
                Iterator var2 = lReqData.keySet().iterator();

                while(true) {
                    String lRDKey;
                    String lRDValue;
                    do {
                        do {
                            if (!var2.hasNext()) {
                                return lBReqData;
                            }

                            lRDKey = (String)var2.next();
                            lRDValue = (String)lReqData.get(lRDKey);
                        } while(StringUtils.isEmpty(lRDKey));
                    } while(StringUtils.isEmpty(lRDValue));

                    ByteArrayOutputStream lBaos = null;
                    GZIPOutputStream lOutputStream = null;

                    try {
                        lBaos = new ByteArrayOutputStream();
                        lOutputStream = new GZIPOutputStream(lBaos);
                        lOutputStream.write(lRDValue.getBytes("UTF-8"));
                        lOutputStream.flush();
                        lOutputStream.close();
                        byte[] lGZIPResult = lBaos.toByteArray();
                        byte[] lBRC4ReqContent = RC4.rc4(lGZIPResult);
                        lBReqData.put(lRDKey, lBRC4ReqContent);
                    } catch (IOException var9) {
                        if (null != lOutputStream) {
                            lOutputStream.close();
                        }

                        if (null != lBaos) {
                            lBaos.close();
                        }
                    }
                }
            } catch (Exception var10) {
                LogUtil.e("buildPostRequestMap", var10);
                return null;
            }
        } else {
            return null;
        }
    }

    public static RestReqDataBuildResult buildMonkeyPostReqDataObj(String URL, Context aContext, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        String appkey = SendService.getInstance().appKey;
        return buildMonkeyPostReqDataObj(appkey, URL, aContext, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, extData);
    }

    public static RestReqDataBuildResult buildMonkeyPostReqDataObj(String userKey, String URL, Context aContext, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> extData) {
        try {
            if (0 == aEventId) {
                return null;
            } else {
                String utdid = DeviceUtils.getUtdid(SendService.getInstance().context);
                if (utdid == null) {
                    LogUtil.e("get utdid failure, so build report failure, now return");
                    return null;
                } else {
                    String[] networkStatus = DeviceUtils.getNetworkType(SendService.getInstance().context);
                    String accessName = networkStatus[0];
                    String accessSubTypeName = null;
                    if (networkStatus.length > 1 && accessName != null && !"Wi-Fi".equals(accessName)) {
                        accessSubTypeName = networkStatus[1];
                    }

                    long lTimestamp = aTimestamp > 0L ? aTimestamp : System.currentTimeMillis();
                    String lRecordTimestamp = "" + lTimestamp;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String lRecordDate = sdf.format(lTimestamp);
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
                    if (appId != null && appId.contains("aliyunos")) {
                        os = "aliyunos";
                    }

                    String osVersion = _fixVariableValue(VERSION.RELEASE);
                    String sdkType = "mini";
                    String sdkReleaseVersion = "1.0";
                    String reserve1 = "" + s_session_start_timestamp;
                    String reserve2 = _fixVariableValue(utdid);
                    String reserve3 = "-";
                    String reserve4 = "-";
                    String reserve5 = "-";
                    String reserve6 = "-";
                    String lBundleVersion = "";
                    if (!StringUtils.isBlank(lBundleVersion)) {
                        ;
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
                    Map<String, Object> lBReqData = new HashMap();
                    byte[] byteReqData = lReqData.getBytes();
                    lBReqData.put("stm_x", byteReqData);
                    RestReqDataBuildResult lResult = new RestReqDataBuildResult();
                    String lReqUrl = RestUrlWrapper.getSignedTransferUrl(URL, (Map)null, lBReqData, aContext, appKey, channel, appVersion, os, "", reserve2);
                    lResult.setReqUrl(lReqUrl);
                    lResult.setPostReqData(lBReqData);
                    return lResult;
                }
            }
        } catch (Exception var64) {
            LogUtil.e("UTRestAPI buildTracePostReqDataObj catch!", var64);
            return null;
        }
    }
}
