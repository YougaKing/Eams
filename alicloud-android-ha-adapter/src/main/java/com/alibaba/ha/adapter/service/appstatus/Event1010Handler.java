package com.alibaba.ha.adapter.service.appstatus;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import com.alibaba.motu.tbrest.SendService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event1010Handler implements AppStatusCallbacks {
    private static final String HISTORY_FILE_NAME = "aliha-appstatus1010.adt";
    private static final int MAX_HISTORY_EVENT_CNT = 600;
    private static final int MAX_HISTORY_FILE_SIZE = 40960;
    private static String TAG = "AliHaAdapter.Event1010Handler";
    private static Event1010Handler s_instance = null;
    private AsyncThreadPool asyncTaskThread = new AsyncThreadPool();
    private Application mApplication;
    private Map<String, String> mExtMap;
    /* access modifiers changed from: private */
    public List<Long> mHistoryEvents;
    /* access modifiers changed from: private */
    public Object mLockObj = new Object();
    private long mToForegroundTimestamp = 0;

    private Event1010Handler() {
    }

    public static synchronized Event1010Handler getInstance() {
        Event1010Handler event1010Handler;
        synchronized (Event1010Handler.class) {
            if (s_instance == null) {
                s_instance = new Event1010Handler();
            }
            event1010Handler = s_instance;
        }
        return event1010Handler;
    }

    public void init(Application application, Map<String, String> map) {
        this.mApplication = application;
        this.mExtMap = map;
        this.asyncTaskThread.start(new Runnable() {
            public void run() {
                synchronized (Event1010Handler.this.mLockObj) {
                    List access$100 = Event1010Handler.this.readFileData();
                    if (access$100 != null) {
                        Event1010Handler.this.mHistoryEvents = new ArrayList(access$100);
                    } else {
                        Event1010Handler.this.mHistoryEvents = null;
                    }
                    if (Event1010Handler.this.mHistoryEvents != null && Event1010Handler.this.mHistoryEvents.size() > 0 && Event1010Handler.this.sendEvent("", Event1010Handler.this.makeTimestampExtData(null))) {
                        Event1010Handler.this.clearHistory();
                    }
                }
            }
        });
    }

    private void _send1010Hit(final long j) {
        if (j > 0) {
            this.asyncTaskThread.start(new Runnable() {
                public void run() {
                    synchronized (Event1010Handler.this.mLockObj) {
                        long time = new Date().getTime();
                        if (Event1010Handler.this.sendEvent("" + j, Event1010Handler.this.makeTimestampExtData(Long.valueOf(time)))) {
                            Event1010Handler.this.clearHistory();
                        } else {
                            Event1010Handler.this.addHistory(Long.valueOf(time));
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public Map<String, String> makeTimestampExtData(Long l) {
        HashMap hashMap = new HashMap();
        String str = "";
        if (this.mHistoryEvents != null && this.mHistoryEvents.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (Long append : this.mHistoryEvents) {
                stringBuffer.append(append);
                stringBuffer.append("_");
            }
            str = stringBuffer.toString();
        }
        if (l != null && l.longValue() > 0) {
            str = str + l;
        } else if (str.length() > 1) {
            str = str.substring(0, str.length() - 1);
        }
        hashMap.put("_timestamps", str);
        return hashMap;
    }

    /* access modifiers changed from: private */
    public void clearHistory() {
        this.mHistoryEvents = null;
        write2File();
    }

    /* access modifiers changed from: private */
    public void addHistory(Long l) {
        if (l != null) {
            if (this.mHistoryEvents == null) {
                this.mHistoryEvents = new ArrayList();
            }
            if (this.mHistoryEvents.size() >= MAX_HISTORY_EVENT_CNT) {
                this.mHistoryEvents.remove(0);
            }
            this.mHistoryEvents.add(l);
            write2File();
        }
    }

    /* access modifiers changed from: private */
    public boolean sendEvent(Object obj, Map<String, String> map) {
        HashMap hashMap = new HashMap(this.mExtMap);
        if (map != null) {
            hashMap.putAll(map);
        }
        return SendService.getInstance().sendRequest(null, System.currentTimeMillis(), "-", 1010, obj, null, null, hashMap).booleanValue();
    }

    public void onSwitchBackground() {
        _send1010Hit(SystemClock.elapsedRealtime() - this.mToForegroundTimestamp);
    }

    public void onSwitchForeground() {
        this.mToForegroundTimestamp = SystemClock.elapsedRealtime();
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0092 A[SYNTHETIC, Splitter:B:28:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a4 A[SYNTHETIC, Splitter:B:34:0x00a4] */
    /* JADX WARNING: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void write2File() {
        /*
            r4 = this;
            r2 = 0
            java.util.List<java.lang.Long> r0 = r4.mHistoryEvents     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            if (r0 == 0) goto L_0x000d
            java.util.List<java.lang.Long> r0 = r4.mHistoryEvents     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            int r0 = r0.size()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            if (r0 != 0) goto L_0x0053
        L_0x000d:
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            r1.<init>()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            android.app.Application r3 = r4.mApplication     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            android.content.Context r3 = r3.getApplicationContext()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.io.File r3 = r3.getFilesDir()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.lang.String r3 = r3.getPath()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.lang.String r3 = "/"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.lang.String r3 = "aliha-appstatus1010.adt"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            boolean r1 = r0.exists()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            if (r1 == 0) goto L_0x0042
            r0.delete()     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
        L_0x0042:
            if (r2 == 0) goto L_0x0047
            r2.close()     // Catch:{ Exception -> 0x0048 }
        L_0x0047:
            return
        L_0x0048:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r0 = r0.getMessage()
            android.util.Log.w(r1, r0)
            goto L_0x0047
        L_0x0053:
            android.app.Application r0 = r4.mApplication     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.lang.String r1 = "aliha-appstatus1010.adt"
            r3 = 0
            java.io.FileOutputStream r0 = r0.openFileOutput(r1, r3)     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x0085, all -> 0x00a1 }
            java.util.List<java.lang.Long> r0 = r4.mHistoryEvents     // Catch:{ Throwable -> 0x00b6 }
            int r0 = r0.size()     // Catch:{ Throwable -> 0x00b6 }
            java.lang.Long[] r0 = new java.lang.Long[r0]     // Catch:{ Throwable -> 0x00b6 }
            java.util.List<java.lang.Long> r2 = r4.mHistoryEvents     // Catch:{ Throwable -> 0x00b6 }
            r2.toArray(r0)     // Catch:{ Throwable -> 0x00b6 }
            r1.writeObject(r0)     // Catch:{ Throwable -> 0x00b6 }
            r1.flush()     // Catch:{ Throwable -> 0x00b6 }
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ Exception -> 0x007a }
            goto L_0x0047
        L_0x007a:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r0 = r0.getMessage()
            android.util.Log.w(r1, r0)
            goto L_0x0047
        L_0x0085:
            r0 = move-exception
            r1 = r2
        L_0x0087:
            java.lang.String r2 = TAG     // Catch:{ all -> 0x00b3 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00b3 }
            android.util.Log.w(r2, r0)     // Catch:{ all -> 0x00b3 }
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ Exception -> 0x0096 }
            goto L_0x0047
        L_0x0096:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r0 = r0.getMessage()
            android.util.Log.w(r1, r0)
            goto L_0x0047
        L_0x00a1:
            r0 = move-exception
        L_0x00a2:
            if (r2 == 0) goto L_0x00a7
            r2.close()     // Catch:{ Exception -> 0x00a8 }
        L_0x00a7:
            throw r0
        L_0x00a8:
            r1 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r1 = r1.getMessage()
            android.util.Log.w(r2, r1)
            goto L_0x00a7
        L_0x00b3:
            r0 = move-exception
            r2 = r1
            goto L_0x00a2
        L_0x00b6:
            r0 = move-exception
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ha.adapter.service.appstatus.Event1010Handler.write2File():void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x006b A[SYNTHETIC, Splitter:B:34:0x006b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.Long> readFileData() {
        /*
            r5 = this;
            r1 = 0
            r0 = 0
            android.app.Application r2 = r5.mApplication     // Catch:{ Throwable -> 0x0049, all -> 0x0067 }
            java.lang.String r3 = "aliha-appstatus1010.adt"
            java.io.FileInputStream r3 = r2.openFileInput(r3)     // Catch:{ Throwable -> 0x0049, all -> 0x0067 }
            int r2 = r3.available()     // Catch:{ Throwable -> 0x0049, all -> 0x0067 }
            if (r2 == 0) goto L_0x0015
            r4 = 40960(0xa000, float:5.7397E-41)
            if (r2 <= r4) goto L_0x0026
        L_0x0015:
            if (r1 == 0) goto L_0x001a
            r0.close()     // Catch:{ Exception -> 0x001b }
        L_0x001a:
            return r1
        L_0x001b:
            r0 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r0 = r0.getMessage()
            android.util.Log.w(r2, r0)
            goto L_0x001a
        L_0x0026:
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x0049, all -> 0x0067 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0049, all -> 0x0067 }
            java.lang.Object r0 = r2.readObject()     // Catch:{ Throwable -> 0x007c }
            java.lang.Long[] r0 = (java.lang.Long[]) r0     // Catch:{ Throwable -> 0x007c }
            java.lang.Long[] r0 = (java.lang.Long[]) r0     // Catch:{ Throwable -> 0x007c }
            java.util.List r0 = java.util.Arrays.asList(r0)     // Catch:{ Throwable -> 0x007c }
            if (r2 == 0) goto L_0x003c
            r2.close()     // Catch:{ Exception -> 0x003e }
        L_0x003c:
            r1 = r0
            goto L_0x001a
        L_0x003e:
            r1 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r1 = r1.getMessage()
            android.util.Log.w(r2, r1)
            goto L_0x003c
        L_0x0049:
            r0 = move-exception
            r2 = r1
        L_0x004b:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x007a }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x007a }
            android.util.Log.w(r3, r0)     // Catch:{ all -> 0x007a }
            if (r2 == 0) goto L_0x007e
            r2.close()     // Catch:{ Exception -> 0x005b }
            r0 = r1
            goto L_0x003c
        L_0x005b:
            r0 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r0 = r0.getMessage()
            android.util.Log.w(r2, r0)
            r0 = r1
            goto L_0x003c
        L_0x0067:
            r0 = move-exception
            r2 = r1
        L_0x0069:
            if (r2 == 0) goto L_0x006e
            r2.close()     // Catch:{ Exception -> 0x006f }
        L_0x006e:
            throw r0
        L_0x006f:
            r1 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r1 = r1.getMessage()
            android.util.Log.w(r2, r1)
            goto L_0x006e
        L_0x007a:
            r0 = move-exception
            goto L_0x0069
        L_0x007c:
            r0 = move-exception
            goto L_0x004b
        L_0x007e:
            r0 = r1
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ha.adapter.service.appstatus.Event1010Handler.readFileData():java.util.List");
    }
}
