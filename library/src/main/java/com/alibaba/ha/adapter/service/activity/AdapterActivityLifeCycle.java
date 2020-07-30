//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import androidx.annotation.NonNull;

@TargetApi(14)
public class AdapterActivityLifeCycle implements ActivityLifecycleCallbacks {
    public AdapterActivityLifeCycle() {
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        ActivityNameManager.getInstance().addActivityName(activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
}
