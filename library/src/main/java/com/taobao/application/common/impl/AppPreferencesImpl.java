//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common.impl;

import com.taobao.application.common.IAppPreferences;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppPreferencesImpl implements IAppPreferences {
    private final Map<String, Object> map;

    private AppPreferencesImpl() {
        this.map = new ConcurrentHashMap<>();
    }

    public static AppPreferencesImpl instance() {
        return AppPreferencesImpl.AppPreferencesImplHolder.appPreferences;
    }

    public int getInt(String var1, int var2) {
        Integer var3 = (Integer) this.getValue(var1);
        return var3 != null ? var3 : var2;
    }

    public void putInt(String var1, int var2) {
        this.putValue(var1, var2);
    }

    public long getLong(String var1, long var2) {
        Long var4 = (Long) this.getValue(var1);
        return var4 != null ? var4 : var2;
    }

    public void putLong(String var1, long var2) {
        this.putValue(var1, var2);
    }

    public float getFloat(String var1, float var2) {
        Float var3 = (Float) this.getValue(var1);
        return var3 != null ? var3 : var2;
    }

    public void putFloat(String var1, float var2) {
        this.putValue(var1, var2);
    }

    public String getString(String var1, String var2) {
        String var3 = (String) this.getValue(var1);
        return var3 != null ? var3 : var2;
    }

    public void putString(String var1, String var2) {
        this.putValue(var1, var2);
    }

    public boolean getBoolean(String var1, boolean var2) {
        Boolean var3 = (Boolean) this.getValue(var1);
        return var3 != null ? var3 : var2;
    }

    public void putBoolean(String var1, boolean var2) {
        this.putValue(var1, var2);
    }

    private <T> T getValue(String var1) {
        return (T) this.map.get(var1);
    }

    private void putValue(String var1, Object var2) {
        if (var1 != null && var2 != null) {
            Object var3 = this.map.put(var1, var2);
            if (var3 != null && var3.getClass() != var2.getClass()) {
                throw new IllegalArgumentException(String.format("new value type:%s != old value type:%s", var2.getClass(), var3.getClass()));
            }
        }
    }

    private static class AppPreferencesImplHolder {
        static final AppPreferencesImpl appPreferences = new AppPreferencesImpl();
    }
}
