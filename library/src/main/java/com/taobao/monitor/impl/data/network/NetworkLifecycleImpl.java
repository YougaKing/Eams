package com.taobao.monitor.impl.data.network;

import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.network.lifecycle.IMtopLifecycle;
import com.taobao.network.lifecycle.INetworkLifecycle;

import java.util.Map;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2020/8/3 11:25
 * @description:
 */
public class NetworkLifecycleImpl implements IMtopLifecycle, INetworkLifecycle {
    private n a = null;

    public NetworkLifecycleImpl() {
        this.m();
    }

    private void m() {
        IDispatcher var1 = g.a("NETWORK_STAGE_DISPATCHER");
        if (var1 instanceof n) {
            this.a = (n)var1;
        }

    }

    public void onRequest(String var1, String var2, Map<String, Object> var3) {
        if (!g.a(this.a)) {
            this.a.g(0);
        }

    }

    public void onValidRequest(String var1, String var2, Map<String, Object> var3) {
    }

    public void onError(String var1, Map<String, Object> var2) {
        if (!g.a(this.a)) {
            this.a.g(2);
        }

    }

    public void onCancel(String var1, Map<String, Object> var2) {
        if (!g.a(this.a)) {
            this.a.g(3);
        }

    }

    public void onFinished(String var1, Map<String, Object> var2) {
        if (!g.a(this.a)) {
            this.a.g(1);
        }

    }

    public void onEvent(String var1, String var2, Map<String, Object> var3) {
    }

    public void onMtopRequest(String var1, String var2, Map<String, Object> var3) {
    }

    public void onMtopError(String var1, Map<String, Object> var2) {
    }

    public void onMtopCancel(String var1, Map<String, Object> var2) {
    }

    public void onMtopFinished(String var1, Map<String, Object> var2) {
    }

    public void onMtopEvent(String var1, String var2, Map<String, Object> var3) {
    }
}
