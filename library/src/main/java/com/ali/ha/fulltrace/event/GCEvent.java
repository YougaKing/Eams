//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.IReportEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class GCEvent implements IReportEvent {
    public long time = TimeUtils.currentTimeMillis();

    public GCEvent() {
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_GC;
    }
}
