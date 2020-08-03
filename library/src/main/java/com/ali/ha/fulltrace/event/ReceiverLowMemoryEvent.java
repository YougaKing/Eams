//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.ByteUtils;
import com.ali.ha.fulltrace.IReportRawByteEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class ReceiverLowMemoryEvent implements IReportRawByteEvent {
    public float level;
    public long time = TimeUtils.currentTimeMillis();

    public ReceiverLowMemoryEvent() {
    }

    public byte[] getBody() {
        return ByteUtils.floatToBytes(this.level);
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_RECEIVE_MEMORY_WARN;
    }
}
