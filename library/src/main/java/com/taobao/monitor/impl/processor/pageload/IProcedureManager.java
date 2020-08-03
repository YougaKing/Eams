//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.pageload;

import com.taobao.monitor.procedure.IProcedure;

public interface IProcedureManager {
    void setCurrentActivityProcedure(IProcedure var1);

    void setCurrentFragmentProcedure(IProcedure var1);

    void setCurrentLauncherProcedure(IProcedure var1);
}
