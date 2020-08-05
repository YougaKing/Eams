package com.taobao.application.common.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
/* compiled from: ApplicationCallbackGroup */
class d implements ActivityLifecycleCallbacks, e<ActivityLifecycleCallbacks> {
    /* access modifiers changed from: private */
    public final ArrayList<ActivityLifecycleCallbacks> b = new ArrayList<>();

    d() {
    }

    public void onActivityCreated(final Activity activity, final Bundle bundle) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = d.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityCreated(activity, bundle);
                }
            }
        });
    }

    public void onActivityStarted(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = d.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityStarted(activity);
                }
            }
        });
    }

    public void onActivityResumed(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = d.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityResumed(activity);
                }
            }
        });
    }

    public void onActivityPaused(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = d.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityPaused(activity);
                }
            }
        });
    }

    public void onActivityStopped(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = d.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityStopped(activity);
                }
            }
        });
    }

    public void onActivitySaveInstanceState(final Activity activity, final Bundle bundle) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = d.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivitySaveInstanceState(activity, bundle);
                }
            }
        });
    }

    public void onActivityDestroyed(final Activity activity) {
        a((Runnable) new Runnable() {
            public void run() {
                Iterator it = d.this.b.iterator();
                while (it.hasNext()) {
                    ((ActivityLifecycleCallbacks) it.next()).onActivityDestroyed(activity);
                }
            }
        });
    }

    /* renamed from: a */
    public void b(final ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                if (!d.this.b.contains(activityLifecycleCallbacks)) {
                    d.this.b.add(activityLifecycleCallbacks);
                }
            }
        });
    }

    /* renamed from: b */
    public void a(final ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activityLifecycleCallbacks == null) {
            throw new IllegalArgumentException();
        }
        a((Runnable) new Runnable() {
            public void run() {
                d.this.b.remove(activityLifecycleCallbacks);
            }
        });
    }

    private void a(Runnable runnable) {
        b.a().b(runnable);
    }
}
