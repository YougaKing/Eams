package com.taobao.monitor.impl.trace;

import android.app.Activity;

import androidx.fragment.app.Fragment;


public class FragmentFunctionDispatcher extends AbsDispatcher<FragmentFunctionListener> implements FragmentFunctionListener {
    public static final FragmentFunctionDispatcher FRAGMENT_FUNCTION_DISPATCHER = new FragmentFunctionDispatcher();

    public FragmentFunctionDispatcher() {
    }

    public void a(final Activity var1, final Fragment var2, final String var3, final long var4) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentFunctionListener>() {
            @Override
            public void run(FragmentFunctionListener fragmentFunctionListener) {
                fragmentFunctionListener.a(var1, var2, var3, var4);
            }
        });
    }
}
