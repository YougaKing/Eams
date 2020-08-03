//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.ByteUtils;
import com.ali.ha.fulltrace.IReportRawByteEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class FPSEvent implements IReportRawByteEvent {
    public float averageLoadFps;
    public float averageUseFps;
    public long time = TimeUtils.currentTimeMillis();

    public FPSEvent() {
    }

    public byte[] getBody() {
        byte[] bytesLoadFPS = ByteUtils.floatToBytes(this.averageLoadFps);
        byte[] bytesUseFPS = ByteUtils.floatToBytes(this.averageUseFps);
        return ByteUtils.merge(new byte[][]{bytesLoadFPS, bytesUseFPS});
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_FPS;
    }
}
