package com.taobao.monitor.adapter.db;

import com.alibaba.motu.tbrest.SendService;
import com.taobao.monitor.adapter.constans.TBAPMConstants;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.network.INetworkSender;
import com.taobao.monitor.util.ThreadUtils;

import java.util.Iterator;
import java.util.List;

public class TBRestSender implements INetworkSender {
    private final Integer aEventId = 61004;
    private final String g = "AliHAMonitor";
    private final String host = null;
    private boolean local = true;
    private SenderLiteDb senderLiteDb = new SenderLiteDb();

    public TBRestSender() {
    }

    @Override
    public void dataUpdate(final String topic, final String value) {
        if (TBAPMConstants.d) {
            ThreadUtils.start(new Runnable() {
                public void run() {
                    try {
                        int var1x = 0;
                        boolean sendResult = false;
                        Logger.i("TBRestSender", value);

                        while (var1x++ < 2) {
                            sendResult = TBRestSender.this.sendRequest(topic, value);
                            if (sendResult) {
                                Logger.i("TBRestSender", "send success" + var1x);
                                break;
                            }
                        }

                        if (!sendResult) {
                            TBRestSender.this.append(topic, value);
                            TBRestSender.this.local = true;
                        }

                        if (sendResult && TBRestSender.this.local) {
                            TBRestSender.this.readAll();
                            TBRestSender.this.local = false;
                        }
                    } catch (Throwable var3) {
                        Logger.throwException(var3);
                    }
                }
            });
        }
    }

    private boolean sendRequest(String topic, String value) {
        return SendService.getInstance().sendRequest(host, System.currentTimeMillis(), null, this.aEventId, "AliHAMonitor", value, topic, null);
    }

    private void readAll() {
        List<String> stringList = this.senderLiteDb.readAll();
        if (stringList != null) {
            Iterator iterator = stringList.iterator();

            while (iterator.hasNext()) {
                String string = (String) iterator.next();
                if (string != null) {
                    String[] split = string.split("HA_APM_______HA_APM");
                    if (split.length >= 2) {
                        this.append(split[0], split[1]);
                    }
                }
            }
        }

        this.senderLiteDb.delete();
    }

    private void append(String var1, String var2) {
        this.senderLiteDb.append(var1 + "HA_APM_______HA_APM" + var2);
    }
}

