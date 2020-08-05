package com.taobao.monitor.impl.data;

import android.view.View;

public class WebViewProxy implements IWebView {
    public static final WebViewProxy INSTANCE = new WebViewProxy();
    private IWebView real;

    public WebViewProxy setReal(IWebView iWebView) {
        this.real = iWebView;
        return this;
    }

    public boolean isWebView(View view) {
        if (this.real != null) {
            return this.real.isWebView(view);
        }
        return false;
    }

    public boolean isWebViewLoadFinished(View view) {
        if (this.real != null) {
            return this.real.isWebViewLoadFinished(view);
        }
        return false;
    }
}
