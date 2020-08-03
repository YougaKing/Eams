package com.taobao.monitor.impl.trace;


import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.impl.logger.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AbsDispatcher<LISTENER> implements IDispatcher<LISTENER> {
    private final List<LISTENER> mList = new ArrayList<>();

    protected AbsDispatcher() {
        Logger.i("AbsDispatcher", this.getClass().getSimpleName(), " init");
    }

    @Override
    public final void addListener(final LISTENER var1) {
        if (!(this instanceof EmptyDispatcher) && var1 != null) {
            if (this.isInstance(var1)) {
                this.postRunnable(new Runnable() {
                    public void run() {
                        if (!AbsDispatcher.this.mList.contains(var1)) {
                            AbsDispatcher.this.mList.add(var1);
                        }

                    }
                });
            }
        }
    }

    private boolean isInstance(LISTENER var1) {
        return this.isInstance(var1, this.getGenericClass());
    }

    private boolean isInstance(LISTENER var1, Class var2) {
        return var2 != null && var2.isInstance(var1);
    }

    private Class getGenericClass() {
        Type var1 = this.getClass().getGenericSuperclass();
        if (var1 instanceof ParameterizedType) {
            Type[] var2 = ((ParameterizedType) var1).getActualTypeArguments();
            return var2.length != 0 ? (Class) var2[0] : Object.class;
        } else {
            return Object.class;
        }
    }

    @Override
    public final void removeListener(final LISTENER var1) {
        if (!(this instanceof EmptyDispatcher) && var1 != null) {
            this.postRunnable(new Runnable() {
                public void run() {
                    AbsDispatcher.this.mList.remove(var1);
                }
            });
        }
    }

    private void postRunnable(Runnable var1) {
        Global.instance().handler().post(var1);
    }

    protected final void a(final DispatcherCall<LISTENER> var1) {
        this.postRunnable(new Runnable() {
            public void run() {
                for (Object var2 : AbsDispatcher.this.mList) {
                    var1.dispatch((LISTENER) var2);
                }
            }
        });
    }

    protected interface DispatcherCall<LISTENER> {
        void dispatch(LISTENER var1);
    }
}