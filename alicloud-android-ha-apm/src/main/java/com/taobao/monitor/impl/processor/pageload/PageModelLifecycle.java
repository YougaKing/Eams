package com.taobao.monitor.impl.processor.pageload;

import android.app.Activity;
import android.os.Bundle;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.trace.ActivityLifeCycleDispatcher;

import java.util.HashMap;
import java.util.Map;

/* compiled from: PageModelLifecycle */
public class PageModelLifecycle implements ActivityLifeCycleDispatcher.LifeCycleListener {
    private Activity mActivity = null;

    /* renamed from: a reason: collision with other field name */
    private Map<Activity, ModelPairLifecycleListener> mModelPairLifecycleListenerMap = new HashMap();
    private final IProcessorFactory<PageLoadProcessor> mPageLoadProcessorFactory = new PageLoadProcessorFactory();
    private final IProcessorFactory<PageLoadPopProcessor> mPageLoadPopProcessorFactory = new PageLoadPopProcessorFactory();
    private Map<Activity, ModelLifecycleListener> mModelLifecycleListenerMap = new HashMap();
    private int v = 0;

    /* compiled from: PageModelLifecycle */
    public interface ModelLifecycleListener {
        void onActivityStarted(Activity activity, long j);

        void onActivityResumed(Activity activity, long j);

        void onActivityPaused(Activity activity, long j);

        void onActivityStopped(Activity activity, long j);

        void onActivityDestroyed(Activity activity, long j);
    }

    /* compiled from: PageModelLifecycle */
    public interface ModelPairLifecycleListener {
        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);
    }

    public void onActivityCreated(Activity activity, Bundle bundle, long j) {
        PageLoadProcessor cVar = (PageLoadProcessor) this.mPageLoadProcessorFactory.createProcessor();
        if (cVar != null) {
            this.mModelLifecycleListenerMap.put(activity, cVar);
            cVar.a(activity, bundle, j);
        }
        this.mActivity = activity;
    }

    public void onActivityStarted(Activity activity, long j) {
        this.v++;
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityStarted(activity, j);
        }
        if (this.mActivity != activity) {
            ModelPairLifecycleListener bVar = (ModelPairLifecycleListener) this.mPageLoadPopProcessorFactory.createProcessor();
            if (bVar != null) {
                bVar.onActivityStarted(activity);
                this.mModelPairLifecycleListenerMap.put(activity, bVar);
            }
        }
        this.mActivity = activity;
    }

    public void onActivityResumed(Activity activity, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityResumed(activity, j);
        }
    }

    public void onActivityPaused(Activity activity, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityPaused(activity, j);
        }
    }

    public void onActivityStopped(Activity activity, long j) {
        this.v--;
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityStopped(activity, j);
        }
        ModelPairLifecycleListener bVar = (ModelPairLifecycleListener) this.mModelPairLifecycleListenerMap.get(activity);
        if (bVar != null) {
            bVar.onActivityStopped(activity);
            this.mModelPairLifecycleListenerMap.remove(activity);
        }
        if (this.v == 0) {
            this.mActivity = null;
        }
    }

    public void onActivityDestroyed(Activity activity, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityDestroyed(activity, j);
        }
        this.mModelLifecycleListenerMap.remove(activity);
        if (activity == this.mActivity) {
            this.mActivity = null;
        }
    }
}
