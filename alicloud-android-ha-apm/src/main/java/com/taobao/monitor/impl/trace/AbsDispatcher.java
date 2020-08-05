package com.taobao.monitor.impl.trace;

import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AbsDispatcher */
public class AbsDispatcher<LISTENER> implements IDispatcher<LISTENER> {
    /* access modifiers changed from: private */
    public final List<LISTENER> mLISTENERS = new ArrayList();

    /* renamed from: com.taobao.monitor.impl.trace.a$a reason: collision with other inner class name */
    /* compiled from: AbsDispatcher */
    protected interface DispatcherRunnable<LISTENER> {
        void run(LISTENER listener);
    }

    protected AbsDispatcher() {
        Logger.i("AbsDispatcher", getClass().getSimpleName(), " init");
    }

    @Override
    public final void addListener(final LISTENER listener) {
        if (!(this instanceof EmptyDispatcher) && listener != null && isInstance(listener)) {
            postRunnable(new Runnable() {
                public void run() {
                    if (!AbsDispatcher.this.mLISTENERS.contains(listener)) {
                        AbsDispatcher.this.mLISTENERS.add(listener);
                    }
                }
            });
        }
    }

    private boolean isInstance(LISTENER listener) {
        return isInstance(listener, getGenericClass());
    }

    private boolean isInstance(LISTENER listener, Class cls) {
        if (cls == null) {
            return false;
        }
        return cls.isInstance(listener);
    }

    private Class getGenericClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return Object.class;
        }
        return (Class) actualTypeArguments[0];
    }

    @Override
    public final void removeListener(final LISTENER listener) {
        if (!(this instanceof EmptyDispatcher) && listener != null) {
            postRunnable(new Runnable() {
                public void run() {
                    AbsDispatcher.this.mLISTENERS.remove(listener);
                }
            });
        }
    }

    private void postRunnable(Runnable runnable) {
        Global.instance().handler().post(runnable);
    }

    /* access modifiers changed from: protected */
    public final void dispatchRunnable(final DispatcherRunnable<LISTENER> runnable) {
        postRunnable(new Runnable() {
            public void run() {
                for (Object listener : AbsDispatcher.this.mLISTENERS) {
                    runnable.run((LISTENER) listener);
                }
            }
        });
    }
}
