package com.alibaba.ha.adapter.plugin;

import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.protocol.AliHaPlugin;
import java.util.concurrent.atomic.AtomicBoolean;

public class TLogPlugin implements AliHaPlugin {
    AtomicBoolean enabling = new AtomicBoolean(false);

    public String getName() {
        return Plugin.tlog.name();
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x007e A[SYNTHETIC, Splitter:B:15:0x007e] */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void start(com.alibaba.ha.protocol.AliHaParam r14) {
        /*
            r13 = this;
            android.app.Application r8 = r14.application
            android.content.Context r1 = r14.context
            java.lang.String r5 = r14.appKey
            java.lang.String r9 = r14.appId
            java.lang.String r0 = r14.appSecret
            java.lang.String r6 = r14.appVersion
            java.lang.String r10 = r14.userNick
            java.lang.String r11 = com.alibaba.motu.tbrest.utils.DeviceUtils.getUtdid(r1)
            if (r1 == 0) goto L_0x0018
            if (r5 == 0) goto L_0x0018
            if (r6 != 0) goto L_0x0020
        L_0x0018:
            java.lang.String r0 = "AliHaAdapter"
            java.lang.String r1 = "param is unlegal, tlog plugin start failure "
            android.util.Log.e(r0, r1)
        L_0x001f:
            return
        L_0x0020:
            com.taobao.tao.log.LogLevel r2 = com.taobao.tao.log.LogLevel.W
            java.lang.String r4 = com.alibaba.motu.tbrest.utils.AppUtils.getMyProcessNameByAppProcessInfo(r1)
            if (r4 != 0) goto L_0x002a
            java.lang.String r4 = "DEFAULT"
        L_0x002a:
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x00e3
            com.alibaba.ha.adapter.service.RandomService r0 = new com.alibaba.ha.adapter.service.RandomService
            r0.<init>()
            java.lang.String r0 = r0.getRandomNum()
            if (r0 != 0) goto L_0x00e3
            java.lang.String r0 = "8951ae070be6560f4fc1401e90a83a4e"
            r7 = r0
        L_0x003e:
            java.lang.String r0 = "AliHaAdapter"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r12 = "init tlog, appKey is "
            java.lang.StringBuilder r3 = r3.append(r12)
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r12 = " appVersion is "
            java.lang.StringBuilder r3 = r3.append(r12)
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r12 = " logLevel is "
            java.lang.StringBuilder r3 = r3.append(r12)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r12 = " namePrefix is "
            java.lang.StringBuilder r3 = r3.append(r12)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.i(r0, r3)
            java.util.concurrent.atomic.AtomicBoolean r0 = r13.enabling
            r3 = 0
            r12 = 1
            boolean r0 = r0.compareAndSet(r3, r12)
            if (r0 == 0) goto L_0x001f
            com.taobao.tao.log.TLogInitializer r0 = com.taobao.tao.log.TLogInitializer.getInstance()     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r3 = "logs"
            com.taobao.tao.log.TLogInitializer r0 = r0.builder(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r0 = r0.setApplication(r8)     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r0 = r0.setSecurityKey(r7)     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r0 = r0.setUserNick(r10)     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r0 = r0.setUtdid(r11)     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r0 = r0.setAppId(r9)     // Catch:{ Exception -> 0x00d9 }
            r0.init()     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.monitor.DefaultTLogMonitorImpl r0 = new com.taobao.tao.log.monitor.DefaultTLogMonitorImpl     // Catch:{ Exception -> 0x00d9 }
            r0.<init>()     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r1 = com.taobao.tao.log.TLogInitializer.getInstance()     // Catch:{ Exception -> 0x00d9 }
            r1.settLogMonitor(r0)     // Catch:{ Exception -> 0x00d9 }
            com.taobao.android.tlog.uploader.TLogUploader r0 = new com.taobao.android.tlog.uploader.TLogUploader     // Catch:{ Exception -> 0x00d9 }
            r0.<init>()     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r1 = com.taobao.tao.log.TLogInitializer.getInstance()     // Catch:{ Exception -> 0x00d9 }
            r1.setLogUploader(r0)     // Catch:{ Exception -> 0x00d9 }
            com.taobao.android.tlog.message.TLogMessage r0 = new com.taobao.android.tlog.message.TLogMessage     // Catch:{ Exception -> 0x00d9 }
            r0.<init>()     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.TLogInitializer r1 = com.taobao.tao.log.TLogInitializer.getInstance()     // Catch:{ Exception -> 0x00d9 }
            r1.setMessageSender(r0)     // Catch:{ Exception -> 0x00d9 }
            com.alibaba.ha.adapter.service.godeye.GodEyeOnAccurateBootListener r0 = new com.alibaba.ha.adapter.service.godeye.GodEyeOnAccurateBootListener     // Catch:{ Exception -> 0x00d9 }
            r0.<init>()     // Catch:{ Exception -> 0x00d9 }
            com.alibaba.ha.adapter.service.telescope.TelescopeService.addOnAccurateBootListener(r0)     // Catch:{ Exception -> 0x00d9 }
            com.alibaba.ha.adapter.service.godeye.GodEyeAppAllInfoListener r0 = new com.alibaba.ha.adapter.service.godeye.GodEyeAppAllInfoListener     // Catch:{ Exception -> 0x00d9 }
            r0.<init>()     // Catch:{ Exception -> 0x00d9 }
            com.taobao.tao.log.godeye.GodeyeInitializer r1 = com.taobao.tao.log.godeye.GodeyeInitializer.getInstance()     // Catch:{ Exception -> 0x00d9 }
            r1.registGodEyeAppListener(r0)     // Catch:{ Exception -> 0x00d9 }
            goto L_0x001f
        L_0x00d9:
            r0 = move-exception
            java.lang.String r1 = "AliHaAdapter"
            java.lang.String r2 = "param is unlegal, tlog plugin start failure "
            android.util.Log.e(r1, r2, r0)
            goto L_0x001f
        L_0x00e3:
            r7 = r0
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ha.adapter.plugin.TLogPlugin.start(com.alibaba.ha.protocol.AliHaParam):void");
    }
}
