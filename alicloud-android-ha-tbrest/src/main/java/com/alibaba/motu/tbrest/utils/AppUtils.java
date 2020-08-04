//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.os.Build.VERSION;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class AppUtils {
    public AppUtils() {
    }

    public static String getMyProcessNameByAppProcessInfo(Context context) {
        if (null != context) {
            int pid = Process.myPid();

            try {
                ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
                Iterator var3 = activityManager.getRunningAppProcesses().iterator();

                while(var3.hasNext()) {
                    RunningAppProcessInfo appProcess = (RunningAppProcessInfo)var3.next();
                    if (appProcess.pid == pid) {
                        return appProcess.processName;
                    }
                }
            } catch (Exception var5) {
            }
        }

        return null;
    }

    public static String getGMT8Time(long timestamp) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return simpleDateFormat.format(new Date(timestamp));
        } catch (Exception var3) {
            LogUtil.e("getGMT8Time", var3);
            return "";
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception var2) {
                LogUtil.e("close.", var2);
            }
        }

    }

    public static String getMyProcessNameByCmdline() {
        try {
            return readLine("/proc/self/cmdline").trim();
        } catch (Exception var1) {
            LogUtil.e("get my process name by cmd line failure .", var1);
            return null;
        }
    }

    public static String getMyStatus() {
        return readFully(new File("/proc/self/status")).trim();
    }

    public static String getMeminfo() {
        return readFully(new File("/proc/meminfo")).trim();
    }

    public static String dumpThread(Thread thread) {
        StringBuilder sb = new StringBuilder();

        try {
            sb.append(String.format("Thread Name: '%s'\n", thread.getName()));
            sb.append(String.format("\"%s\" prio=%d tid=%d %s\n", thread.getName(), thread.getPriority(), thread.getId(), thread.getState()));
            StackTraceElement[] stackTraces = thread.getStackTrace();
            StackTraceElement[] var3 = stackTraces;
            int var4 = stackTraces.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                StackTraceElement stackTraceElement = var3[var5];
                sb.append(String.format("\tat %s\n", stackTraceElement.toString()));
            }
        } catch (Exception var7) {
            LogUtil.e("dumpThread", var7);
        }

        return sb.toString();
    }

    public static String dumpMeminfo(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
            MemoryInfo memoryInfo = new MemoryInfo();
            Integer threshold = null;
            if (null != activityManager) {
                activityManager.getMemoryInfo(memoryInfo);
                threshold = (int)(memoryInfo.threshold >> 10);
            }

            return "JavaTotal:" + Runtime.getRuntime().totalMemory() + " JavaFree:" + Runtime.getRuntime().freeMemory() + " NativeHeap:" + Debug.getNativeHeapSize() + " NativeAllocated:" + Debug.getNativeHeapAllocatedSize() + " NativeFree:" + Debug.getNativeHeapFreeSize() + " threshold:" + (null != threshold ? threshold + " KB" : "not valid");
        } catch (Exception var4) {
            LogUtil.e("dumpMeminfo", var4);
            return "";
        }
    }

    private static long[] getSizes(String path) {
        long[] sizes = new long[]{-1L, -1L, -1L};

        try {
            StatFs statFs = new StatFs(path);
            long blockSize = 0L;
            long blockCount = 0L;
            long freeBlocks = 0L;
            long availableBlocks = 0L;
            if (VERSION.SDK_INT < 18) {
                blockSize = (long)statFs.getBlockSize();
                blockCount = (long)statFs.getBlockCount();
                freeBlocks = (long)statFs.getFreeBlocks();
                availableBlocks = (long)statFs.getAvailableBlocks();
            } else {
                blockSize = statFs.getBlockSizeLong();
                blockCount = statFs.getBlockCountLong();
                freeBlocks = statFs.getFreeBlocksLong();
                availableBlocks = statFs.getAvailableBlocksLong();
            }

            sizes[0] = blockSize * blockCount;
            sizes[1] = blockSize * freeBlocks;
            sizes[2] = blockSize * availableBlocks;
        } catch (Exception var11) {
            LogUtil.e("getSizes", var11);
        }

        return sizes;
    }

    public static String dumpStorage(Context context) {
        StringBuilder stringBuffer = new StringBuilder();
        boolean hasSDCard = false;

        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                hasSDCard = true;
            }
        } catch (Exception var11) {
            LogUtil.w("hasSDCard", var11);
        }

        boolean installSDCard = false;

        try {
            ApplicationInfo appInfo = context.getApplicationInfo();
            if ((appInfo.flags & 262144) != 0) {
                installSDCard = true;
            }
        } catch (Exception var10) {
            LogUtil.w("installSDCard", var10);
        }

        stringBuffer.append("hasSDCard: " + hasSDCard + "\n");
        stringBuffer.append("installSDCard: " + installSDCard + "\n");

        try {
            File rootDir = Environment.getRootDirectory();
            if (null != rootDir) {
                long[] sizes = getSizes(rootDir.getAbsolutePath());
                stringBuffer.append("RootDirectory: " + rootDir.getAbsolutePath() + " ");
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", sizes[0], sizes[1], sizes[2]));
            }

            File dataDir = Environment.getDataDirectory();
            if (null != dataDir) {
                long[] sizes = getSizes(dataDir.getAbsolutePath());
                stringBuffer.append("DataDirectory: " + dataDir.getAbsolutePath() + " ");
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", sizes[0], sizes[1], sizes[2]));
            }

            File externalStorageDir = Environment.getExternalStorageDirectory();
            if (null != dataDir) {
                stringBuffer.append("ExternalStorageDirectory: " + externalStorageDir.getAbsolutePath() + " ");
                long[] sizes = getSizes(externalStorageDir.getAbsolutePath());
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", sizes[0], sizes[1], sizes[2]));
            }

            File downloadCacheDir = Environment.getDownloadCacheDirectory();
            if (null != downloadCacheDir) {
                stringBuffer.append("DownloadCacheDirectory: " + downloadCacheDir.getAbsolutePath() + " ");
                long[] sizes = getSizes(downloadCacheDir.getAbsolutePath());
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", sizes[0], sizes[1], sizes[2]));
            }
        } catch (Exception var9) {
            LogUtil.e("getSizes", var9);
        }

        return stringBuffer.toString();
    }

    public static Boolean isBackgroundRunning(Context context) {
        try {
            String oom_adjline = readLine("/proc/self/oom_adj").trim();
            int oom_adj = Integer.parseInt(oom_adjline);
            return 0 == oom_adj ? false : true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static boolean writeFile(File file, String str) {
        return writeFile(file, str, false);
    }

    public static boolean writeFile(File file, String str, boolean append) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(file, append);
            writer.write(str);
            writer.flush();
            boolean var4 = true;
            return var4;
        } catch (IOException var8) {
            LogUtil.e("writeFile", var8);
        } finally {
            closeQuietly(writer);
        }

        return false;
    }

    public static String readLine(String filePath) {
        return readLine(new File(filePath));
    }

    public static String readLine(File file) {
        List<String> lines = readLines(file, 1);
        return !lines.isEmpty() ? (String)lines.get(0) : "";
    }

    public static List<String> readLines(File file, int n) {
        List<String> lines = new ArrayList();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int count = 0;

            String line;
            while(null != (line = reader.readLine())) {
                ++count;
                lines.add(line);
                if (n > 0 && count >= n) {
                    break;
                }
            }
        } catch (IOException var9) {
            LogUtil.e("readLine", var9);
        } finally {
            closeQuietly(reader);
        }

        return lines;
    }

    public static String readLineAndDel(File file) {
        try {
            String line = readLine(file);
            file.delete();
            return line;
        } catch (Exception var2) {
            LogUtil.e("readLineAndDel", var2);
            return null;
        }
    }

    public static void readLine(File file, AppUtils.ReaderListener listener) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;
            while(null != (line = reader.readLine())) {
                if (listener.onReadLine(line)) {
                    return;
                }
            }
        } catch (IOException var7) {
            LogUtil.e("readLine", var7);
        } finally {
            closeQuietly(reader);
        }

    }

    public static String readFully(File file) {
        StringBuilder builder = new StringBuilder();
        FileInputStream in = null;
        InputStreamReader input = null;

        try {
            in = new FileInputStream(file);
            input = new InputStreamReader(in);
            int DEFAULT_BUFFER_SIZE = 4096;
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            boolean var6 = false;

            int n;
            while(-1 != (n = input.read(buffer))) {
                builder.append(buffer, 0, n);
            }
        } catch (Exception var10) {
            LogUtil.e("readFully.", var10);
        } finally {
            closeQuietly(input);
            closeQuietly(in);
        }

        return builder.toString();
    }

    public interface ReaderListener {
        boolean onReadLine(String var1);
    }
}
