//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.network.lifecycle;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NetworkLifecycleManager implements INetworkLifecycle {
    private INetworkLifecycle lifecycle;
    private Lock readLock;
    private Lock writeLock;

    private NetworkLifecycleManager() {
        ReentrantReadWriteLock var1 = new ReentrantReadWriteLock();
        this.readLock = var1.readLock();
        this.writeLock = var1.writeLock();
    }

    public static NetworkLifecycleManager instance() {
        return NetworkLifecycleManager.Holder.INSTANCE;
    }

    public void setLifecycle(INetworkLifecycle var1) {
        this.writeLock.lock();

        try {
            if (this.lifecycle == null) {
                this.lifecycle = var1;
            }
        } finally {
            this.writeLock.unlock();
        }

    }

    public void removeLifecycle(INetworkLifecycle var1) {
        this.writeLock.lock();

        try {
            this.lifecycle = null;
        } finally {
            this.writeLock.unlock();
        }

    }

    public void onRequest(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onRequest(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onValidRequest(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onValidRequest(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onError(String var1, Map<String, Object> var2) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onError(var1, var2);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onCancel(String var1, Map<String, Object> var2) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onCancel(var1, var2);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onFinished(String var1, Map<String, Object> var2) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onFinished(var1, var2);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onEvent(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onEvent(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    private static final class Holder {
        private static final NetworkLifecycleManager INSTANCE = new NetworkLifecycleManager();

        private Holder() {
        }
    }
}
