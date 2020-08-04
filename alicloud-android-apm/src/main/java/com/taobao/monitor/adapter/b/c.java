package com.taobao.monitor.adapter.b;

import com.alibaba.motu.tbrest.SendService;
import com.taobao.monitor.d.a;
import com.taobao.monitor.impl.logger.Logger;
import java.util.List;

/* compiled from: TBRestSender */
public class c implements a {
    private a a = new b();

    /* renamed from: a reason: collision with other field name */
    private final Integer f8a = Integer.valueOf(61004);
    /* access modifiers changed from: private */
    public boolean e = true;
    private final String g = "AliHAMonitor";
    private final String host = null;

    public void b(final String str, final String str2) {
        if (com.taobao.monitor.adapter.a.a.d) {
            com.taobao.monitor.a.a.start(new Runnable() {
                public void run() {
                    int i2 = 0;
                    try {
                        Logger.i("TBRestSender", new Object[]{str2});
                        boolean z = false;
                        while (true) {
                            int i3 = i2;
                            i2 = i3 + 1;
                            if (i3 >= 2) {
                                break;
                            }
                            z = c.a(c.this, str, str2);
                            if (z) {
                                Logger.i("TBRestSender", new Object[]{"send success" + i2});
                                break;
                            }
                        }
                        if (!z) {
                            c.a(c.this, str, str2);
                            c.this.e = true;
                        }
                        if (z && c.a(c.this)) {
                            c.a(c.this);
                            c.this.e = false;
                        }
                    } catch (Throwable th) {
                        Logger.throwException(th);
                    }
                }
            });
        }
    }

    private boolean a(String str, String str2) {
        return SendService.getInstance().sendRequest(this.host, System.currentTimeMillis(), null, this.f8a.intValue(), "AliHAMonitor", str2, str, null).booleanValue();
    }

    private void c() {
        List<String> a2 = this.a.a();
        if (a2 != null) {
            for (String str : a2) {
                if (str != null) {
                    String[] split = str.split("HA_APM_______HA_APM");
                    if (split.length >= 2) {
                        a(split[0], split[1]);
                    }
                }
            }
        }
        this.a.delete();
    }

    private void c(String str, String str2) {
        this.a.a(str + "HA_APM_______HA_APM" + str2);
    }
}
