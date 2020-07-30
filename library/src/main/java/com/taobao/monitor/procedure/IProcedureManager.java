//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

public interface IProcedureManager {
    IProcedure getCurrentActivityProcedure();

    IProcedure getCurrentFragmentProcedure();

    IProcedure getCurrentProcedure();

    IProcedure getRootProcedure();

    IProcedure getLauncherProcedure();
}
