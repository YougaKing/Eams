package com.taobao.monitor.impl.data.network;

import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.NetworkStageDispatcher;
import com.taobao.network.lifecycle.IMtopLifecycle;
import com.taobao.network.lifecycle.INetworkLifecycle;

import java.util.Map;


public class NetworkLifecycleImpl implements IMtopLifecycle, INetworkLifecycle {
    private NetworkStageDispatcher networkStageDispatcher = null;

    public NetworkLifecycleImpl() {
        this.m();
    }

    private void m() {
        IDispatcher var1 = DispatcherManager.getDispatcher("NETWORK_STAGE_DISPATCHER");
        if (var1 instanceof NetworkStageDispatcher) {
            this.networkStageDispatcher = (NetworkStageDispatcher) var1;
        }

    }

    public void onRequest(String var1, String var2, Map<String, Object> var3) {
        if (!DispatcherManager.isEmpty(this.networkStageDispatcher)) {
            this.networkStageDispatcher.networkStage(0);
        }

    }

    public void onValidRequest(String var1, String var2, Map<String, Object> var3) {
    }

    public void onError(String var1, Map<String, Object> var2) {
        if (!DispatcherManager.isEmpty(this.networkStageDispatcher)) {
            this.networkStageDispatcher.networkStage(2);
        }

    }

    public void onCancel(String var1, Map<String, Object> var2) {
        if (!DispatcherManager.isEmpty(this.networkStageDispatcher)) {
            this.networkStageDispatcher.networkStage(3);
        }

    }

    public void onFinished(String var1, Map<String, Object> var2) {
        if (!DispatcherManager.isEmpty(this.networkStageDispatcher)) {
            this.networkStageDispatcher.networkStage(1);
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
