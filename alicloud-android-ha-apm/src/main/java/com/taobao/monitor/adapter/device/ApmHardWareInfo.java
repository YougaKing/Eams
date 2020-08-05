//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.adapter.device;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.Keep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ApmHardWareInfo {
    long d;
    String e;
    String f;
    ViewGroup mViewGroup;
    ApmHardWareInfo.ApmGLSurfaceView mApmGLSurfaceView;
    private ApmDeviceInfoCallback mApmDeviceInfoCallback;
    private Editor mEditor;

    @Keep
    public void getGpuInfo(Activity var1) {
        try {
            this.mViewGroup = (ViewGroup) var1.getWindow().getDecorView();
            if (this.mViewGroup != null) {
                this.mApmGLSurfaceView = new ApmHardWareInfo.ApmGLSurfaceView(var1);
                this.mApmGLSurfaceView.setAlpha(0.0F);
                LayoutParams var2 = new LayoutParams(1, 1);
                this.mViewGroup.addView(this.mApmGLSurfaceView, var2);
            }
        } catch (Throwable var3) {
            var3.printStackTrace();
        }

    }

    long a() {
        long var1 = 0L;

        try {
            String var3 = null;
            File var4 = new File("/sys/devices/platform/gpusysfs/gpu_max_clock");
            if (!var4.exists()) {
                var4 = new File("/sys/devices/platform/gpusysfs/max_freq");
            }

            if (var4.exists()) {
                FileReader var5 = new FileReader(var4);
                BufferedReader var6 = new BufferedReader(var5);
                var3 = var6.readLine();
                if (var3 != null) {
                    var1 = Long.parseLong(var3);
                    if (var1 > 0L) {
                        var1 = var1 / 1000L / 1000L;
                    }
                }

                var5.close();
                if (var1 > 0L) {
                    return var1;
                }
            }

            var4 = new File("/sys/class/devfreq/");
            if (var4.exists() && var4.isDirectory()) {
                File[] var12 = var4.listFiles();
                if (var12 == null) {
                    return 0L;
                }

                for (int var13 = 0; var13 < var12.length; ++var13) {
                    File var7 = var12[var13];
                    if (var7.getName().contains("kgsl")) {
                        File var8 = new File(var12[var13].getAbsolutePath() + "/max_freq");
                        if (!var8.exists()) {
                            var8 = new File(var12[var13].getAbsolutePath() + "/max_gpuclk");
                        }

                        if (var8.exists()) {
                            FileReader var9 = new FileReader(var8);
                            BufferedReader var10 = new BufferedReader(var9);
                            var3 = var10.readLine();
                            if (var3 != null) {
                                var1 = Long.parseLong(var3);
                                if (var1 > 0L) {
                                    var1 = var1 / 1000L / 1000L;
                                }
                            }

                            var9.close();
                        }
                        break;
                    }
                }
            }
        } catch (Exception var11) {
        }

        if (var1 == 0L) {
            var1 = this.a("/sys/devices/");
        }

        return var1;
    }

    long a(String var1) {
        long var2 = 0L;

        try {
            File var4 = new File(var1);
            if (var4.exists() && var4.isDirectory()) {
                File[] var5 = var4.listFiles();
                if (var5 == null) {
                    return 0L;
                }

                for (int var6 = 0; var6 < var5.length; ++var6) {
                    File var7 = var5[var6];
                    if (var7 != null && var7.getName().contains("kgsl") && var7.isDirectory()) {
                        var2 = this.a(var7.getAbsolutePath());
                        if (var2 > 0L) {
                            return var2;
                        }
                    }
                }
            }

            File var10 = new File(var1 + "/max_freq");
            if (!var10.exists()) {
                var10 = new File(var1 + "/max_gpuclk");
            }

            if (var10.exists()) {
                FileReader var11 = new FileReader(var10);
                BufferedReader var12 = new BufferedReader(var11);
                String var8 = var12.readLine();
                if (var8 != null) {
                    var2 = Long.parseLong(var8);
                    if (var2 > 0L) {
                        var2 = var2 / 1000L / 1000L;
                    }
                }

                var11.close();
            }
        } catch (Exception var9) {
        }

        return var2;
    }

    class ApmGLSurfaceView extends GLSurfaceView {
        ApmRenderer mApmRenderer;

        public ApmGLSurfaceView(Context var2) {
            super(var2);
            this.setEGLConfigChooser(8, 8, 8, 8, 0, 0);
            this.mApmRenderer = ApmHardWareInfo.this.new ApmRenderer();
            this.setRenderer(this.mApmRenderer);
        }
    }

    class ApmRenderer implements Renderer {
        ApmRenderer() {
        }

        public void onSurfaceCreated(GL10 var1, EGLConfig var2) {
            try {
                ApmHardWareInfo.this.e = var1.glGetString(7937);
                ApmHardWareInfo.this.f = var1.glGetString(7936);
                ApmHardWareInfo.this.d = ApmHardWareInfo.this.a();
                ApmHardWareInfo.this.mApmDeviceInfoCallback.a(ApmHardWareInfo.this.e, ApmHardWareInfo.this.f);
                ApmHardWareInfo.this.mEditor.putString("GPU_NAME", ApmHardWareInfo.this.e);
                ApmHardWareInfo.this.mEditor.putString("GPU_BRAND", ApmHardWareInfo.this.f);
                ApmHardWareInfo.this.mEditor.apply();
            } catch (Throwable var4) {
            }

        }

        public void onDrawFrame(GL10 var1) {
        }

        public void onSurfaceChanged(GL10 var1, int var2, int var3) {
        }
    }
}
