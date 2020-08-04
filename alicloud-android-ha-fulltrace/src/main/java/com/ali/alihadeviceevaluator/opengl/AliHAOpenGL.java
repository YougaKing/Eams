package com.ali.alihadeviceevaluator.opengl;

import android.app.ActivityManager;
import android.content.Context;

public class AliHAOpenGL {
    public float mOpenGLVersion = 0.0f;
    public int mScore;

    public void generateOpenGLVersion(Context context) {
        float f;
        if (this.mOpenGLVersion == 0.0f && context != null) {
            try {
                String glEsVersion = ((ActivityManager) context.getSystemService("activity")).getDeviceConfigurationInfo().getGlEsVersion();
                if (glEsVersion != null) {
                    f = Float.parseFloat(glEsVersion);
                    this.mOpenGLVersion = f;
                    this.mScore = getScore(this.mOpenGLVersion);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            f = 2.0f;
            this.mOpenGLVersion = f;
            this.mScore = getScore(this.mOpenGLVersion);
        }
    }

    public int getScore(float f) {
        if (((double) f) > 4.0d) {
            return 10;
        }
        if (((double) f) >= 4.0d) {
            return 9;
        }
        if (((double) f) >= 3.2d) {
            return 8;
        }
        if (((double) f) >= 3.1d) {
            return 7;
        }
        if (((double) f) >= 3.0d) {
            return 6;
        }
        if (((double) f) >= 2.0d) {
            return 3;
        }
        return 8;
    }
}
