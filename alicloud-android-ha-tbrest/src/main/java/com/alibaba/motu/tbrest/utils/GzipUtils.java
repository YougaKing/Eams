//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {
    public GzipUtils() {
    }

    public static byte[] gzip(byte[] originData) {
        if (null != originData && 0 != originData.length) {
            byte[] gzipedByte = null;
            ByteArrayOutputStream bos = null;
            GZIPOutputStream gos = null;

            try {
                bos = new ByteArrayOutputStream();
                gos = new GZIPOutputStream(bos, originData.length);
                gos.write(originData);
                gos.finish();
                gzipedByte = bos.toByteArray();
            } catch (Exception var17) {
                var17.printStackTrace();
            } finally {
                if (gos != null) {
                    try {
                        gos.close();
                    } catch (IOException var16) {
                        var16.printStackTrace();
                    }
                }

                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException var15) {
                        var15.printStackTrace();
                    }
                }

            }

            return gzipedByte;
        } else {
            return originData;
        }
    }

    public static byte[] unGzip(byte[] gzipedData) {
        byte[] originData = null;
        ByteArrayInputStream bis = null;
        GZIPInputStream gis = null;
        ByteArrayOutputStream bos = null;

        try {
            bis = new ByteArrayInputStream(gzipedData);
            gis = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            bos = new ByteArrayOutputStream();

            int num;
            while((num = gis.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, num);
            }

            bos.flush();
            originData = bos.toByteArray();
        } catch (Exception var23) {
            var23.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception var22) {
                    var22.printStackTrace();
                }
            }

            if (gis != null) {
                try {
                    gis.close();
                } catch (IOException var21) {
                    var21.printStackTrace();
                }
            }

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }

        }

        return originData;
    }

    public static byte[] gzipAndRc4Bytes(String input) {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        GZIPOutputStream lOutputStream = null;

        try {
            lOutputStream = new GZIPOutputStream(bas);
            lOutputStream.write(input.getBytes("UTF-8"));
            lOutputStream.flush();
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            if (lOutputStream != null) {
                try {
                    lOutputStream.close();
                } catch (Exception var12) {
                }
            }

        }

        byte[] ret = RC4.rc4(bas.toByteArray());

        try {
            bas.close();
        } catch (Exception var13) {
        }

        return ret;
    }
}
