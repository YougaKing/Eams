//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.rest;

import android.content.Context;
import com.alibaba.motu.tbrest.utils.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class RestSecuritySDKRequestAuthentication {
    private Context mContext;
    private String mAppkey = null;
    private Object s_securityGuardManagerObj = null;
    private Object s_secureSignatureCompObj = null;
    private Class s_securityGuardParamContextClz = null;
    private Field s_securityGuardParamContext_appKey = null;
    private Field s_securityGuardParamContext_paramMap = null;
    private Field s_securityGuardParamContext_requestType = null;
    private Method s_signRequestMethod = null;
    private int s_secureIndex = 1;
    private boolean mBInitSecurityCheck = false;

    public String getAppkey() {
        return this.mAppkey;
    }

    public RestSecuritySDKRequestAuthentication(Context context, String aAppkey) {
        this.mContext = context;
        this.mAppkey = aAppkey;
    }

    private synchronized void _initSecurityCheck() {
        if (!this.mBInitSecurityCheck) {
            Class clz = null;

            Method lisOpenMethod;
            try {
                clz = Class.forName("com.taobao.wireless.security.sdk.SecurityGuardManager");
                Method lgetInstanceMethod = clz.getMethod("getInstance", Context.class);
                this.s_securityGuardManagerObj = lgetInstanceMethod.invoke((Object)null, this.mContext);
                lisOpenMethod = clz.getMethod("getSecureSignatureComp");
                this.s_secureSignatureCompObj = lisOpenMethod.invoke(this.s_securityGuardManagerObj);
            } catch (Throwable var9) {
                LogUtil.i("initSecurityCheck failure, It's ok ");
            }

            try {
                if (clz != null) {
                    this.s_securityGuardParamContextClz = Class.forName("com.taobao.wireless.security.sdk.SecurityGuardParamContext");
                    this.s_securityGuardParamContext_appKey = this.s_securityGuardParamContextClz.getDeclaredField("appKey");
                    this.s_securityGuardParamContext_paramMap = this.s_securityGuardParamContextClz.getDeclaredField("paramMap");
                    this.s_securityGuardParamContext_requestType = this.s_securityGuardParamContextClz.getDeclaredField("requestType");
                    boolean lisThirdParty = false;
                    lisOpenMethod = null;

                    try {
                        lisOpenMethod = clz.getMethod("isOpen");
                    } catch (Throwable var7) {
                        LogUtil.i("initSecurityCheck failure, It's ok");
                    }

                    Class lBodyCompClz;
                    if (lisOpenMethod != null) {
                        lisThirdParty = (Boolean)lisOpenMethod.invoke(this.s_securityGuardManagerObj);
                    } else {
                        lBodyCompClz = null;

                        try {
                            lBodyCompClz = Class.forName("com.taobao.wireless.security.sdk.securitybody.ISecurityBodyComponent");
                        } catch (Throwable var6) {
                            LogUtil.i("initSecurityCheck failure, It's ok");
                        }

                        if (lBodyCompClz == null) {
                            lisThirdParty = true;
                        }
                    }

                    this.s_secureIndex = lisThirdParty ? 1 : 12;
                    lBodyCompClz = Class.forName("com.taobao.wireless.security.sdk.securesignature.ISecureSignatureComponent");
                    this.s_signRequestMethod = lBodyCompClz.getMethod("signRequest", this.s_securityGuardParamContextClz);
                }
            } catch (Throwable var8) {
                LogUtil.i("initSecurityCheck failure, It's ok");
            }

            this.mBInitSecurityCheck = true;
        }
    }

    public String getSign(String toBeSignedStr) {
        if (!this.mBInitSecurityCheck) {
            this._initSecurityCheck();
        }

        if (this.mAppkey == null) {
            LogUtil.e("RestSecuritySDKRequestAuthentication:getSign There is no appkey,please check it!");
            return null;
        } else if (toBeSignedStr == null) {
            return null;
        } else {
            String lSignedStr = null;
            if (this.s_securityGuardManagerObj != null && this.s_securityGuardParamContextClz != null && this.s_securityGuardParamContext_appKey != null && this.s_securityGuardParamContext_paramMap != null && this.s_securityGuardParamContext_requestType != null && this.s_signRequestMethod != null && this.s_secureSignatureCompObj != null) {
                try {
                    Object lsecurityGuardParamContext = this.s_securityGuardParamContextClz.newInstance();
                    this.s_securityGuardParamContext_appKey.set(lsecurityGuardParamContext, this.mAppkey);
                    Map lParamMap = (Map)this.s_securityGuardParamContext_paramMap.get(lsecurityGuardParamContext);
                    lParamMap.put("INPUT", toBeSignedStr);
                    this.s_securityGuardParamContext_requestType.set(lsecurityGuardParamContext, this.s_secureIndex);
                    lSignedStr = (String)this.s_signRequestMethod.invoke(this.s_secureSignatureCompObj, lsecurityGuardParamContext);
                } catch (InstantiationException var5) {
                    var5.printStackTrace();
                } catch (IllegalAccessException var6) {
                    var6.printStackTrace();
                } catch (IllegalArgumentException var7) {
                    var7.printStackTrace();
                } catch (InvocationTargetException var8) {
                    var8.printStackTrace();
                }
            }

            return lSignedStr;
        }
    }
}
