package com.alibaba.ha.adapter.service.appstatus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@TargetApi(14)
public class AppStatusMonitor implements ActivityLifecycleCallbacks {
    private static String TAG = "AliHaAdapter.AppStatusMonitor";
    private static AppStatusMonitor s_instance = null;
    private int mActivitiesActive = 0;
    /* access modifiers changed from: private */
    public List<AppStatusCallbacks> mAppStatusCallbacksList = new LinkedList();
    /* access modifiers changed from: private */
    public Object mAppStatusCallbacksLockObj = new Object();
    private Timer mApplicationStatusCheckTimer = null;
    private Object mApplicationStatusLockObj = new Object();
    private TimerTask mApplicationStatusTimerTask;
    /* access modifiers changed from: private */
    public boolean mIsInForeground = false;

    private class NotInForegroundTimerTask extends TimerTask {
        private NotInForegroundTimerTask() {
        }

        public void run() {
            AppStatusMonitor.this.mIsInForeground = false;
            synchronized (AppStatusMonitor.this.mAppStatusCallbacksLockObj) {
                for (AppStatusCallbacks onSwitchBackground : AppStatusMonitor.this.mAppStatusCallbacksList) {
                    onSwitchBackground.onSwitchBackground();
                }
            }
        }
    }

    private AppStatusMonitor() {
    }

    public static synchronized AppStatusMonitor getInstance() {
        AppStatusMonitor appStatusMonitor;
        synchronized (AppStatusMonitor.class) {
            if (s_instance == null) {
                s_instance = new AppStatusMonitor();
            }
            appStatusMonitor = s_instance;
        }
        return appStatusMonitor;
    }

    public void registerAppStatusCallbacks(AppStatusCallbacks appStatusCallbacks) {
        if (appStatusCallbacks != null) {
            synchronized (this.mAppStatusCallbacksLockObj) {
                this.mAppStatusCallbacksList.add(appStatusCallbacks);
            }
        }
    }

    public void unregisterAppStatusCallbacks(AppStatusCallbacks appStatusCallbacks) {
        if (appStatusCallbacks != null) {
            synchronized (this.mAppStatusCallbacksLockObj) {
                this.mAppStatusCallbacksList.remove(appStatusCallbacks);
            }
        }
    }

    private void _clearApplicationStatusCheckExistingTimer() {
        synchronized (this.mApplicationStatusLockObj) {
            if (this.mApplicationStatusCheckTimer != null) {
                this.mApplicationStatusCheckTimer.cancel();
                this.mApplicationStatusCheckTimer = null;
            }
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (AppStatusCallbacks onActivityCreated : this.mAppStatusCallbacksList) {
                onActivityCreated.onActivityCreated(activity, bundle);
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (AppStatusCallbacks onActivityDestroyed : this.mAppStatusCallbacksList) {
                onActivityDestroyed.onActivityDestroyed(activity);
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (AppStatusCallbacks onActivityPaused : this.mAppStatusCallbacksList) {
                onActivityPaused.onActivityPaused(activity);
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (AppStatusCallbacks onActivityResumed : this.mAppStatusCallbacksList) {
                onActivityResumed.onActivityResumed(activity);
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (AppStatusCallbacks onActivitySaveInstanceState : this.mAppStatusCallbacksList) {
                onActivitySaveInstanceState.onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        _clearApplicationStatusCheckExistingTimer();
        this.mActivitiesActive++;
        if (!this.mIsInForeground) {
            synchronized (this.mAppStatusCallbacksLockObj) {
                for (AppStatusCallbacks onSwitchForeground : this.mAppStatusCallbacksList) {
                    onSwitchForeground.onSwitchForeground();
                }
            }
        }
        this.mIsInForeground = true;
    }

    public void onActivityStopped(Activity activity) {
        this.mActivitiesActive--;
        if (this.mActivitiesActive == 0) {
            _clearApplicationStatusCheckExistingTimer();
            this.mApplicationStatusTimerTask = new NotInForegroundTimerTask();
            this.mApplicationStatusCheckTimer = new Timer();
            this.mApplicationStatusCheckTimer.schedule(this.mApplicationStatusTimerTask, 1000);
        }
    }
}
