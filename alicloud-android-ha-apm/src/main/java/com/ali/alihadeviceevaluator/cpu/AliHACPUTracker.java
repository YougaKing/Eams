package com.ali.alihadeviceevaluator.cpu;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import com.ali.alihadeviceevaluator.util.BigNumUtils;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AliHACPUTracker implements Runnable {
    private static final boolean Debug = false;
    private static final int[] PROCESS_STATS_FORMAT = {32, 544, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224};
    static final int PROCESS_STAT_MAJOR_FAULTS = 1;
    static final int PROCESS_STAT_MINOR_FAULTS = 0;
    static final int PROCESS_STAT_STIME = 3;
    static final int PROCESS_STAT_UTIME = 2;
    public static final int PROC_COMBINE = 256;
    public static final int PROC_OUT_FLOAT = 16384;
    public static final int PROC_OUT_LONG = 8192;
    public static final int PROC_OUT_STRING = 4096;
    public static final int PROC_PARENS = 512;
    public static final int PROC_QUOTES = 1024;
    public static final int PROC_SPACE_TERM = 32;
    public static final int PROC_TAB_TERM = 9;
    public static final int PROC_TERM_MASK = 255;
    public static final int PROC_ZERO_TERM = 0;
    private static final int[] SYSTEM_CPU_FORMAT = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    private static final String TAG = "CpuTracker";
    private volatile boolean initCpu = true;
    private int lastRelIdleTime;
    private long mBaseIdleTime;
    private long mBaseIoWaitTime;
    private long mBaseIrqTime;
    private long mBaseSoftIrqTime;
    private long mBaseSystemTime;
    private long mBaseUserTime;
    private Handler mCPUHandler;
    private float mCpuPercent = -1.0f;
    private float mCurProcessCpuPercent = -1.0f;
    public long mDeltaDuration = 7000;
    private long mFirstDeltaDuration = 2000;
    private long mProcessBaseSystemTime;
    private long mProcessBaseUserTime;
    private volatile double o_cpu = 0.0d;
    private volatile double o_idle = 0.0d;
    private volatile boolean open = true;
    private ReadWriteLock peakCpuLock = new ReentrantReadWriteLock();
    private ReadWriteLock peakCurProCpuLock = new ReentrantReadWriteLock();
    private Method readProcFile;
    private String statFile;
    private final long[] statsData = new long[4];
    private long[] sysCpu = new long[7];

    public AliHACPUTracker(int i, Handler handler) {
        if (handler != null) {
            this.mCPUHandler = handler;
        } else {
            HandlerThread handlerThread = new HandlerThread(TAG);
            handlerThread.start();
            this.mCPUHandler = new Handler(handlerThread.getLooper());
        }
        init(i);
    }

    public AliHACPUTracker(int i) {
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mCPUHandler = new Handler(handlerThread.getLooper());
        init(i);
    }

    private void init(int i) {
        try {
            this.statFile = "/proc/" + i + "/stat";
            this.readProcFile = Process.class.getMethod("readProcFile", new Class[]{String.class, int[].class, String[].class, long[].class, float[].class});
            this.readProcFile.setAccessible(true);
            if (VERSION.SDK_INT < 26) {
                this.mCPUHandler.post(this);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void run() {
        try {
            if (this.initCpu) {
                this.mCPUHandler.postDelayed(this, this.mFirstDeltaDuration);
            } else if (this.open) {
                this.mCPUHandler.postDelayed(this, this.mDeltaDuration);
            }
            updateCpuPercent();
            updateCurProcessCpuPercent();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void reset(long j) {
        if (VERSION.SDK_INT < 26) {
            this.mCPUHandler.removeCallbacks(this);
            if (j > 0) {
                this.mDeltaDuration = j;
                this.mCPUHandler.postDelayed(this, this.mDeltaDuration);
                this.open = true;
                return;
            }
            this.open = Debug;
        }
    }

    public float peakCpuPercent() {
        this.peakCpuLock.readLock().lock();
        float f = this.mCpuPercent;
        this.peakCpuLock.readLock().unlock();
        return f;
    }

    public float peakCurProcessCpuPercent() {
        this.peakCurProCpuLock.readLock().lock();
        float f = this.mCurProcessCpuPercent;
        this.peakCurProCpuLock.readLock().unlock();
        return f;
    }

    public float updateCpuPercent() {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2;
        double d = 0.0d;
        float f = 0.0f;
        this.peakCpuLock.writeLock().lock();
        if (this.initCpu) {
            this.initCpu = Debug;
            try {
                randomAccessFile2 = new RandomAccessFile("/proc/stat", "r");
                try {
                    String[] split = randomAccessFile2.readLine().split(" ");
                    this.o_idle = Double.parseDouble(split[5]);
                    this.o_cpu = Double.parseDouble(split[2]) + Double.parseDouble(split[3]) + Double.parseDouble(split[4]) + Double.parseDouble(split[6]) + Double.parseDouble(split[8]) + Double.parseDouble(split[7]);
                    closeRandomAccessFile(randomAccessFile2);
                } catch (Throwable th) {
                    th = th;
                    try {
                        th.printStackTrace();
                        closeRandomAccessFile(randomAccessFile2);
                        this.peakCpuLock.writeLock().unlock();
                        return f;
                    } catch (Throwable th2) {
                        th = th2;
                        closeRandomAccessFile(randomAccessFile2);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile2 = null;
                closeRandomAccessFile(randomAccessFile2);
                throw th;
            }
        } else {
            try {
                randomAccessFile = new RandomAccessFile("/proc/stat", "r");
                try {
                    String[] split2 = randomAccessFile.readLine().split(" ");
                    double parseDouble = Double.parseDouble(split2[5]);
                    double parseDouble2 = Double.parseDouble(split2[2]) + Double.parseDouble(split2[3]) + Double.parseDouble(split2[4]) + Double.parseDouble(split2[6]) + Double.parseDouble(split2[8]) + Double.parseDouble(split2[7]);
                    if (0.0d != (parseDouble2 + parseDouble) - (this.o_cpu + this.o_idle)) {
                        d = BigNumUtils.div(100.0d * (parseDouble2 - this.o_cpu), (parseDouble2 + parseDouble) - (this.o_cpu + this.o_idle), 2);
                        if (d < 0.0d) {
                            d = 0.0d;
                        } else if (d > 100.0d) {
                            d = 100.0d;
                        }
                    }
                    this.o_cpu = parseDouble2;
                    this.o_idle = parseDouble;
                    f = (float) d;
                    this.mCpuPercent = f;
                    closeRandomAccessFile(randomAccessFile);
                } catch (Throwable th4) {
                    th = th4;
                    try {
                        th.printStackTrace();
                        closeRandomAccessFile(randomAccessFile);
                        this.peakCpuLock.writeLock().unlock();
                        return f;
                    } catch (Throwable th5) {
                        th = th5;
                        closeRandomAccessFile(randomAccessFile);
                        throw th;
                    }
                }
            } catch (Throwable th6) {
                th = th6;
                randomAccessFile = null;
                closeRandomAccessFile(randomAccessFile);
                throw th;
            }
        }
        this.peakCpuLock.writeLock().unlock();
        return f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x00a3 A[Catch:{ Exception -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x01a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float updateCurProcessCpuPercent() {
        /*
            r28 = this;
            r5 = 0
            r0 = r28
            java.lang.reflect.Method r4 = r0.readProcFile
            if (r4 == 0) goto L_0x000d
            r0 = r28
            java.lang.String r4 = r0.statFile
            if (r4 != 0) goto L_0x0039
        L_0x000d:
            java.lang.String r4 = "CpuTracker"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "readProcFile : "
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r28
            java.lang.reflect.Method r7 = r0.readProcFile
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = ", statFile : "
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r28
            java.lang.String r7 = r0.statFile
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            android.util.Log.e(r4, r6)
            r4 = r5
        L_0x0038:
            return r4
        L_0x0039:
            r0 = r28
            java.util.concurrent.locks.ReadWriteLock r4 = r0.peakCurProCpuLock
            java.util.concurrent.locks.Lock r4 = r4.writeLock()
            r4.lock()
            r0 = r28
            java.lang.reflect.Method r4 = r0.readProcFile     // Catch:{ Exception -> 0x01b3 }
            r6 = 0
            r7 = 5
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x01b3 }
            r8 = 0
            r0 = r28
            java.lang.String r9 = r0.statFile     // Catch:{ Exception -> 0x01b3 }
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 1
            int[] r9 = PROCESS_STATS_FORMAT     // Catch:{ Exception -> 0x01b3 }
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 2
            r9 = 0
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 3
            r0 = r28
            long[] r9 = r0.statsData     // Catch:{ Exception -> 0x01b3 }
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 4
            r9 = 0
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            java.lang.Object r4 = r4.invoke(r6, r7)     // Catch:{ Exception -> 0x01b3 }
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ Exception -> 0x01b3 }
            boolean r4 = r4.booleanValue()     // Catch:{ Exception -> 0x01b3 }
            if (r4 == 0) goto L_0x019c
            r0 = r28
            java.lang.reflect.Method r4 = r0.readProcFile     // Catch:{ Exception -> 0x01b3 }
            r6 = 0
            r7 = 5
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x01b3 }
            r8 = 0
            java.lang.String r9 = "/proc/stat"
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 1
            int[] r9 = SYSTEM_CPU_FORMAT     // Catch:{ Exception -> 0x01b3 }
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 2
            r9 = 0
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 3
            r0 = r28
            long[] r9 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            r8 = 4
            r9 = 0
            r7[r8] = r9     // Catch:{ Exception -> 0x01b3 }
            java.lang.Object r4 = r4.invoke(r6, r7)     // Catch:{ Exception -> 0x01b3 }
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ Exception -> 0x01b3 }
            boolean r4 = r4.booleanValue()     // Catch:{ Exception -> 0x01b3 }
            if (r4 == 0) goto L_0x019c
            r4 = 1
        L_0x00a1:
            if (r4 == 0) goto L_0x01a5
            r0 = r28
            long[] r4 = r0.statsData     // Catch:{ Exception -> 0x01b3 }
            r6 = 2
            r6 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long r8 = r0.mProcessBaseUserTime     // Catch:{ Exception -> 0x01b3 }
            long r6 = r6 - r8
            int r7 = (int) r6     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long[] r4 = r0.statsData     // Catch:{ Exception -> 0x01b3 }
            r6 = 3
            r8 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long r10 = r0.mProcessBaseSystemTime     // Catch:{ Exception -> 0x01b3 }
            long r8 = r8 - r10
            int r8 = (int) r8     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long[] r4 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r6 = 0
            r10 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long[] r4 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r6 = 1
            r12 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            long r10 = r10 + r12
            r0 = r28
            long[] r4 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r6 = 2
            r12 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long[] r4 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r6 = 3
            r14 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long[] r4 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r6 = 4
            r16 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long[] r4 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r6 = 5
            r18 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long[] r4 = r0.sysCpu     // Catch:{ Exception -> 0x01b3 }
            r6 = 6
            r20 = r4[r6]     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long r0 = r0.mBaseUserTime     // Catch:{ Exception -> 0x01b3 }
            r22 = r0
            long r22 = r10 - r22
            r0 = r22
            int r9 = (int) r0     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            long r0 = r0.mBaseSystemTime     // Catch:{ Exception -> 0x01b3 }
            r22 = r0
            long r22 = r12 - r22
            r0 = r22
            int r0 = (int) r0     // Catch:{ Exception -> 0x01b3 }
            r22 = r0
            r0 = r28
            long r0 = r0.mBaseIoWaitTime     // Catch:{ Exception -> 0x01b3 }
            r24 = r0
            long r24 = r16 - r24
            r0 = r24
            int r0 = (int) r0     // Catch:{ Exception -> 0x01b3 }
            r23 = r0
            r0 = r28
            long r0 = r0.mBaseIrqTime     // Catch:{ Exception -> 0x01b3 }
            r24 = r0
            long r24 = r18 - r24
            r0 = r24
            int r0 = (int) r0     // Catch:{ Exception -> 0x01b3 }
            r24 = r0
            r0 = r28
            long r0 = r0.mBaseSoftIrqTime     // Catch:{ Exception -> 0x01b3 }
            r26 = r0
            long r26 = r20 - r26
            r0 = r26
            int r0 = (int) r0     // Catch:{ Exception -> 0x01b3 }
            r25 = r0
            r0 = r28
            long r0 = r0.mBaseIdleTime     // Catch:{ Exception -> 0x01b3 }
            r26 = r0
            long r26 = r14 - r26
            r0 = r26
            int r4 = (int) r0     // Catch:{ Exception -> 0x01b3 }
            r6 = 1
            if (r4 <= r6) goto L_0x019f
            r6 = r4
        L_0x013d:
            int r4 = r9 + r22
            int r4 = r4 + r23
            int r4 = r4 + r24
            int r4 = r4 + r25
            int r4 = r4 + r6
            r9 = 1
            if (r4 <= r9) goto L_0x01d5
            int r7 = r7 + r8
            int r7 = r7 * 100
            float r7 = (float) r7     // Catch:{ Exception -> 0x01b3 }
            float r4 = (float) r4     // Catch:{ Exception -> 0x01b3 }
            r8 = 2
            float r4 = com.ali.alihadeviceevaluator.util.BigNumUtils.div(r7, r4, r8)     // Catch:{ Exception -> 0x01b3 }
            r0 = r28
            r0.mCurProcessCpuPercent = r4     // Catch:{ Exception -> 0x01d2 }
        L_0x0157:
            r0 = r28
            long[] r5 = r0.statsData     // Catch:{ Exception -> 0x01d2 }
            r7 = 2
            r8 = r5[r7]     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            r0.mProcessBaseUserTime = r8     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            long[] r5 = r0.statsData     // Catch:{ Exception -> 0x01d2 }
            r7 = 3
            r8 = r5[r7]     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            r0.mProcessBaseSystemTime = r8     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            r0.mBaseUserTime = r10     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            r0.mBaseSystemTime = r12     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            r0.mBaseIdleTime = r14     // Catch:{ Exception -> 0x01d2 }
            r0 = r16
            r2 = r28
            r2.mBaseIoWaitTime = r0     // Catch:{ Exception -> 0x01d2 }
            r0 = r18
            r2 = r28
            r2.mBaseIrqTime = r0     // Catch:{ Exception -> 0x01d2 }
            r0 = r20
            r2 = r28
            r2.mBaseSoftIrqTime = r0     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            r0.lastRelIdleTime = r6     // Catch:{ Exception -> 0x01d2 }
            r0 = r28
            java.util.concurrent.locks.ReadWriteLock r5 = r0.peakCurProCpuLock
            java.util.concurrent.locks.Lock r5 = r5.writeLock()
            r5.unlock()
            goto L_0x0038
        L_0x019c:
            r4 = 0
            goto L_0x00a1
        L_0x019f:
            r0 = r28
            int r4 = r0.lastRelIdleTime     // Catch:{ Exception -> 0x01b3 }
            r6 = r4
            goto L_0x013d
        L_0x01a5:
            r0 = r28
            java.util.concurrent.locks.ReadWriteLock r4 = r0.peakCurProCpuLock
            java.util.concurrent.locks.Lock r4 = r4.writeLock()
            r4.unlock()
            r4 = r5
            goto L_0x0038
        L_0x01b3:
            r6 = move-exception
            r4 = r5
        L_0x01b5:
            r6.printStackTrace()     // Catch:{ all -> 0x01c5 }
            r0 = r28
            java.util.concurrent.locks.ReadWriteLock r5 = r0.peakCurProCpuLock
            java.util.concurrent.locks.Lock r5 = r5.writeLock()
            r5.unlock()
            goto L_0x0038
        L_0x01c5:
            r4 = move-exception
            r0 = r28
            java.util.concurrent.locks.ReadWriteLock r5 = r0.peakCurProCpuLock
            java.util.concurrent.locks.Lock r5 = r5.writeLock()
            r5.unlock()
            throw r4
        L_0x01d2:
            r5 = move-exception
            r6 = r5
            goto L_0x01b5
        L_0x01d5:
            r4 = r5
            goto L_0x0157
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.alihadeviceevaluator.cpu.AliHACPUTracker.updateCurProcessCpuPercent():float");
    }

    private void closeRandomAccessFile(RandomAccessFile randomAccessFile) {
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
