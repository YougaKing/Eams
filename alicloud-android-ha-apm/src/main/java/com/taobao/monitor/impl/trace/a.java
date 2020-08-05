package com.taobao.monitor.impl.trace;

import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.Logger;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AbsDispatcher */
public class a<LISTENER> implements IDispatcher<LISTENER> {
    /* access modifiers changed from: private */
    public final List<LISTENER> a = new ArrayList();

    /* renamed from: com.taobao.monitor.impl.trace.a$a reason: collision with other inner class name */
    /* compiled from: AbsDispatcher */
    protected interface C0003a<LISTENER> {
        void c(LISTENER listener);
    }

    protected a() {
        Logger.i("AbsDispatcher", getClass().getSimpleName(), " init");
    }

    public final void addListener(final LISTENER listener) {
        if (!(this instanceof h) && listener != null && a(listener)) {
            c(new Runnable() {
                public void run() {
                    if (!a.this.a.contains(listener)) {
                        a.this.a.add(listener);
                    }
                }
            });
        }
    }

    private boolean a(LISTENER listener) {
        return a(listener, a());
    }

    private boolean a(LISTENER listener, Class cls) {
        if (cls == null) {
            return false;
        }
        return cls.isInstance(listener);
    }

    private Class a() {
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

    public final void removeListener(final LISTENER listener) {
        if (!(this instanceof h) && listener != null) {
            c(new Runnable() {
                public void run() {
                    a.this.a.remove(listener);
                }
            });
        }
    }

    private void c(Runnable runnable) {
        Global.instance().handler().post(runnable);
    }

    /* access modifiers changed from: protected */
    public final void a(final C0003a<LISTENER> aVar) {
        c(new Runnable() {
            public void run() {
                for (Object c : a.this.a) {
                    aVar.c(c);
                }
            }
        });
    }
}
