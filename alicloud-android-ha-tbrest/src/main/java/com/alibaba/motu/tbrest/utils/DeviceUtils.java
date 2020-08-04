//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class DeviceUtils {
    private static String cpuName = null;
    private static String carrier = null;
    public static final String NETWORK_CLASS_WIFI = "Wi-Fi";
    private static final String NETWORK_CLASS_2_G = "2G";
    private static final String NETWORK_CLASS_3_G = "3G";
    private static final String NETWORK_CLASS_4_G = "4G";
    private static final String NETWORK_CLASS_UNKNOWN = "Unknown";
    private static String[] arrayOfString = new String[]{"Unknown", "Unknown"};
    private static String imsi = null;
    private static String imei = null;

    public DeviceUtils() {
    }

    public static String getCpuName() {
        if (cpuName != null) {
            return cpuName;
        } else {
            String str1 = "/proc/cpuinfo";
            String str2 = "";
            FileReader fr = null;
            BufferedReader localBufferedReader = null;

            try {
                fr = new FileReader(str1);
                localBufferedReader = new BufferedReader(fr);

                do {
                    if ((str2 = localBufferedReader.readLine()) == null) {
                        return null;
                    }
                } while(!str2.contains("Hardware"));

                cpuName = str2.split(":")[1];
                String var4 = cpuName;
                return var4;
            } catch (IOException var15) {
                return null;
            } finally {
                try {
                    if (fr != null) {
                        fr.close();
                    }

                    if (localBufferedReader != null) {
                        localBufferedReader.close();
                    }
                } catch (Exception var14) {
                }

            }
        }
    }

    public static String getCarrier(Context context) {
        try {
            if (carrier != null) {
                return carrier;
            } else {
                TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
                carrier = telephonyManager.getNetworkOperatorName();
                return carrier;
            }
        } catch (Exception var2) {
            return null;
        }
    }

    @SuppressLint({"WrongConstant"})
    public static String[] getNetworkType(Context context) {
        if (context == null) {
            return arrayOfString;
        } else {
            try {
                PackageManager pManager = context.getPackageManager();
                if (pManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                    return arrayOfString;
                }

                ConnectivityManager cManager = (ConnectivityManager)context.getSystemService("connectivity");
                if (cManager == null) {
                    return arrayOfString;
                }

                NetworkInfo nInfo = cManager.getActiveNetworkInfo();
                if (nInfo == null) {
                    return arrayOfString;
                }

                if (nInfo.isConnected()) {
                    if (nInfo.getType() == 1) {
                        arrayOfString[0] = "Wi-Fi";
                        return arrayOfString;
                    }

                    if (nInfo.getType() == 0) {
                        arrayOfString[0] = getNetworkClass(nInfo.getSubtype());
                        arrayOfString[1] = nInfo.getSubtypeName();
                        return arrayOfString;
                    }
                }
            } catch (Throwable var4) {
            }

            return arrayOfString;
        }
    }

    private static String getNetworkClass(int networkType) {
        switch(networkType) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String getLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception var1) {
            LogUtil.e("get country error ", var1);
            return null;
        }
    }

    public static String getCountry() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Exception var1) {
            LogUtil.e("get country error ", var1);
            return null;
        }
    }

    public static String getResolution(Context context) {
        String resolution = "Unknown";

        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            if (width > height) {
                width ^= height;
                height ^= width;
                width ^= height;
            }

            resolution = height + "*" + width;
        } catch (Exception var5) {
            LogUtil.e("DeviceUtils getResolution: error", var5);
        }

        return resolution;
    }

    public static String getUtdid(Context context) {
        try {
            return "UTDevice.getUtdid(context)";
        } catch (Exception var2) {
            LogUtil.e("get utdid error ", var2);
            return null;
        }
    }

    public static String getImsi(Context context) {
        if (imsi != null) {
            return imsi;
        } else {
            if (context != null) {
                try {
                    TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
                    if (tm != null) {
                        imsi = tm.getSubscriberId();
                    }
                } catch (Exception var2) {
                }
            }

            if (StringUtils.isEmpty(imsi)) {
                imsi = getUniqueID();
            }

            return imsi;
        }
    }

    public static String getImei(Context context) {
        if (imei != null) {
            return imei;
        } else {
            imei = getUniqueID();
            return imei;
        }
    }

    public static byte[] IntGetBytes(int i) {
        byte[] bInt = new byte[4];
        bInt[3] = (byte)(i % 256);
        int value = i >> 8;
        bInt[2] = (byte)(value % 256);
        value >>= 8;
        bInt[1] = (byte)(value % 256);
        value >>= 8;
        bInt[0] = (byte)(value % 256);
        return bInt;
    }

    public static final String getUniqueID() {
        try {
            int t1 = (int)(System.currentTimeMillis() / 1000L);
            int t2 = (int)System.nanoTime();
            int t3 = (new Random()).nextInt();
            int t4 = (new Random()).nextInt();
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
        } catch (Exception var9) {
            return null;
        }
    }
}
