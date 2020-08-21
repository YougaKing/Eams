package com.taobao.application.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IPageListener {
    int DRAW_START = 1;
    int INIT_TIME = 0;
    int INTERACTIVE = 3;
    public static final int SKI_INTERACTIVE = 4;
    int VISIBLE = 2;

    @Documented
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageStatus {
    }

    void onPageChanged(String pageName, int pageStatus, long timeMillis);
}
