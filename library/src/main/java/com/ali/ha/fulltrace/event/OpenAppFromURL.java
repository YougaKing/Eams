//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.ByteUtils;
import com.ali.ha.fulltrace.IReportRawByteEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class OpenAppFromURL implements IReportRawByteEvent {
    public long time = TimeUtils.currentTimeMillis();
    public String url;

    public OpenAppFromURL() {
    }

    public byte[] getBody() {
        if (this.url != null && this.url.length() != 0) {
            byte[] bytes = this.url.getBytes();
            int length = bytes.length;
            return ByteUtils.merge(ByteUtils.int2Bytes(length), bytes);
        } else {
            return ByteUtils.int2Bytes(0);
        }
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_OPEN_APP_FROM_URL;
    }
}
