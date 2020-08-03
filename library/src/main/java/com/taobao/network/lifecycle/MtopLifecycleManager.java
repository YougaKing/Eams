//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.network.lifecycle;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MtopLifecycleManager implements IMtopLifecycle {
    private IMtopLifecycle lifecycle;
    private Lock readLock;
    private Lock writeLock;

    private MtopLifecycleManager() {
        ReentrantReadWriteLock var1 = new ReentrantReadWriteLock();
        this.readLock = var1.readLock();
        this.writeLock = var1.writeLock();
    }

    public static MtopLifecycleManager instance() {
        return MtopLifecycleManager.Holder.INSTANCE;
    }

    public void setLifecycle(IMtopLifecycle var1) {
        this.writeLock.lock();

        try {
            if (this.lifecycle == null) {
                this.lifecycle = var1;
            }
        } finally {
            this.writeLock.unlock();
        }

    }

    public void removeLifecycle(IMtopLifecycle var1) {
        this.writeLock.lock();

        try {
            this.lifecycle = null;
        } finally {
            this.writeLock.unlock();
        }

    }

    public void onMtopRequest(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onMtopRequest(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onMtopError(String var1, Map<String, Object> var2) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onMtopError(var1, var2);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onMtopCancel(String var1, Map<String, Object> var2) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onMtopCancel(var1, var2);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onMtopFinished(String var1, Map<String, Object> var2) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onMtopFinished(var1, var2);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onMtopEvent(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            if (this.lifecycle != null) {
                this.lifecycle.onMtopEvent(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    private static final class Holder {
        private static final MtopLifecycleManager INSTANCE = new MtopLifecycleManager();

        private Holder() {
        }
    }
}
