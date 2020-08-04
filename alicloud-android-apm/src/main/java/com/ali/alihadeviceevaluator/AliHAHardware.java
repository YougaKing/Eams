package com.ali.alihadeviceevaluator;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import com.ali.alihadeviceevaluator.cpu.AliHACPUInfo;
import com.ali.alihadeviceevaluator.cpu.AliHACPUTracker;
import com.ali.alihadeviceevaluator.display.AliHADisplayInfo;
import com.ali.alihadeviceevaluator.mem.AliHAMemoryTracker;
import com.ali.alihadeviceevaluator.opengl.AliHAOpenGL;
import com.ali.alihadeviceevaluator.util.AliHALifecycle;
import java.util.HashMap;

public class AliHAHardware {
    public static final String CONFIG_CPUTRACKTICK = "cpuTrackTick";
    public static final int DEVICE_IS_FATAL = 3;
    public static final int DEVICE_IS_GOOD = 0;
    public static final int DEVICE_IS_NORMAL = 1;
    public static final int DEVICE_IS_RISKY = 2;
    public static final int HIGH_END_DEVICE = 0;
    public static final int LOW_END_DEVICE = 2;
    public static final int MEDIUM_DEVICE = 1;
    private AliHALifecycle lifecycle;
    private volatile AliHACPUTracker mAliHACPUTracker;
    private volatile AliHAMemoryTracker mAliHAMemoryTracker;
    /* access modifiers changed from: private */
    public volatile CPUInfo mCPUInfo;
    private Context mContext;
    private volatile DisplayInfo mDisplayInfo;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public volatile MemoryInfo mMemoryInfo;
    /* access modifiers changed from: private */
    public volatile OutlineInfo mOutlineInfo;

    public class CPUInfo {
        public float avgFreq = 0.0f;
        public int cpuCoreNum = 0;
        public int cpuDeivceScore = -1;
        public float cpuUsageOfApp = -1.0f;
        public float cpuUsageOfDevcie = -1.0f;
        public int deviceLevel = -1;
        public int runtimeLevel = -1;

        public CPUInfo() {
        }
    }

    public class DisplayInfo {
        public float mDensity = 0.0f;
        public int mHeightPixels = 0;
        public int mOpenGLDeviceLevel = -1;
        public String mOpenGLVersion = "0";
        public int mWidthPixels = 0;

        public DisplayInfo() {
        }
    }

    public class MemoryInfo {
        public long dalvikPSSMemory;
        public int deviceLevel = -1;
        public long deviceTotalMemory;
        public long deviceUsedMemory;
        public long jvmTotalMemory;
        public long jvmUsedMemory;
        public long nativePSSMemory;
        public long nativeTotalMemory;
        public long nativeUsedMemory;
        public int runtimeLevel = -1;
        public long totalPSSMemory;

        public MemoryInfo() {
        }
    }

    public class OutlineInfo {
        public int deviceLevel = -1;
        public int deviceLevelEasy;
        public int deviceScore;
        public int runtimeLevel = -1;

        public OutlineInfo() {
        }

        public OutlineInfo update() {
            AliHAHardware.this.getCpuInfo();
            AliHAHardware.this.getDisplayInfo();
            AliHAHardware.this.mOutlineInfo.runtimeLevel = Math.round(((0.8f * ((float) AliHAHardware.this.mMemoryInfo.runtimeLevel)) + (1.2f * ((float) AliHAHardware.this.mCPUInfo.runtimeLevel))) / 2.0f);
            return this;
        }
    }

    private static class SingleHolder {
        /* access modifiers changed from: private */
        public static AliHAHardware mInstance = new AliHAHardware();

        private SingleHolder() {
        }
    }

    public static AliHAHardware getInstance() {
        return SingleHolder.mInstance;
    }

    private AliHAHardware() {
    }

    public void setUp(Application application, Handler handler) {
        this.mContext = application;
        this.mHandler = handler;
        if (this.mAliHACPUTracker == null) {
            this.mAliHACPUTracker = new AliHACPUTracker(Process.myPid(), this.mHandler);
        }
        this.lifecycle = new AliHALifecycle();
        application.registerActivityLifecycleCallbacks(this.lifecycle);
    }

