package com.taobao.monitor.adapter.db;

import com.alibaba.motu.tbrest.SendService;
import com.taobao.monitor.adapter.constants.TBAPMConstants;
import com.taobao.monitor.impl.logger.Logger;
import com.taobao.monitor.network.INetworkSender;
import com.taobao.monitor.thread.ThreadUtils;

import java.util.List;

/* compiled from: TBRestSender */
public class TBRestSender implements INetworkSender {
    private static final int mEventId = 61004;
    private static final String mArg = "AliHAMonitor";
    private static final String host = null;
    private ILiteDb mLiteDb = new SenderLiteDb();

    /* renamed from: a reason: collision with other field name */
    /* access modifiers changed from: private */
    private boolean mCache = true;

    @Override
    public void send(final String topic, final String json) {
        if (TBAPMConstants.sender) {
            ThreadUtils.start(new Runnable() {
                public void run() {
                    int count = 0;
                    try {
                        Logger.i("TBRestSender", json);
                        boolean send = false;
                        while (true) {
                            if (count >= 2) {
                                break;
                            }
                            count++;
                            send = sendRequest(topic, json);
                            if (send) {
                                Logger.i("TBRestSender", "send success" + count);
                                break;
                            }
                        }
                        if (!send) {
                            save(topic, json);
                            mCache = true;
                        }
                        if (send && mCache) {
                            sendAll();
                            mCache = false;
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
                .sendRequest(host, System.currentTimeMillis(), null, mEventId, mArg, str2, str, null);
    }

    private void sendAll() {
        List<String> stringList = this.mLiteDb.getAll();
        if (stringList != null) {
            for (String str : stringList) {
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

    private void save(String str, String str2) {
        this.mLiteDb.save(str + "HA_APM_______HA_APM" + str2);
    }
}
