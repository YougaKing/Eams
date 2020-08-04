package com.taobao.application.common;

import android.support.annotation.Nullable;

public interface IAppPreferences {
    public static final IAppPreferences DEFAULT = new IAppPreferences() {
        @Nullable
        public String getString(String str, @Nullable String str2) {
            return str2;
        }

        public int getInt(String str, int i) {
            return i;
        }

        public long getLong(String str, long j) {
            return j;
        }

        public float getFloat(String str, float f) {
            return f;
        }

        public boolean getBoolean(String str, boolean z) {
            return z;
        }
    };

    boolean getBoolean(String str, boolean z);

    float getFloat(String str, float f);

    int getInt(String str, int i);

    long getLong(String str, long j);

    @Nullable
    String getString(String str, @Nullable String str2);
}