    public void setUp(Application application) {
        setUp(application, null);
    }

    public Context getContext() {
        return this.mContext;
    }

    public void onAppBackGround() {
        if (this.mAliHACPUTracker != null) {
            this.mAliHACPUTracker.reset(0);
        }
    }

    public void onAppForeGround() {
        if (this.mAliHACPUTracker != null) {
            this.mAliHACPUTracker.reset(this.mAliHACPUTracker.mDeltaDuration);
        }
    }

    public void effectConfig(HashMap<String, String> hashMap) {
        Long l;
        if (hashMap != null && this.mAliHACPUTracker != null) {
            String str = (String) hashMap.get(CONFIG_CPUTRACKTICK);
            Long valueOf = Long.valueOf(-1);
            try {
                l = Long.valueOf(str);
            } catch (Throwable th) {
                th.printStackTrace();
                l = valueOf;
            }
            if (l.longValue() != -1) {
                this.mAliHACPUTracker.reset(l.longValue());
            }
        }
    }

    public DisplayInfo getDisplayInfo() {
        if (this.mContext == null) {
            return new DisplayInfo();
        }
        if (this.mDisplayInfo == null) {
            AliHADisplayInfo displayInfo = AliHADisplayInfo.getDisplayInfo(this.mContext);
            this.mDisplayInfo = new DisplayInfo();
            this.mDisplayInfo.mDensity = displayInfo.mDensity;
            this.mDisplayInfo.mHeightPixels = displayInfo.mHeightPixels;
            this.mDisplayInfo.mWidthPixels = displayInfo.mWidthPixels;
            AliHAOpenGL aliHAOpenGL = new AliHAOpenGL();
            aliHAOpenGL.generateOpenGLVersion(this.mContext);
            this.mDisplayInfo.mOpenGLVersion = String.valueOf(aliHAOpenGL.mOpenGLVersion);
            this.mDisplayInfo.mOpenGLDeviceLevel = evaluateLevel(aliHAOpenGL.mScore, 8, 6);
        }
        return this.mDisplayInfo;
    }

    public CPUInfo getCpuInfo() {
        if (this.mContext == null) {
            return new CPUInfo();
        }
        if (this.mCPUInfo == null) {
            AliHACPUInfo aliHACPUInfo = new AliHACPUInfo();
            aliHACPUInfo.evaluateCPUScore();
            if (this.mAliHACPUTracker == null) {
                this.mAliHACPUTracker = new AliHACPUTracker(Process.myPid(), this.mHandler);
            }
            this.mCPUInfo = new CPUInfo();
            this.mCPUInfo.cpuCoreNum = aliHACPUInfo.mCPUCore;
            this.mCPUInfo.avgFreq = aliHACPUInfo.mCPUAvgFreq;
            this.mCPUInfo.cpuDeivceScore = aliHACPUInfo.mCPUScore;
            this.mCPUInfo.deviceLevel = evaluateLevel(aliHACPUInfo.mCPUScore, 8, 5);
        }
        this.mCPUInfo.cpuUsageOfApp = this.mAliHACPUTracker.peakCurProcessCpuPercent();
        this.mCPUInfo.cpuUsageOfDevcie = this.mAliHACPUTracker.peakCpuPercent();
        this.mCPUInfo.runtimeLevel = evaluateLevel((int) (100.0f - this.mCPUInfo.cpuUsageOfDevcie), 90, 60, 20);
        return this.mCPUInfo;
    }

