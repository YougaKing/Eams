package com.ali.ha.fulltrace.dump;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.ali.ha.fulltrace.BuildConfig;
import com.ali.ha.fulltrace.FTHeader;
import com.ali.ha.fulltrace.FileUtils;
import com.ali.ha.fulltrace.FulltraceGlobal;
import com.ali.ha.fulltrace.IReportEvent;
import com.ali.ha.fulltrace.IReportRawByteEvent;
import com.ali.ha.fulltrace.ProtocolConstants;
import com.ali.ha.fulltrace.logger.Logger;
import java.io.File;
import java.util.HashMap;

public class DumpManager {
    public static final String LOG_PATH = "log";
    public static final String TAG = "FULLTRACE";
    private static volatile byte initState;
    public static long session = 0;
    private volatile boolean isInited;

    private static final class SingleInstanceHolder {
        /* access modifiers changed from: private */
        public static final DumpManager sInstance = new DumpManager();

        private SingleInstanceHolder() {
        }
    }

    /* access modifiers changed from: private */
    public native void appendBytesBody(short s, long j, byte[] bArr);

    /* access modifiers changed from: private */
    public native void appendNoBody(short s, long j);

    private native boolean init(String str, String str2, HashMap<String, String> hashMap, HashMap<String, String> hashMap2, HashMap<String, String> hashMap3);

    private native void trim(String str, String str2);

    static {
        initState = -1;
        try {
            System.loadLibrary("fulltrace");
        } catch (Throwable e) {
            e.printStackTrace();
            initState = 1;
        }
    }

    private DumpManager() {
        this.isInited = false;
    }

    public static final DumpManager getInstance() {
        return SingleInstanceHolder.sInstance;
    }

    public void append(final IReportEvent event) {
        if (initState == 1) {
            Logger.e(TAG, "Appending, but so was loaded failed!");
            return;
        }
        runInReportThread(new Runnable() {
            public void run() {
                try {
                    if (event instanceof IReportRawByteEvent) {
                        byte[] datas = ((IReportRawByteEvent) event).getBody();
                        long time = event.getTime();
                        short type = event.getType();
                        Logger.d(DumpManager.TAG, "send rawBody type: 0x" + Integer.toHexString(type) + ", time:" + time + ", Body:" + datas);
                        if (datas != null) {
                            DumpManager.this.appendBytesBody(type, time, datas);
                        }
                    } else if (event instanceof IReportEvent) {
                        Logger.d(DumpManager.TAG, "send nobody type: 0x" + Integer.toHexString(event.getType()));
                        DumpManager.this.appendNoBody(event.getType(), event.getTime());
                    }
                } catch (Throwable e) {
                    Logger.e("native method not found.\n" + e, new Object[0]);
                }
            }
        });
    }

    private void runInReportThread(Runnable r) {
        FulltraceGlobal.instance().dumpHandler().post(r);
    }

    public void initTraceLog(Application application, HashMap<String, String> appInfos, HashMap<String, String> deviceInfos) {
        if (!this.isInited) {
            this.isInited = true;
            if (initState == 1) {
                Log.e(TAG, "initing, but so was loaded failed!");
                return;
            }
            HashMap<String, String> typeDescriptors = ProtocolConstants.getTypeDescriptor();
            String path = getPathPrefix(application);
            String path2Cache = getPathCachPrefix(application);
            session = System.currentTimeMillis();
            if (init(path2Cache + File.separator + session, path + File.separator + session, appInfos, deviceInfos, typeDescriptors)) {
                initState = 0;
            } else {
                initState = 2;
            }
        }
    }

    public void trimHotdataBeforeUpload(String cachePath, String sessionPath) {
        if (initState == 1) {
            Log.e(TAG, "Triming, but so was loaded failed!");
        } else if (!TextUtils.isEmpty(cachePath) && !TextUtils.isEmpty(sessionPath)) {
            trim(cachePath, sessionPath);
        }
    }

    public static String getPathPrefix(Context context) {
        String proNameTr = FTHeader.processName.replace(':', '.');
        if (TextUtils.isEmpty(proNameTr)) {
            return BuildConfig.FLAVOR;
        }
        return FileUtils.getFulltraceCachePath(context, LOG_PATH + File.separator + proNameTr);
    }

    public static String getPathCachPrefix(Context context) {
        String proNameTr = FTHeader.processName.replace(':', '.');
        if (TextUtils.isEmpty(proNameTr)) {
            return BuildConfig.FLAVOR;
        }
        return FileUtils.getFulltraceDataPath(context, LOG_PATH + File.separator + proNameTr);
    }
}
