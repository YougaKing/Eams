package com.taobao.monitor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.taobao.monitor.procedure.Header;
import com.taobao.monitor.procedure.ProcedureFactoryProxy;
import com.taobao.monitor.procedure.ProcedureManagerProxy;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: ProcedureLauncher */
public class ProcedureLauncher {
    private static boolean init = false;

    /* compiled from: ProcedureLauncher */
    private interface Action<T> {
        T call();
    }

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
        Header.appKey = getString(map.get("onlineAppKey"), "12278902");
        Header.appBuild = getString(map.get("appBuild"), "");
        Header.appVersion = getString(map.get("appVersion"), (Action<String>) new Action<String>() {
            /* renamed from: a */
            public String call() {
                Context context = ProcedureGlobal.instance().context();
                try {
                    return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "unknown";
                }
            }
        });
        Header.appPatch = getString(map.get("appPatch"), "");
        Header.channel = getString(map.get("channel"), "");
        Header.utdid = getString(map.get("deviceId"), "");
        Header.brand = Build.BRAND;
        Header.deviceModel = Build.MODEL;
        String[] a2 = systemProperties();
        if (!TextUtils.isEmpty(a2[0])) {
            Header.osVersion = a2[0];
            Header.os = a2[1];
        } else {
            Header.osVersion = VERSION.RELEASE;
            Header.os = "android";
        }
        Header.processName = getString(map.get("process"), (Action<String>) new Action<String>() {
            /* renamed from: a */
            public String call() {
                return com.taobao.monitor.process.a.b();
            }
        });
        Header.session = String.valueOf(System.currentTimeMillis());
        Header.ttid = getString(map.get("ttid"), "");
    }

    private static String getString(Object obj, String str) {
        if (!(obj instanceof String) || TextUtils.isEmpty((String) obj)) {
            return str;
        }
        return (String) obj;
    }

    private static String getString(Object obj, Action<String> aVar) {
        if (!(obj instanceof String) || TextUtils.isEmpty((String) obj)) {
            return (String) aVar.call();
        }
        return (String) obj;
    }

    @SuppressLint({"DefaultLocale"})
    private static String[] systemProperties() {
        String str;
        String str2;
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            str = (String) method.invoke(null, new Object[]{"ro.yunos.version"});
            try {
                str2 = (String) method.invoke(null, new Object[]{"java.vm.name"});
            } catch (Exception e) {
                e.printStackTrace();
                str2 = null;
                return new String[]{str, str2};
            }
        } catch (Exception e) {
            str = null;
            e.printStackTrace();
            str2 = null;
            return new String[]{str, str2};
        }
        return new String[]{str, str2};
    }
}
