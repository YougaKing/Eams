package com.ali.alihadeviceevaluator.util;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.ali.alihadeviceevaluator.AliHAHardware;

public class AliHALifecycle implements ActivityLifecycleCallbacks {
    private int mActivityCount = 0;

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        this.mActivityCount++;
        if (1 == this.mActivityCount) {
            AliHAHardware.getInstance().onAppForeGround();
        }
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
        this.mActivityCount--;
        if (this.mActivityCount == 0) {
            AliHAHardware.getInstance().onAppBackGround();
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
