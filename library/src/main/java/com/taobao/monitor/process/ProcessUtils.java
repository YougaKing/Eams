package com.taobao.monitor.process;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.taobao.monitor.ProcedureGlobal;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

public class ProcessUtils {
    private static String mProcessName;

    public static String processName() {
        Context context = ProcedureGlobal.instance().context();
        if (TextUtils.isEmpty(mProcessName)) {
            String processName = getProcessName();
            if (TextUtils.isEmpty(processName) && context != null) {
                processName = getProcessName(context);
            }
            mProcessName = processName;
        }
        return mProcessName;
    }

    private static String getProcessName() {
        int var0 = Process.myPid();
        String args = "/proc/" + var0 + "/cmdline";
        FileInputStream inputStream = null;
        String result = null;
        byte[] bytes = new byte[1024];
        int b = 0;

        try {
            inputStream = new FileInputStream(args);
            b = inputStream.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        if (b > 0) {
            result = new String(bytes, 0, b);
            result = result.trim();
        }

        return result;
    }

    private static String getProcessName(Context context) {
        if (context == null) {
            return null;
        } else if (mProcessName != null) {
            return mProcessName;
        } else {
            int myPid = Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return null;
            } else {
                List<RunningAppProcessInfo>  runningAppProcesses = activityManager.getRunningAppProcesses();
                if (runningAppProcesses == null) {
                    return null;
                } else {
                    Iterator iterator = runningAppProcesses.iterator();

                    while (iterator.hasNext()) {
                        RunningAppProcessInfo processInfo = (RunningAppProcessInfo) iterator.next();
                        if (processInfo != null && processInfo.pid == myPid) {
                            mProcessName = processInfo.processName;
                            break;
                        }
                    }

                    return mProcessName;
                }
            }
        }
    }
}

