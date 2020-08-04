package com.alibaba.motu.tbrest.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import com.alibaba.motu.tbrest.BuildConfig;
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
import java.util.List;
import java.util.TimeZone;

public class AppUtils {

    public interface ReaderListener {
        boolean onReadLine(String str);
    }

    public static String getMyProcessNameByAppProcessInfo(Context context) {
        if (context != null) {
            int pid = Process.myPid();
            try {
                for (RunningAppProcessInfo appProcess : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                    if (appProcess.pid == pid) {
                        return appProcess.processName;
                    }
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static String getGMT8Time(long timestamp) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return simpleDateFormat.format(new Date(timestamp));
        } catch (Exception e) {
            LogUtil.e("getGMT8Time", e);
            return BuildConfig.FLAVOR;
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LogUtil.e("close.", e);
            }
        }
    }

    public static String getMyProcessNameByCmdline() {
        try {
            return readLine("/proc/self/cmdline").trim();
        } catch (Exception e) {
            LogUtil.e("get my process name by cmd line failure .", e);
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
            sb.append(String.format("Thread Name: '%s'\n", new Object[]{thread.getName()}));
            sb.append(String.format("\"%s\" prio=%d tid=%d %s\n", new Object[]{thread.getName(), Integer.valueOf(thread.getPriority()), Long.valueOf(thread.getId()), thread.getState()}));
            for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
                sb.append(String.format("\tat %s\n", new Object[]{stackTraceElement.toString()}));
            }
        } catch (Exception e) {
            LogUtil.e("dumpThread", e);
        }
        return sb.toString();
    }

