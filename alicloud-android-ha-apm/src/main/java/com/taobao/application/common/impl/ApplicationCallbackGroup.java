package com.taobao.application.common.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
/* compiled from: ApplicationCallbackGroup */
class ApplicationCallbackGroup implements ActivityLifecycleCallbacks, ICallbackGroup<ActivityLifecycleCallbacks> {
    /* access modifiers changed from: private */
    public final ArrayList<ActivityLifecycleCallbacks> b = new ArrayList<>();

    ApplicationCallbackGroup() {
    }

    public void onActivityCreated(final Activity activity, final Bundle bundle) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApplicationCallbackGroup.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityCreated(activity, bundle);
                }
            }
        });
    }

    public void onActivityStarted(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApplicationCallbackGroup.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityStarted(activity);
                }
            }
        });
    }

    public void onActivityResumed(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApplicationCallbackGroup.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityResumed(activity);
                }
            }
        });
    }

    public void onActivityPaused(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApplicationCallbackGroup.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityPaused(activity);
                }
            }
        });
    }

    public void onActivityStopped(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApplicationCallbackGroup.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityStopped(activity);
                }
            }
        });
    }

    public void onActivitySaveInstanceState(final Activity activity, final Bundle bundle) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApplicationCallbackGroup.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivitySaveInstanceState(activity, bundle);
                }
            }
        });
    }

    public void onActivityDestroyed(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = ApplicationCallbackGroup.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityDestroyed(activity);
                }
            }
        });
    }

    /* renamed from: a */
    public void addCallback(final ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                if (!ApplicationCallbackGroup.this.b.contains(activityLifecycleCallbacks)) {
                    ApplicationCallbackGroup.this.b.add(activityLifecycleCallbacks);
                }
            }
        });
    }

    /* renamed from: b */
    public void removeCallback(final ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                ApplicationCallbackGroup.this.b.remove(activityLifecycleCallbacks);
            }
        });
    }

    private void a(Runnable runnable) {
        ApmImpl.instance().b(runnable);
    }
}
