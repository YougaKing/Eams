package com.taobao.monitor.e;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.List;

/* compiled from: ProcessUtils */
public final class a {
    private static String j;

    public static String b() {
        Context context = com.taobao.monitor.a.a().context();
        if (TextUtils.isEmpty(j)) {
            String c = c();
            if (TextUtils.isEmpty(c) && context != null) {
                c = a(context);
            }
            j = c;
        }
        return j;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052 A[SYNTHETIC, Splitter:B:24:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String c() {
        /*
            r0 = 0
            r3 = 0
            int r1 = android.os.Process.myPid()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "/proc/"
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r2 = "/cmdline"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r1.toString()
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r1]
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0041, all -> 0x004d }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0041, all -> 0x004d }
            int r2 = r1.read(r4)     // Catch:{ Exception -> 0x005c, all -> 0x0058 }
            if (r1 == 0) goto L_0x0060
            r1.close()     // Catch:{ Exception -> 0x003e }
            r1 = r2
        L_0x0032:
            if (r1 <= 0) goto L_0x003d
            java.lang.String r0 = new java.lang.String
            r0.<init>(r4, r3, r1)
            java.lang.String r0 = r0.trim()
        L_0x003d:
            return r0
        L_0x003e:
            r1 = move-exception
            r1 = r2
            goto L_0x0032
        L_0x0041:
            r1 = move-exception
            r1 = r0
        L_0x0043:
            if (r1 == 0) goto L_0x005e
            r1.close()     // Catch:{ Exception -> 0x004a }
            r1 = r3
            goto L_0x0032
        L_0x004a:
            r1 = move-exception
            r1 = r3
            goto L_0x0032
        L_0x004d:
            r1 = move-exception
            r2 = r1
            r3 = r0
        L_0x0050:
            if (r3 == 0) goto L_0x0055
            r3.close()     // Catch:{ Exception -> 0x0056 }
        L_0x0055:
            throw r2
        L_0x0056:
            r0 = move-exception
            goto L_0x0055
        L_0x0058:
            r0 = move-exception
            r2 = r0
            r3 = r1
            goto L_0x0050
        L_0x005c:
            r2 = move-exception
            goto L_0x0043
        L_0x005e:
            r1 = r3
            goto L_0x0032
        L_0x0060:
            r1 = r2
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.monitor.e.a.c():java.lang.String");
    }

    private static String a(Context context) {
        if (context == null) {
            return null;
        }
        if (j != null) {
            return j;
        }
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return null;
        }
        List runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        Iterator it = runningAppProcesses.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RunningAppProcessInfo runningAppProcessInfo = (RunningAppProcessInfo) it.next();
            if (runningAppProcessInfo != null && runningAppProcessInfo.pid == myPid) {
                j = runningAppProcessInfo.processName;
                break;
            }
        }
        return j;
    }
}
