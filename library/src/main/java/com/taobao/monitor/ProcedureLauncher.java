package com.taobao.monitor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.TextUtils;

import com.taobao.monitor.procedure.Header;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import com.taobao.monitor.process.ProcessUtils;

import java.lang.reflect.Method;
import java.util.Map;

public class ProcedureLauncher {

    private static boolean init = false;

    public static void init(Context context, Map<String, Object> map) {
        if (!init) {
            init = true;
            ProcedureGlobal.instance().setContext(context);
            generateHeader(context, map);
            ProcedureManagerProxy.PROXY.setReal(ProcedureGlobal.PROCEDURE_MANAGER);
            ProcedureFactoryProxy.PROXY.setReal(ProcedureGlobal.PROCEDURE_FACTORY);
        }
    }

    private static void generateHeader(Context context, Map<String, Object> map) {
        Header.appId = context.getPackageName();
        Header.appKey = instanceofString(map.get("onlineAppKey"), "12278902");
        Header.appBuild = instanceofString(map.get("appBuild"), "");
        Header.appVersion = instanceofString(map.get("appVersion"), new Action<String>() {

            @Override
            public String call() {
                Context contextC = ProcedureGlobal.instance().context();
                try {
                    PackageInfo packageInfo = contextC.getPackageManager().getPackageInfo(contextC.getPackageName(), 0);
                    return packageInfo.versionName;
                } catch (Exception var3) {
                    var3.printStackTrace();
                    return "unknown";
                }
            }
        });
        Header.appPatch = instanceofString(map.get("appPatch"), "");
        Header.channel = instanceofString(map.get("channel"), "");
        Header.utdid = instanceofString(map.get("deviceId"), "");
        Header.brand = Build.BRAND;
        Header.deviceModel = Build.MODEL;
        String[] var2 = osProperty();
        if (!TextUtils.isEmpty(var2[0])) {
            Header.osVersion = var2[0];
            Header.os = var2[1];
        } else {
            Header.osVersion = Build.VERSION.RELEASE;
            Header.os = "android";
        }

        Header.processName = instanceofString(map.get("process"), new Action<String>() {

            @Override
            public String call() {
                return ProcessUtils.processName();
            }
        });
        Header.session = String.valueOf(System.currentTimeMillis());
        Header.ttid = instanceofString(map.get("ttid"), "");
    }

    private static String instanceofString(Object object, String string) {
        return object instanceof String && !TextUtils.isEmpty((String) object) ? (String) object : string;
    }

    private static String instanceofString(Object object, Action<String> action) {
        return object instanceof String && !TextUtils.isEmpty((String) object) ? (String) object : action.call();
    }

    @SuppressLint({"DefaultLocale"})
    private static String[] osProperty() {
        String version = null;
        String name = null;

        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            version = (String) method.invoke((Object) null, "ro.yunos.version");
            name = (String) method.invoke((Object) null, "java.vm.name");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return new String[]{version, name};
    }

    private interface Action<T> {
        T call();
    }
}

