package com.ali.alihadeviceevaluator.cpu;

public class AliHACPUInfo {
    private float[] m8CoreFreqStage = {1.9f, 1.8f, 1.7f, 1.5f, 1.4f, 1.2f, 1.0f, 0.9f, 0.8f};
    public float mCPUAvgFreq = Float.MAX_VALUE;
    public int mCPUCore;
    public float mCPUMaxFreq = 0.0f;
    public float mCPUMinFreq;
    public int mCPUScore = -1;
    private float[] mCoreFreqStage = {2.4f, 2.2f, 2.0f, 1.8f, 1.5f, 1.3f, 1.2f, 1.0f, 0.9f};

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0085 A[SYNTHETIC, Splitter:B:36:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008a A[SYNTHETIC, Splitter:B:39:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00c5 A[SYNTHETIC, Splitter:B:61:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ca A[SYNTHETIC, Splitter:B:64:0x00ca] */
    /* JADX WARNING: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void fetchCPUInfo() {
        /*
            r10 = this;
            r4 = 0
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()
            int r0 = r0.availableProcessors()
            r10.mCPUCore = r0
            int r0 = r10.mCPUCore
            if (r0 > 0) goto L_0x0010
        L_0x000f:
            return
        L_0x0010:
            int r0 = r10.mCPUCore     // Catch:{ Throwable -> 0x00e0, all -> 0x00c0 }
            float[] r6 = new float[r0]     // Catch:{ Throwable -> 0x00e0, all -> 0x00c0 }
            r0 = 0
            r2 = 0
            r5 = r2
            r3 = r4
            r1 = r4
        L_0x0019:
            int r2 = r10.mCPUCore     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            if (r5 >= r2) goto L_0x0094
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            r3.<init>()     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            java.lang.String r7 = "/sys/devices/system/cpu/cpu"
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            java.lang.String r7 = "/cpufreq/cpuinfo_max_freq"
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            if (r3 != 0) goto L_0x0046
        L_0x0041:
            int r2 = r5 + 1
            r5 = r2
            r3 = r4
            goto L_0x0019
        L_0x0046:
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00e7 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r1 = r2.readLine()     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
            if (r1 == 0) goto L_0x0072
            long r8 = java.lang.Long.parseLong(r1)     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
            float r1 = (float) r8     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
            r7 = 1232348160(0x49742400, float:1000000.0)
            float r1 = r1 / r7
            r6[r5] = r1     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
            float r7 = r10.mCPUMaxFreq     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
            int r7 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r7 >= 0) goto L_0x0069
            r10.mCPUMaxFreq = r1     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
        L_0x0069:
            float r7 = r10.mCPUMinFreq     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
            int r7 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r7 <= 0) goto L_0x0071
            r10.mCPUMinFreq = r1     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
        L_0x0071:
            float r0 = r0 + r1
        L_0x0072:
            if (r3 == 0) goto L_0x0077
            r3.close()     // Catch:{ IOException -> 0x0079 }
        L_0x0077:
            r1 = r2
            goto L_0x0041
        L_0x0079:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Throwable -> 0x007e, all -> 0x00d8 }
            goto L_0x0077
        L_0x007e:
            r0 = move-exception
            r1 = r2
        L_0x0080:
            r0.printStackTrace()     // Catch:{ all -> 0x00de }
            if (r1 == 0) goto L_0x0088
            r1.close()     // Catch:{ IOException -> 0x00bb }
        L_0x0088:
            if (r3 == 0) goto L_0x000f
            r3.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x000f
        L_0x008e:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x000f
        L_0x0094:
            r2 = 1120403456(0x42c80000, float:100.0)
            float r0 = r0 * r2
            int r2 = r10.mCPUCore     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            float r2 = (float) r2     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            float r0 = r0 / r2
            int r0 = java.lang.Math.round(r0)     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            int r0 = r0 / 100
            float r0 = (float) r0     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            r10.mCPUAvgFreq = r0     // Catch:{ Throwable -> 0x00e4, all -> 0x00db }
            if (r1 == 0) goto L_0x00a9
            r1.close()     // Catch:{ IOException -> 0x00b6 }
        L_0x00a9:
            if (r4 == 0) goto L_0x000f
            r3.close()     // Catch:{ IOException -> 0x00b0 }
            goto L_0x000f
        L_0x00b0:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x000f
        L_0x00b6:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00a9
        L_0x00bb:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0088
        L_0x00c0:
            r0 = move-exception
            r3 = r4
            r1 = r4
        L_0x00c3:
            if (r1 == 0) goto L_0x00c8
            r1.close()     // Catch:{ IOException -> 0x00ce }
        L_0x00c8:
            if (r3 == 0) goto L_0x00cd
            r3.close()     // Catch:{ IOException -> 0x00d3 }
        L_0x00cd:
            throw r0
        L_0x00ce:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00c8
        L_0x00d3:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x00cd
        L_0x00d8:
            r0 = move-exception
            r1 = r2
            goto L_0x00c3
        L_0x00db:
            r0 = move-exception
            r3 = r4
            goto L_0x00c3
        L_0x00de:
            r0 = move-exception
            goto L_0x00c3
        L_0x00e0:
            r0 = move-exception
            r3 = r4
            r1 = r4
            goto L_0x0080
        L_0x00e4:
            r0 = move-exception
            r3 = r4
            goto L_0x0080
        L_0x00e7:
            r0 = move-exception
            goto L_0x0080
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.alihadeviceevaluator.cpu.AliHACPUInfo.fetchCPUInfo():void");
    }

    public void evaluateCPUScore() {
        float[] fArr;
        int i = 9;
        fetchCPUInfo();
        if (this.mCPUCore >= 8) {
            fArr = this.m8CoreFreqStage;
        } else {
            fArr = this.mCoreFreqStage;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= fArr.length) {
                i2 = 9;
                break;
            } else if (this.mCPUMaxFreq >= fArr[i2]) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = 10 - i2;
        if (this.mCPUCore >= 16) {
            i = 10;
        } else if (this.mCPUCore < 8) {
            if (this.mCPUCore >= 6) {
                i = 8;
            } else if (this.mCPUCore >= 4) {
                i = 6;
            } else if (this.mCPUCore >= 2) {
                i = 4;
            } else {
                i = 0;
            }
        }
        this.mCPUScore = (int) ((((float) i3) * 0.6f) + (((float) i) * 0.4f));
    }
}
