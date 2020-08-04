package com.alibaba.motu.tbrest.request;

import com.alibaba.motu.tbrest.SendService;

public class UrlWrapper {
    private static final int MAX_CONNECTION_TIME_OUT = 10000;
    private static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 60000;
    public static int mErrorCode = 0;
    private static RestSslSocketFactory mRestSslSocketFactory = null;

    static {
        System.setProperty("http.keepAlive", "true");
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0285 A[SYNTHETIC, Splitter:B:100:0x0285] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02a3 A[SYNTHETIC, Splitter:B:109:0x02a3] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0283 A[EDGE_INSN: B:124:0x0283->B:99:0x0283 ?: BREAK  
    EDGE_INSN: B:124:0x0283->B:99:0x0283 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0147 A[SYNTHETIC, Splitter:B:41:0x0147] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x017d A[Catch:{ IOException -> 0x0185, all -> 0x02b0 }, LOOP:0: B:49:0x016b->B:51:0x017d, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0190 A[SYNTHETIC, Splitter:B:57:0x0190] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0241 A[SYNTHETIC, Splitter:B:80:0x0241] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0264 A[SYNTHETIC, Splitter:B:88:0x0264] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0276 A[SYNTHETIC, Splitter:B:94:0x0276] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:85:0x0251=Splitter:B:85:0x0251, B:77:0x022e=Splitter:B:77:0x022e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.motu.tbrest.request.BizResponse sendRequest(java.lang.String r26, java.lang.String r27, byte[] r28) {
        /*
            java.lang.StringBuilder r23 = new java.lang.StringBuilder
            r23.<init>()
            java.lang.String r24 = "sendRequest use adashx, bytes length : "
            java.lang.StringBuilder r24 = r23.append(r24)
            if (r28 != 0) goto L_0x01b0
            java.lang.String r23 = "0"
        L_0x000f:
            r0 = r24
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r23 = r23.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.d(r23)
            com.alibaba.motu.tbrest.request.BizResponse r5 = new com.alibaba.motu.tbrest.request.BizResponse
            r5.<init>()
            com.alibaba.motu.tbrest.SendService r23 = com.alibaba.motu.tbrest.SendService.getInstance()     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r0 = r23
            java.lang.Boolean r0 = r0.openHttp     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r23 = r0
            boolean r23 = r23.booleanValue()     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            if (r23 == 0) goto L_0x01bb
            java.net.URL r22 = new java.net.URL     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r0 = r22
            r1 = r27
            r0.<init>(r1)     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
        L_0x003c:
            java.net.URLConnection r8 = r22.openConnection()     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            boolean r0 = r8 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r23 = r0
            if (r23 == 0) goto L_0x006b
            com.alibaba.motu.tbrest.request.RestSslSocketFactory r23 = mRestSslSocketFactory     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            if (r23 != 0) goto L_0x0061
            java.lang.String r23 = r22.getHost()     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            boolean r23 = android.text.TextUtils.isEmpty(r23)     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            if (r23 != 0) goto L_0x0061
            com.alibaba.motu.tbrest.request.RestSslSocketFactory r23 = new com.alibaba.motu.tbrest.request.RestSslSocketFactory     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            java.lang.String r24 = r22.getHost()     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r23.<init>(r24)     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            mRestSslSocketFactory = r23     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
        L_0x0061:
            r0 = r8
            javax.net.ssl.HttpsURLConnection r0 = (javax.net.ssl.HttpsURLConnection) r0     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r23 = r0
            com.alibaba.motu.tbrest.request.RestSslSocketFactory r24 = mRestSslSocketFactory     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r23.setSSLSocketFactory(r24)     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
        L_0x006b:
            if (r8 == 0) goto L_0x01af
            r23 = 1
            r0 = r23
            r8.setDoOutput(r0)
            r23 = 1
            r0 = r23
            r8.setDoInput(r0)
            java.lang.String r23 = "POST"
            r0 = r23
            r8.setRequestMethod(r0)     // Catch:{ ProtocolException -> 0x01ca }
            r23 = 0
            r0 = r23
            r8.setUseCaches(r0)
            r23 = 10000(0x2710, float:1.4013E-41)
            r0 = r23
            r8.setConnectTimeout(r0)
            r23 = 60000(0xea60, float:8.4078E-41)
            r0 = r23
            r8.setReadTimeout(r0)
            r23 = 1
            r0 = r23
            r8.setInstanceFollowRedirects(r0)
            java.lang.String r23 = "Content-Type"
            java.lang.String r24 = "application/x-www-form-urlencoded"
            r0 = r23
            r1 = r24
            r8.setRequestProperty(r0, r1)
            java.lang.String r23 = "Charset"
            java.lang.String r24 = "UTF-8"
            r0 = r23
            r1 = r24
            r8.setRequestProperty(r0, r1)
            boolean r23 = android.text.TextUtils.isEmpty(r26)
            if (r23 != 0) goto L_0x00c4
            java.lang.String r23 = "x-k"
            r0 = r23
            r1 = r26
            r8.setRequestProperty(r0, r1)
        L_0x00c4:
            com.alibaba.motu.tbrest.SendService r23 = com.alibaba.motu.tbrest.SendService.getInstance()     // Catch:{ Throwable -> 0x0219 }
            r0 = r23
            java.lang.String r4 = r0.appSecret     // Catch:{ Throwable -> 0x0219 }
            if (r4 == 0) goto L_0x01cc
            int r23 = r4.length()     // Catch:{ Throwable -> 0x0219 }
            if (r23 <= 0) goto L_0x01cc
            com.alibaba.motu.tbrest.sign.RestBaseRequestAuthentication r19 = new com.alibaba.motu.tbrest.sign.RestBaseRequestAuthentication     // Catch:{ Throwable -> 0x0219 }
            r23 = 1
            r0 = r19
            r1 = r26
            r2 = r23
            r0.<init>(r1, r4, r2)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = com.alibaba.motu.tbrest.utils.MD5Utils.getMd5Hex(r28)     // Catch:{ Throwable -> 0x0219 }
            r0 = r19
            r1 = r23
            java.lang.String r21 = r0.getSign(r1)     // Catch:{ Throwable -> 0x0219 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0219 }
            r23.<init>()     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r24 = "signValue:"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x0219 }
            r0 = r23
            r1 = r21
            java.lang.StringBuilder r23 = r0.append(r1)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = r23.toString()     // Catch:{ Throwable -> 0x0219 }
            com.alibaba.motu.tbrest.utils.LogUtil.d(r23)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = "x-s"
            r0 = r23
            r1 = r21
            r8.setRequestProperty(r0, r1)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = "x-t"
            java.lang.String r24 = "2"
            r0 = r23
            r1 = r24
            r8.setRequestProperty(r0, r1)     // Catch:{ Throwable -> 0x0219 }
        L_0x011b:
            long r16 = java.lang.System.currentTimeMillis()
            r15 = 0
            r8.connect()     // Catch:{ SSLHandshakeException -> 0x022d, Exception -> 0x0250 }
            if (r28 == 0) goto L_0x0145
            r0 = r28
            int r0 = r0.length     // Catch:{ SSLHandshakeException -> 0x022d, Exception -> 0x0250 }
            r23 = r0
            if (r23 <= 0) goto L_0x0145
            java.io.DataOutputStream r18 = new java.io.DataOutputStream     // Catch:{ SSLHandshakeException -> 0x022d, Exception -> 0x0250 }
            java.io.OutputStream r23 = r8.getOutputStream()     // Catch:{ SSLHandshakeException -> 0x022d, Exception -> 0x0250 }
            r0 = r18
            r1 = r23
            r0.<init>(r1)     // Catch:{ SSLHandshakeException -> 0x022d, Exception -> 0x0250 }
            r0 = r18
            r1 = r28
            r0.write(r1)     // Catch:{ SSLHandshakeException -> 0x02be, Exception -> 0x02ba, all -> 0x02b6 }
            r18.flush()     // Catch:{ SSLHandshakeException -> 0x02be, Exception -> 0x02ba, all -> 0x02b6 }
            r15 = r18
        L_0x0145:
            if (r15 == 0) goto L_0x014a
            r15.close()     // Catch:{ IOException -> 0x0223 }
        L_0x014a:
            long r24 = java.lang.System.currentTimeMillis()
            long r24 = r24 - r16
            r0 = r24
            r5.rt = r0
            r9 = 0
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream
            r6.<init>()
            java.io.DataInputStream r10 = new java.io.DataInputStream     // Catch:{ IOException -> 0x02b3 }
            java.io.InputStream r23 = r8.getInputStream()     // Catch:{ IOException -> 0x02b3 }
            r0 = r23
            r10.<init>(r0)     // Catch:{ IOException -> 0x02b3 }
            r23 = 2048(0x800, float:2.87E-42)
            r0 = r23
            byte[] r7 = new byte[r0]     // Catch:{ IOException -> 0x0185, all -> 0x02b0 }
        L_0x016b:
            r23 = 0
            r24 = 2048(0x800, float:2.87E-42)
            r0 = r23
            r1 = r24
            int r14 = r10.read(r7, r0, r1)     // Catch:{ IOException -> 0x0185, all -> 0x02b0 }
            r23 = -1
            r0 = r23
            if (r14 == r0) goto L_0x0283
            r23 = 0
            r0 = r23
            r6.write(r7, r0, r14)     // Catch:{ IOException -> 0x0185, all -> 0x02b0 }
            goto L_0x016b
        L_0x0185:
            r11 = move-exception
            r9 = r10
        L_0x0187:
            java.lang.String r23 = r11.toString()     // Catch:{ all -> 0x02a0 }
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)     // Catch:{ all -> 0x02a0 }
            if (r9 == 0) goto L_0x0193
            r9.close()     // Catch:{ Exception -> 0x0296 }
        L_0x0193:
            int r23 = r6.size()
            if (r23 <= 0) goto L_0x01af
            byte[] r23 = r6.toByteArray()
            int r23 = com.alibaba.motu.tbrest.request.BizRequest.parseResult(r23)
            mErrorCode = r23
            int r23 = mErrorCode
            r0 = r23
            r5.errCode = r0
            java.lang.String r23 = com.alibaba.motu.tbrest.request.BizRequest.mResponseAdditionalData
            r0 = r23
            r5.data = r0
        L_0x01af:
            return r5
        L_0x01b0:
            r0 = r28
            int r0 = r0.length
            r23 = r0
            java.lang.Integer r23 = java.lang.Integer.valueOf(r23)
            goto L_0x000f
        L_0x01bb:
            java.net.URL r22 = new java.net.URL     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            r0 = r22
            r1 = r27
            r0.<init>(r1)     // Catch:{ MalformedURLException -> 0x01c6, IOException -> 0x01c8 }
            goto L_0x003c
        L_0x01c6:
            r12 = move-exception
            goto L_0x01af
        L_0x01c8:
            r13 = move-exception
            goto L_0x01af
        L_0x01ca:
            r11 = move-exception
            goto L_0x01af
        L_0x01cc:
            java.lang.String r20 = ""
            com.alibaba.motu.tbrest.sign.RestBaseRequestAuthentication r19 = new com.alibaba.motu.tbrest.sign.RestBaseRequestAuthentication     // Catch:{ Throwable -> 0x0219 }
            r23 = 0
            r0 = r19
            r1 = r26
            r2 = r20
            r3 = r23
            r0.<init>(r1, r2, r3)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = com.alibaba.motu.tbrest.utils.MD5Utils.getMd5Hex(r28)     // Catch:{ Throwable -> 0x0219 }
            r0 = r19
            r1 = r23
            java.lang.String r21 = r0.getSign(r1)     // Catch:{ Throwable -> 0x0219 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0219 }
            r23.<init>()     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r24 = "signValue:"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x0219 }
            r0 = r23
            r1 = r21
            java.lang.StringBuilder r23 = r0.append(r1)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = r23.toString()     // Catch:{ Throwable -> 0x0219 }
            com.alibaba.motu.tbrest.utils.LogUtil.d(r23)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = "x-s"
            r0 = r23
            r1 = r21
            r8.setRequestProperty(r0, r1)     // Catch:{ Throwable -> 0x0219 }
            java.lang.String r23 = "x-t"
            java.lang.String r24 = "3"
            r0 = r23
            r1 = r24
            r8.setRequestProperty(r0, r1)     // Catch:{ Throwable -> 0x0219 }
            goto L_0x011b
        L_0x0219:
            r11 = move-exception
            java.lang.String r23 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)
            goto L_0x011b
        L_0x0223:
            r11 = move-exception
            java.lang.String r23 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)
            goto L_0x014a
        L_0x022d:
            r11 = move-exception
        L_0x022e:
            java.lang.String r23 = r11.toString()     // Catch:{ all -> 0x0273 }
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)     // Catch:{ all -> 0x0273 }
            long r24 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0273 }
            long r24 = r24 - r16
            r0 = r24
            r5.rt = r0     // Catch:{ all -> 0x0273 }
            if (r15 == 0) goto L_0x01af
            r15.close()     // Catch:{ IOException -> 0x0246 }
            goto L_0x01af
        L_0x0246:
            r11 = move-exception
            java.lang.String r23 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)
            goto L_0x01af
        L_0x0250:
            r11 = move-exception
        L_0x0251:
            java.lang.String r23 = r11.toString()     // Catch:{ all -> 0x0273 }
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)     // Catch:{ all -> 0x0273 }
            long r24 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0273 }
            long r24 = r24 - r16
            r0 = r24
            r5.rt = r0     // Catch:{ all -> 0x0273 }
            if (r15 == 0) goto L_0x01af
            r15.close()     // Catch:{ IOException -> 0x0269 }
            goto L_0x01af
        L_0x0269:
            r11 = move-exception
            java.lang.String r23 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)
            goto L_0x01af
        L_0x0273:
            r23 = move-exception
        L_0x0274:
            if (r15 == 0) goto L_0x0279
            r15.close()     // Catch:{ IOException -> 0x027a }
        L_0x0279:
            throw r23
        L_0x027a:
            r11 = move-exception
            java.lang.String r24 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r24)
            goto L_0x0279
        L_0x0283:
            if (r10 == 0) goto L_0x02c3
            r10.close()     // Catch:{ Exception -> 0x028b }
            r9 = r10
            goto L_0x0193
        L_0x028b:
            r11 = move-exception
            java.lang.String r23 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)
            r9 = r10
            goto L_0x0193
        L_0x0296:
            r11 = move-exception
            java.lang.String r23 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r23)
            goto L_0x0193
        L_0x02a0:
            r23 = move-exception
        L_0x02a1:
            if (r9 == 0) goto L_0x02a6
            r9.close()     // Catch:{ Exception -> 0x02a7 }
        L_0x02a6:
            throw r23
        L_0x02a7:
            r11 = move-exception
            java.lang.String r24 = r11.toString()
            com.alibaba.motu.tbrest.utils.LogUtil.e(r24)
            goto L_0x02a6
        L_0x02b0:
            r23 = move-exception
            r9 = r10
            goto L_0x02a1
        L_0x02b3:
            r11 = move-exception
            goto L_0x0187
        L_0x02b6:
            r23 = move-exception
            r15 = r18
            goto L_0x0274
        L_0x02ba:
            r11 = move-exception
            r15 = r18
            goto L_0x0251
        L_0x02be:
            r11 = move-exception
            r15 = r18
            goto L_0x022e
        L_0x02c3:
            r9 = r10
            goto L_0x0193
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.motu.tbrest.request.UrlWrapper.sendRequest(java.lang.String, java.lang.String, byte[]):com.alibaba.motu.tbrest.request.BizResponse");
    }

    public static BizResponse sendRequest(String adashxHost, byte[] bytes) {
        String adashUrl;
        String appkey = SendService.getInstance().appKey;
        if (SendService.getInstance().openHttp.booleanValue()) {
            adashUrl = "http://" + adashxHost + "/upload";
        } else {
            adashUrl = "https://" + adashxHost + "/upload";
        }
        return sendRequest(appkey, adashUrl, bytes);
    }

    public static BizResponse sendRequestByUrl(String url, byte[] bytes) {
        return sendRequest(SendService.getInstance().appKey, url, bytes);
    }
}
