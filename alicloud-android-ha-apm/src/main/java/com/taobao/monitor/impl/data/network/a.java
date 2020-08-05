package com.taobao.monitor.impl.data.network;

import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
import com.taobao.network.lifecycle.IMtopLifecycle;
import com.taobao.network.lifecycle.INetworkLifecycle;
import java.util.Map;

/* compiled from: NetworkLifecycleImpl */
public class a implements IMtopLifecycle, INetworkLifecycle {
    private NetworkStageDispatcher a = null;

    public a() {
        m();
    }

    private void m() {
        IDispatcher a2 = DispatcherManager.getDispatcher("NETWORK_STAGE_DISPATCHER");
        if (a2 instanceof NetworkStageDispatcher) {
            this.a = (NetworkStageDispatcher) a2;
        }
    }

    public void onRequest(String str, String str2, Map<String, Object> map) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.networkStage(0);
        }
    }

    public void onValidRequest(String str, String str2, Map<String, Object> map) {
    }

    public void onError(String str, Map<String, Object> map) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.networkStage(2);
        }
    }

    public void onCancel(String str, Map<String, Object> map) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.networkStage(3);
        }
    }

    public void onFinished(String str, Map<String, Object> map) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.networkStage(1);
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
