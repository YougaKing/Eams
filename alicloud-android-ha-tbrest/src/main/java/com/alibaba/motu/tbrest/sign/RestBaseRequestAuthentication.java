//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.sign;

import com.alibaba.motu.tbrest.utils.LogUtil;
import com.alibaba.motu.tbrest.utils.MD5Utils;
import com.alibaba.motu.tbrest.utils.RC4;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RestBaseRequestAuthentication {
    private boolean mEncode = false;
    private String mAppkey = null;
    private String mAppSecret = null;
    private byte[] mDefaultAppAppSecret = null;

    public RestBaseRequestAuthentication(String aAppkey, String aAppSecret) {
        this.mAppkey = aAppkey;
        this.mAppSecret = aAppSecret;
    }

    public RestBaseRequestAuthentication(String aAppkey, String aAppSecret, boolean isEncode) {
        this.mAppkey = aAppkey;
        this.mAppSecret = aAppSecret;
        this.mEncode = isEncode;
    }

    public static String calcHmac(byte[] key, byte[] src) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec sk = new SecretKeySpec(key, mac.getAlgorithm());
        mac.init(sk);
        byte[] result = mac.doFinal(src);
        return MD5Utils.toHexString(result);
    }

    public String getAppkey() {
        return this.mAppkey;
    }

    public String getAppSecret() {
        return this.mAppSecret;
    }

    public boolean isEncode() {
        return this.mEncode;
    }

    public String getSign(String toBeSignedStr) {
        if (this.mAppkey != null && this.mAppSecret != null) {
            if (toBeSignedStr == null) {
                return null;
            } else {
                String lHex2 = "";

                try {
                    if (this.mEncode) {
                        lHex2 = calcHmac(this.mAppSecret.getBytes(), toBeSignedStr.getBytes());
                    } else {
                        lHex2 = calcHmac(this.getDefaultAppAppSecret(), toBeSignedStr.getBytes());
                    }
                } catch (Exception var4) {
                }

                return lHex2;
            }
        } else {
            LogUtil.e("There is no appkey,please check it!");
            return null;
        }
    }

    private byte[] getDefaultAppAppSecret() {
        if (this.mDefaultAppAppSecret == null) {
            byte[] newkey = new byte[]{66, 37, 42, -119, 118, -104, -30, 4, -95, 15, -26, -12, -75, -102, 71, 23, -3, -120, -1, -57, 42, 99, -16, -101, 103, -74, 93, -114, 112, -26, -24, -24};
            this.mDefaultAppAppSecret = RC4.rc4(newkey);
        }

        return this.mDefaultAppAppSecret;
    }
}
