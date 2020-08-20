package com.taobao.monitor.performance;

public class APMAdapterFactoryProxy implements IApmAdapterFactory {
    private static final APMAdapterFactoryProxy INSTANCE = new APMAdapterFactoryProxy();
    private IApmAdapterFactory remoteFactory = new DefaultApmAdapterFactory();

    private APMAdapterFactoryProxy() {
    }

    public IWXApmAdapter createApmAdapter() {
        return this.remoteFactory.createApmAdapter();
    }

    public IWXApmAdapter createApmAdapterByType(String type) {
        return this.remoteFactory.createApmAdapterByType(type);
    }

    public void setFactory(IApmAdapterFactory iApmAdapterFactory) {
        this.remoteFactory = iApmAdapterFactory;
    }

    public static APMAdapterFactoryProxy instance() {
        return INSTANCE;
    }
}
