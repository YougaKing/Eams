//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.util;

import com.taobao.monitor.impl.common.Global;

public class TopicUtils {
    public TopicUtils() {
    }

    public static String getFullTopic(String var0) {
        return Global.instance().getNamespace() + var0;
    }
}
