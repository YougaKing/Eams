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

public interface IApmEventListener {
    int NOTIFY_FOREGROUND_2_BACKGROUND = 1;
    int NOTIFY_BACKGROUND_2_FOREGROUND = 2;
    int NOTIFY_FOR_IN_BACKGROUND = 50;

    void onEvent(int var1);

    @Documented
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApmEventType {
    }
}