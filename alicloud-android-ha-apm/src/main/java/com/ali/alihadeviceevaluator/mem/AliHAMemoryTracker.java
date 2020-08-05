package com.ali.alihadeviceevaluator.mem;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import java.lang.reflect.Method;

public class AliHAMemoryTracker {
    public long[] getHeapJVM() {
        return new long[]{Runtime.getRuntime().maxMemory() >> 10, (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) >> 10};
    }

    public long[] getHeapNative() {
        return new long[]{Debug.getNativeHeapSize() >> 10, (Debug.getNativeHeapAllocatedSize() - Debug.getNativeHeapFreeSize()) >> 10};
    }

    public long[] getDeviceMem() {
        long[] memInfo = getMemInfo();
        return new long[]{memInfo[0], memInfo[0] - ((memInfo[1] + memInfo[2]) + memInfo[3])};
    }

    private long[] getMemInfo() {
        long[] jArr = new long[4];
        try {
            Method method = Class.forName("android.os.Process").getMethod("readProcLines", new Class[]{String.class, String[].class, long[].class});
            String[] strArr = {"MemTotal:", "MemFree:", "Buffers:", "Cached:"};
            long[] jArr2 = new long[strArr.length];
            jArr2[0] = 30;
            jArr2[1] = -30;
            Object[] objArr = {"/proc/meminfo", strArr, jArr2};
            if (method != null) {
                method.invoke(null, objArr);
                for (int i = 0; i < jArr2.length; i++) {
                    jArr[i] = jArr2[i];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jArr;
    }

    public long[] getPrivateDirty(Context context, int i) {
        MemoryInfo memoryInfo = ((ActivityManager) context.getSystemService("activity")).getProcessMemoryInfo(new int[]{i})[0];
        return new long[]{(long) memoryInfo.dalvikPrivateDirty, (long) memoryInfo.nativePrivateDirty, (long) memoryInfo.getTotalPrivateDirty()};
    }

    public long[] getPSS(Context context, int i) {
        long[] jArr = new long[3];
        if (i >= 0) {
            try {
                MemoryInfo memoryInfo = ((ActivityManager) context.getSystemService("activity")).getProcessMemoryInfo(new int[]{i})[0];
                jArr[0] = (long) memoryInfo.dalvikPss;
                jArr[1] = (long) memoryInfo.nativePss;
                jArr[2] = (long) memoryInfo.getTotalPss();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            jArr[0] = 0;
            jArr[1] = 0;
            jArr[2] = 0;
        }
        return jArr;
    }
}
