package com.alibaba.motu.tbrest.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.alibaba.motu.tbrest.rest.RestHttpUtils;
import com.ta.utdid2.device.UTDevice;
import java.util.Locale;
import java.util.Random;

public class DeviceUtils {
    private static final String NETWORK_CLASS_2_G = "2G";
    private static final String NETWORK_CLASS_3_G = "3G";
    private static final String NETWORK_CLASS_4_G = "4G";
    private static final String NETWORK_CLASS_UNKNOWN = "Unknown";
    public static final String NETWORK_CLASS_WIFI = "Wi-Fi";
    private static String[] arrayOfString = {NETWORK_CLASS_UNKNOWN, NETWORK_CLASS_UNKNOWN};
    private static String carrier = null;
    private static String cpuName = null;
    private static String imei = null;
    private static String imsi = null;

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0054 A[SYNTHETIC, Splitter:B:31:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0059 A[Catch:{ Exception -> 0x005d }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0062 A[SYNTHETIC, Splitter:B:38:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0067 A[Catch:{ Exception -> 0x006b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCpuName() {
        /*
            java.lang.String r6 = cpuName
            if (r6 == 0) goto L_0x0007
            java.lang.String r6 = cpuName
        L_0x0006:
            return r6
        L_0x0007:
            java.lang.String r4 = "/proc/cpuinfo"
            java.lang.String r5 = ""
            r0 = 0
            r2 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ IOException -> 0x0051, all -> 0x005f }
            r1.<init>(r4)     // Catch:{ IOException -> 0x0051, all -> 0x005f }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0074, all -> 0x006d }
            r3.<init>(r1)     // Catch:{ IOException -> 0x0074, all -> 0x006d }
        L_0x0017:
            java.lang.String r5 = r3.readLine()     // Catch:{ IOException -> 0x0077, all -> 0x0070 }
            if (r5 == 0) goto L_0x003f
            java.lang.String r6 = "Hardware"
            boolean r6 = r5.contains(r6)     // Catch:{ IOException -> 0x0077, all -> 0x0070 }
            if (r6 == 0) goto L_0x0017
            java.lang.String r6 = ":"
            java.lang.String[] r6 = r5.split(r6)     // Catch:{ IOException -> 0x0077, all -> 0x0070 }
            r7 = 1
            r6 = r6[r7]     // Catch:{ IOException -> 0x0077, all -> 0x0070 }
            cpuName = r6     // Catch:{ IOException -> 0x0077, all -> 0x0070 }
            java.lang.String r6 = cpuName     // Catch:{ IOException -> 0x0077, all -> 0x0070 }
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ Exception -> 0x003d }
        L_0x0037:
            if (r3 == 0) goto L_0x0006
            r3.close()     // Catch:{ Exception -> 0x003d }
            goto L_0x0006
        L_0x003d:
            r7 = move-exception
            goto L_0x0006
        L_0x003f:
            if (r1 == 0) goto L_0x0044
            r1.close()     // Catch:{ Exception -> 0x004d }
        L_0x0044:
            if (r3 == 0) goto L_0x0049
            r3.close()     // Catch:{ Exception -> 0x004d }
        L_0x0049:
            r2 = r3
            r0 = r1
        L_0x004b:
            r6 = 0
            goto L_0x0006
        L_0x004d:
            r6 = move-exception
            r2 = r3
            r0 = r1
            goto L_0x004b
        L_0x0051:
            r6 = move-exception
        L_0x0052:
            if (r0 == 0) goto L_0x0057
            r0.close()     // Catch:{ Exception -> 0x005d }
        L_0x0057:
            if (r2 == 0) goto L_0x004b
            r2.close()     // Catch:{ Exception -> 0x005d }
            goto L_0x004b
        L_0x005d:
            r6 = move-exception
            goto L_0x004b
        L_0x005f:
            r6 = move-exception
        L_0x0060:
            if (r0 == 0) goto L_0x0065
            r0.close()     // Catch:{ Exception -> 0x006b }
        L_0x0065:
            if (r2 == 0) goto L_0x006a
            r2.close()     // Catch:{ Exception -> 0x006b }
        L_0x006a:
            throw r6
        L_0x006b:
            r7 = move-exception
            goto L_0x006a
        L_0x006d:
            r6 = move-exception
            r0 = r1
            goto L_0x0060
        L_0x0070:
            r6 = move-exception
            r2 = r3
            r0 = r1
            goto L_0x0060
        L_0x0074:
            r6 = move-exception
            r0 = r1
            goto L_0x0052
        L_0x0077:
            r6 = move-exception
            r2 = r3
            r0 = r1
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.motu.tbrest.utils.DeviceUtils.getCpuName():java.lang.String");
    }

    public static String getCarrier(Context context) {
        try {
            if (carrier != null) {
                return carrier;
            }
            carrier = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
            return carrier;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressLint({"WrongConstant"})
    public static String[] getNetworkType(Context context) {
        if (context == null) {
            return arrayOfString;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                return arrayOfString;
            }
            ConnectivityManager cManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (cManager == null) {
                return arrayOfString;
            }
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();
            if (nInfo == null) {
                return arrayOfString;
            }
            if (nInfo.isConnected()) {
                if (nInfo.getType() == 1) {
                    arrayOfString[0] = NETWORK_CLASS_WIFI;
                    return arrayOfString;
                } else if (nInfo.getType() == 0) {
                    arrayOfString[0] = getNetworkClass(nInfo.getSubtype());
                    arrayOfString[1] = nInfo.getSubtypeName();
                    return arrayOfString;
                }
            }
            return arrayOfString;
        } catch (Throwable th) {
        }
    }

    private static String getNetworkClass(int networkType) {
        switch (networkType) {
            case 1:
            case 2:
            case Base64.DONT_GUNZIP /*4*/:
            case 7:
            case 11:
                return NETWORK_CLASS_2_G;
            case RestHttpUtils.HTTP_REQ_TYPE_POST_URL_ENCODED /*3*/:
            case 5:
            case 6:
            case Base64.DO_BREAK_LINES /*8*/:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return NETWORK_CLASS_3_G;
            case 13:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    public static String getLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e) {
            LogUtil.e("get country error ", e);
            return null;
        }
    }

    public static String getCountry() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Exception e) {
            LogUtil.e("get country error ", e);
            return null;
        }
    }

    public static String getResolution(Context context) {
        String resolution = NETWORK_CLASS_UNKNOWN;
        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            if (width > height) {
                int width2 = width ^ height;
                height ^= width2;
                width = width2 ^ height;
            }
            return height + "*" + width;
        } catch (Exception e) {
            LogUtil.e("DeviceUtils getResolution: error", e);
            return resolution;
        }
    }

    public static String getUtdid(Context context) {
        try {
            return UTDevice.getUtdid(context);
        } catch (Exception e) {
            LogUtil.e("get utdid error ", e);
            return null;
        }
    }

    public static String getImsi(Context context) {
        if (imsi != null) {
            return imsi;
        }
        if (context != null) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
                if (tm != null) {
                    imsi = tm.getSubscriberId();
                }
            } catch (Exception e) {
            }
        }
        if (StringUtils.isEmpty(imsi)) {
            imsi = getUniqueID();
        }
        return imsi;
    }

    public static String getImei(Context context) {
        if (imei != null) {
            return imei;
        }
        imei = getUniqueID();
        return imei;
    }

    public static byte[] IntGetBytes(int i) {
        byte[] bInt = new byte[4];
        int value = i;
        bInt[3] = (byte) (value % 256);
        int value2 = value >> 8;
        bInt[2] = (byte) (value2 % 256);
        int value3 = value2 >> 8;
        bInt[1] = (byte) (value3 % 256);
        bInt[0] = (byte) ((value3 >> 8) % 256);
        return bInt;
    }

    public static final String getUniqueID() {
        try {
            int t1 = (int) (System.currentTimeMillis() / 1000);
            int t2 = (int) System.nanoTime();
            int t3 = new Random().nextInt();
            int t4 = new Random().nextInt();
            byte[] b1 = IntGetBytes(t1);
            byte[] b2 = IntGetBytes(t2);
            byte[] b3 = IntGetBytes(t3);
            byte[] b4 = IntGetBytes(t4);
            byte[] bUniqueID = new byte[16];
            System.arraycopy(b1, 0, bUniqueID, 0, 4);
            System.arraycopy(b2, 0, bUniqueID, 4, 4);
            System.arraycopy(b3, 0, bUniqueID, 8, 4);
            System.arraycopy(b4, 0, bUniqueID, 12, 4);
            return Base64.encodeBase64String(bUniqueID);
        } catch (Exception e) {
            return null;
        }
    }
}
