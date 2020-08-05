package com.taobao.monitor.impl.data.e;

import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.trace.n;
import com.taobao.network.lifecycle.IMtopLifecycle;
import com.taobao.network.lifecycle.INetworkLifecycle;
import java.util.Map;

/* compiled from: NetworkLifecycleImpl */
public class a implements IMtopLifecycle, INetworkLifecycle {
    private n a = null;

    public a() {
        m();
    }

    private void m() {
        IDispatcher a2 = g.a("NETWORK_STAGE_DISPATCHER");
        if (a2 instanceof n) {
            this.a = (n) a2;
        }
    }

    public void onRequest(String str, String str2, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.g(0);
        }
    }

    public void onValidRequest(String str, String str2, Map<String, Object> map) {
    }

    public void onError(String str, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.g(2);
        }
    }

    public void onCancel(String str, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.g(3);
        }
    }

    public void onFinished(String str, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.g(1);
        }
    }

    public void onEvent(String str, String str2, Map<String, Object> map) {
    }

    public void onMtopRequest(String str, String str2, Map<String, Object> map) {
    }

    public void onMtopError(String str, Map<String, Object> map) {
    }

    public void onMtopCancel(String str, Map<String, Object> map) {
    }

    public void onMtopFinished(String str, Map<String, Object> map) {
    }

    public void onMtopEvent(String str, String str2, Map<String, Object> map) {
    }
}
