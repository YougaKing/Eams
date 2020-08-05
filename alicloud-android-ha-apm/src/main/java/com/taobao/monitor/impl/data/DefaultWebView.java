package com.taobao.monitor.impl.data;

import android.view.View;
import android.webkit.WebView;

/* compiled from: DefaultWebView */
public class DefaultWebView extends AbsWebView {
    public static final DefaultWebView DEFAULT_WEB_VIEW = new DefaultWebView();

    private DefaultWebView() {
    }

    public boolean isWebView(View view) {
        return view instanceof WebView;
    }

    public int getProgress(View view) {
        return ((WebView) view).getProgress();
    }
}
