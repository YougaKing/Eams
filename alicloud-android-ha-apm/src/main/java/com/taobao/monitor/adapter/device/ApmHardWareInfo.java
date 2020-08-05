package com.taobao.monitor.adapter.device;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.support.annotation.Keep;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ApmHardWareInfo {
    private final Editor a;

    /* renamed from: a reason: collision with other field name */
    ViewGroup f9a;

    /* renamed from: a reason: collision with other field name */
    a f10a;

    /* renamed from: a reason: collision with other field name */
    private final a f11a;
    long d;
    String e;
    String f;

    class a extends GLSurfaceView {
        b a;

        public a(Context context) {
            super(context);
            setEGLConfigChooser(8, 8, 8, 8, 0, 0);
            this.a = new b();
            setRenderer(this.a);
        }
    }

    class b implements Renderer {
        b() {
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            try {
                ApmHardWareInfo.this.e = gl10.glGetString(7937);
                ApmHardWareInfo.this.f = gl10.glGetString(7936);
                ApmHardWareInfo.this.d = ApmHardWareInfo.this.a();
                ApmHardWareInfo.a(ApmHardWareInfo.this).a(ApmHardWareInfo.this.e, ApmHardWareInfo.this.f);
                ApmHardWareInfo.a(ApmHardWareInfo.this).putString("GPU_NAME", ApmHardWareInfo.this.e);
                ApmHardWareInfo.a(ApmHardWareInfo.this).putString("GPU_BRAND", ApmHardWareInfo.this.f);
                ApmHardWareInfo.a(ApmHardWareInfo.this).apply();
            } catch (Throwable th) {
            }
        }

        public void onDrawFrame(GL10 gl10) {
        }

        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        }
    }

    @Keep
    public void getGpuInfo(Activity activity) {
        try {
            this.f9a = (ViewGroup) activity.getWindow().getDecorView();
            if (this.f9a != null) {
                this.f10a = new a(activity);
                this.f10a.setAlpha(0.0f);
                this.f9a.addView(this.f10a, new LayoutParams(1, 1));
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public long a() {
        long j;
        try {
            File file = new File("/sys/devices/platform/gpusysfs/gpu_max_clock");
            if (!file.exists()) {
                file = new File("/sys/devices/platform/gpusysfs/max_freq");
            }
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                String readLine = new BufferedReader(fileReader).readLine();
                if (readLine != null) {
                    j = Long.parseLong(readLine);
                    if (j > 0) {
                        try {
                            j = (j / 1000) / 1000;
                        } catch (Exception e2) {
                        }
                    }
                } else {
                    j = 0;
                }
                fileReader.close();
                if (j > 0) {
                    return j;
                }
            } else {
                j = 0;
            }
            File file2 = new File("/sys/class/devfreq/");
            if (file2.exists() && file2.isDirectory()) {
                File[] listFiles = file2.listFiles();
                if (listFiles == null) {
                    return 0;
                }
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= listFiles.length) {
                        break;
                    } else if (listFiles[i2].getName().contains("kgsl")) {
                        File file3 = new File(listFiles[i2].getAbsolutePath() + "/max_freq");
                        if (!file3.exists()) {
                            file3 = new File(listFiles[i2].getAbsolutePath() + "/max_gpuclk");
                        }
                        if (file3.exists()) {
                            FileReader fileReader2 = new FileReader(file3);
                            String readLine2 = new BufferedReader(fileReader2).readLine();
                            if (readLine2 != null) {
                                j = Long.parseLong(readLine2);
                                if (j > 0) {
                                    j = (j / 1000) / 1000;
                                }
                            }
                            fileReader2.close();
                        }
                    } else {
                        i = i2 + 1;
                    }
                }
            }
        } catch (Exception e3) {
            j = 0;
        }
        if (j == 0) {
            return a("/sys/devices/");
        }
        return j;
    }

    /* access modifiers changed from: 0000 */
    public long a(String str) {
        long j;
        try {
            File file = new File(str);
            if (!file.exists() || !file.isDirectory()) {
                j = 0;
            } else {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    return 0;
                }
                int i = 0;
                j = 0;
                while (i < listFiles.length) {
                    try {
                        File file2 = listFiles[i];
                        if (file2 != null && file2.getName().contains("kgsl") && file2.isDirectory()) {
                            j = a(file2.getAbsolutePath());
                            if (j > 0) {
                                return j;
                            }
                        }
                        i++;
                    } catch (Exception e2) {
                        return j;
                    }
                }
            }
            File file3 = new File(str + "/max_freq");
            if (!file3.exists()) {
                file3 = new File(str + "/max_gpuclk");
            }
            if (!file3.exists()) {
                return j;
            }
            FileReader fileReader = new FileReader(file3);
            String readLine = new BufferedReader(fileReader).readLine();
            if (readLine != null) {
                j = Long.parseLong(readLine);
                if (j > 0) {
                    j = (j / 1000) / 1000;
                }
            }
            fileReader.close();
            return j;
        } catch (Exception e3) {
            return 0;
        }
    }
}
