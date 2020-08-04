package com.ali.ha.fulltrace.upload;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.ali.ha.fulltrace.FTHeader;
import com.ali.ha.fulltrace.FileUtils;
import com.ali.ha.fulltrace.FulltraceGlobal;
import com.ali.ha.fulltrace.dump.DumpManager;
import com.ali.ha.fulltrace.logger.Logger;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UploadManager {
    public static final String HOTDATA = "hotdata";
    private static final long ONE_DAY = 86400000;
    private static final String TAG = "UploadManager";
    private static final String TRACE_END = ".trace";
    private long BG_TO_FG_CHECK_DELAY;
    private int BOOT_DELAY_CHECK;
    private long FG_TO_BG_CHECK_DELAY;
    private long MAX_CACHE_DAY;
    private long MAX_CACHE_SIZE;
    private long MAX_CHECK_INTERVAL;
    private long MAX_LOAD_FILE_SIZE;
    private long MAX_MOBILE_TRAFFIC;
    private long currentSize;
    private volatile boolean isFinished;
    private volatile boolean isForeground;
    private volatile boolean isUploading;
    private Application mApplication;
    private SharedPreferences sharedPreferences;

    public static class SP {
        public static final String KEY_DATE = "date";
        public static final String KEY_SIZE = "size";
        public static final String SP_NAME = "com.ali.fulltrace";
    }

    private static final class SingleInstanceHolder {
        /* access modifiers changed from: private */
        public static final UploadManager sInstance = new UploadManager();

        private SingleInstanceHolder() {
        }
    }

    private UploadManager() {
        this.currentSize = 0;
        this.isForeground = true;
        this.isFinished = false;
        this.isUploading = false;
        this.BOOT_DELAY_CHECK = 20000;
        this.MAX_MOBILE_TRAFFIC = 1048576;
        this.MAX_CACHE_DAY = 604800000;
        this.MAX_CHECK_INTERVAL = 300000;
        this.BG_TO_FG_CHECK_DELAY = 10000;
        this.FG_TO_BG_CHECK_DELAY = 3000;
        this.MAX_LOAD_FILE_SIZE = 256000;
        this.MAX_CACHE_SIZE = 52428800;
    }

    public static final UploadManager getInstance() {
        return SingleInstanceHolder.sInstance;
    }

    public void init(Application application) {
        this.mApplication = application;
        loadDefaultData();
        FulltraceGlobal.instance().uploadHandler().postDelayed(new Runnable() {
            public void run() {
                try {
                    UploadManager.this.upload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, (long) this.BOOT_DELAY_CHECK);
    }

    private void loadDefaultData() {
        this.sharedPreferences = UploadSharedPreferences.instance().getProcessSP(this.mApplication, SP.SP_NAME);
        long spDate = this.sharedPreferences.getLong(SP.KEY_DATE, 0);
        this.currentSize = this.sharedPreferences.getLong(SP.KEY_SIZE, 0);
        long currentday = System.currentTimeMillis() / ONE_DAY;
        if (currentday != spDate) {
            this.sharedPreferences.edit().putLong(SP.KEY_DATE, currentday).putLong(SP.KEY_SIZE, 0).apply();
            this.currentSize = 0;
        }
        this.BOOT_DELAY_CHECK = 30000;
        this.MAX_MOBILE_TRAFFIC = 1048576;
        this.MAX_CACHE_DAY = 604800000;
        this.MAX_CHECK_INTERVAL = 300000;
        this.BG_TO_FG_CHECK_DELAY = 10000;
        this.FG_TO_BG_CHECK_DELAY = 3000;
        this.MAX_LOAD_FILE_SIZE = 256000;
        this.MAX_CACHE_SIZE = 52428800;
    }

    /* access modifiers changed from: private */
    public void upload() {
        if (!this.isUploading && !this.isFinished) {
            this.isUploading = true;
            trimAllFiles();
            List<File> uploadDirs = getUploadDir();
            if (uploadDirs == null || uploadDirs.size() <= 0) {
                Logger.i(TAG, "upload dir is empty !");
                this.isFinished = true;
                this.isUploading = false;
                return;
            }
            clearInvalidLog(uploadDirs, trimLogFile(uploadDirs));
            Logger.d("start upload", new Object[0]);
            this.isFinished = uploadLogFiles(uploadDirs);
            if (!this.isFinished && this.isForeground) {
                FulltraceGlobal.instance().uploadHandler().postDelayed(new Runnable() {
                    public void run() {
                        try {
                            UploadManager.this.upload();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, this.MAX_CHECK_INTERVAL);
            }
            this.isUploading = false;
            Logger.d("finish upload", new Object[0]);
        }
    }

    private boolean uploadLogFiles(List<File> dirs) {
        for (File subDir : dirs) {
            if (subDir.isDirectory()) {
                File[] logfiles = subDir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        if (!pathname.isFile() || UploadManager.this.getFileNameTime(pathname, UploadManager.TRACE_END) <= 0) {
                            return false;
                        }
                        return true;
                    }
                });
                if (logfiles == null || logfiles.length <= 0) {
                    Logger.i(TAG, "upload dir is empty=" + subDir.getAbsolutePath());
                    FileUtils.deleteFile(subDir);
                } else {
                    List<File> sortLogFiles = Arrays.asList(logfiles);
                    if (sortLogFiles.size() > 1) {
                        Collections.sort(sortLogFiles, new Comparator<File>() {
                            public int compare(File o1, File o2) {
                                long result = UploadManager.this.getFileNameTime(o1, UploadManager.TRACE_END) - UploadManager.this.getFileNameTime(o2, UploadManager.TRACE_END);
                                if (result == 0) {
                                    return 0;
                                }
                                return result > 0 ? 1 : -1;
                            }
                        });
                    }
                    boolean uploadResult = false;
                    String session = Integer.toString((subDir.getAbsolutePath().substring(subDir.getAbsolutePath().lastIndexOf("/") + 1) + FTHeader.utdid).hashCode());
                    int sum = sortLogFiles.size();
                    for (int i = 0; i < sum; i++) {
                        File file = (File) sortLogFiles.get(i);
                        uploadResult = uploadFile(file, session + "#" + (i + 1) + "#" + sum);
                        Logger.i(TAG, "upload file=" + file.getAbsolutePath() + " " + uploadResult + " " + file.length());
                        if (!uploadResult) {
                            break;
                        }
                        file.delete();
                    }
                    if (!uploadResult) {
                        return false;
                    }
                    FileUtils.deleteFile(subDir);
                }
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x0284 A[SYNTHETIC, Splitter:B:102:0x0284] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0289 A[SYNTHETIC, Splitter:B:105:0x0289] */
    /* JADX WARNING: Removed duplicated region for block: B:128:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01ed A[ADDED_TO_REGION, Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0251 A[SYNTHETIC, Splitter:B:89:0x0251] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0256 A[SYNTHETIC, Splitter:B:92:0x0256] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:74:0x01dc=Splitter:B:74:0x01dc, B:107:0x028c=Splitter:B:107:0x028c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean uploadFile(java.io.File r25, java.lang.String r26) {
        /*
            r24 = this;
            long r18 = r25.length()
            r20 = 0
            int r17 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r17 != 0) goto L_0x000d
            r16 = 1
        L_0x000c:
            return r16
        L_0x000d:
            boolean r12 = r24.isMobileConnected()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r14 = 0
            if (r12 == 0) goto L_0x0085
            r0 = r24
            long r0 = r0.currentSize     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            long r20 = r25.length()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r0 = r20
            double r0 = (double) r0     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r20 = r0
            r22 = 4600877379321698714(0x3fd999999999999a, double:0.4)
            double r20 = r20 * r22
            r0 = r20
            long r0 = (long) r0     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r20 = r0
            long r14 = r18 + r20
            r0 = r24
            long r0 = r0.MAX_MOBILE_TRAFFIC     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            int r17 = (r14 > r18 ? 1 : (r14 == r18 ? 0 : -1))
            if (r17 < 0) goto L_0x0085
            java.lang.String r17 = "UploadManager"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            java.lang.StringBuilder r20 = new java.lang.StringBuilder     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r20.<init>()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r21 = "upload size larger than MAX_MOBILE_TRAFFIC! "
            java.lang.StringBuilder r20 = r20.append(r21)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r21 = r25.getAbsolutePath()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.StringBuilder r20 = r20.append(r21)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r21 = " "
            java.lang.StringBuilder r20 = r20.append(r21)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            long r22 = r25.length()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r0 = r20
            r1 = r22
            java.lang.StringBuilder r20 = r0.append(r1)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r21 = " "
            java.lang.StringBuilder r20 = r20.append(r21)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r0 = r20
            java.lang.StringBuilder r20 = r0.append(r14)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r20 = r20.toString()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18[r19] = r20     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.w(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r16 = 0
            goto L_0x000c
        L_0x0085:
            byte[] r7 = com.ali.ha.fulltrace.FileUtils.readFileToBytes(r25)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            if (r7 != 0) goto L_0x00c9
            java.lang.String r17 = "UploadManager"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            java.lang.StringBuilder r20 = new java.lang.StringBuilder     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r20.<init>()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r21 = "read file failed! "
            java.lang.StringBuilder r20 = r20.append(r21)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r21 = r25.getAbsolutePath()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.StringBuilder r20 = r20.append(r21)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r21 = " "
            java.lang.StringBuilder r20 = r20.append(r21)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            long r22 = r25.length()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r0 = r20
            r1 = r22
            java.lang.StringBuilder r20 = r0.append(r1)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r20 = r20.toString()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18[r19] = r20     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r16 = 1
            goto L_0x000c
        L_0x00c9:
            r4 = 0
            r10 = 0
            r6 = 0
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x023b }
            r5.<init>()     // Catch:{ Throwable -> 0x023b }
            java.util.zip.GZIPOutputStream r11 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r11.<init>(r5)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r11.write(r7)     // Catch:{ Throwable -> 0x02bd, all -> 0x02b6 }
            r11.flush()     // Catch:{ Throwable -> 0x02bd, all -> 0x02b6 }
            r11.close()     // Catch:{ Throwable -> 0x02bd, all -> 0x02b6 }
            r10 = 0
            byte[] r9 = r5.toByteArray()     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            if (r9 == 0) goto L_0x00eb
            int r0 = r9.length     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r17 = r0
            if (r17 != 0) goto L_0x017a
        L_0x00eb:
            java.lang.String r17 = "UploadManager"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r18 = r0
            r19 = 0
            java.lang.String r20 = "gzip failed!"
            r18[r19] = r20     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r16 = 1
            if (r5 == 0) goto L_0x0105
            r5.close()     // Catch:{ IOException -> 0x0162 }
        L_0x0105:
            if (r10 == 0) goto L_0x000c
            r10.close()     // Catch:{ IOException -> 0x010c }
            goto L_0x000c
        L_0x010c:
            r8 = move-exception
            java.lang.String r17 = "gzipOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x000c
        L_0x0120:
            r13 = move-exception
            r25.delete()
            java.lang.String r17 = "UploadManager"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r18 = r0
            r19 = 0
            java.lang.StringBuilder r20 = new java.lang.StringBuilder
            r20.<init>()
            java.lang.String r21 = "read file oom! "
            java.lang.StringBuilder r20 = r20.append(r21)
            java.lang.String r21 = r25.getAbsolutePath()
            java.lang.StringBuilder r20 = r20.append(r21)
            java.lang.String r21 = " "
            java.lang.StringBuilder r20 = r20.append(r21)
            long r22 = r25.length()
            r0 = r20
            r1 = r22
            java.lang.StringBuilder r20 = r0.append(r1)
            java.lang.String r20 = r20.toString()
            r18[r19] = r20
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)
        L_0x015e:
            r16 = 0
            goto L_0x000c
        L_0x0162:
            r8 = move-exception
            java.lang.String r17 = "baOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x0105
        L_0x0175:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x015e
        L_0x017a:
            r17 = 2
            r0 = r17
            byte[] r6 = android.util.Base64.encode(r9, r0)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            if (r6 == 0) goto L_0x0189
            int r0 = r6.length     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r17 = r0
            if (r17 != 0) goto L_0x01d1
        L_0x0189:
            java.lang.String r17 = "UploadManager"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r18 = r0
            r19 = 0
            java.lang.String r20 = "base64 failed!"
            r18[r19] = r20     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b3 }
            r16 = 1
            if (r5 == 0) goto L_0x01a3
            r5.close()     // Catch:{ IOException -> 0x01be }
        L_0x01a3:
            if (r10 == 0) goto L_0x000c
            r10.close()     // Catch:{ IOException -> 0x01aa }
            goto L_0x000c
        L_0x01aa:
            r8 = move-exception
            java.lang.String r17 = "gzipOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x000c
        L_0x01be:
            r8 = move-exception
            java.lang.String r17 = "baOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x01a3
        L_0x01d1:
            if (r5 == 0) goto L_0x01d6
            r5.close()     // Catch:{ IOException -> 0x0214 }
        L_0x01d6:
            if (r10 == 0) goto L_0x01db
            r10.close()     // Catch:{ IOException -> 0x0227 }
        L_0x01db:
            r4 = r5
        L_0x01dc:
            java.lang.String r17 = new java.lang.String     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r0 = r17
            r0.<init>(r6)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r0 = r26
            r1 = r17
            boolean r16 = com.ali.ha.fulltrace.SendManager.send(r0, r1)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            if (r16 == 0) goto L_0x000c
            if (r12 == 0) goto L_0x000c
            r0 = r24
            r0.currentSize = r14     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r0 = r24
            android.content.SharedPreferences r0 = r0.sharedPreferences     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r17 = r0
            android.content.SharedPreferences$Editor r17 = r17.edit()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            java.lang.String r18 = "size"
            r0 = r24
            long r0 = r0.currentSize     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r20 = r0
            r0 = r17
            r1 = r18
            r2 = r20
            android.content.SharedPreferences$Editor r17 = r0.putLong(r1, r2)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r17.apply()     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x000c
        L_0x0214:
            r8 = move-exception
            java.lang.String r17 = "baOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x01d6
        L_0x0227:
            r8 = move-exception
            java.lang.String r17 = "gzipOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r4 = r5
            goto L_0x01dc
        L_0x023b:
            r8 = move-exception
        L_0x023c:
            java.lang.String r17 = "UploadManager"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0281 }
            r18 = r0
            r19 = 0
            java.lang.String r20 = "gzip and base64 error!"
            r18[r19] = r20     // Catch:{ all -> 0x0281 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ all -> 0x0281 }
            if (r4 == 0) goto L_0x0254
            r4.close()     // Catch:{ IOException -> 0x026e }
        L_0x0254:
            if (r10 == 0) goto L_0x01dc
            r10.close()     // Catch:{ IOException -> 0x025a }
            goto L_0x01dc
        L_0x025a:
            r8 = move-exception
            java.lang.String r17 = "gzipOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x01dc
        L_0x026e:
            r8 = move-exception
            java.lang.String r17 = "baOS close failed"
            r18 = 1
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r18 = r0
            r19 = 0
            r18[r19] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r17, r18)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x0254
        L_0x0281:
            r17 = move-exception
        L_0x0282:
            if (r4 == 0) goto L_0x0287
            r4.close()     // Catch:{ IOException -> 0x028d }
        L_0x0287:
            if (r10 == 0) goto L_0x028c
            r10.close()     // Catch:{ IOException -> 0x02a0 }
        L_0x028c:
            throw r17     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
        L_0x028d:
            r8 = move-exception
            java.lang.String r18 = "baOS close failed"
            r19 = 1
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r19 = r0
            r20 = 0
            r19[r20] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r18, r19)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x0287
        L_0x02a0:
            r8 = move-exception
            java.lang.String r18 = "gzipOS close failed"
            r19 = 1
            r0 = r19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            r19 = r0
            r20 = 0
            r19[r20] = r8     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            com.ali.ha.fulltrace.logger.Logger.e(r18, r19)     // Catch:{ OutOfMemoryError -> 0x0120, Throwable -> 0x0175 }
            goto L_0x028c
        L_0x02b3:
            r17 = move-exception
            r4 = r5
            goto L_0x0282
        L_0x02b6:
            r17 = move-exception
            r10 = r11
            r4 = r5
            goto L_0x0282
        L_0x02ba:
            r8 = move-exception
            r4 = r5
            goto L_0x023c
        L_0x02bd:
            r8 = move-exception
            r10 = r11
            r4 = r5
            goto L_0x023c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.ha.fulltrace.upload.UploadManager.uploadFile(java.io.File, java.lang.String):boolean");
    }

    private void clearInvalidLog(List<File> dirs, long totalSize) {
        int size = dirs.size();
        long outSize = totalSize - this.MAX_CACHE_SIZE;
        long currentTime = System.currentTimeMillis();
        for (int i = size - 1; i > -1; i--) {
            File subDir = (File) dirs.get(i);
            if (subDir.isDirectory()) {
                File[] logfiles = subDir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.getName().endsWith(UploadManager.TRACE_END);
                    }
                });
                if (logfiles != null) {
                    for (File f : logfiles) {
                        if (f.isFile()) {
                            if (outSize > 0) {
                                Logger.i(TAG, "total size large than MAX_CACHE_SIZE! " + totalSize + " remove=" + f.getAbsolutePath() + "  " + f.length() + " outSize=" + outSize);
                                outSize -= f.length();
                                f.delete();
                            } else if (f.length() >= this.MAX_LOAD_FILE_SIZE) {
                                Logger.e(TAG, "file size is to large! " + f.getAbsolutePath() + " " + f.length());
                                f.delete();
                            } else {
                                long time = getFileNameTime(f, TRACE_END);
                                if (time > 0 && currentTime - time > this.MAX_CACHE_DAY) {
                                    Logger.i(TAG, "file date is expired! " + f.getAbsolutePath());
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
        long totoalSize = 0;
        for (File subDir : dirs) {
            if (subDir.isDirectory()) {
                File[] logfiles = subDir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.getName().endsWith(UploadManager.TRACE_END);
                    }
                });
                if (logfiles != null) {
                    for (File f : logfiles) {
                        if (f.isFile()) {
                            totoalSize += f.length();
                        }
                    }
                }
            }
        }
        return totoalSize;
    }

    private List<File> getUploadDir() {
        File logDir = new File(DumpManager.getPathPrefix(this.mApplication));
        if (logDir.exists()) {
            File[] logSessions = logDir.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (!pathname.isDirectory() || UploadManager.this.getLongValue(pathname.getName()) <= 0) {
                        return false;
                    }
                    return true;
                }
            });
            if (logSessions != null && logSessions.length > 1) {
                List<File> files = Arrays.asList(logSessions);
                if (files.size() > 1) {
                    Collections.sort(files, new Comparator<File>() {
                        public int compare(File o1, File o2) {
                            long result = UploadManager.this.getLongValue(o2.getName()) - UploadManager.this.getLongValue(o1.getName());
                            if (result == 0) {
                                return 0;
                            }
                            return result > 0 ? 1 : -1;
                        }
                    });
                }
                List<File> results = new ArrayList<>(files);
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
                    if (!pathname.isDirectory() || UploadManager.this.getLongValue(pathname.getName()) <= 0 || pathname.getName().equals(String.valueOf(DumpManager.session))) {
                        return false;
                    }
                    return true;
                }
            });
            if (logCaches != null && logCaches.length > 0) {
                for (File file : logCaches) {
                    String targetPath = logRootPath + File.separator + file.getName();
                    if (new File(file.getAbsolutePath() + File.separator + HOTDATA).exists()) {
                        DumpManager.getInstance().trimHotdataBeforeUpload(file.getAbsolutePath(), targetPath);
                    }
                    FileUtils.deleteFile(file);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public long getFileNameTime(File traceFile, String end) {
        String name = traceFile.getName();
        if (TextUtils.isEmpty(end)) {
            return getLongValue(name);
        }
        int index = name.indexOf(end);
        if (index > 0) {
            return getLongValue(name.substring(0, index));
        }
        return -1;
    }

    public boolean isMobileConnected() {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) this.mApplication.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == 0) {
                return networkInfo.isAvailable();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public long getLongValue(String value) {
        try {
            return Long.parseLong(value);
        } catch (Throwable t) {
            t.printStackTrace();
            return -1;
        }
    }
}
