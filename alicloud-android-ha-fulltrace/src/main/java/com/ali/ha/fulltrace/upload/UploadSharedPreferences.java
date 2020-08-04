package com.ali.ha.fulltrace.upload;

import android.content.Context;
import android.content.SharedPreferences;
import com.ali.ha.fulltrace.FTHeader;
import java.util.HashMap;
import java.util.Map;

public class UploadSharedPreferences {
    private Map<String, SharedPreferences> processSharedPreferences;

    private static class Holder {
        /* access modifiers changed from: private */
        public static final UploadSharedPreferences instance = new UploadSharedPreferences();

        private Holder() {
        }
    }

    private UploadSharedPreferences() {
        this.processSharedPreferences = new HashMap();
    }

    public static UploadSharedPreferences instance() {
        return Holder.instance;
    }

    public SharedPreferences getProcessSP(Context context, String spName) {
        SharedPreferences sharedPreferences = (SharedPreferences) this.processSharedPreferences.get(spName);
        if (sharedPreferences == null) {
            synchronized (UploadSharedPreferences.class) {
                sharedPreferences = (SharedPreferences) this.processSharedPreferences.get(spName);
                if (sharedPreferences == null) {
                    sharedPreferences = context.getSharedPreferences(spName + FTHeader.processName, 0);
                    this.processSharedPreferences.put(spName, sharedPreferences);
                }
            }
        }
        return sharedPreferences;
    }
}
