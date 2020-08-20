package com.taobao.monitor.impl.trace;

import android.app.Activity;

import androidx.fragment.app.Fragment;

/* compiled from: FragmentFunctionDispatcher */
public class FragmentFunctionDispatcher extends AbsDispatcher<FragmentFunctionListener> implements FragmentFunctionListener {
    public static final FragmentFunctionDispatcher FRAGMENT_FUNCTION_DISPATCHER = new FragmentFunctionDispatcher();

    public void onFragmentAttached(Activity activity, Fragment fragment, String methodName, long timeMillis) {
        final Activity activity2 = activity;
        final Fragment fragment2 = fragment;
        final String str2 = methodName;
        final long j2 = timeMillis;
        dispatchRunnable(new DispatcherRunnable<FragmentFunctionListener>() {
            /* renamed from: a */
            public void run(FragmentFunctionListener kVar) {
                kVar.onFragmentAttached(activity2, fragment2, str2, j2);
            }
        });
    }
}
