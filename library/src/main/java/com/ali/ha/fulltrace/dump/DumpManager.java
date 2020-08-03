//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.dump;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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
    private volatile boolean isInited;
    private static volatile byte initState = -1;
    public static final String TAG = "FULLTRACE";
    public static final String LOG_PATH = "log";
    public static long session = 0L;

    private DumpManager() {
        this.isInited = false;
    }

    public static final DumpManager getInstance() {
        return DumpManager.SingleInstanceHolder.sInstance;
    }

    public void append(final IReportEvent event) {
        if (initState == 1) {
            Logger.e("FULLTRACE", new Object[]{"Appending, but so was loaded failed!"});
        } else {
            Runnable tmp = new Runnable() {
                public void run() {
                    try {
                        if (event instanceof IReportRawByteEvent) {
                            byte[] datas = ((IReportRawByteEvent)event).getBody();
                            long time = event.getTime();
                            short type = event.getType();
                            Logger.d("FULLTRACE", new Object[]{"send rawBody type: 0x" + Integer.toHexString(type) + ", time:" + time + ", Body:" + datas});
                            if (null != datas) {
                                DumpManager.this.appendBytesBody(type, time, datas);
                            }
                        } else if (event instanceof IReportEvent) {
                            Logger.d("FULLTRACE", new Object[]{"send nobody type: 0x" + Integer.toHexString(event.getType())});
                            DumpManager.this.appendNoBody(event.getType(), event.getTime());
                        }
                    } catch (Throwable var5) {
                        Logger.e("native method not found.\n" + var5, new Object[0]);
                    }

                }
            };
            this.runInReportThread(tmp);
        }
    }

    private void runInReportThread(Runnable r) {
        FulltraceGlobal.instance().dumpHandler().post(r);
    }

    public void initTraceLog(Application application, HashMap<String, String> appInfos, HashMap<String, String> deviceInfos) {
        if (!this.isInited) {
            this.isInited = true;
            if (initState == 1) {
                Log.e("FULLTRACE", "initing, but so was loaded failed!");
            } else {
                HashMap<String, String> typeDescriptors = ProtocolConstants.getTypeDescriptor();
                String path = getPathPrefix(application);
                String path2Cache = getPathCachPrefix(application);
                session = System.currentTimeMillis();
                if (this.init(path2Cache + File.separator + session, path + File.separator + session, appInfos, deviceInfos, typeDescriptors)) {
                    initState = 0;
                } else {
                    initState = 2;
                }

            }
        }
    }

    public void trimHotdataBeforeUpload(String cachePath, String sessionPath) {
        if (initState == 1) {
            Log.e("FULLTRACE", "Triming, but so was loaded failed!");
        } else {
            if (!TextUtils.isEmpty(cachePath) && !TextUtils.isEmpty(sessionPath)) {
                this.trim(cachePath, sessionPath);
            }

        }
    }

    public static String getPathPrefix(Context context) {
        String proName = FTHeader.processName;
        String proNameTr = proName.replace(':', '.');
        return TextUtils.isEmpty(proNameTr) ? "" : FileUtils.getFulltraceCachePath(context, "log" + File.separator + proNameTr);
    }

    public static String getPathCachPrefix(Context context) {
        String proName = FTHeader.processName;
        String proNameTr = proName.replace(':', '.');
        return TextUtils.isEmpty(proNameTr) ? "" : FileUtils.getFulltraceDataPath(context, "log" + File.separator + proNameTr);
    }

    private native boolean init(String var1, String var2, HashMap<String, String> var3, HashMap<String, String> var4, HashMap<String, String> var5);

    private native void appendBytesBody(short var1, long var2, byte[] var4);

    private native void appendNoBody(short var1, long var2);

    private native void trim(String var1, String var2);

    static {
        try {
            System.loadLibrary("fulltrace");
        } catch (Throwable var1) {
            var1.printStackTrace();
            initState = 1;
        }

    }

    private static final class SingleInstanceHolder {
        private static final DumpManager sInstance = new DumpManager();

        private SingleInstanceHolder() {
        }
    }
}
