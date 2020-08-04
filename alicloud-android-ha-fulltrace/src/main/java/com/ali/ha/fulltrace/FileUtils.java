package com.ali.ha.fulltrace;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    static final String ROOT_PATH = "fulltrace/";

    public static String getFulltraceFilePath(Context context, String subPath) {
        return getFulltraceSDRootPath(context, subPath, false);
    }

    public static String getFulltraceCachePath(Context context, String subPath) {
        return getFulltraceSDRootPath(context, subPath, true);
    }

    private static String getFulltraceSDRootPath(Context context, String subPath, boolean isCache) {
        if (subPath == null) {
            subPath = BuildConfig.FLAVOR;
        }
        File rootPath = null;
        try {
            if (TextUtils.equals("mounted", Environment.getExternalStorageState())) {
                File dir = isCache ? context.getExternalCacheDir() : context.getExternalFilesDir(null);
                if (dir != null) {
                    rootPath = new File(dir, ROOT_PATH + subPath);
                }
            }
        } catch (NullPointerException e) {
        }
        if (rootPath == null) {
            rootPath = new File(isCache ? context.getCacheDir() : context.getFilesDir(), ROOT_PATH + subPath);
        }
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }
        return rootPath.getAbsolutePath();
    }

    public static String getFulltraceDataPath(Context context, String subPath) {
        File file = context.getDir("fulltrace", 0);
        if (file == null) {
            return BuildConfig.FLAVOR;
        }
        File res = new File(file.getAbsolutePath(), subPath);
        if (!res.exists()) {
            res.mkdirs();
        }
        return res.getAbsolutePath();
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        file.delete();
    }

    public static byte[] readFileToBytes(File file) throws IOException {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        FileInputStream originFileInputStream = new FileInputStream(file.getAbsoluteFile());
        byte[] buffer = new byte[1024];
        while (true) {
            int len = originFileInputStream.read(buffer);
            if (len > 0) {
                byteOutputStream.write(buffer, 0, len);
            } else {
                originFileInputStream.close();
                return byteOutputStream.toByteArray();
            }
        }
    }
}
