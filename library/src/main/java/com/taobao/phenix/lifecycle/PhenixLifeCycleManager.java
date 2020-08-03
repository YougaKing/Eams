//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.phenix.lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PhenixLifeCycleManager implements IPhenixLifeCycle {
    private List<IPhenixLifeCycle> lifeCycles;
    private Lock readLock;
    private Lock writeLock;

    private PhenixLifeCycleManager() {
        this.lifeCycles = new ArrayList(2);
        ReentrantReadWriteLock var1 = new ReentrantReadWriteLock();
        this.readLock = var1.readLock();
        this.writeLock = var1.writeLock();
    }

    public static PhenixLifeCycleManager instance() {
        return PhenixLifeCycleManager.Holder.INSTANCE;
    }

    public void addLifeCycle(IPhenixLifeCycle var1) {
        this.writeLock.lock();

        try {
            if (var1 != null && !this.lifeCycles.contains(var1)) {
                this.lifeCycles.add(var1);
            }
        } finally {
            this.writeLock.unlock();
        }

    }

    public void removeLifeCycle(IPhenixLifeCycle var1) {
        this.writeLock.lock();

        try {
            this.lifeCycles.remove(var1);
        } finally {
            this.writeLock.unlock();
        }

    }

    public void onRequest(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            Iterator var4 = this.lifeCycles.iterator();

            while(var4.hasNext()) {
                IPhenixLifeCycle var5 = (IPhenixLifeCycle)var4.next();
                var5.onRequest(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onError(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            Iterator var4 = this.lifeCycles.iterator();

            while(var4.hasNext()) {
                IPhenixLifeCycle var5 = (IPhenixLifeCycle)var4.next();
                var5.onError(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onCancel(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            Iterator var4 = this.lifeCycles.iterator();

            while(var4.hasNext()) {
                IPhenixLifeCycle var5 = (IPhenixLifeCycle)var4.next();
                var5.onCancel(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onFinished(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            Iterator var4 = this.lifeCycles.iterator();

            while(var4.hasNext()) {
                IPhenixLifeCycle var5 = (IPhenixLifeCycle)var4.next();
                var5.onFinished(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    public void onEvent(String var1, String var2, Map<String, Object> var3) {
        this.readLock.lock();

        try {
            Iterator var4 = this.lifeCycles.iterator();

            while(var4.hasNext()) {
                IPhenixLifeCycle var5 = (IPhenixLifeCycle)var4.next();
                var5.onEvent(var1, var2, var3);
            }
        } finally {
            this.readLock.unlock();
        }

    }

    private static class Holder {
        private static final PhenixLifeCycleManager INSTANCE = new PhenixLifeCycleManager();

        private Holder() {
        }
    }
}
