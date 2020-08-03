//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.IReportEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class JankEvent implements IReportEvent {
    public long time = TimeUtils.currentTimeMillis();

    public JankEvent() {
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_JANK;
    }
}