    public static String dumpMeminfo(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            MemoryInfo memoryInfo = new MemoryInfo();
            Integer threshold = null;
            if (activityManager != null) {
                activityManager.getMemoryInfo(memoryInfo);
                threshold = Integer.valueOf((int) (memoryInfo.threshold >> 10));
            }
            return "JavaTotal:" + Runtime.getRuntime().totalMemory() + " JavaFree:" + Runtime.getRuntime().freeMemory() + " NativeHeap:" + Debug.getNativeHeapSize() + " NativeAllocated:" + Debug.getNativeHeapAllocatedSize() + " NativeFree:" + Debug.getNativeHeapFreeSize() + " threshold:" + (threshold != null ? threshold + " KB" : "not valid");
        } catch (Exception e) {
            LogUtil.e("dumpMeminfo", e);
            return BuildConfig.FLAVOR;
        }
    }

    private static long[] getSizes(String path) {
        long blockSize;
        long blockCount;
        long freeBlocks;
        long availableBlocks;
        long[] sizes = {-1, -1, -1};
        try {
            StatFs statFs = new StatFs(path);
            if (VERSION.SDK_INT < 18) {
                blockSize = (long) statFs.getBlockSize();
                blockCount = (long) statFs.getBlockCount();
                freeBlocks = (long) statFs.getFreeBlocks();
                availableBlocks = (long) statFs.getAvailableBlocks();
            } else {
                blockSize = statFs.getBlockSizeLong();
                blockCount = statFs.getBlockCountLong();
                freeBlocks = statFs.getFreeBlocksLong();
                availableBlocks = statFs.getAvailableBlocksLong();
            }
            sizes[0] = blockSize * blockCount;
            sizes[1] = blockSize * freeBlocks;
            sizes[2] = blockSize * availableBlocks;
        } catch (Exception e) {
            LogUtil.e("getSizes", e);
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
        } catch (Exception e) {
            LogUtil.w("hasSDCard", e);
        }
        boolean installSDCard = false;
        try {
            if ((context.getApplicationInfo().flags & 262144) != 0) {
                installSDCard = true;
            }
        } catch (Exception e2) {
            LogUtil.w("installSDCard", e2);
        }
        stringBuffer.append("hasSDCard: " + hasSDCard + "\n");
        stringBuffer.append("installSDCard: " + installSDCard + "\n");
        try {
            File rootDir = Environment.getRootDirectory();
            if (rootDir != null) {
                long[] sizes = getSizes(rootDir.getAbsolutePath());
                stringBuffer.append("RootDirectory: " + rootDir.getAbsolutePath() + " ");
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(sizes[0]), Long.valueOf(sizes[1]), Long.valueOf(sizes[2])}));
            }
            File dataDir = Environment.getDataDirectory();
            if (dataDir != null) {
                long[] sizes2 = getSizes(dataDir.getAbsolutePath());
                stringBuffer.append("DataDirectory: " + dataDir.getAbsolutePath() + " ");
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(sizes2[0]), Long.valueOf(sizes2[1]), Long.valueOf(sizes2[2])}));
            }
            File externalStorageDir = Environment.getExternalStorageDirectory();
            if (dataDir != null) {
                stringBuffer.append("ExternalStorageDirectory: " + externalStorageDir.getAbsolutePath() + " ");
                long[] sizes3 = getSizes(externalStorageDir.getAbsolutePath());
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(sizes3[0]), Long.valueOf(sizes3[1]), Long.valueOf(sizes3[2])}));
            }
            File downloadCacheDir = Environment.getDownloadCacheDirectory();
            if (downloadCacheDir != null) {
                stringBuffer.append("DownloadCacheDirectory: " + downloadCacheDir.getAbsolutePath() + " ");
                long[] sizes4 = getSizes(downloadCacheDir.getAbsolutePath());
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(sizes4[0]), Long.valueOf(sizes4[1]), Long.valueOf(sizes4[2])}));
            }
        } catch (Exception e3) {
            LogUtil.e("getSizes", e3);
        }
        return stringBuffer.toString();
    }

    public static Boolean isBackgroundRunning(Context context) {
        try {
            if (Integer.parseInt(readLine("/proc/self/oom_adj").trim()) == 0) {
                return Boolean.valueOf(false);
            }
            return Boolean.valueOf(true);
        } catch (Exception e) {
            return Boolean.valueOf(false);
        }
    }

    public static boolean writeFile(File file, String str) {
        return writeFile(file, str, false);
    }

    public static boolean writeFile(File file, String str, boolean append) {
        FileWriter writer = null;
        try {
            FileWriter writer2 = new FileWriter(file, append);
            try {
                writer2.write(str);
                writer2.flush();
                closeQuietly(writer2);
                FileWriter fileWriter = writer2;
                return true;
            } catch (IOException e) {
                e = e;
                writer = writer2;
                try {
                    LogUtil.e("writeFile", e);
                    closeQuietly(writer);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(writer);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                writer = writer2;
                closeQuietly(writer);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            LogUtil.e("writeFile", e);
            closeQuietly(writer);
            return false;
        }
    }

    public static String readLine(String filePath) {
        return readLine(new File(filePath));
    }

    public static String readLine(File file) {
        List<String> lines = readLines(file, 1);
        return !lines.isEmpty() ? (String) lines.get(0) : BuildConfig.FLAVOR;
    }

    public static List<String> readLines(File file, int n) {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int count = 0;
            while (true) {
                try {
                    String line = reader2.readLine();
                    if (line == null) {
                        break;
                    }
                    count++;
                    lines.add(line);
                    if (n > 0 && count >= n) {
                        break;
                    }
                } catch (IOException e) {
                    e = e;
                    reader = reader2;
                    try {
                        LogUtil.e("readLine", e);
                        closeQuietly(reader);
                        return lines;
                    } catch (Throwable th) {
                        th = th;
                        closeQuietly(reader);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    reader = reader2;
                    closeQuietly(reader);
                    throw th;
                }
            }
            closeQuietly(reader2);
            BufferedReader bufferedReader = reader2;
        } catch (IOException e2) {
            e = e2;
            LogUtil.e("readLine", e);
            closeQuietly(reader);
            return lines;
        }
        return lines;
    }

    public static String readLineAndDel(File file) {
        try {
            String line = readLine(file);
            file.delete();
            return line;
        } catch (Exception e) {
            LogUtil.e("readLineAndDel", e);
            return null;
        }
    }

    public static void readLine(File file, ReaderListener listener) {
        String line;
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            do {
                try {
                    line = reader2.readLine();
                    if (line == null) {
                        closeQuietly(reader2);
                        BufferedReader bufferedReader = reader2;
                        return;
                    }
                } catch (IOException e) {
                    e = e;
                    reader = reader2;
                    try {
                        LogUtil.e("readLine", e);
                        closeQuietly(reader);
                    } catch (Throwable th) {
                        th = th;
                        closeQuietly(reader);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    reader = reader2;
                    closeQuietly(reader);
                    throw th;
                }
            } while (!listener.onReadLine(line));
            closeQuietly(reader2);
            BufferedReader bufferedReader2 = reader2;
        } catch (IOException e2) {
            e = e2;
            LogUtil.e("readLine", e);
            closeQuietly(reader);
        }
    }

    public static String readFully(File file) {
        InputStreamReader input;
        StringBuilder builder = new StringBuilder();
        FileInputStream in = null;
        InputStreamReader input2 = null;
        try {
            FileInputStream in2 = new FileInputStream(file);
            try {
                input = new InputStreamReader(in2);
            } catch (Exception e) {
                e = e;
                in = in2;
                try {
                    LogUtil.e("readFully.", e);
                    closeQuietly(input2);
                    closeQuietly(in);
                    return builder.toString();
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(input2);
                    closeQuietly(in);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                in = in2;
                closeQuietly(input2);
                closeQuietly(in);
                throw th;
            }
            try {
                char[] buffer = new char[4096];
                while (true) {
                    int n = input.read(buffer);
                    if (-1 == n) {
                        break;
                    }
                    builder.append(buffer, 0, n);
                }
                closeQuietly(input);
                closeQuietly(in2);
                InputStreamReader inputStreamReader = input;
                FileInputStream fileInputStream = in2;
            } catch (Exception e2) {
                e = e2;
                input2 = input;
                in = in2;
                LogUtil.e("readFully.", e);
                closeQuietly(input2);
                closeQuietly(in);
                return builder.toString();
            } catch (Throwable th3) {
                th = th3;
                input2 = input;
                in = in2;
                closeQuietly(input2);
                closeQuietly(in);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            LogUtil.e("readFully.", e);
            closeQuietly(input2);
            closeQuietly(in);
            return builder.toString();
        }
        return builder.toString();
    }
}
