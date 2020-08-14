//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.fulltrace.upload;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import com.ali.ha.fulltrace.FTHeader;
import com.ali.ha.fulltrace.FileUtils;
import com.ali.ha.fulltrace.FulltraceGlobal;
import com.ali.ha.fulltrace.SendManager;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.logger.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class UploadManager {
    private static final String TAG = "UploadManager";
    private Application mApplication;
    private SharedPreferences sharedPreferences;
    private long currentSize;
    private volatile boolean isForeground;
    private volatile boolean isFinished;
    private volatile boolean isUploading;
    private static final long ONE_DAY = 86400000L;
    private int BOOT_DELAY_CHECK;
    private long MAX_MOBILE_TRAFFIC;
    private long MAX_CACHE_DAY;
    private long MAX_CHECK_INTERVAL;
    private long BG_TO_FG_CHECK_DELAY;
    private long FG_TO_BG_CHECK_DELAY;
    private long MAX_LOAD_FILE_SIZE;
    private long MAX_CACHE_SIZE;
    private static final String TRACE_END = ".trace";
    public static final String HOTDATA = "hotdata";

    private UploadManager() {
        this.currentSize = 0L;
        this.isForeground = true;
        this.isFinished = false;
        this.isUploading = false;
        this.BOOT_DELAY_CHECK = 20000;
        this.MAX_MOBILE_TRAFFIC = 1048576L;
        this.MAX_CACHE_DAY = 604800000L;
        this.MAX_CHECK_INTERVAL = 300000L;
        this.BG_TO_FG_CHECK_DELAY = 10000L;
        this.FG_TO_BG_CHECK_DELAY = 3000L;
        this.MAX_LOAD_FILE_SIZE = 256000L;
        this.MAX_CACHE_SIZE = 52428800L;
    }

    public static final UploadManager getInstance() {
        return UploadManager.SingleInstanceHolder.sInstance;
    }

    public void init(Application application) {
        this.mApplication = application;
        this.loadDefaultData();
        FulltraceGlobal.instance().uploadHandler().postDelayed(new Runnable() {
            public void run() {
                try {
                    UploadManager.this.upload();
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        }, (long)this.BOOT_DELAY_CHECK);
    }

    private void loadDefaultData() {
        this.sharedPreferences = UploadSharedPreferences.instance().getProcessSP(this.mApplication, "com.ali.fulltrace");
        long spDate = this.sharedPreferences.getLong("date", 0L);
        this.currentSize = this.sharedPreferences.getLong("size", 0L);
        long currentday = System.currentTimeMillis() / 86400000L;
        if (currentday != spDate) {
            this.sharedPreferences.edit().putLong("date", currentday).putLong("size", 0L).apply();
            this.currentSize = 0L;
        }

        this.BOOT_DELAY_CHECK = 30000;
        this.MAX_MOBILE_TRAFFIC = 1048576L;
        this.MAX_CACHE_DAY = 604800000L;
        this.MAX_CHECK_INTERVAL = 300000L;
        this.BG_TO_FG_CHECK_DELAY = 10000L;
        this.FG_TO_BG_CHECK_DELAY = 3000L;
        this.MAX_LOAD_FILE_SIZE = 256000L;
        this.MAX_CACHE_SIZE = 52428800L;
    }

    private void upload() {
        if (!this.isUploading && !this.isFinished) {
            this.isUploading = true;
            this.trimAllFiles();
            List<File> uploadDirs = this.getUploadDir();
            if (uploadDirs != null && uploadDirs.size() > 0) {
                long size = this.trimLogFile(uploadDirs);
                this.clearInvalidLog(uploadDirs, size);
                Logger.d("start upload", new Object[0]);
                this.isFinished = this.uploadLogFiles(uploadDirs);
                if (!this.isFinished && this.isForeground) {
                    FulltraceGlobal.instance().uploadHandler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                UploadManager.this.upload();
                            } catch (Exception var2) {
                                var2.printStackTrace();
                            }

                        }
                    }, this.MAX_CHECK_INTERVAL);
                }

                this.isUploading = false;
                Logger.d("finish upload", new Object[0]);
            } else {
                Logger.i("UploadManager", new Object[]{"upload dir is empty !"});
                this.isFinished = true;
                this.isUploading = false;
            }
        }

    }

    private boolean uploadLogFiles(List<File> dirs) {
        Iterator var2 = dirs.iterator();

        while(true) {
            while(true) {
                File subDir;
                do {
                    if (!var2.hasNext()) {
                        return true;
                    }

                    subDir = (File)var2.next();
                } while(!subDir.isDirectory());

                File[] logfiles = subDir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        if (pathname.isFile()) {
                            return UploadManager.this.getFileNameTime(pathname, ".trace") > 0L;
                        } else {
                            return false;
                        }
                    }
                });
                if (null != logfiles && logfiles.length > 0) {
                    List<File> sortLogFiles = Arrays.asList(logfiles);
                    if (sortLogFiles.size() > 1) {
                        Collections.sort(sortLogFiles, new Comparator<File>() {
                            public int compare(File o1, File o2) {
                                long a1 = UploadManager.this.getFileNameTime(o1, ".trace");
                                long a2 = UploadManager.this.getFileNameTime(o2, ".trace");
                                long result = a1 - a2;
                                return result == 0L ? 0 : (result > 0L ? 1 : -1);
                            }
                        });
                    }

                    boolean uploadResult = false;
                    String timestamp = subDir.getAbsolutePath().substring(subDir.getAbsolutePath().lastIndexOf("/") + 1);
                    String session = Integer.toString((timestamp + FTHeader.utdid).hashCode());
                    int sum = sortLogFiles.size();

                    for(int i = 0; i < sum; ++i) {
                        File file = (File)sortLogFiles.get(i);
                        uploadResult = this.uploadFile(file, session + "#" + (i + 1) + "#" + sum);
                        Logger.i("UploadManager", new Object[]{"upload file=" + file.getAbsolutePath() + " " + uploadResult + " " + file.length()});
                        if (!uploadResult) {
                            break;
                        }

                        file.delete();
                    }

                    if (!uploadResult) {
                        return false;
                    }

                    FileUtils.deleteFile(subDir);
                } else {
                    Logger.i("UploadManager", new Object[]{"upload dir is empty=" + subDir.getAbsolutePath()});
                    FileUtils.deleteFile(subDir);
                }
            }
        }
    }

    private boolean uploadFile(File file, String arg3) {
        if (file.length() == 0L) {
            return true;
        } else {
            try {
                boolean isMobileConnected = this.isMobileConnected();
                long newUploadSize = 0L;
                if (isMobileConnected) {
                    newUploadSize = this.currentSize + (long)((double)file.length() * 0.4D);
                    if (newUploadSize >= this.MAX_MOBILE_TRAFFIC) {
                        Logger.w("UploadManager", new Object[]{"upload size larger than MAX_MOBILE_TRAFFIC! " + file.getAbsolutePath() + " " + file.length() + " " + newUploadSize});
                        return false;
                    }
                }

                byte[] content = FileUtils.readFileToBytes(file);
                if (null == content) {
                    Logger.e("UploadManager", new Object[]{"read file failed! " + file.getAbsolutePath() + " " + file.length()});
                    return true;
                }

                ByteArrayOutputStream baOS = null;
                GZIPOutputStream gzipOS = null;
                byte[] base64Content = null;

                try {
                    baOS = new ByteArrayOutputStream();
                    gzipOS = new GZIPOutputStream(baOS);
                    gzipOS.write(content);
                    gzipOS.flush();
                    gzipOS.close();
                    gzipOS = null;
                    byte[] gzipContent = baOS.toByteArray();
                    boolean var11;
                    if (null == gzipContent || gzipContent.length == 0) {
                        Logger.e("UploadManager", new Object[]{"gzip failed!"});
                        var11 = true;
                        return var11;
                    }

                    base64Content = Base64.encode(gzipContent, 2);
                    if (null == base64Content || base64Content.length == 0) {
                        Logger.e("UploadManager", new Object[]{"base64 failed!"});
                        var11 = true;
                        return var11;
                    }
                } catch (Throwable var31) {
                    Logger.e("UploadManager", new Object[]{"gzip and base64 error!"});
                } finally {
                    try {
                        if (baOS != null) {
                            baOS.close();
                        }
                    } catch (IOException var30) {
                        Logger.e("baOS close failed", new Object[]{var30});
                    }

                    try {
                        if (gzipOS != null) {
                            gzipOS.close();
                        }
                    } catch (IOException var29) {
                        Logger.e("gzipOS close failed", new Object[]{var29});
                    }

                }

                boolean sendResult = SendManager.send(arg3, new String(base64Content));
                if (sendResult && isMobileConnected) {
                    this.currentSize = newUploadSize;
                    this.sharedPreferences.edit().putLong("size", this.currentSize).apply();
                }

                return sendResult;
            } catch (OutOfMemoryError var33) {
                file.delete();
                Logger.e("UploadManager", new Object[]{"read file oom! " + file.getAbsolutePath() + " " + file.length()});
            } catch (Throwable var34) {
                var34.printStackTrace();
            }

            return false;
        }
    }

    private void clearInvalidLog(List<File> dirs, long totalSize) {
        int size = dirs.size();
        long outSize = totalSize - this.MAX_CACHE_SIZE;
        long currentTime = System.currentTimeMillis();

        for(int i = size - 1; i > -1; --i) {
            File subDir = (File)dirs.get(i);
            if (subDir.isDirectory()) {
                File[] logfiles = subDir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.getName().endsWith(".trace");
                    }
                });
                if (null != logfiles) {
                    File[] var12 = logfiles;
                    int var13 = logfiles.length;

                    for(int var14 = 0; var14 < var13; ++var14) {
                        File f = var12[var14];
                        if (f.isFile()) {
                            if (outSize > 0L) {
                                Logger.i("UploadManager", new Object[]{"total size large than MAX_CACHE_SIZE! " + totalSize + " remove=" + f.getAbsolutePath() + "  " + f.length() + " outSize=" + outSize});
                                outSize -= f.length();
                                f.delete();
                            } else if (f.length() >= this.MAX_LOAD_FILE_SIZE) {
                                Logger.e("UploadManager", new Object[]{"file size is to large! " + f.getAbsolutePath() + " " + f.length()});
                                f.delete();
                            } else {
                                long time = this.getFileNameTime(f, ".trace");
                                if (time > 0L && currentTime - time > this.MAX_CACHE_DAY) {
                                    Logger.i("UploadManager", new Object[]{"file date is expired! " + f.getAbsolutePath()});
                                    f.delete();
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private long trimLogFile(List<File> dirs) {
        long totoalSize = 0L;
        Iterator var4 = dirs.iterator();

        while(true) {
            File[] logfiles;
            do {
                File subDir;
                do {
                    if (!var4.hasNext()) {
                        return totoalSize;
                    }

                    subDir = (File)var4.next();
                } while(!subDir.isDirectory());

                logfiles = subDir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.getName().endsWith(".trace");
                    }
                });
            } while(null == logfiles);

            File[] var7 = logfiles;
            int var8 = logfiles.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                File f = var7[var9];
                if (f.isFile()) {
                    totoalSize += f.length();
                }
            }
        }
    }

    private List<File> getUploadDir() {
        String logRootPath = DumpManager.getPathPrefix(this.mApplication);
        File logDir = new File(logRootPath);
        if (logDir.exists()) {
            File[] logSessions = logDir.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        return UploadManager.this.getLongValue(pathname.getName()) > 0L;
                    } else {
                        return false;
                    }
                }
            });
            if (null != logSessions && logSessions.length > 1) {
                List<File> files = Arrays.asList(logSessions);
                if (files.size() > 1) {
                    Collections.sort(files, new Comparator<File>() {
                        public int compare(File o1, File o2) {
                            long a1 = UploadManager.this.getLongValue(o1.getName());
                            long a2 = UploadManager.this.getLongValue(o2.getName());
                            long result = a2 - a1;
                            return result == 0L ? 0 : (result > 0L ? 1 : -1);
                        }
                    });
                }

                List<File> results = new ArrayList(files);
                results.remove(0);
                return results;
            }
        }

        return null;
    }

    private void trimAllFiles() {
        String cacheRootPath = DumpManager.getPathCachPrefix(this.mApplication);
        String logRootPath = DumpManager.getPathPrefix(this.mApplication);
        File logDir = new File(cacheRootPath);
        if (logDir.exists()) {
            File[] logCaches = logDir.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (!pathname.isDirectory()) {
                        return false;
                    } else {
                        return UploadManager.this.getLongValue(pathname.getName()) > 0L && !pathname.getName().equals(String.valueOf(DumpManager.session));
                    }
                }
            });
            if (logCaches != null && logCaches.length > 0) {
                File[] var5 = logCaches;
                int var6 = logCaches.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    File file = var5[var7];
                    String cacheFile = file.getAbsolutePath() + File.separator + "hotdata";
                    String targetPath = logRootPath + File.separator + file.getName();
                    if ((new File(cacheFile)).exists()) {
                        DumpManager.getInstance().trimHotdataBeforeUpload(file.getAbsolutePath(), targetPath);
                    }

                    FileUtils.deleteFile(file);
                }
            }
        }

    }

    private long getFileNameTime(File traceFile, String end) {
        String name = traceFile.getName();
        if (TextUtils.isEmpty(end)) {
            return this.getLongValue(name);
        } else {
            int index = name.indexOf(end);
            return index > 0 ? this.getLongValue(name.substring(0, index)) : -1L;
        }
    }

    public boolean isMobileConnected() {
        try {
            ConnectivityManager manager = (ConnectivityManager)this.mApplication.getSystemService("connectivity");
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == 0) {
                return networkInfo.isAvailable();
            }
        } catch (Throwable var3) {
            var3.printStackTrace();
        }

        return false;
    }

    private long getLongValue(String value) {
        try {
            return Long.parseLong(value);
        } catch (Throwable var3) {
            var3.printStackTrace();
            return -1L;
        }
    }

    private static final class SingleInstanceHolder {
        private static final UploadManager sInstance = new UploadManager();

        private SingleInstanceHolder() {
        }
    }

    public static class SP {
        public static final String SP_NAME = "com.ali.fulltrace";
        public static final String KEY_DATE = "date";
        public static final String KEY_SIZE = "size";

        public SP() {
        }
    }
}
