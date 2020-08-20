//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.alihadeviceevaluator.cpu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AliHACPUInfo {
    public int mCPUCore;
    public float mCPUMaxFreq = 0.0F;
    public float mCPUAvgFreq = 3.4028235E38F;
    public float mCPUMinFreq;
    public int mCPUScore = -1;
    private float[] m8CoreFreqStage = new float[]{1.9F, 1.8F, 1.7F, 1.5F, 1.4F, 1.2F, 1.0F, 0.9F, 0.8F};
    private float[] mCoreFreqStage = new float[]{2.4F, 2.2F, 2.0F, 1.8F, 1.5F, 1.3F, 1.2F, 1.0F, 0.9F};

    public AliHACPUInfo() {
    }

    private void fetchCPUInfo() {
        this.mCPUCore = Runtime.getRuntime().availableProcessors();
        if (this.mCPUCore > 0) {
            BufferedReader var1 = null;
            FileReader var2 = null;

            try {
                float[] var3 = new float[this.mCPUCore];
                float var4 = 0.0F;

                for(int var5 = 0; var5 < this.mCPUCore; ++var5) {
                    File var6 = new File("/sys/devices/system/cpu/cpu" + var5 + "/cpufreq/cpuinfo_max_freq");
                    if (var6.exists()) {
                        var2 = new FileReader(var6);
                        var1 = new BufferedReader(var2);
                        String var7 = var1.readLine();
                        if (var7 != null) {
                            long var8 = Long.parseLong(var7);
                            float var10 = (float)var8 / 1000000.0F;
                            var3[var5] = var10;
                            if (this.mCPUMaxFreq < var10) {
                                this.mCPUMaxFreq = var10;
                            }

                            if (this.mCPUMinFreq > var10) {
                                this.mCPUMinFreq = var10;
                            }

                            var4 += var10;
                        }

                        if (var2 != null) {
                            try {
                                var2.close();
                            } catch (IOException var24) {
                                var24.printStackTrace();
                            }
                        }

                        var2 = null;
                    }
                }

                this.mCPUAvgFreq = (float)(Math.round(100.0F * var4 / (float)this.mCPUCore) / 100);
            } catch (Throwable var25) {
                var25.printStackTrace();
            } finally {
                if (var1 != null) {
                    try {
                        var1.close();
                    } catch (IOException var23) {
                        var23.printStackTrace();
                    }
                }

                if (var2 != null) {
                    try {
                        var2.close();
                    } catch (IOException var22) {
                        var22.printStackTrace();
                    }
                }

            }

        }
    }

    public void evaluateCPUScore() {
        this.fetchCPUInfo();
        boolean var1 = false;
        float[] var2;
        if (this.mCPUCore >= 8) {
            var2 = this.m8CoreFreqStage;
        } else {
            var2 = this.mCoreFreqStage;
        }

        int var4 = 9;

        for(int var3 = 0; var3 < var2.length; ++var3) {
            if (this.mCPUMaxFreq >= var2[var3]) {
                var4 = var3;
                break;
            }
        }

        var4 = 10 - var4;
        byte var5 = 0;
        if (this.mCPUCore >= 16) {
            var5 = 10;
        } else if (this.mCPUCore >= 8) {
            var5 = 9;
        } else if (this.mCPUCore >= 6) {
            var5 = 8;
        } else if (this.mCPUCore >= 4) {
            var5 = 6;
        } else if (this.mCPUCore >= 2) {
            var5 = 4;
        }

        this.mCPUScore = (int)((float)var4 * 0.6F + (float)var5 * 0.4F);
    }
}
