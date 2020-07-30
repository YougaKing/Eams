//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.appstatus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@TargetApi(14)
public class AppStatusMonitor implements ActivityLifecycleCallbacks {
    private static String TAG = "AliHaAdapter.AppStatusMonitor";
    private int mActivitiesActive = 0;
    private boolean mIsInForeground = false;
    private TimerTask mApplicationStatusTimerTask;
    private final Object mApplicationStatusLockObj = new Object();
    private Timer mApplicationStatusCheckTimer = null;
    private List<AppStatusCallbacks> mAppStatusCallbacksList = new LinkedList<>();
    private final Object mAppStatusCallbacksLockObj = new Object();
    private static AppStatusMonitor s_instance = null;

    private AppStatusMonitor() {
    }

    public static synchronized AppStatusMonitor getInstance() {
        if (null == s_instance) {
            s_instance = new AppStatusMonitor();
        }

        return s_instance;
    }

    public void registerAppStatusCallbacks(AppStatusCallbacks callbacks) {
        if (null != callbacks) {
            synchronized(this.mAppStatusCallbacksLockObj) {
                this.mAppStatusCallbacksList.add(callbacks);
            }
        }

    }

    public void unregisterAppStatusCallbacks(AppStatusCallbacks callbacks) {
        if (null != callbacks) {
            synchronized(this.mAppStatusCallbacksLockObj) {
                this.mAppStatusCallbacksList.remove(callbacks);
            }
        }

    }

    private void clearApplicationStatusCheckExistingTimer() {
        synchronized(this.mApplicationStatusLockObj) {
            if (this.mApplicationStatusCheckTimer != null) {
                this.mApplicationStatusCheckTimer.cancel();
                this.mApplicationStatusCheckTimer = null;
            }

        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle bundle) {
        synchronized(this.mAppStatusCallbacksLockObj) {
            Iterator iterator = this.mAppStatusCallbacksList.iterator();

            while(iterator.hasNext()) {
                AppStatusCallbacks callbacks = (AppStatusCallbacks)iterator.next();
                callbacks.onActivityCreated(activity, bundle);
            }

        }
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        synchronized(this.mAppStatusCallbacksLockObj) {
            Iterator iterator = this.mAppStatusCallbacksList.iterator();

            while(iterator.hasNext()) {
                AppStatusCallbacks callbacks = (AppStatusCallbacks)iterator.next();
                callbacks.onActivityDestroyed(activity);
            }

        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        synchronized(this.mAppStatusCallbacksLockObj) {
            Iterator iterator = this.mAppStatusCallbacksList.iterator();

            while(iterator.hasNext()) {
                AppStatusCallbacks callbacks = (AppStatusCallbacks)iterator.next();
                callbacks.onActivityPaused(activity);
            }

        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        synchronized(this.mAppStatusCallbacksLockObj) {
            Iterator iterator = this.mAppStatusCallbacksList.iterator();

            while(iterator.hasNext()) {
                AppStatusCallbacks callbacks = (AppStatusCallbacks)iterator.next();
                callbacks.onActivityResumed(activity);
            }

        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        synchronized(this.mAppStatusCallbacksLockObj) {
            Iterator iterator = this.mAppStatusCallbacksList.iterator();

            while(iterator.hasNext()) {
                AppStatusCallbacks callbacks = (AppStatusCallbacks)iterator.next();
                callbacks.onActivitySaveInstanceState(activity, outState);
            }

        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        this.clearApplicationStatusCheckExistingTimer();
        ++this.mActivitiesActive;
        if (!this.mIsInForeground) {
            synchronized(this.mAppStatusCallbacksLockObj) {
                Iterator iterator = this.mAppStatusCallbacksList.iterator();

                while(iterator.hasNext()) {
                    AppStatusCallbacks callbacks = (AppStatusCallbacks)iterator.next();
                    callbacks.onSwitchForeground();
                }
            }
        }

        this.mIsInForeground = true;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        --this.mActivitiesActive;
        if (this.mActivitiesActive == 0) {
            this.clearApplicationStatusCheckExistingTimer();
            this.mApplicationStatusTimerTask = new AppStatusMonitor.NotInForegroundTimerTask();
            this.mApplicationStatusCheckTimer = new Timer();
            this.mApplicationStatusCheckTimer.schedule(this.mApplicationStatusTimerTask, 1000L);
        }

    }

    private class NotInForegroundTimerTask extends TimerTask {
        private NotInForegroundTimerTask() {
        }

        public void run() {
            AppStatusMonitor.this.mIsInForeground = false;
            synchronized(AppStatusMonitor.this.mAppStatusCallbacksLockObj) {
                Iterator iterator = AppStatusMonitor.this.mAppStatusCallbacksList.iterator();

                while(iterator.hasNext()) {
                    AppStatusCallbacks statusCallbacks = (AppStatusCallbacks)iterator.next();
                    statusCallbacks.onSwitchBackground();
                }

            }
        }
    }
}
