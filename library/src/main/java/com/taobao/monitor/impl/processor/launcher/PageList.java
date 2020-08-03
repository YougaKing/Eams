//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.impl.processor.launcher;

import java.util.ArrayList;
import java.util.List;

public class PageList {
    private static List<String> blackList = new ArrayList();
    private static List<String> whiteList = new ArrayList();
    private static List<String> complexPageList = new ArrayList();

    public PageList() {
    }

    public static void addBlackPage(String var0) {
        blackList.add(var0);
    }

    public static boolean inBlackList(String var0) {
        return blackList.contains(var0);
    }

    public static void addWhitePage(String var0) {
        whiteList.add(var0);
    }

    public static boolean inWhiteList(String var0) {
        return whiteList.contains(var0);
    }

    public static boolean isWhiteListEmpty() {
        return whiteList.isEmpty();
    }

    public static void addComplexPage(String var0) {
        complexPageList.add(var0);
    }

    public static boolean inComplexPage(String var0) {
        return complexPageList.contains(var0);
    }
}
