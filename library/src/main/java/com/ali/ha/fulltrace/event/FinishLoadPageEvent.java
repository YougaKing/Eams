//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.event;

import com.ali.ha.fulltrace.ByteUtils;
import com.ali.ha.fulltrace.IReportRawByteEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.TimeUtils;

public class FinishLoadPageEvent implements IReportRawByteEvent {
    public String pageName;
    public float duration;
    public float freeMemory;
    public float residentMemory;
    public float virtualMemory;
    public float cpuUsageOfApp;
    public float cpuUsageOfDevice;
    public long time = TimeUtils.currentTimeMillis();

    public FinishLoadPageEvent() {
    }

    public byte[] getBody() {
        int pageLength = this.pageName == null ? 0 : this.pageName.length();
        byte[] bytesPage = this.pageName == null ? new byte[0] : this.pageName.getBytes();
        byte[] bytesDuration = ByteUtils.floatToBytes(this.duration);
        byte[] bytesFreeMemory = ByteUtils.floatToBytes(this.freeMemory);
        byte[] bytesResidentMemory = ByteUtils.floatToBytes(this.residentMemory);
        byte[] bytesVirtualMemory = ByteUtils.floatToBytes(this.virtualMemory);
        byte[] bytesCpuUsageOfApp = ByteUtils.floatToBytes(this.cpuUsageOfApp);
        byte[] bytesCpuUsageOfDevice = ByteUtils.floatToBytes(this.cpuUsageOfDevice);
        return ByteUtils.merge(new byte[][]{ByteUtils.int2Bytes(pageLength), bytesPage, bytesDuration, bytesFreeMemory, bytesResidentMemory, bytesVirtualMemory, bytesCpuUsageOfApp, bytesCpuUsageOfDevice});
    }

    public long getTime() {
        return this.time;
    }

    public short getType() {
        return ProtocolConstants.EVENT_FINISH_LOAD_PAGE;
    }
}