    private int evaluateLevel(int i, int... iArr) {
        if (-1 == i) {
            return -1;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= iArr.length) {
                i2 = -1;
                break;
            } else if (i >= iArr[i2]) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 != -1 || i < 0) {
            return i2;
        }
        return iArr.length;
    }

    public MemoryInfo getMemoryInfo() {
        int i = -1;
        if (this.mContext == null) {
            return new MemoryInfo();
        }
        if (this.mMemoryInfo == null) {
            this.mMemoryInfo = new MemoryInfo();
            this.mAliHAMemoryTracker = new AliHAMemoryTracker();
        }
        try {
            long[] deviceMem = this.mAliHAMemoryTracker.getDeviceMem();
            this.mMemoryInfo.deviceTotalMemory = deviceMem[0];
            this.mMemoryInfo.deviceUsedMemory = deviceMem[1];
            long[] heapJVM = this.mAliHAMemoryTracker.getHeapJVM();
            this.mMemoryInfo.jvmTotalMemory = heapJVM[0];
            this.mMemoryInfo.jvmUsedMemory = heapJVM[1];
            int i2 = heapJVM[0] != 0 ? (int) ((((double) heapJVM[1]) * 100.0d) / ((double) heapJVM[0])) : -1;
            long[] heapNative = this.mAliHAMemoryTracker.getHeapNative();
            this.mMemoryInfo.nativeTotalMemory = heapNative[0];
            this.mMemoryInfo.nativeUsedMemory = heapNative[1];
            if (heapNative[0] != 0) {
                i = (int) ((((double) heapNative[1]) * 100.0d) / ((double) heapNative[0]));
            }
            long[] pss = this.mAliHAMemoryTracker.getPSS(this.mContext, Process.myPid());
            this.mMemoryInfo.dalvikPSSMemory = pss[0];
            this.mMemoryInfo.nativePSSMemory = pss[1];
            this.mMemoryInfo.totalPSSMemory = pss[2];
            this.mMemoryInfo.deviceLevel = evaluateLevel((int) this.mMemoryInfo.deviceTotalMemory, 5242880, 2621440);
            int evaluateLevel = evaluateLevel(100 - i2, 70, 50, 30);
            this.mMemoryInfo.runtimeLevel = Math.round(((float) (evaluateLevel(100 - i, 60, 40, 20) + evaluateLevel)) / 2.0f);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return this.mMemoryInfo;
    }

    public OutlineInfo getOutlineInfo() {
        if (this.mContext == null) {
            return new OutlineInfo();
        }
        if (this.mOutlineInfo == null) {
            this.mOutlineInfo = new OutlineInfo();
            if (this.mMemoryInfo == null) {
                getMemoryInfo();
            }
            if (this.mCPUInfo == null) {
                getCpuInfo();
            }
            if (this.mDisplayInfo == null) {
                getDisplayInfo();
            }
            this.mOutlineInfo.deviceLevelEasy = Math.round((((0.9f * ((float) this.mMemoryInfo.deviceLevel)) + (1.5f * ((float) this.mCPUInfo.deviceLevel))) + (0.6f * ((float) this.mDisplayInfo.mOpenGLDeviceLevel))) / 3.0f);
            this.mOutlineInfo.runtimeLevel = Math.round(((float) (this.mMemoryInfo.runtimeLevel + this.mCPUInfo.runtimeLevel)) / 2.0f);
        } else {
            if (this.mMemoryInfo == null) {
                getMemoryInfo();
            }
            if (this.mCPUInfo == null) {
                getCpuInfo();
            }
            if (this.mDisplayInfo == null) {
                getDisplayInfo();
            }
            this.mOutlineInfo.runtimeLevel = Math.round(((0.8f * ((float) this.mMemoryInfo.runtimeLevel)) + (1.2f * ((float) this.mCPUInfo.runtimeLevel))) / 2.0f);
        }
        return this.mOutlineInfo;
    }

    public void setDeviceScore(int i) {
        if (this.mOutlineInfo == null) {
            getOutlineInfo();
        }
        if (this.mOutlineInfo != null) {
            this.mOutlineInfo.deviceScore = i;
            if (i >= 90) {
                this.mOutlineInfo.deviceLevel = 0;
            } else if (i >= 70) {
                this.mOutlineInfo.deviceLevel = 1;
            } else {
                this.mOutlineInfo.deviceLevel = 2;
            }
        }
    }
}
