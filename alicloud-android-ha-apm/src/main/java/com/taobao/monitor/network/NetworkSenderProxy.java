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
            public void b(String str, String str2) {
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
    public void b(String str, String str2) {
        if (this.mNetworkSender != null) {
            this.mNetworkSender.b(str, str2);
        }
    }
}
