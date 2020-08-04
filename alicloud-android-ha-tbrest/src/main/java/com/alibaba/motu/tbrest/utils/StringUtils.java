//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.utils;

import java.util.Iterator;
import java.util.Map;

public class StringUtils {
    public StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String defaultString(String cs, String defualt) {
        return isBlank(cs) ? defualt : cs;
    }

    public static int hashCode(String value) {
        int h = 0;
        if (h == 0 && value.length() > 0) {
            char[] val = value.toCharArray();

            for(int i = 0; i < val.length; ++i) {
                h = 31 * h + val[i];
            }
        }

        return h;
    }

    public static String convertObjectToString(Object o) {
        if (o != null) {
            if (o instanceof String) {
                return ((String)o).toString();
            } else if (o instanceof Integer) {
                return "" + (Integer)o;
            } else if (o instanceof Long) {
                return "" + (Long)o;
            } else if (o instanceof Double) {
                return "" + (Double)o;
            } else if (o instanceof Float) {
                return "" + (Float)o;
            } else if (o instanceof Short) {
                return "" + (Short)o;
            } else if (o instanceof Byte) {
                return "" + (Byte)o;
            } else if (o instanceof Boolean) {
                return ((Boolean)o).toString();
            } else {
                return o instanceof Character ? ((Character)o).toString() : o.toString();
            }
        } else {
            return "";
        }
    }

    public static String convertMapToString(Map<String, String> pMaps) {
        if (null != pMaps) {
            boolean lIsFirst = true;
            StringBuffer lSB = new StringBuffer();
            Iterator var3 = pMaps.keySet().iterator();

            while(var3.hasNext()) {
                String lKey = (String)var3.next();
                String lValue = (String)pMaps.get(lKey);
                if (null != lValue && null != lKey) {
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
        } else {
            return null;
        }
    }
}
