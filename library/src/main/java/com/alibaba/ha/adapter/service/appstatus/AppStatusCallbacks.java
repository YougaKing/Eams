//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.appstatus;

import android.app.Activity;
import android.os.Bundle;

public interface AppStatusCallbacks {
    void onSwitchBackground();

    void onSwitchForeground();

    void onActivityCreated(Activity activity, Bundle bundle);

    void onActivityDestroyed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityStarted(Activity activity);

    void onActivityStopped(Activity activity);
}
