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

public interface IPageListener {
    int INIT_TIME = 0;
    int DRAW_START = 1;
    int VISIBLE = 2;
    int INTERACTIVE = 3;
    int SKI_INTERACTIVE = 4;

    void onPageChanged(String var1, int var2, long var3);

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.PARAMETER})
    public @interface PageStatus {
    }
}
