//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IAppLaunchListener {
    int COLD = 0;
    int HOT = 1;
    int LAUNCH_DRAW_START = 0;
    int LAUNCH_VISIBLE = 1;
    int LAUNCH_INTERACTIVE = 2;
    int LAUNCH_SKI_INTERACTIVE = 3;
    int LAUNCH_COMPLETED = 4;

    void onLaunchChanged(int var1, int var2);

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.PARAMETER})
    public @interface LaunchStatus {
    }
}
