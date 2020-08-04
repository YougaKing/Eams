package com.alibaba.motu.tbrest.rest;

public class RestHttpUtils {
    public static final int HTTP_REQ_TYPE_GET = 1;
    public static final int HTTP_REQ_TYPE_POST_FORM_DATA = 2;
    public static final int HTTP_REQ_TYPE_POST_URL_ENCODED = 3;
    public static final int MAX_CONNECTION_TIME_OUT = 10000;
    public static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 60000;
    private static final String POST_Field_BOTTOM = "--GJircTeP--\r\n";
    private static final String POST_Field_TOP = "--GJircTeP\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: application/octet-stream \r\n\r\n";

    /* JADX WARNING: Removed duplicated region for block: B:105:0x0298 A[Catch:{ IOException -> 0x02a0, all -> 0x0321 }, LOOP:1: B:103:0x0286->B:105:0x0298, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x02da A[SYNTHETIC, Splitter:B:126:0x02da] */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x02ec A[SYNTHETIC, Splitter:B:132:0x02ec] */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x0309 A[SYNTHETIC, Splitter:B:141:0x0309] */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x0312  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0331  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x0307 A[EDGE_INSN: B:170:0x0307->B:140:0x0307 ?: BREAK  
    EDGE_INSN: B:170:0x0307->B:140:0x0307 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:177:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] sendRequest(int r24, java.lang.String r25, java.util.Map<java.lang.String, java.lang.Object> r26, boolean r27) {
        /*
            boolean r19 = com.alibaba.motu.tbrest.utils.StringUtils.isEmpty(r25)
            if (r19 == 0) goto L_0x0009
            r19 = 0
        L_0x0008:
            return r19
        L_0x0009:
            java.net.URL r18 = new java.net.URL     // Catch:{ MalformedURLException -> 0x012e, IOException -> 0x013a }
            r0 = r18
            r1 = r25
            r0.<init>(r1)     // Catch:{ MalformedURLException -> 0x012e, IOException -> 0x013a }
            java.net.URLConnection r4 = r18.openConnection()     // Catch:{ MalformedURLException -> 0x012e, IOException -> 0x013a }
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch:{ MalformedURLException -> 0x012e, IOException -> 0x013a }
            if (r4 == 0) goto L_0x0335
            r19 = 2
            r0 = r24
            r1 = r19
            if (r0 == r1) goto L_0x002a
            r19 = 3
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x0031
        L_0x002a:
            r19 = 1
            r0 = r19
            r4.setDoOutput(r0)
        L_0x0031:
            r19 = 1
            r0 = r19
            r4.setDoInput(r0)
            r19 = 2
            r0 = r24
            r1 = r19
            if (r0 == r1) goto L_0x0048
            r19 = 3
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x0146
        L_0x0048:
            java.lang.String r19 = "POST"
            r0 = r19
            r4.setRequestMethod(r0)     // Catch:{ ProtocolException -> 0x014f }
        L_0x004f:
            r19 = 0
            r0 = r19
            r4.setUseCaches(r0)
            r19 = 10000(0x2710, float:1.4013E-41)
            r0 = r19
            r4.setConnectTimeout(r0)
            r19 = 60000(0xea60, float:8.4078E-41)
            r0 = r19
            r4.setReadTimeout(r0)
            java.lang.String r19 = "Connection"
            java.lang.String r20 = "close"
            r0 = r19
            r1 = r20
            r4.setRequestProperty(r0, r1)
            if (r27 == 0) goto L_0x007d
            java.lang.String r19 = "Accept-Encoding"
            java.lang.String r20 = "gzip,deflate"
            r0 = r19
            r1 = r20
            r4.setRequestProperty(r0, r1)
        L_0x007d:
            r19 = 1
            r0 = r19
            r4.setInstanceFollowRedirects(r0)
            r6 = 0
            r19 = 2
            r0 = r24
            r1 = r19
            if (r0 == r1) goto L_0x0095
            r19 = 3
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x0228
        L_0x0095:
            r19 = 2
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x015b
            java.lang.String r19 = "Content-Type"
            java.lang.String r20 = "multipart/form-data; boundary=GJircTeP"
            r0 = r19
            r1 = r20
            r4.setRequestProperty(r0, r1)
        L_0x00a8:
            r5 = 0
            if (r26 == 0) goto L_0x0218
            int r19 = r26.size()
            if (r19 <= 0) goto L_0x0218
            java.io.ByteArrayOutputStream r13 = new java.io.ByteArrayOutputStream
            r13.<init>()
            java.util.Set r12 = r26.keySet()
            int r19 = r12.size()
            r0 = r19
            java.lang.String[] r14 = new java.lang.String[r0]
            r12.toArray(r14)
            com.alibaba.motu.tbrest.rest.RestKeyArraySorter r19 = com.alibaba.motu.tbrest.rest.RestKeyArraySorter.getInstance()
            r20 = 1
            r0 = r19
            r1 = r20
            java.lang.String[] r14 = r0.sortResourcesList(r14, r1)
            int r0 = r14.length
            r21 = r0
            r19 = 0
            r20 = r19
        L_0x00da:
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x0201
            r11 = r14[r20]
            r19 = 2
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x0179
            r0 = r26
            java.lang.Object r19 = r0.get(r11)
            byte[] r19 = (byte[]) r19
            r15 = r19
            byte[] r15 = (byte[]) r15
            if (r15 == 0) goto L_0x0129
            java.lang.String r19 = "--GJircTeP\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: application/octet-stream \r\n\r\n"
            r22 = 2
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ IOException -> 0x0170 }
            r22 = r0
            r23 = 0
            r22[r23] = r11     // Catch:{ IOException -> 0x0170 }
            r23 = 1
            r22[r23] = r11     // Catch:{ IOException -> 0x0170 }
            r0 = r19
            r1 = r22
            java.lang.String r19 = java.lang.String.format(r0, r1)     // Catch:{ IOException -> 0x0170 }
            byte[] r19 = r19.getBytes()     // Catch:{ IOException -> 0x0170 }
            r0 = r19
            r13.write(r0)     // Catch:{ IOException -> 0x0170 }
            r13.write(r15)     // Catch:{ IOException -> 0x0170 }
            java.lang.String r19 = "\r\n"
            byte[] r19 = r19.getBytes()     // Catch:{ IOException -> 0x0170 }
            r0 = r19
            r13.write(r0)     // Catch:{ IOException -> 0x0170 }
        L_0x0129:
            int r19 = r20 + 1
            r20 = r19
            goto L_0x00da
        L_0x012e:
            r9 = move-exception
            java.lang.String r19 = "connection error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            r19 = 0
            goto L_0x0008
        L_0x013a:
            r9 = move-exception
            java.lang.String r19 = "connection error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            r19 = 0
            goto L_0x0008
        L_0x0146:
            java.lang.String r19 = "GET"
            r0 = r19
            r4.setRequestMethod(r0)     // Catch:{ ProtocolException -> 0x014f }
            goto L_0x004f
        L_0x014f:
            r9 = move-exception
            java.lang.String r19 = "connection error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            r19 = 0
            goto L_0x0008
        L_0x015b:
            r19 = 3
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x00a8
            java.lang.String r19 = "Content-Type"
            java.lang.String r20 = "application/x-www-form-urlencoded"
            r0 = r19
            r1 = r20
            r4.setRequestProperty(r0, r1)
            goto L_0x00a8
        L_0x0170:
            r9 = move-exception
            java.lang.String r19 = "write lBaos error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0129
        L_0x0179:
            r19 = 3
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x0129
            r0 = r26
            java.lang.Object r15 = r0.get(r11)
            java.lang.String r15 = (java.lang.String) r15
            int r19 = r13.size()
            if (r19 <= 0) goto L_0x01cd
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01c3 }
            r19.<init>()     // Catch:{ IOException -> 0x01c3 }
            java.lang.String r22 = "&"
            r0 = r19
            r1 = r22
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ IOException -> 0x01c3 }
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r11)     // Catch:{ IOException -> 0x01c3 }
            java.lang.String r22 = "="
            r0 = r19
            r1 = r22
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ IOException -> 0x01c3 }
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r15)     // Catch:{ IOException -> 0x01c3 }
            java.lang.String r19 = r19.toString()     // Catch:{ IOException -> 0x01c3 }
            byte[] r19 = r19.getBytes()     // Catch:{ IOException -> 0x01c3 }
            r0 = r19
            r13.write(r0)     // Catch:{ IOException -> 0x01c3 }
            goto L_0x0129
        L_0x01c3:
            r9 = move-exception
            java.lang.String r19 = "write lBaos error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0129
        L_0x01cd:
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01f7 }
            r19.<init>()     // Catch:{ IOException -> 0x01f7 }
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r11)     // Catch:{ IOException -> 0x01f7 }
            java.lang.String r22 = "="
            r0 = r19
            r1 = r22
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ IOException -> 0x01f7 }
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r15)     // Catch:{ IOException -> 0x01f7 }
            java.lang.String r19 = r19.toString()     // Catch:{ IOException -> 0x01f7 }
            byte[] r19 = r19.getBytes()     // Catch:{ IOException -> 0x01f7 }
            r0 = r19
            r13.write(r0)     // Catch:{ IOException -> 0x01f7 }
            goto L_0x0129
        L_0x01f7:
            r9 = move-exception
            java.lang.String r19 = "write lBaos error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0129
        L_0x0201:
            r19 = 2
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x0214
            java.lang.String r19 = "--GJircTeP--\r\n"
            byte[] r19 = r19.getBytes()     // Catch:{ IOException -> 0x02bb }
            r0 = r19
            r13.write(r0)     // Catch:{ IOException -> 0x02bb }
        L_0x0214:
            byte[] r6 = r13.toByteArray()
        L_0x0218:
            if (r6 == 0) goto L_0x021b
            int r5 = r6.length
        L_0x021b:
            java.lang.String r19 = "Content-Length"
            java.lang.String r20 = java.lang.String.valueOf(r5)
            r0 = r19
            r1 = r20
            r4.setRequestProperty(r0, r1)
        L_0x0228:
            r16 = 0
            r4.connect()     // Catch:{ Exception -> 0x02ce }
            r19 = 2
            r0 = r24
            r1 = r19
            if (r0 == r1) goto L_0x023d
            r19 = 3
            r0 = r24
            r1 = r19
            if (r0 != r1) goto L_0x025b
        L_0x023d:
            if (r6 == 0) goto L_0x025b
            int r0 = r6.length     // Catch:{ Exception -> 0x02ce }
            r19 = r0
            if (r19 <= 0) goto L_0x025b
            java.io.DataOutputStream r17 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x02ce }
            java.io.OutputStream r19 = r4.getOutputStream()     // Catch:{ Exception -> 0x02ce }
            r0 = r17
            r1 = r19
            r0.<init>(r1)     // Catch:{ Exception -> 0x02ce }
            r0 = r17
            r0.write(r6)     // Catch:{ Exception -> 0x033d, all -> 0x0339 }
            r17.flush()     // Catch:{ Exception -> 0x033d, all -> 0x0339 }
            r16 = r17
        L_0x025b:
            if (r16 == 0) goto L_0x0260
            r16.close()     // Catch:{ IOException -> 0x02c5 }
        L_0x0260:
            r7 = 0
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            if (r27 == 0) goto L_0x02f9
            java.lang.String r19 = "gzip"
            java.lang.String r20 = r4.getContentEncoding()     // Catch:{ IOException -> 0x02a0 }
            boolean r19 = r19.equals(r20)     // Catch:{ IOException -> 0x02a0 }
            if (r19 == 0) goto L_0x02f9
            java.util.zip.GZIPInputStream r8 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x02a0 }
            java.io.InputStream r19 = r4.getInputStream()     // Catch:{ IOException -> 0x02a0 }
            r0 = r19
            r8.<init>(r0)     // Catch:{ IOException -> 0x02a0 }
            r7 = r8
        L_0x0280:
            r19 = 2048(0x800, float:2.87E-42)
            r0 = r19
            byte[] r3 = new byte[r0]     // Catch:{ IOException -> 0x02a0 }
        L_0x0286:
            r19 = 0
            r20 = 2048(0x800, float:2.87E-42)
            r0 = r19
            r1 = r20
            int r10 = r7.read(r3, r0, r1)     // Catch:{ IOException -> 0x02a0 }
            r19 = -1
            r0 = r19
            if (r10 == r0) goto L_0x0307
            r19 = 0
            r0 = r19
            r2.write(r3, r0, r10)     // Catch:{ IOException -> 0x02a0 }
            goto L_0x0286
        L_0x02a0:
            r9 = move-exception
            java.lang.String r19 = "write out error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)     // Catch:{ all -> 0x0321 }
            r19 = 0
            if (r7 == 0) goto L_0x0008
            r7.close()     // Catch:{ Exception -> 0x02b1 }
            goto L_0x0008
        L_0x02b1:
            r9 = move-exception
            java.lang.String r20 = "out close error!"
            r0 = r20
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0008
        L_0x02bb:
            r9 = move-exception
            java.lang.String r19 = "write lBaos error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0214
        L_0x02c5:
            r9 = move-exception
            java.lang.String r19 = "out close error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0260
        L_0x02ce:
            r9 = move-exception
        L_0x02cf:
            java.lang.String r19 = "write out error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)     // Catch:{ all -> 0x02e9 }
            r19 = 0
            if (r16 == 0) goto L_0x0008
            r16.close()     // Catch:{ IOException -> 0x02df }
            goto L_0x0008
        L_0x02df:
            r9 = move-exception
            java.lang.String r20 = "out close error!"
            r0 = r20
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0008
        L_0x02e9:
            r19 = move-exception
        L_0x02ea:
            if (r16 == 0) goto L_0x02ef
            r16.close()     // Catch:{ IOException -> 0x02f0 }
        L_0x02ef:
            throw r19
        L_0x02f0:
            r9 = move-exception
            java.lang.String r20 = "out close error!"
            r0 = r20
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x02ef
        L_0x02f9:
            java.io.DataInputStream r8 = new java.io.DataInputStream     // Catch:{ IOException -> 0x02a0 }
            java.io.InputStream r19 = r4.getInputStream()     // Catch:{ IOException -> 0x02a0 }
            r0 = r19
            r8.<init>(r0)     // Catch:{ IOException -> 0x02a0 }
            r7 = r8
            goto L_0x0280
        L_0x0307:
            if (r7 == 0) goto L_0x030c
            r7.close()     // Catch:{ Exception -> 0x0318 }
        L_0x030c:
            int r19 = r2.size()
            if (r19 <= 0) goto L_0x0331
            byte[] r19 = r2.toByteArray()
            goto L_0x0008
        L_0x0318:
            r9 = move-exception
            java.lang.String r19 = "out close error!"
            r0 = r19
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x030c
        L_0x0321:
            r19 = move-exception
            if (r7 == 0) goto L_0x0327
            r7.close()     // Catch:{ Exception -> 0x0328 }
        L_0x0327:
            throw r19
        L_0x0328:
            r9 = move-exception
            java.lang.String r20 = "out close error!"
            r0 = r20
            com.alibaba.motu.tbrest.utils.LogUtil.e(r0, r9)
            goto L_0x0327
        L_0x0331:
            r19 = 0
            goto L_0x0008
        L_0x0335:
            r19 = 0
            goto L_0x0008
        L_0x0339:
            r19 = move-exception
            r16 = r17
            goto L_0x02ea
        L_0x033d:
            r9 = move-exception
            r16 = r17
            goto L_0x02cf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.motu.tbrest.rest.RestHttpUtils.sendRequest(int, java.lang.String, java.util.Map, boolean):byte[]");
    }
}
