package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.ByteUtils;
import com.ali.ha.fulltrace.IReportRawByteEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class StartUpBeginEvent implements IReportRawByteEvent {
    public boolean firstInstall;
    public boolean isBackgroundLaunch = false;
    public String launchType;
    public long time = TimeUtils.currentTimeMillis();

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_APP_START_UP_BEGIN;
    }

    public byte[] getBody() {
        int i;
        int i2;
        byte[] res;
        byte[] datas = new byte[2];
        if (this.firstInstall) {
            i = 1;
        } else {
            i = 0;
        }
        datas[0] = (byte) i;
        if (this.isBackgroundLaunch) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        datas[1] = (byte) i2;
        if (this.launchType == null || this.launchType.length() == 0) {
            res = ByteUtils.int2Bytes(0);
        } else {
            byte[] bytes = this.launchType.getBytes();
            res = ByteUtils.merge(ByteUtils.int2Bytes(bytes.length), bytes);
        }
        return ByteUtils.merge(datas, res);
    }
}
