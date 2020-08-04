package com.alibaba.motu.tbrest.rest;

import android.content.Context;
import com.alibaba.motu.tbrest.utils.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class RestSecuritySDKRequestAuthentication {
    private String mAppkey = null;
    private boolean mBInitSecurityCheck = false;
    private Context mContext;
    private int s_secureIndex = 1;
    private Object s_secureSignatureCompObj = null;
    private Object s_securityGuardManagerObj = null;
    private Class s_securityGuardParamContextClz = null;
    private Field s_securityGuardParamContext_appKey = null;
    private Field s_securityGuardParamContext_paramMap = null;
    private Field s_securityGuardParamContext_requestType = null;
    private Method s_signRequestMethod = null;

    public String getAppkey() {
        return this.mAppkey;
    }

    public RestSecuritySDKRequestAuthentication(Context context, String aAppkey) {
        this.mContext = context;
        this.mAppkey = aAppkey;
    }

    private synchronized void _initSecurityCheck() {
        boolean lisThirdParty;
        Method lisOpenMethod;
        if (!this.mBInitSecurityCheck) {
            Class<?> clz = null;
            try {
                clz = Class.forName("com.taobao.wireless.security.sdk.SecurityGuardManager");
                this.s_securityGuardManagerObj = clz.getMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{this.mContext});
                this.s_secureSignatureCompObj = clz.getMethod("getSecureSignatureComp", new Class[0]).invoke(this.s_securityGuardManagerObj, new Object[0]);
            } catch (Throwable th) {
                LogUtil.i("initSecurityCheck failure, It's ok ");
            }
            if (clz != null) {
                try {
                    this.s_securityGuardParamContextClz = Class.forName("com.taobao.wireless.security.sdk.SecurityGuardParamContext");
                    this.s_securityGuardParamContext_appKey = this.s_securityGuardParamContextClz.getDeclaredField("appKey");
                    this.s_securityGuardParamContext_paramMap = this.s_securityGuardParamContextClz.getDeclaredField("paramMap");
                    this.s_securityGuardParamContext_requestType = this.s_securityGuardParamContextClz.getDeclaredField("requestType");
                    lisThirdParty = false;
                    lisOpenMethod = null;
                    lisOpenMethod = clz.getMethod("isOpen", new Class[0]);
                } catch (Throwable th2) {
                    LogUtil.i("initSecurityCheck failure, It's ok");
                }
                if (lisOpenMethod != null) {
                    lisThirdParty = ((Boolean) lisOpenMethod.invoke(this.s_securityGuardManagerObj, new Object[0])).booleanValue();
                } else {
                    Class<?> lBodyCompClz = null;
                    try {
                        lBodyCompClz = Class.forName("com.taobao.wireless.security.sdk.securitybody.ISecurityBodyComponent");
                    } catch (Throwable th3) {
                        LogUtil.i("initSecurityCheck failure, It's ok");
                    }
                    if (lBodyCompClz == null) {
                        lisThirdParty = true;
                    }
                }
                this.s_secureIndex = lisThirdParty ? 1 : 12;
                this.s_signRequestMethod = Class.forName("com.taobao.wireless.security.sdk.securesignature.ISecureSignatureComponent").getMethod("signRequest", new Class[]{this.s_securityGuardParamContextClz});
            }
            this.mBInitSecurityCheck = true;
        }
        return;
    }

    public String getSign(String toBeSignedStr) {
        if (!this.mBInitSecurityCheck) {
            _initSecurityCheck();
        }
        if (this.mAppkey == null) {
            LogUtil.e("RestSecuritySDKRequestAuthentication:getSign There is no appkey,please check it!");
            return null;
        } else if (toBeSignedStr == null) {
            return null;
        } else {
            String str = null;
            if (this.s_securityGuardManagerObj == null || this.s_securityGuardParamContextClz == null || this.s_securityGuardParamContext_appKey == null || this.s_securityGuardParamContext_paramMap == null || this.s_securityGuardParamContext_requestType == null || this.s_signRequestMethod == null || this.s_secureSignatureCompObj == null) {
                return str;
            }
            try {
                Object lsecurityGuardParamContext = this.s_securityGuardParamContextClz.newInstance();
                this.s_securityGuardParamContext_appKey.set(lsecurityGuardParamContext, this.mAppkey);
                ((Map) this.s_securityGuardParamContext_paramMap.get(lsecurityGuardParamContext)).put("INPUT", toBeSignedStr);
                this.s_securityGuardParamContext_requestType.set(lsecurityGuardParamContext, Integer.valueOf(this.s_secureIndex));
                return (String) this.s_signRequestMethod.invoke(this.s_secureSignatureCompObj, new Object[]{lsecurityGuardParamContext});
            } catch (InstantiationException e) {
                e.printStackTrace();
                return str;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                return str;
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
                return str;
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                return str;
            }
        }
    }
}
