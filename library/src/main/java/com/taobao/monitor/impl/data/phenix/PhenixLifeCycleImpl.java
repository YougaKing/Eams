package com.taobao.monitor.impl.data.phenix;

import com.taobao.monitor.impl.logger.DataLoggerUtils;
import com.taobao.monitor.impl.trace.DispatcherManager;
import com.taobao.monitor.impl.trace.IDispatcher;
import com.taobao.monitor.impl.trace.ImageStageDispatcher;
import com.taobao.monitor.impl.util.SafeUtils;
import com.taobao.phenix.lifecycle.IPhenixLifeCycle;

import java.util.HashMap;
import java.util.Map;

public class PhenixLifeCycleImpl implements IPhenixLifeCycle {

    private ImageStageDispatcher imageStageDispatcher = null;

    public PhenixLifeCycleImpl() {
        this.m();
    }

    private void m() {
        IDispatcher imageStageDispatcher = DispatcherManager.getDispatcher("IMAGE_STAGE_DISPATCHER");
        if (imageStageDispatcher instanceof ImageStageDispatcher) {
            this.imageStageDispatcher = (ImageStageDispatcher) imageStageDispatcher;
        }

    }

    public void onRequest(String var1, String var2, Map<String, Object> var3) {
        if (!DispatcherManager.isEmpty(this.imageStageDispatcher)) {
            this.imageStageDispatcher.imageStage(0);
        }

        try {
            HashMap var4 = new HashMap(var3);
            var4.put("procedureName", "ImageLib");
            var4.put("stage", "onRequest");
            var4.put("requestId", var1);
            var4.put("requestUrl", var2);
            DataLoggerUtils.log("image", var4);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public void onError(String var1, String var2, Map<String, Object> var3) {
        if (!DispatcherManager.isEmpty(this.imageStageDispatcher)) {
            this.imageStageDispatcher.imageStage(2);
        }

        HashMap var4 = new HashMap(var3);
        var4.put("procedureName", "ImageLib");
        var4.put("stage", "onError");
        var4.put("requestId", var1);
        var4.put("requestUrl", var2);
        DataLoggerUtils.log("image", var4);
    }

    public void onCancel(String var1, String var2, Map<String, Object> var3) {
        if (!DispatcherManager.isEmpty(this.imageStageDispatcher)) {
            this.imageStageDispatcher.imageStage(3);
        }

        HashMap var4 = new HashMap(var3);
        var4.put("procedureName", "ImageLib");
        var4.put("stage", "onCancel");
        var4.put("requestId", var1);
        var4.put("requestUrl", var2);
        DataLoggerUtils.log("image", var4);
    }

    public void onFinished(String var1, String var2, Map<String, Object> var3) {
        if (!DispatcherManager.isEmpty(this.imageStageDispatcher)) {
            this.imageStageDispatcher.imageStage(1);
        }

        HashMap var4 = new HashMap(var3);
        var4.put("procedureName", "ImageLib");
        var4.put("stage", "onFinished");
        var4.put("requestId", var1);
        var4.put("requestUrl", var2);
        DataLoggerUtils.log("image", var4);
    }

    public void onEvent(String var1, String var2, Map<String, Object> var3) {
        String var4 = null;
        if (var3 != null) {
            var4 = SafeUtils.instanceofString(var3.get("requestUrl"), "");
        }

        HashMap var5 = new HashMap(var3);
        var5.put("procedureName", "ImageLib");
        var5.put("stage", var2);
        var5.put("requestId", var1);
        var5.put("requestUrl", var4);
        DataLoggerUtils.log("image", var5);
    }
}
