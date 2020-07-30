//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.common;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

public class Global {
    private Context context;
    private Handler handler;
    private String namespace;

    private Global() {
    }

    public static Global instance() {
        return GlobalHolder.GLOBAL;
    }

    public Context context() {
        return this.context;
    }

    public Global setContext(Context var1) {
        this.context = var1;
        return this;
    }

    public Global setNamespace(String var1) {
        this.namespace = var1;
        return this;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Handler handler() {
        if (this.handler == null) {
            HandlerThread var1 = new HandlerThread("APM-Monitor-Biz");
            var1.start();
            this.handler = new Handler(var1.getLooper());
        }

        return this.handler;
    }

    public Handler getAsyncUiHandler() {
        return this.handler;
    }

    public void setHandler(Handler var1) {
        this.handler = var1;
    }

    private static class GlobalHolder {
        static final Global GLOBAL = new Global();
    }
}
