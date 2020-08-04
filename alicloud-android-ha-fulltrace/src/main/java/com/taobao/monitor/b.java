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
public class b {
    private static boolean a = false;

    /* compiled from: ProcedureLauncher */
    private interface a<T> {
        T call();
    }

    public static void a(Context context, Map<String, Object> map) {
        if (!a) {
            a = true;
            a.a().a(context);
            b(context, map);
            ProcedureManagerProxy.PROXY.setReal(a.f0a);
            ProcedureFactoryProxy.PROXY.setReal(a.a);
        }
    }

    private static void b(Context context, Map<String, Object> map) {
        Header.appId = context.getPackageName();
        Header.appKey = a(map.get("onlineAppKey"), "12278902");
        Header.appBuild = a(map.get("appBuild"), "");
        Header.appVersion = a(map.get("appVersion"), (a<String>) new a<String>() {
            /* renamed from: a */
            public String call() {
                Context context = a.a().context();
                try {
                    return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "unknown";
                }
            }
        });
        Header.appPatch = a(map.get("appPatch"), "");
        Header.channel = a(map.get("channel"), "");
        Header.utdid = a(map.get("deviceId"), "");
        Header.brand = Build.BRAND;
        Header.deviceModel = Build.MODEL;
        String[] a2 = a();
        if (!TextUtils.isEmpty(a2[0])) {
            Header.osVersion = a2[0];
            Header.os = a2[1];
        } else {
            Header.osVersion = VERSION.RELEASE;
            Header.os = "android";
        }
        Header.processName = a(map.get("process"), (a<String>) new a<String>() {
            /* renamed from: a */
            public String call() {
                return com.taobao.monitor.process.a.b();
            }
        });
        Header.session = String.valueOf(System.currentTimeMillis());
        Header.ttid = a(map.get("ttid"), "");
    }

    private static String a(Object obj, String str) {
        if (!(obj instanceof String) || TextUtils.isEmpty((String) obj)) {
            return str;
        }
        return (String) obj;
    }

    private static String a(Object obj, a<String> aVar) {
        if (!(obj instanceof String) || TextUtils.isEmpty((String) obj)) {
            return (String) aVar.call();
        }
        return (String) obj;
    }

    @SuppressLint({"DefaultLocale"})
    private static String[] a() {
        String str;
        String str2;
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            str = (String) method.invoke(null, new Object[]{"ro.yunos.version"});
            try {
                str2 = (String) method.invoke(null, new Object[]{"java.vm.name"});
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                str2 = null;
                return new String[]{str, str2};
            }
        } catch (Exception e2) {
            e = e2;
            str = null;
            e.printStackTrace();
            str2 = null;
            return new String[]{str, str2};
        }
        return new String[]{str, str2};
    }
}
