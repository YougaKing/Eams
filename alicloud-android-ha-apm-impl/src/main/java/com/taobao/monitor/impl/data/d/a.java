package com.taobao.monitor.impl.data.d;

import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.g;
import com.taobao.monitor.impl.trace.m;
import com.taobao.monitor.impl.util.e;
import com.taobao.phenix.lifecycle.IPhenixLifeCycle;
import java.util.HashMap;
import java.util.Map;

/* compiled from: PhenixLifeCycleImpl */
public class a implements IPhenixLifeCycle {
    private m a = null;

    public a() {
        m();
    }

    private void m() {
        IDispatcher a2 = g.a("IMAGE_STAGE_DISPATCHER");
        if (a2 instanceof m) {
            this.a = (m) a2;
        }
    }

    public void onRequest(String str, String str2, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.f(0);
        }
        try {
            HashMap hashMap = new HashMap(map);
            hashMap.put("procedureName", "ImageLib");
            hashMap.put("stage", "onRequest");
            hashMap.put("requestId", str);
            hashMap.put("requestUrl", str2);
            DataLoggerUtils.log("image", hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onError(String str, String str2, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.f(2);
        }
        HashMap hashMap = new HashMap(map);
        hashMap.put("procedureName", "ImageLib");
        hashMap.put("stage", "onError");
        hashMap.put("requestId", str);
        hashMap.put("requestUrl", str2);
        DataLoggerUtils.log("image", hashMap);
    }

    public void onCancel(String str, String str2, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.f(3);
        }
        HashMap hashMap = new HashMap(map);
        hashMap.put("procedureName", "ImageLib");
        hashMap.put("stage", "onCancel");
        hashMap.put("requestId", str);
        hashMap.put("requestUrl", str2);
        DataLoggerUtils.log("image", hashMap);
    }

    public void onFinished(String str, String str2, Map<String, Object> map) {
        if (!g.a((IDispatcher) this.a)) {
            this.a.f(1);
        }
        HashMap hashMap = new HashMap(map);
        hashMap.put("procedureName", "ImageLib");
        hashMap.put("stage", "onFinished");
        hashMap.put("requestId", str);
        hashMap.put("requestUrl", str2);
        DataLoggerUtils.log("image", hashMap);
    }

    public void onEvent(String str, String str2, Map<String, Object> map) {
        String str3 = null;
        if (map != null) {
            str3 = e.a(map.get("requestUrl"), "");
        }
        HashMap hashMap = new HashMap(map);
        hashMap.put("procedureName", "ImageLib");
        hashMap.put("stage", str2);
        hashMap.put("requestId", str);
        hashMap.put("requestUrl", str3);
        DataLoggerUtils.log("image", hashMap);
    }
}
