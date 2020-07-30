package com.taobao.monitor.network;

public interface INetworkSender {

    void dataUpdate(String topic, String value);
}
