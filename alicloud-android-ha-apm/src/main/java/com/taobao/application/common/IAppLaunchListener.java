package com.taobao.application.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IAppLaunchListener {
    int COLD = 0;
    int HOT = 1;
    int LAUNCH_COMPLETED = 4;
    int LAUNCH_DRAW_START = 0;
    int LAUNCH_INTERACTIVE = 2;
    public static final int LAUNCH_SKI_INTERACTIVE = 3;
    int LAUNCH_VISIBLE = 1;

    @Documented
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchStatus {
    }

    void onLaunchChanged(int launchType, int launchStatus);
}
