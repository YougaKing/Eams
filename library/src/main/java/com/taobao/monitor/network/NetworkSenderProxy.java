package com.taobao.monitor.network;

public class NetworkSenderProxy implements INetworkSender {

    private INetworkSender mINetworkSender;

    private NetworkSenderProxy() {
        this.mINetworkSender = new INetworkSender() {
            @Override
            public void dataUpdate(String topic, String value) {

            }
        };
    }

    public static NetworkSenderProxy instance() {
        return NetworkSenderProxyHolder.NETWORK_SENDER_PROXY;
    }

    public NetworkSenderProxy setNetworkSender(INetworkSender iNetworkSender) {
        this.mINetworkSender = iNetworkSender;
        return this;
    }


    @Override
    public void dataUpdate(String topic, String value) {
        if (this.mINetworkSender != null) {
            this.mINetworkSender.dataUpdate(topic, value);
        }
    }

    private static class NetworkSenderProxyHolder {
        static final NetworkSenderProxy NETWORK_SENDER_PROXY = new NetworkSenderProxy();
    }
}
