//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
    private Context mContext;
    private Handler mHandler;
    private AliHALifecycle lifecycle;
    public static final String CONFIG_CPUTRACKTICK = "cpuTrackTick";
    private volatile AliHAHardware.DisplayInfo mDisplayInfo;
    private volatile AliHAHardware.CPUInfo mCPUInfo;
    private volatile AliHACPUTracker mAliHACPUTracker;
    private volatile AliHAHardware.MemoryInfo mMemoryInfo;
    private volatile AliHAMemoryTracker mAliHAMemoryTracker;
    private volatile AliHAHardware.OutlineInfo mOutlineInfo;
    public static final int HIGH_END_DEVICE = 0;
    public static final int MEDIUM_DEVICE = 1;
    public static final int LOW_END_DEVICE = 2;
    public static final int DEVICE_IS_GOOD = 0;
    public static final int DEVICE_IS_NORMAL = 1;
    public static final int DEVICE_IS_RISKY = 2;
    public static final int DEVICE_IS_FATAL = 3;

    public static AliHAHardware getInstance() {
        return AliHAHardware.SingleHolder.mInstance;
    }

    private AliHAHardware() {
    }

    public void setUp(Application var1, Handler var2) {
        this.mContext = var1;
        this.mHandler = var2;
        if (this.mAliHACPUTracker == null) {
            this.mAliHACPUTracker = new AliHACPUTracker(Process.myPid(), this.mHandler);
        }

        this.lifecycle = new AliHALifecycle();
        var1.registerActivityLifecycleCallbacks(this.lifecycle);
    }

    public void setUp(Application var1) {
        this.setUp(var1, (Handler)null);
    }

    public Context getContext() {
        return this.mContext;
    }

    public void onAppBackGround() {
        if (this.mAliHACPUTracker != null) {
            this.mAliHACPUTracker.reset(0L);
        }

    }

    public void onAppForeGround() {
        if (this.mAliHACPUTracker != null) {
            this.mAliHACPUTracker.reset(this.mAliHACPUTracker.mDeltaDuration);
        }

    }

    public void effectConfig(HashMap<String, String> var1) {
        if (var1 != null) {
            if (this.mAliHACPUTracker != null) {
                String var2 = (String)var1.get("cpuTrackTick");
                Long var3 = -1L;

                try {
                    var3 = Long.valueOf(var2);
                } catch (Throwable var5) {
                    var5.printStackTrace();
                }

                if (var3 != -1L) {
                    this.mAliHACPUTracker.reset(var3);
                }

            }
        }
    }

    public AliHAHardware.DisplayInfo getDisplayInfo() {
        if (this.mContext == null) {
            return new AliHAHardware.DisplayInfo();
        } else {
            if (this.mDisplayInfo == null) {
                AliHADisplayInfo var1 = AliHADisplayInfo.getDisplayInfo(this.mContext);
                this.mDisplayInfo = new AliHAHardware.DisplayInfo();
                this.mDisplayInfo.mDensity = var1.mDensity;
                this.mDisplayInfo.mHeightPixels = var1.mHeightPixels;
                this.mDisplayInfo.mWidthPixels = var1.mWidthPixels;
                AliHAOpenGL var2 = new AliHAOpenGL();
                var2.generateOpenGLVersion(this.mContext);
                this.mDisplayInfo.mOpenGLVersion = String.valueOf(var2.mOpenGLVersion);
                this.mDisplayInfo.mOpenGLDeviceLevel = this.evaluateLevel(var2.mScore, 8, 6);
            }

            return this.mDisplayInfo;
        }
    }

    public AliHAHardware.CPUInfo getCpuInfo() {
        if (this.mContext == null) {
            return new AliHAHardware.CPUInfo();
        } else {
            if (this.mCPUInfo == null) {
                AliHACPUInfo var1 = new AliHACPUInfo();
                var1.evaluateCPUScore();
                if (this.mAliHACPUTracker == null) {
                    this.mAliHACPUTracker = new AliHACPUTracker(Process.myPid(), this.mHandler);
                }

                this.mCPUInfo = new AliHAHardware.CPUInfo();
                this.mCPUInfo.cpuCoreNum = var1.mCPUCore;
                this.mCPUInfo.avgFreq = var1.mCPUAvgFreq;
                this.mCPUInfo.cpuDeivceScore = var1.mCPUScore;
                this.mCPUInfo.deviceLevel = this.evaluateLevel(var1.mCPUScore, 8, 5);
            }

            this.mCPUInfo.cpuUsageOfApp = this.mAliHACPUTracker.peakCurProcessCpuPercent();
            this.mCPUInfo.cpuUsageOfDevcie = this.mAliHACPUTracker.peakCpuPercent();
            this.mCPUInfo.runtimeLevel = this.evaluateLevel((int)(100.0F - this.mCPUInfo.cpuUsageOfDevcie), 90, 60, 20);
            return this.mCPUInfo;
        }
    }

    private int evaluateLevel(int var1, int... var2) {
        if (-1 == var1) {
            return -1;
        } else {
            int var3 = -1;

            for(int var4 = 0; var4 < var2.length; ++var4) {
                if (var1 >= var2[var4]) {
                    var3 = var4;
                    break;
                }
            }

            if (var3 == -1 && var1 >= 0) {
                var3 = var2.length;
            }

            return var3;
        }
    }

    public AliHAHardware.MemoryInfo getMemoryInfo() {
        if (this.mContext == null) {
            return new AliHAHardware.MemoryInfo();
        } else {
            if (this.mMemoryInfo == null) {
                this.mMemoryInfo = new AliHAHardware.MemoryInfo();
                this.mAliHAMemoryTracker = new AliHAMemoryTracker();
            }

            try {
                long[] var1 = this.mAliHAMemoryTracker.getDeviceMem();
                this.mMemoryInfo.deviceTotalMemory = var1[0];
                this.mMemoryInfo.deviceUsedMemory = var1[1];
                var1 = this.mAliHAMemoryTracker.getHeapJVM();
                this.mMemoryInfo.jvmTotalMemory = var1[0];
                this.mMemoryInfo.jvmUsedMemory = var1[1];
                int var2 = var1[0] != 0L ? (int)(100.0D * (double)var1[1] / (double)var1[0]) : -1;
                var1 = this.mAliHAMemoryTracker.getHeapNative();
                this.mMemoryInfo.nativeTotalMemory = var1[0];
                this.mMemoryInfo.nativeUsedMemory = var1[1];
                int var3 = var1[0] != 0L ? (int)(100.0D * (double)var1[1] / (double)var1[0]) : -1;
                var1 = this.mAliHAMemoryTracker.getPSS(this.mContext, Process.myPid());
                this.mMemoryInfo.dalvikPSSMemory = var1[0];
                this.mMemoryInfo.nativePSSMemory = var1[1];
                this.mMemoryInfo.totalPSSMemory = var1[2];
                this.mMemoryInfo.deviceLevel = this.evaluateLevel((int)this.mMemoryInfo.deviceTotalMemory, 5242880, 2621440);
                int var4 = this.evaluateLevel(100 - var2, 70, 50, 30);
                int var5 = this.evaluateLevel(100 - var3, 60, 40, 20);
                this.mMemoryInfo.runtimeLevel = Math.round((float)(var4 + var5) / 2.0F);
            } catch (Throwable var6) {
                var6.printStackTrace();
            }

            return this.mMemoryInfo;
        }
    }

    public AliHAHardware.OutlineInfo getOutlineInfo() {
        if (this.mContext == null) {
            return new AliHAHardware.OutlineInfo();
        } else {
            if (this.mOutlineInfo == null) {
                this.mOutlineInfo = new AliHAHardware.OutlineInfo();
                if (this.mMemoryInfo == null) {
                    this.getMemoryInfo();
                }

                if (this.mCPUInfo == null) {
                    this.getCpuInfo();
                }

                if (this.mDisplayInfo == null) {
                    this.getDisplayInfo();
                }

                this.mOutlineInfo.deviceLevelEasy = Math.round((0.9F * (float)this.mMemoryInfo.deviceLevel + 1.5F * (float)this.mCPUInfo.deviceLevel + 0.6F * (float)this.mDisplayInfo.mOpenGLDeviceLevel) / 3.0F);
                this.mOutlineInfo.runtimeLevel = Math.round((float)(this.mMemoryInfo.runtimeLevel + this.mCPUInfo.runtimeLevel) / 2.0F);
            } else {
                if (this.mMemoryInfo == null) {
                    this.getMemoryInfo();
                }

                if (this.mCPUInfo == null) {
                    this.getCpuInfo();
                }

                if (this.mDisplayInfo == null) {
                    this.getDisplayInfo();
                }

                this.mOutlineInfo.runtimeLevel = Math.round((0.8F * (float)this.mMemoryInfo.runtimeLevel + 1.2F * (float)this.mCPUInfo.runtimeLevel) / 2.0F);
            }

            return this.mOutlineInfo;
        }
    }

    public void setDeviceScore(int var1) {
        if (this.mOutlineInfo == null) {
            this.getOutlineInfo();
        }

        if (this.mOutlineInfo != null) {
            this.mOutlineInfo.deviceScore = var1;
            if (var1 >= 90) {
                this.mOutlineInfo.deviceLevel = 0;
            } else if (var1 >= 70) {
                this.mOutlineInfo.deviceLevel = 1;
            } else {
                this.mOutlineInfo.deviceLevel = 2;
            }
        }

    }

    public class OutlineInfo {
        public int deviceLevel = -1;
        public int deviceLevelEasy;
        public int deviceScore;
        public int runtimeLevel = -1;

        public OutlineInfo() {
        }

        public AliHAHardware.OutlineInfo update() {
            AliHAHardware.this.getCpuInfo();
            AliHAHardware.this.getDisplayInfo();
            AliHAHardware.this.mOutlineInfo.runtimeLevel = Math.round((0.8F * (float)AliHAHardware.this.mMemoryInfo.runtimeLevel + 1.2F * (float)AliHAHardware.this.mCPUInfo.runtimeLevel) / 2.0F);
            return this;
        }
    }

    public class MemoryInfo {
        public long deviceTotalMemory;
        public long deviceUsedMemory;
        public long jvmTotalMemory;
        public long jvmUsedMemory;
        public long nativeTotalMemory;
        public long nativeUsedMemory;
        public long dalvikPSSMemory;
        public long nativePSSMemory;
        public long totalPSSMemory;
        public int deviceLevel = -1;
        public int runtimeLevel = -1;

        public MemoryInfo() {
        }
    }

    public class CPUInfo {
        public int cpuCoreNum = 0;
        public float avgFreq = 0.0F;
        public float cpuUsageOfApp = -1.0F;
        public float cpuUsageOfDevcie = -1.0F;
        public int cpuDeivceScore = -1;
        public int deviceLevel = -1;
        public int runtimeLevel = -1;

        public CPUInfo() {
        }
    }

    public class DisplayInfo {
        public float mDensity = 0.0F;
        public int mWidthPixels = 0;
        public int mHeightPixels = 0;
        public String mOpenGLVersion = "0";
        public int mOpenGLDeviceLevel = -1;

        public DisplayInfo() {
        }
    }

    private static class SingleHolder {
        private static AliHAHardware mInstance = new AliHAHardware();

        private SingleHolder() {
        }
    }
}
