//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.ByteUtils;
import com.ali.ha.fulltrace.IReportRawByteEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class StartUpBeginEvent implements IReportRawByteEvent {
    public long time = TimeUtils.currentTimeMillis();
    public boolean firstInstall;
    public String launchType;
    public boolean isBackgroundLaunch = false;

    public StartUpBeginEvent() {
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_APP_START_UP_BEGIN;
    }

    public byte[] getBody() {
        byte[] datas = new byte[]{(byte)(this.firstInstall ? 1 : 0), (byte)(this.isBackgroundLaunch ? 1 : 0)};
        byte[] res;
        if (this.launchType != null && this.launchType.length() != 0) {
            byte[] bytes = this.launchType.getBytes();
            int length = bytes.length;
            res = ByteUtils.merge(new byte[][]{ByteUtils.int2Bytes(length), bytes});
        } else {
            res = ByteUtils.int2Bytes(0);
        }

        return ByteUtils.merge(new byte[][]{datas, res});
    }
}
