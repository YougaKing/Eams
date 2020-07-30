//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.ha.adapter.service.appstatus;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.alibaba.motu.tbrest.SendService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Event1010Handler implements AppStatusCallbacks {
    private static String TAG = "AliHaAdapter.Event1010Handler";
    private static final int MAX_HISTORY_EVENT_CNT = 600;
    private static final int MAX_HISTORY_FILE_SIZE = 40960;
    private static final String HISTORY_FILE_NAME = "aliha-appstatus1010.adt";
    private Application mApplication;
    private long mToForegroundTimestamp = 0L;
    private List<Long> mHistoryEvents;
    private Map<String, String> mExtMap;
    private final Object mLockObj = new Object();
    private AsyncThreadPool asyncTaskThread = new AsyncThreadPool();
    private static Event1010Handler s_instance = null;

    private Event1010Handler() {
    }

    public static synchronized Event1010Handler getInstance() {
        if (null == s_instance) {
            s_instance = new Event1010Handler();
        }

        return s_instance;
    }

    public void init(Application application, Map<String, String> extMap) {
        this.mApplication = application;
        this.mExtMap = extMap;
        Runnable runnable = new Runnable() {
            public void run() {
                synchronized (Event1010Handler.this.mLockObj) {
                    List<Long>  data = Event1010Handler.this.readFileData();
                    if (data != null) {
                        Event1010Handler.this.mHistoryEvents = new ArrayList<>(data);
                    } else {
                        Event1010Handler.this.mHistoryEvents = null;
                    }

                    if (Event1010Handler.this.mHistoryEvents != null && Event1010Handler.this.mHistoryEvents.size() > 0 && Event1010Handler.this.sendEvent("", Event1010Handler.this.makeTimestampExtData((Long) null))) {
                        Event1010Handler.this.clearHistory();
                    }

                }
            }
        };
        this.asyncTaskThread.start(runnable);
    }

    private void _send1010Hit(final long var1) {
        if (var1 > 0L) {
            Runnable var3 = new Runnable() {
                public void run() {
                    synchronized (Event1010Handler.this.mLockObj) {
                        long var2 = (new Date()).getTime();
                        if (Event1010Handler.this.sendEvent("" + var1, Event1010Handler.this.makeTimestampExtData(var2))) {
                            Event1010Handler.this.clearHistory();
                        } else {
                            Event1010Handler.this.addHistory(var2);
                        }

                    }
                }
            };
            this.asyncTaskThread.start(var3);
        }

    }

    private Map<String, String> makeTimestampExtData(Long var1) {
        HashMap var2 = new HashMap();
        String var3 = "";
        if (this.mHistoryEvents != null && this.mHistoryEvents.size() > 0) {
            StringBuffer var4 = new StringBuffer();
            Iterator var5 = this.mHistoryEvents.iterator();

            while (var5.hasNext()) {
                Long var6 = (Long) var5.next();
                var4.append(var6);
                var4.append("_");
            }

            var3 = var4.toString();
        }

        if (var1 != null && var1 > 0L) {
            var3 = var3 + var1;
        } else if (var3.length() > 1) {
            var3 = var3.substring(0, var3.length() - 1);
        }

        var2.put("_timestamps", var3);
        return var2;
    }

    private void clearHistory() {
        this.mHistoryEvents = null;
        this.write2File();
    }

    private void addHistory(Long var1) {
        if (var1 != null) {
            if (this.mHistoryEvents == null) {
                this.mHistoryEvents = new ArrayList();
            }

            if (this.mHistoryEvents.size() >= 600) {
                this.mHistoryEvents.remove(0);
            }

            this.mHistoryEvents.add(var1);
            this.write2File();
        }
    }

    private boolean sendEvent(Object var1, Map<String, String> var2) {
        HashMap var3 = new HashMap(this.mExtMap);
        if (var2 != null) {
            var3.putAll(var2);
        }

        return SendService.getInstance().sendRequest((String) null, System.currentTimeMillis(), "-", 1010, var1, (Object) null, (Object) null, var3);
    }

    public void onSwitchBackground() {
        long var1 = SystemClock.elapsedRealtime() - this.mToForegroundTimestamp;
        this._send1010Hit(var1);
    }

    public void onSwitchForeground() {
        this.mToForegroundTimestamp = SystemClock.elapsedRealtime();
    }

    public void onActivityCreated(Activity var1, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity var1) {
    }

    public void onActivityPaused(Activity var1) {
    }

    public void onActivityResumed(Activity var1) {
    }

    public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
    }

    public void onActivityStarted(Activity var1) {
    }

    public void onActivityStopped(Activity var1) {
    }

    private void write2File() {
        ObjectOutputStream var1 = null;

        try {
            if (this.mHistoryEvents != null && this.mHistoryEvents.size() != 0) {
                FileOutputStream var15 = this.mApplication.openFileOutput("aliha-appstatus1010.adt", 0);
                var1 = new ObjectOutputStream(var15);
                Long[] var3 = new Long[this.mHistoryEvents.size()];
                this.mHistoryEvents.toArray(var3);
                var1.writeObject(var3);
                var1.flush();
                return;
            }

            File var2 = new File(this.mApplication.getApplicationContext().getFilesDir().getPath() + "/" + "aliha-appstatus1010.adt");
            if (var2.exists()) {
                var2.delete();
            }
        } catch (Throwable var13) {
            Log.w(TAG, var13.getMessage());
            return;
        } finally {
            if (var1 != null) {
                try {
                    var1.close();
                } catch (Exception var12) {
                    Log.w(TAG, var12.getMessage());
                }
            }

        }

    }

    private List<Long> readFileData() {
        List<Long> data;
        ObjectInputStream objectInputStream = null;

        Long[] resultArray;
        try {
            FileInputStream fileInputStream = this.mApplication.openFileInput("aliha-appstatus1010.adt");
            int length = fileInputStream.available();
            if (length != 0 && length <= 40960) {
                objectInputStream = new ObjectInputStream(fileInputStream);
                resultArray = (Long[]) objectInputStream.readObject();
                data = Arrays.asList(resultArray);
                return data;
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
