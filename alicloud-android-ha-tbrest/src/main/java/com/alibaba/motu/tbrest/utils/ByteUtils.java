//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.utils;

public class ByteUtils {
    public ByteUtils() {
    }

    public static int bytesToInt(byte[] buf, int offset, int len) {
        if (null != buf && offset >= 0 && len >= 0 && buf.length >= offset + len) {
            byte[] tempbuf = new byte[len];

            for(int i = 0; i < len; ++i) {
                tempbuf[i] = buf[offset];
                ++offset;
            }

            return bytesToInt(tempbuf);
        } else {
            return 0;
        }
    }

    public static int bytesToInt(byte[] buf) {
        if (null != buf && buf.length <= 4) {
            int ret = 0;

            for(int i = 0; i < buf.length; ++i) {
                ret |= (buf[i] & 255) << (buf.length - i - 1) * 8;
            }

            return ret;
        } else {
            return 0;
        }
    }

    public static String bytes2UTF8String(byte[] buf) {
        String dst = "";

        try {
            dst = new String(buf, "UTF-8");
        } catch (Exception var3) {
            dst = "";
        }

        return dst;
    }

    public static String bytes2UTF8string(byte[] buf, int offset, int len) {
        if (null != buf && offset >= 0 && len >= 0 && buf.length >= offset + len) {
            byte[] tempbuf = new byte[len];

            for(int i = 0; i < len; ++i) {
                tempbuf[i] = buf[offset];
                ++offset;
            }

            return bytes2UTF8String(tempbuf);
        } else {
            return "";
        }
    }

    public static byte[] intToBytes(int i, int n) {
        if (n <= 4 && n >= 1) {
            byte[] buf = new byte[n];

            for(int j = 0; j < n; ++j) {
                buf[j] = (byte)(i >> 8 * (n - j - 1) & 255);
            }

            return buf;
        } else {
            return null;
        }
    }

    public static byte[] intToBytes1(int i) {
        byte[] result = new byte[]{(byte)(i & 255)};
        return result;
    }

    public static byte[] intToBytes2(int i) {
        byte[] result = new byte[]{(byte)(i >> 8 & 255), (byte)(i & 255)};
        return result;
    }

    public static byte[] intToBytes3(int i) {
        byte[] result = new byte[]{(byte)(i >> 16 & 255), (byte)(i >> 8 & 255), (byte)(i & 255)};
        return result;
    }

    public static byte[] intToBytes4(int i) {
        byte[] result = new byte[]{(byte)(i >> 24 & 255), (byte)(i >> 16 & 255), (byte)(i >> 8 & 255), (byte)(i & 255)};
        return result;
    }

    public static byte[] subBytes(byte[] buf, int offset, int len) {
        if (null != buf && offset >= 0 && len >= 0 && buf.length >= offset + len) {
            byte[] bs = new byte[len];

            for(int i = offset; i < offset + len; ++i) {
                bs[i - offset] = buf[i];
            }

            return bs;
        } else {
            return null;
        }
    }
}
