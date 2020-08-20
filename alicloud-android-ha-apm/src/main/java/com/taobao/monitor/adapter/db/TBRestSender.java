package com.taobao.monitor.adapter.db;

import com.alibaba.motu.tbrest.SendService;
import com.taobao.monitor.adapter.constants.TBAPMConstants;
import com.taobao.monitor.network.INetworkSender;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.thread.ThreadUtils;

import java.util.List;

/* compiled from: TBRestSender */
public class TBRestSender implements INetworkSender {
    private ILiteDb mLiteDb = new SenderLiteDb();

    /* renamed from: a reason: collision with other field name */
    private final Integer f8a = 61004;
    /* access modifiers changed from: private */
    public boolean e = true;
    private final String g = "AliHAMonitor";
    private final String host = null;

    @Override
    public void b(final String str, final String str2) {
        if (TBAPMConstants.d) {
            ThreadUtils.start(new Runnable() {
                public void run() {
                    int i2 = 0;
                    try {
                        Logger.i("TBRestSender", str2);
                        boolean z = false;
                        while (true) {
                            int i3 = i2;
                            i2 = i3 + 1;
                            if (i3 >= 2) {
                                break;
                            }
                            z = TBRestSender.this.sendRequest(str, str2);
                            if (z) {
                                Logger.i("TBRestSender", "send success" + i2);
                                break;
                            }
                        }
                        if (!z) {
                            TBRestSender.this.c(str, str2);
                            TBRestSender.this.e = true;
                        }
                        if (z && TBRestSender.this.e) {
                            TBRestSender.this.c();
                            TBRestSender.this.e = false;
                        }
                    } catch (Throwable th) {
                        Logger.throwException(th);
                    }
                }
            });
        }
    }

    private boolean sendRequest(String str, String str2) {
        return SendService.getInstance()
                .sendRequest(this.host, System.currentTimeMillis(), null, this.f8a.intValue(), "AliHAMonitor", str2, str, null)
                .booleanValue();
    }

    private void c() {
        List<String> a2 = this.mLiteDb.a();
        if (a2 != null) {
            for (String str : a2) {
                if (str != null) {
                    String[] split = str.split("HA_APM_______HA_APM");
                    if (split.length >= 2) {
                        sendRequest(split[0], split[1]);
                    }
                }
            }
        }
        this.mLiteDb.delete();
    }

    private void c(String str, String str2) {
        this.mLiteDb.a(str + "HA_APM_______HA_APM" + str2);
    }
}
