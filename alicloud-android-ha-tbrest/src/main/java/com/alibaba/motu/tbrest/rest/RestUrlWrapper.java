//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.rest;

import android.content.Context;
import com.alibaba.motu.tbrest.utils.LogUtil;
import com.alibaba.motu.tbrest.utils.MD5Utils;
import com.alibaba.motu.tbrest.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class RestUrlWrapper {
    public static final String FIELD_T = "t";
    public static final String FIELD_APPKEY = "appkey";
    public static final String FIELD_CHANNEL = "channel";
    public static final String FIELD_APPVERSION = "app_version";
    public static final String FIELD_PLATFORM = "platform";
    public static final String FIELD_SDK_VERSION = "sdk_version";
    public static final String FIELD_UTDID = "utdid";
    public static final String FIELD_V = "v";
    static boolean enableSecuritySDK = false;
    static Context mContext;

    public RestUrlWrapper() {
    }

    public static void enableSecuritySDK() {
        enableSecuritySDK = true;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static String getSignedTransferUrl(String pUrl, Map<String, Object> pQueryMap, Map<String, Object> pDataMap, Context aContext, String aAppkey, String aChannel, String aAppVersion, String aPlatform, String aSdkVersion, String aUtdid) throws Exception {
        String lSignDataStr = "";
        if (null != pDataMap && pDataMap.size() > 0) {
            Set<String> keys = pDataMap.keySet();
            String[] lKeysArr = new String[keys.size()];
            keys.toArray(lKeysArr);
            lKeysArr = RestKeyArraySorter.getInstance().sortResourcesList(lKeysArr, true);
            String[] var13 = lKeysArr;
            int var14 = lKeysArr.length;

            for(int var15 = 0; var15 < var14; ++var15) {
                String key = var13[var15];
                byte[] lValue = (byte[])((byte[])pDataMap.get(key));
                lSignDataStr = lSignDataStr + key + MD5Utils.getMd5Hex(lValue);
            }
        }

        String lUrl;
        try {
            lUrl = _wrapUrl(pUrl, (String)null, (String)null, lSignDataStr, aContext, aAppkey, aChannel, aAppVersion, aPlatform, aSdkVersion, aUtdid);
        } catch (Exception var18) {
            lUrl = _wrapUrl(RestConstants.getTransferUrl(), (String)null, (String)null, lSignDataStr, aContext, aAppkey, aChannel, aAppVersion, aPlatform, aSdkVersion, aUtdid);
        }

        return lUrl;
    }

    private static String _wrapUrl(String url, String pUrlQueryStr, String pSignQueryStr, String pSignDataStr, Context aContext, String aAppkey, String aChannel, String aAppVersion, String aPlatform, String aSdkVersion, String aUtdid) throws Exception {
        String lAppkey = aAppkey;
        String lChannel = aChannel;
        String lAppVersion = aAppVersion;
        String lPlatform = aPlatform;
        String lSdkVersion = "4.1.0";
        String lUtdid = aUtdid;
        String v = "3.0";
        String t = String.valueOf(System.currentTimeMillis());
        String lNewUrl = null;
        String lisSecureflag = "";
        String lSignValue = "";
        String lUrlQueryStr;
        if (enableSecuritySDK && null != mContext) {
            try {
                lUrlQueryStr = lAppkey + lChannel + lAppVersion + lPlatform + lSdkVersion + lUtdid + t + v + lisSecureflag + (pSignQueryStr == null ? "" : pSignQueryStr) + (pSignDataStr == null ? "" : pSignDataStr);
                lUrlQueryStr = MD5Utils.getMd5Hex(lUrlQueryStr.getBytes());
                RestSecuritySDKRequestAuthentication lRequestAuthentication = new RestSecuritySDKRequestAuthentication(mContext, aAppkey);
                lSignValue = lRequestAuthentication.getSign(lUrlQueryStr);
                if (StringUtils.isNotBlank(lSdkVersion)) {
                    lisSecureflag = "1";
                }
            } catch (Exception var25) {
                LogUtil.w("security sdk signed", var25);
            }
        }

        lUrlQueryStr = "";
        if (!StringUtils.isEmpty(pUrlQueryStr)) {
            lUrlQueryStr = pUrlQueryStr + "&";
        }

        lNewUrl = String.format("%s?%sak=%s&av=%s&c=%s&v=%s&s=%s&d=%s&sv=%s&p=%s&t=%s&u=%s&is=%s", url, lUrlQueryStr, _getEncoded(aAppkey), _getEncoded(aAppVersion), _getEncoded(aChannel), _getEncoded(v), _getEncoded(lSignValue), _getEncoded(aUtdid), lSdkVersion, aPlatform, t, "", lisSecureflag);
        return lNewUrl;
    }

    private static String _getEncoded(String aValue) {
        if (null == aValue) {
            return "";
        } else {
            try {
                return URLEncoder.encode(aValue, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return aValue;
            }
        }
    }
}
