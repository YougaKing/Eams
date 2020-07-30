//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;


public class ProcedureManager implements IProcedureManager {
    private final IProcedure root;
    private final IProcedure applicationProcedure;
    private volatile IProcedure activityProcedure;
    private volatile IProcedure launcherProcedure;
    private volatile IProcedure fragmentProcedure;

    public ProcedureManager() {
        this.root = IProcedure.DEFAULT;
        this.applicationProcedure = IProcedure.DEFAULT;
        this.launcherProcedure = IProcedure.DEFAULT;
    }

    public IProcedure setCurrentActivityProcedure(IProcedure var1) {
        this.activityProcedure = var1;
        return var1;
    }

    @Override
    public IProcedure getCurrentActivityProcedure() {
        return this.activityProcedure;
    }

    public IProcedure setCurrentFragmentProcedure(IProcedure var1) {
        this.fragmentProcedure = var1;
        return var1;
    }

    @Override
    public IProcedure getCurrentFragmentProcedure() {
        return this.fragmentProcedure;
    }

    @Override
    public IProcedure getCurrentProcedure() {
        if (this.launcherProcedure != null && this.launcherProcedure.isAlive()) {
            return this.launcherProcedure;
        } else if (this.activityProcedure != null) {
            return this.activityProcedure;
        } else {
            return this.fragmentProcedure != null ? this.fragmentProcedure : this.applicationProcedure;
        }
    }

    @Override
    public IProcedure getRootProcedure() {
        return this.root;
    }

    @Override
    public IProcedure getLauncherProcedure() {
        return this.launcherProcedure;
    }

    public IProcedure setLauncherProcedure(IProcedure var1) {
        if (var1 == null) {
            this.launcherProcedure = IProcedure.DEFAULT;
        } else {
            this.launcherProcedure = var1;
        }

        return this.launcherProcedure;
    }
}
