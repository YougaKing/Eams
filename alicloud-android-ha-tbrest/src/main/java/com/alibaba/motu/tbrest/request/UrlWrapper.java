//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.motu.tbrest.request;

import android.text.TextUtils;
import com.alibaba.motu.tbrest.SendService;
import com.alibaba.motu.tbrest.sign.RestBaseRequestAuthentication;
import com.alibaba.motu.tbrest.utils.LogUtil;
import com.alibaba.motu.tbrest.utils.MD5Utils;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;

public class UrlWrapper {
    private static final int MAX_CONNECTION_TIME_OUT = 10000;
    private static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 60000;
    public static int mErrorCode = 0;
    private static RestSslSocketFactory mRestSslSocketFactory = null;

    public UrlWrapper() {
    }

    public static BizResponse sendRequest(String appkey, String adashUrl, byte[] bytes) {
        LogUtil.d("sendRequest use adashx, bytes length : " + (bytes == null ? "0" : bytes.length));
        BizResponse bizResponse = new BizResponse();

        HttpURLConnection conn;
        try {
            URL url;
            if (SendService.getInstance().openHttp) {
                url = new URL(adashUrl);
            } else {
                url = new URL(adashUrl);
            }

            conn = (HttpURLConnection)url.openConnection();
            if (conn instanceof HttpsURLConnection) {
                if (mRestSslSocketFactory == null && !TextUtils.isEmpty(url.getHost())) {
                    mRestSslSocketFactory = new RestSslSocketFactory(url.getHost());
                }

                ((HttpsURLConnection)conn).setSSLSocketFactory(mRestSslSocketFactory);
            }
        } catch (MalformedURLException var49) {
            return bizResponse;
        } catch (IOException var50) {
            return bizResponse;
        }

        if (conn != null) {
            conn.setDoOutput(true);
            conn.setDoInput(true);

            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException var48) {
                return bizResponse;
            }

            conn.setUseCaches(false);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(60000);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Charset", "UTF-8");
            if (!TextUtils.isEmpty(appkey)) {
                conn.setRequestProperty("x-k", appkey);
            }

            try {
                String appSecret = SendService.getInstance().appSecret;
                if (appSecret != null && appSecret.length() > 0) {
                    RestBaseRequestAuthentication requestAuthentication = new RestBaseRequestAuthentication(appkey, appSecret, true);
                    String signValue = requestAuthentication.getSign(MD5Utils.getMd5Hex(bytes));
                    LogUtil.d("signValue:" + signValue);
                    conn.setRequestProperty("x-s", signValue);
                    conn.setRequestProperty("x-t", "2");
                } else {
                    String secret = "";
                    RestBaseRequestAuthentication requestAuthentication = new RestBaseRequestAuthentication(appkey, secret, false);
                    String signValue = requestAuthentication.getSign(MD5Utils.getMd5Hex(bytes));
                    LogUtil.d("signValue:" + signValue);
                    conn.setRequestProperty("x-s", signValue);
                    conn.setRequestProperty("x-t", "3");
                }
            } catch (Throwable var56) {
                LogUtil.e(var56.toString());
            }

            long now = System.currentTimeMillis();
            DataOutputStream out = null;

            label432: {
                BizResponse var10;
                try {
                    conn.connect();
                    if (bytes != null && bytes.length > 0) {
                        out = new DataOutputStream(conn.getOutputStream());
                        out.write(bytes);
                        out.flush();
                    }
                    break label432;
                } catch (SSLHandshakeException var53) {
                    LogUtil.e(var53.toString());
                    bizResponse.rt = System.currentTimeMillis() - now;
                    var10 = bizResponse;
                } catch (Exception var54) {
                    LogUtil.e(var54.toString());
                    bizResponse.rt = System.currentTimeMillis() - now;
                    var10 = bizResponse;
                    return var10;
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException var46) {
                            LogUtil.e(var46.toString());
                        }
                    }

                }

                return var10;
            }

            bizResponse.rt = System.currentTimeMillis() - now;
            InputStream dis = null;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();

            try {
                dis = new DataInputStream(conn.getInputStream());
                byte[] buffer = new byte[2048];

                int i;
                while((i = dis.read(buffer, 0, 2048)) != -1) {
                    bs.write(buffer, 0, i);
                }
            } catch (IOException var51) {
                LogUtil.e(var51.toString());
            } finally {
                if (dis != null) {
                    try {
                        dis.close();
                    } catch (Exception var47) {
                        LogUtil.e(var47.toString());
                    }
                }

            }

            if (bs.size() > 0) {
                mErrorCode = BizRequest.parseResult(bs.toByteArray());
                bizResponse.errCode = mErrorCode;
                bizResponse.data = BizRequest.mResponseAdditionalData;
            }
        }

        return bizResponse;
    }

    public static BizResponse sendRequest(String adashxHost, byte[] bytes) {
        String appkey = SendService.getInstance().appKey;
        String adashUrl = null;
        if (SendService.getInstance().openHttp) {
            adashUrl = "http://" + adashxHost + "/upload";
        } else {
            adashUrl = "https://" + adashxHost + "/upload";
        }

        return sendRequest(appkey, adashUrl, bytes);
    }

    public static BizResponse sendRequestByUrl(String url, byte[] bytes) {
        String appkey = SendService.getInstance().appKey;
        return sendRequest(appkey, url, bytes);
    }

    static {
        System.setProperty("http.keepAlive", "true");
    }
}
