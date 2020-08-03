//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common.data;

public class DeviceHelper extends AbstractHelper {
    public DeviceHelper() {
    }

    public void setMobileModel(String var1) {
        this.preferences.putString("mobileModel", var1);
    }

    public void setDeviceLevel(int var1) {
        this.preferences.putInt("deviceLevel", var1);
    }

    public void setCpuScore(int var1) {
        this.preferences.putInt("cpuScore", var1);
    }

    public void setMemScore(int var1) {
        this.preferences.putInt("memScore", var1);
    }

    public void setOldDeviceScore(int var1) {
        this.preferences.putInt("oldDeviceScore", var1);
    }

    public void setCpuBrand(String var1) {
        this.preferences.putString("cpuBrand", var1);
    }

    public void setCpuModel(String var1) {
        this.preferences.putString("cpuModel", var1);
    }

    public void setGpuBrand(String var1) {
        this.preferences.putString("gpuBrand", var1);
    }

    public void setGpuModel(String var1) {
        this.preferences.putString("gpuModel", var1);
    }
}
