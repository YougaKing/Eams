package com.alibaba.motu.tbrest.rest;

import android.content.Context;
import com.alibaba.motu.tbrest.SendService;
import java.util.Map;

public class RestReqSend {
    public static boolean sendLog(Context aContext, String adashxServerHost, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        return sendLog(SendService.getInstance().appKey, aContext, adashxServerHost, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean sendLog(java.lang.String r17, android.content.Context r18, java.lang.String r19, long r20, java.lang.String r22, int r23, java.lang.Object r24, java.lang.Object r25, java.lang.Object r26, java.util.Map<java.lang.String, java.lang.String> r27) {
        /*
            java.lang.String r3 = "RestAPI start send log!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0055 }
            r3 = r17
            r4 = r20
            r6 = r22
            r7 = r23
            r8 = r24
            r9 = r25
            r10 = r26
            r11 = r27
            java.lang.String r15 = com.alibaba.motu.tbrest.rest.RestReqDataBuilder.buildRequestData(r3, r4, r6, r7, r8, r9, r10, r11)     // Catch:{ Throwable -> 0x0055 }
            boolean r3 = com.alibaba.motu.tbrest.utils.StringUtils.isNotBlank(r15)     // Catch:{ Throwable -> 0x0055 }
            if (r3 == 0) goto L_0x005d
            java.lang.String r3 = "RestAPI build data succ!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0055 }
            java.util.HashMap r13 = new java.util.HashMap     // Catch:{ Throwable -> 0x0055 }
            r3 = 1
            r13.<init>(r3)     // Catch:{ Throwable -> 0x0055 }
            java.lang.String r3 = java.lang.String.valueOf(r23)     // Catch:{ Throwable -> 0x0055 }
            r13.put(r3, r15)     // Catch:{ Throwable -> 0x0055 }
            r14 = 0
            r0 = r17
            r1 = r18
            byte[] r14 = com.alibaba.motu.tbrest.request.BizRequest.getPackRequest(r0, r1, r13)     // Catch:{ Exception -> 0x004c }
        L_0x003a:
            if (r14 == 0) goto L_0x005b
            java.lang.String r3 = "packRequest success!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0055 }
            r0 = r19
            com.alibaba.motu.tbrest.request.BizResponse r2 = com.alibaba.motu.tbrest.request.UrlWrapper.sendRequest(r0, r14)     // Catch:{ Throwable -> 0x0055 }
            boolean r3 = r2.isSuccess()     // Catch:{ Throwable -> 0x0055 }
        L_0x004b:
            return r3
        L_0x004c:
            r12 = move-exception
            java.lang.String r3 = r12.toString()     // Catch:{ Throwable -> 0x0055 }
            com.alibaba.motu.tbrest.utils.LogUtil.e(r3)     // Catch:{ Throwable -> 0x0055 }
            goto L_0x003a
        L_0x0055:
            r12 = move-exception
            java.lang.String r3 = "system error!"
            com.alibaba.motu.tbrest.utils.LogUtil.e(r3, r12)
        L_0x005b:
            r3 = 0
            goto L_0x004b
        L_0x005d:
            java.lang.String r3 = "UTRestAPI build data failure!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0055 }
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.motu.tbrest.rest.RestReqSend.sendLog(java.lang.String, android.content.Context, java.lang.String, long, java.lang.String, int, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map):boolean");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean sendLogByUrl(java.lang.String r17, android.content.Context r18, java.lang.String r19, long r20, java.lang.String r22, int r23, java.lang.Object r24, java.lang.Object r25, java.lang.Object r26, java.util.Map<java.lang.String, java.lang.String> r27) {
        /*
            java.lang.String r3 = "RestAPI start send log by url!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0057 }
            r3 = r17
            r4 = r20
            r6 = r22
            r7 = r23
            r8 = r24
            r9 = r25
            r10 = r26
            r11 = r27
            java.lang.String r15 = com.alibaba.motu.tbrest.rest.RestReqDataBuilder.buildRequestData(r3, r4, r6, r7, r8, r9, r10, r11)     // Catch:{ Throwable -> 0x0057 }
            boolean r3 = com.alibaba.motu.tbrest.utils.StringUtils.isNotBlank(r15)     // Catch:{ Throwable -> 0x0057 }
            if (r3 == 0) goto L_0x005f
            java.lang.String r3 = "RestAPI build data succ by url!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0057 }
            java.util.HashMap r13 = new java.util.HashMap     // Catch:{ Throwable -> 0x0057 }
            r3 = 1
            r13.<init>(r3)     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r3 = java.lang.String.valueOf(r23)     // Catch:{ Throwable -> 0x0057 }
            r13.put(r3, r15)     // Catch:{ Throwable -> 0x0057 }
            r14 = 0
            r0 = r17
            r1 = r18
            byte[] r14 = com.alibaba.motu.tbrest.request.BizRequest.getPackRequest(r0, r1, r13)     // Catch:{ Exception -> 0x004e }
        L_0x003a:
            if (r14 == 0) goto L_0x005d
            java.lang.String r3 = "packRequest success by url!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0057 }
            r0 = r17
            r1 = r19
            com.alibaba.motu.tbrest.request.BizResponse r2 = com.alibaba.motu.tbrest.request.UrlWrapper.sendRequest(r0, r1, r14)     // Catch:{ Throwable -> 0x0057 }
            boolean r3 = r2.isSuccess()     // Catch:{ Throwable -> 0x0057 }
        L_0x004d:
            return r3
        L_0x004e:
            r12 = move-exception
            java.lang.String r3 = r12.toString()     // Catch:{ Throwable -> 0x0057 }
            com.alibaba.motu.tbrest.utils.LogUtil.e(r3)     // Catch:{ Throwable -> 0x0057 }
            goto L_0x003a
        L_0x0057:
            r12 = move-exception
            java.lang.String r3 = "system error by url!"
            com.alibaba.motu.tbrest.utils.LogUtil.e(r3, r12)
        L_0x005d:
            r3 = 0
            goto L_0x004d
        L_0x005f:
            java.lang.String r3 = "UTRestAPI build data failure by url!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0057 }
            goto L_0x005d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.motu.tbrest.rest.RestReqSend.sendLogByUrl(java.lang.String, android.content.Context, java.lang.String, long, java.lang.String, int, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map):boolean");
    }

    @Deprecated
    public static String sendLogByUrl(String url, Context aContext, long aTimestamp, String aPage, int aEventId, Object aArg1, Object aArg2, Object aArg3, Map<String, String> aExtData) {
        return sendLogByUrl(url, SendService.getInstance().appKey, aContext, aTimestamp, aPage, aEventId, aArg1, aArg2, aArg3, aExtData);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String sendLogByUrl(java.lang.String r19, java.lang.String r20, android.content.Context r21, long r22, java.lang.String r24, int r25, java.lang.Object r26, java.lang.Object r27, java.lang.Object r28, java.util.Map<java.lang.String, java.lang.String> r29) {
        /*
            java.lang.String r3 = "sendLogByUrl RestAPI start send log!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0067 }
            r3 = r20
            r4 = r19
            r5 = r21
            r6 = r22
            r8 = r24
            r9 = r25
            r10 = r26
            r11 = r27
            r12 = r28
            r13 = r29
            com.alibaba.motu.tbrest.rest.RestReqDataBuildResult r16 = com.alibaba.motu.tbrest.rest.RestReqDataBuilder.buildMonkeyPostReqDataObj(r3, r4, r5, r6, r8, r9, r10, r11, r12, r13)     // Catch:{ Throwable -> 0x0067 }
            if (r16 == 0) goto L_0x006e
            java.lang.String r3 = "sendLogByUrl RestAPI build data succ!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0067 }
            java.util.Map r14 = r16.getPostReqData()     // Catch:{ Throwable -> 0x0067 }
            if (r14 != 0) goto L_0x0032
            java.lang.String r3 = "sendLogByUrl postReqData is null!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0067 }
            r18 = 0
        L_0x0031:
            return r18
        L_0x0032:
            java.lang.String r17 = r16.getReqUrl()     // Catch:{ Throwable -> 0x0067 }
            boolean r3 = com.alibaba.motu.tbrest.utils.StringUtils.isEmpty(r17)     // Catch:{ Throwable -> 0x0067 }
            if (r3 == 0) goto L_0x0044
            java.lang.String r3 = "sendLogByUrl reqUrl is null!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0067 }
            r18 = 0
            goto L_0x0031
        L_0x0044:
            r3 = 2
            r4 = 1
            r0 = r17
            byte[] r15 = com.alibaba.motu.tbrest.rest.RestHttpUtils.sendRequest(r3, r0, r14, r4)     // Catch:{ Throwable -> 0x0067 }
            if (r15 == 0) goto L_0x005d
            java.lang.String r18 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0060 }
            java.lang.String r3 = "UTF-8"
            r0 = r18
            r0.<init>(r15, r3)     // Catch:{ UnsupportedEncodingException -> 0x0060 }
            boolean r3 = com.alibaba.motu.tbrest.utils.StringUtils.isEmpty(r18)     // Catch:{ UnsupportedEncodingException -> 0x0060 }
            if (r3 == 0) goto L_0x0031
        L_0x005d:
            r18 = 0
            goto L_0x0031
        L_0x0060:
            r2 = move-exception
            java.lang.String r3 = "sendLogByUrl result encoding UTF-8 error!"
            com.alibaba.motu.tbrest.utils.LogUtil.e(r3, r2)     // Catch:{ Throwable -> 0x0067 }
            goto L_0x005d
        L_0x0067:
            r2 = move-exception
            java.lang.String r3 = "sendLogByUrl system error!"
            com.alibaba.motu.tbrest.utils.LogUtil.e(r3, r2)
            goto L_0x005d
        L_0x006e:
            java.lang.String r3 = "sendLogByUrl UTRestAPI build data failure!"
            com.alibaba.motu.tbrest.utils.LogUtil.i(r3)     // Catch:{ Throwable -> 0x0067 }
            goto L_0x005d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.motu.tbrest.rest.RestReqSend.sendLogByUrl(java.lang.String, java.lang.String, android.content.Context, long, java.lang.String, int, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map):java.lang.String");
    }
}
