//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.application.common;


import androidx.annotation.Nullable;

public interface IAppPreferences {
    IAppPreferences DEFAULT = new IAppPreferences() {
        @Nullable
        public String getString(String var1, @Nullable String var2) {
            return var2;
        }

        public int getInt(String var1, int var2) {
            return var2;
        }

        public long getLong(String var1, long var2) {
            return var2;
        }

        public float getFloat(String var1, float var2) {
            return var2;
        }

        public boolean getBoolean(String var1, boolean var2) {
            return var2;
        }
    };

    @Nullable
    String getString(String var1, @Nullable String var2);

    int getInt(String var1, int var2);

    long getLong(String var1, long var2);

    float getFloat(String var1, float var2);

    boolean getBoolean(String var1, boolean var2);
}
