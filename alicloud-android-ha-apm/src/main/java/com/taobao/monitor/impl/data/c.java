package com.taobao.monitor.impl.data;

import android.view.View;
import android.webkit.WebView;

/* compiled from: DefaultWebView */
public class c extends AbsWebView {
    public static final c a = new c();

    private c() {
    }

    public boolean isWebView(View view) {
        return view instanceof WebView;
    }

    public int getProgress(View view) {
        return ((WebView) view).getProgress();
    }
}
