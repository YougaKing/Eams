package com.alibaba.motu.tbrest.utils;

import com.alibaba.motu.tbrest.BuildConfig;
import java.util.Map;

public class StringUtils {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(CharSequence cs) {
        if (cs == null) {
            return true;
        }
        int strLen = cs.length();
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String defaultString(String cs, String defualt) {
        return isBlank(cs) ? defualt : cs;
    }

    public static int hashCode(String value) {
        int h = 0;
        if (0 == 0 && value.length() > 0) {
            for (char c : value.toCharArray()) {
                h = (h * 31) + c;
            }
        }
        return h;
    }

    public static String convertObjectToString(Object o) {
        if (o == null) {
            return BuildConfig.FLAVOR;
        }
        if (o instanceof String) {
            return ((String) o).toString();
        }
        if (o instanceof Integer) {
            return BuildConfig.FLAVOR + ((Integer) o).intValue();
        }
        if (o instanceof Long) {
            return BuildConfig.FLAVOR + ((Long) o).longValue();
        }
        if (o instanceof Double) {
            return BuildConfig.FLAVOR + ((Double) o).doubleValue();
        }
        if (o instanceof Float) {
            return BuildConfig.FLAVOR + ((Float) o).floatValue();
        }
        if (o instanceof Short) {
            return BuildConfig.FLAVOR + ((Short) o).shortValue();
        }
        if (o instanceof Byte) {
            return BuildConfig.FLAVOR + ((Byte) o).byteValue();
        }
        if (o instanceof Boolean) {
            return ((Boolean) o).toString();
        }
        if (o instanceof Character) {
            return ((Character) o).toString();
        }
        return o.toString();
    }

    public static String convertMapToString(Map<String, String> pMaps) {
        if (pMaps == null) {
            return null;
        }
        boolean lIsFirst = true;
        StringBuffer lSB = new StringBuffer();
        for (String lKey : pMaps.keySet()) {
            String lValue = (String) pMaps.get(lKey);
            if (!(lValue == null || lKey == null)) {
                if (lIsFirst) {
                    if (!"--invalid--".equals(lValue)) {
                        lSB.append(lKey + "=" + lValue);
                    } else {
                        lSB.append(lKey);
                    }
                    lIsFirst = false;
                } else if (!"--invalid--".equals(lValue)) {
                    lSB.append(",").append(lKey + "=" + lValue);
                } else {
                    lSB.append(",").append(lKey);
                }
            }
        }
        return lSB.toString();
    }
}
