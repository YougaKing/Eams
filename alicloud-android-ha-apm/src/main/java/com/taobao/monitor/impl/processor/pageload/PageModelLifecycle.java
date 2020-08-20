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
        void onActivityStarted(Activity activity, long timeMillis);

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

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle, long timeMillis) {
        PageLoadProcessor cVar = (PageLoadProcessor) this.mPageLoadProcessorFactory.createProcessor();
        if (cVar != null) {
            this.mModelLifecycleListenerMap.put(activity, cVar);
            cVar.a(activity, bundle, timeMillis);
        }
        this.mActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity, long timeMillis) {
        this.v++;
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityStarted(activity, timeMillis);
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

    @Override
    public void onActivityResumed(Activity activity, long timeMillis) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityResumed(activity, timeMillis);
        }
    }

    @Override
    public void onActivityPaused(Activity activity, long timeMillis) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityPaused(activity, timeMillis);
        }
    }

    @Override
    public void onActivityStopped(Activity activity, long timeMillis) {
        this.v--;
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityStopped(activity, timeMillis);
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

    @Override
    public void onActivityDestroyed(Activity activity, long timeMillis) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.mModelLifecycleListenerMap.get(activity);
        if (aVar != null) {
            aVar.onActivityDestroyed(activity, timeMillis);
        }
        this.mModelLifecycleListenerMap.remove(activity);
        if (activity == this.mActivity) {
            this.mActivity = null;
        }
    }
}
