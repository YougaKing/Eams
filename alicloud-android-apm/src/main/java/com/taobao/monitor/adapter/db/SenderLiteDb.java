package com.taobao.monitor.adapter.db;

import com.taobao.monitor.impl.common.Global;
import java.io.File;

/* compiled from: SenderLiteDb */
class SenderLiteDb implements ILiteDb {
    private final File file = new File(Global.instance().context().getCacheDir() + "/" + "apm_db.db");

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c A[Catch:{ Exception -> 0x0030 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r5) {
        /*
            r4 = this;
            r4.b()     // Catch:{ Exception -> 0x0030 }
            java.io.File r0 = r4.file     // Catch:{ Exception -> 0x0030 }
            long r0 = r0.length()     // Catch:{ Exception -> 0x0030 }
            r2 = 4194304(0x400000, double:2.0722615E-317)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x0027
            r2 = 0
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ all -> 0x0028 }
            java.io.File r0 = r4.file     // Catch:{ all -> 0x0028 }
            r3 = 1
            r1.<init>(r0, r3)     // Catch:{ all -> 0x0028 }
            java.io.Writer r0 = r1.append(r5)     // Catch:{ all -> 0x0035 }
            java.lang.String r2 = "\n"
            r0.append(r2)     // Catch:{ all -> 0x0035 }
            if (r1 == 0) goto L_0x0027
            r1.close()     // Catch:{ Exception -> 0x0030 }
        L_0x0027:
            return
        L_0x0028:
            r0 = move-exception
            r1 = r2
        L_0x002a:
            if (r1 == 0) goto L_0x002f
            r1.close()     // Catch:{ Exception -> 0x0030 }
        L_0x002f:
            throw r0     // Catch:{ Exception -> 0x0030 }
        L_0x0030:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0027
        L_0x0035:
            r0 = move-exception
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.monitor.adapter.b.b.a(java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039 A[Catch:{ Exception -> 0x003d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.String> a() {
        /*
            r6 = this;
            r1 = 0
            r6.b()     // Catch:{ Exception -> 0x003d }
            java.io.File r0 = r6.file     // Catch:{ Exception -> 0x003d }
            long r2 = r0.length()     // Catch:{ Exception -> 0x003d }
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x003e
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x0035 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ all -> 0x0035 }
            java.io.File r3 = r6.file     // Catch:{ all -> 0x0035 }
            r0.<init>(r3)     // Catch:{ all -> 0x0035 }
            r2.<init>(r0)     // Catch:{ all -> 0x0035 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0040 }
            r0.<init>()     // Catch:{ all -> 0x0040 }
            java.lang.String r3 = r2.readLine()     // Catch:{ all -> 0x0040 }
        L_0x0025:
            if (r3 == 0) goto L_0x002f
            r0.add(r3)     // Catch:{ all -> 0x0040 }
            java.lang.String r3 = r2.readLine()     // Catch:{ all -> 0x0040 }
            goto L_0x0025
        L_0x002f:
            if (r2 == 0) goto L_0x0034
            r2.close()     // Catch:{ Exception -> 0x003d }
        L_0x0034:
            return r0
        L_0x0035:
            r0 = move-exception
            r2 = r1
        L_0x0037:
            if (r2 == 0) goto L_0x003c
            r2.close()     // Catch:{ Exception -> 0x003d }
        L_0x003c:
            throw r0     // Catch:{ Exception -> 0x003d }
        L_0x003d:
            r0 = move-exception
        L_0x003e:
            r0 = r1
            goto L_0x0034
        L_0x0040:
            r0 = move-exception
            goto L_0x0037
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.monitor.adapter.b.b.a():java.util.List");
    }

    public void delete() {
        if (this.file.exists()) {
            this.file.delete();
        }
    }

    private void b() throws Exception {
        if (!this.file.exists()) {
            this.file.createNewFile();
        } else if (this.file.isDirectory()) {
            this.file.delete();
            this.file.createNewFile();
        }
    }
}
