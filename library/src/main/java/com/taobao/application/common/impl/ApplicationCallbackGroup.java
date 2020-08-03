//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
class ApplicationCallbackGroup implements ActivityLifecycleCallbacks, ICallbackGroup<ActivityLifecycleCallbacks> {
    private final ArrayList<ActivityLifecycleCallbacks> mLifecycleCallbacks = new ArrayList<>();

    ApplicationCallbackGroup() {
    }

    public void onActivityCreated(final Activity var1, final Bundle var2) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApplicationCallbackGroup.this.mLifecycleCallbacks.iterator();

                while(var1x.hasNext()) {
                    ActivityLifecycleCallbacks var2x = (ActivityLifecycleCallbacks)var1x.next();
                    var2x.onActivityCreated(var1, var2);
                }

            }
        });
    }

    public void onActivityStarted(final Activity var1) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApplicationCallbackGroup.this.mLifecycleCallbacks.iterator();

                while(var1x.hasNext()) {
                    ActivityLifecycleCallbacks var2 = (ActivityLifecycleCallbacks)var1x.next();
                    var2.onActivityStarted(var1);
                }

            }
        });
    }

    public void onActivityResumed(final Activity var1) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApplicationCallbackGroup.this.mLifecycleCallbacks.iterator();

                while(var1x.hasNext()) {
                    ActivityLifecycleCallbacks var2 = (ActivityLifecycleCallbacks)var1x.next();
                    var2.onActivityResumed(var1);
                }

            }
        });
    }

    public void onActivityPaused(final Activity var1) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApplicationCallbackGroup.this.mLifecycleCallbacks.iterator();

                while(var1x.hasNext()) {
                    ActivityLifecycleCallbacks var2 = (ActivityLifecycleCallbacks)var1x.next();
                    var2.onActivityPaused(var1);
                }

            }
        });
    }

    public void onActivityStopped(final Activity var1) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApplicationCallbackGroup.this.mLifecycleCallbacks.iterator();

                while(var1x.hasNext()) {
                    ActivityLifecycleCallbacks var2 = (ActivityLifecycleCallbacks)var1x.next();
                    var2.onActivityStopped(var1);
                }

            }
        });
    }

    public void onActivitySaveInstanceState(final Activity var1, final Bundle var2) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApplicationCallbackGroup.this.mLifecycleCallbacks.iterator();

                while(var1x.hasNext()) {
                    ActivityLifecycleCallbacks var2x = (ActivityLifecycleCallbacks)var1x.next();
                    var2x.onActivitySaveInstanceState(var1, var2);
                }

            }
        });
    }

    public void onActivityDestroyed(final Activity var1) {
        this.postRunnable(new Runnable() {
            public void run() {
                Iterator var1x = ApplicationCallbackGroup.this.mLifecycleCallbacks.iterator();

                while(var1x.hasNext()) {
                    ActivityLifecycleCallbacks var2 = (ActivityLifecycleCallbacks)var1x.next();
                    var2.onActivityDestroyed(var1);
                }

            }
        });
    }

    public void a(final ActivityLifecycleCallbacks var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.postRunnable(new Runnable() {
                public void run() {
                    if (!ApplicationCallbackGroup.this.mLifecycleCallbacks.contains(var1)) {
                        ApplicationCallbackGroup.this.mLifecycleCallbacks.add(var1);
                    }

                }
            });
        }
    }

    public void b(final ActivityLifecycleCallbacks var1) {
        if (var1 == null) {
            throw new IllegalArgumentException();
        } else {
            this.postRunnable(new Runnable() {
                public void run() {
                    ApplicationCallbackGroup.this.mLifecycleCallbacks.remove(var1);
                }
            });
        }
    }

    private void postRunnable(Runnable var1) {
        ApmImpl.instance().postRunnable(var1);
    }
}
