//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

class DefaultProcedureManager implements IProcedureManager {
    DefaultProcedureManager() {
    }

    public IProcedure getCurrentActivityProcedure() {
        return IProcedure.DEFAULT;
    }

    public IProcedure getCurrentFragmentProcedure() {
        return IProcedure.DEFAULT;
    }

    public IProcedure getCurrentProcedure() {
        return IProcedure.DEFAULT;
    }

    public IProcedure getRootProcedure() {
        return IProcedure.DEFAULT;
    }

    public IProcedure getLauncherProcedure() {
        return IProcedure.DEFAULT;
    }
}
