package com.ali.ha.fulltrace;

import com.ali.ha.fulltrace.logger.Logger;
import com.alibaba.motu.tbrest.SendService;

public class SendManager {
    private static final String TAG = "SendManager";
    private static final String arg1 = "AliHA";
    private static final Integer eventId = Integer.valueOf(61004);
    private static final String host = null;

    public static boolean send(String id, String content) {
        try {
            Boolean result = SendService.getInstance().sendRequest(host, System.currentTimeMillis(), null, eventId.intValue(), arg1, content, id, null);
            if (result.booleanValue()) {
                Logger.i(TAG, "send success");
            } else {
                Logger.w(TAG, "send failure");
            }
            return result.booleanValue();
        } catch (Throwable e) {
            Logger.throwException(e);
            return false;
        }
    }
}
