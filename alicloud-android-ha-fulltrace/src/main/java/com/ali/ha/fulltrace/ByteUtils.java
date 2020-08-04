package com.ali.ha.fulltrace;

public class ByteUtils {
    public static final byte BYTES_COUNT_BYTE = 1;
    public static final byte BYTES_COUNT_DOUBLE = 8;
    public static final byte BYTES_COUNT_FLOAT = 4;
    public static final byte BYTES_COUNT_INT = 4;
    public static final byte BYTES_COUNT_LONG = 8;
    public static final byte BYTES_COUNT_SHORT = 2;

    public static byte[] floatToBytes(float Value) {
        long accum = (long) Float.floatToRawIntBits(Value);
        return new byte[]{(byte) ((int) (accum & 255)), (byte) ((int) ((accum >> 8) & 255)), (byte) ((int) ((accum >> 16) & 255)), (byte) ((int) ((accum >> 24) & 255))};
    }

    public static byte[] doubleToBytes(double Value) {
        long accum = Double.doubleToRawLongBits(Value);
        return new byte[]{(byte) ((int) (accum & 255)), (byte) ((int) ((accum >> 8) & 255)), (byte) ((int) ((accum >> 16) & 255)), (byte) ((int) ((accum >> 24) & 255)), (byte) ((int) ((accum >> 32) & 255)), (byte) ((int) ((accum >> 40) & 255)), (byte) ((int) ((accum >> 48) & 255)), (byte) ((int) ((accum >> 56) & 255))};
    }

    public static byte[] long2Bytes(long value) {
        return new byte[]{(byte) ((int) value), (byte) ((int) (value >> 8)), (byte) ((int) (value >> 16)), (byte) ((int) (value >> 24)), (byte) ((int) (value >> 32)), (byte) ((int) (value >> 40)), (byte) ((int) (value >> 48)), (byte) ((int) (value >> 56))};
    }

    public static byte[] int2Bytes(int value) {
        return new byte[]{(byte) value, (byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24)};
    }

    public static byte[] short2Bytes(short value) {
        return new byte[]{(byte) value, (byte) (value >> 8)};
    }

    public static byte[] merge(byte[]... bs) {
        int length = 0;
        for (byte[] length2 : bs) {
            length += length2.length;
        }
        byte[] whole = new byte[length];
        int offset = 0;
        for (int i = 0; i < bs.length; i++) {
            System.arraycopy(bs[i], 0, whole, offset, bs[i].length);
            offset += bs[i].length;
        }
        return whole;
    }

    public static int fill(byte[] target, byte[] source, int start) {
        System.arraycopy(source, 0, target, start, source.length);
        return source.length;
    }
}
