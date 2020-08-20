package com.taobao.monitor.network;

/* compiled from: NetworkSenderProxy */
public class NetworkSenderProxy implements INetworkSender {
    private INetworkSender mNetworkSender;

    /* compiled from: NetworkSenderProxy */
    private static class NetworkSenderProxyHolder {
        static final NetworkSenderProxy NETWORK_SENDER_PROXY = new NetworkSenderProxy();
    }

    private NetworkSenderProxy() {
        this.mNetworkSender = new INetworkSender() {
            @Override
            public void send(String topic, String json) {
            }
        };
    }

    public static NetworkSenderProxy instance() {
        return NetworkSenderProxyHolder.NETWORK_SENDER_PROXY;
    }

    public NetworkSenderProxy setNetworkSender(INetworkSender aVar) {
        this.mNetworkSender = aVar;
        return this;
    }

    @Override
    public void send(String topic, String json) {
        if (this.mNetworkSender != null) {
            this.mNetworkSender.send(topic, json);
        }
    }
}
