package com.taobao.application.common.data;

import com.taobao.application.common.impl.AppPreferencesImpl;

public abstract class AbstractHelper {

    protected final AppPreferencesImpl preferences = AppPreferencesImpl.instance();

    AbstractHelper() {
    }
}
