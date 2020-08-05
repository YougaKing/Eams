package com.taobao.monitor.impl.data.phenix;

import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.util.SafeUtils;
import com.taobao.phenix.lifecycle.IPhenixLifeCycle;
import java.util.HashMap;
import java.util.Map;

/* compiled from: PhenixLifeCycleImpl */
public class PhenixLifeCycleImpl implements IPhenixLifeCycle {
    private ImageStageDispatcher a = null;

    public PhenixLifeCycleImpl() {
        m();
    }

    private void m() {
        IDispatcher a2 = DispatcherManager.getDispatcher("IMAGE_STAGE_DISPATCHER");
        if (a2 instanceof ImageStageDispatcher) {
            this.a = (ImageStageDispatcher) a2;
        }
    }

    public void onRequest(String str, String str2, Map<String, Object> map) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.imageStage(0);
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
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.imageStage(2);
        }
        HashMap hashMap = new HashMap(map);
        hashMap.put("procedureName", "ImageLib");
        hashMap.put("stage", "onError");
        hashMap.put("requestId", str);
        hashMap.put("requestUrl", str2);
        DataLoggerUtils.log("image", hashMap);
    }

    public void onCancel(String str, String str2, Map<String, Object> map) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.imageStage(3);
        }
        HashMap hashMap = new HashMap(map);
        hashMap.put("procedureName", "ImageLib");
        hashMap.put("stage", "onCancel");
        hashMap.put("requestId", str);
        hashMap.put("requestUrl", str2);
        DataLoggerUtils.log("image", hashMap);
    }

    public void onFinished(String str, String str2, Map<String, Object> map) {
        if (!DispatcherManager.isEmpty((IDispatcher) this.a)) {
            this.a.imageStage(1);
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
            str3 = SafeUtils.transformString(map.get("requestUrl"), "");
        }
        HashMap hashMap = new HashMap(map);
        hashMap.put("procedureName", "ImageLib");
        hashMap.put("stage", str2);
        hashMap.put("requestId", str);
        hashMap.put("requestUrl", str3);
        DataLoggerUtils.log("image", hashMap);
    }
}
