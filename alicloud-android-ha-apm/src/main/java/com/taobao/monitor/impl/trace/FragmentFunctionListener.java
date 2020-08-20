package com.taobao.monitor.impl.trace;

import android.app.Activity;

import androidx.fragment.app.Fragment;

/* compiled from: FragmentFunctionListener */
public interface FragmentFunctionListener {

    void onFragmentAttached(Activity activity, Fragment fragment, String methodName, long timeMillis);
}
