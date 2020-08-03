//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.IReportEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class StartUpEndEvent implements IReportEvent {
    public long time = TimeUtils.currentTimeMillis();

    public StartUpEndEvent() {
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_APP_START_UP_END;
    }
}
