package com.taobao.monitor.impl.processor.fragmentload;

import androidx.fragment.app.Fragment;

import com.taobao.monitor.impl.data.GlobalStats;
import com.taobao.monitor.impl.processor.IProcessorFactory;
import com.taobao.monitor.impl.trace.FragmentLifecycleDispatcher;

import java.util.HashMap;
import java.util.Map;

/* compiled from: FragmentModelLifecycle */
public class FragmentModelLifecycle implements FragmentLifecycleDispatcher.LifecycleListener {
    private Fragment mFragment;

    /* renamed from: a reason: collision with other field name */
    private final IProcessorFactory<FragmentProcessor> f48a = new FragmentProcessorFactory();

    /* renamed from: a reason: collision with other field name */
    private Map<Fragment, b> f49a = new HashMap();
    private final IProcessorFactory<b> b = new FragmentPopProcessorFactory();
    private int k = 0;
    private Map<Fragment, ModelLifecycleListener> map = new HashMap();

    /* renamed from: com.taobao.monitor.impl.processor.fragmentload.a$a reason: collision with other inner class name */
    /* compiled from: FragmentModelLifecycle */
    public interface ModelLifecycleListener {
        void onFragmentPreAttached(Fragment fragment, long j);

        void onFragmentAttached(Fragment fragment, long j);

        void onFragmentPreCreated(Fragment fragment, long j);

        void onFragmentCreated(Fragment fragment, long j);

        void onFragmentActivityCreated(Fragment fragment, long j);

        void onFragmentViewCreated(Fragment fragment, long j);

        void g(Fragment fragment, long j);

        void onFragmentResumed(Fragment fragment, long j);

        void onFragmentPaused(Fragment fragment, long j);

        void j(Fragment fragment, long j);

        void onFragmentSaveInstanceState(Fragment fragment, long j);

        void onFragmentViewDestroyed(Fragment fragment, long j);

        void onFragmentDestroyed(Fragment fragment, long j);

        void onFragmentDetached(Fragment fragment, long j);
    }

    /* compiled from: FragmentModelLifecycle */
    public interface b {
        void onFragmentStarted(Fragment fragment);

        void onFragmentStopped(Fragment fragment);
    }

    public void onFragmentPreAttached(Fragment fragment, long j) {
        GlobalStats.activityStatusManager.b(fragment.getClass().getName());
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.f48a.createProcessor();
        if (aVar != null) {
            this.map.put(fragment, aVar);
            aVar.onFragmentPreAttached(fragment, j);
            this.mFragment = fragment;
        }
    }

    public void onFragmentAttached(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentAttached(fragment, j);
        }
    }

    public void onFragmentPreCreated(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentPreCreated(fragment, j);
        }
    }

    public void onFragmentCreated(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentCreated(fragment, j);
        }
    }

    public void onFragmentActivityCreated(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentActivityCreated(fragment, j);
        }
    }

    public void onFragmentViewCreated(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentViewCreated(fragment, j);
        }
    }

    public void onFragmentStarted(Fragment fragment, long j) {
        this.k++;
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.g(fragment, j);
        }
        if (this.mFragment != fragment && FragmentInterceptorProxy.INSTANCE.needPopFragment(fragment)) {
            b bVar = (b) this.b.createProcessor();
            if (bVar != null) {
                bVar.onFragmentStarted(fragment);
                this.f49a.put(fragment, bVar);
            }
        }
        this.mFragment = fragment;
    }

    public void onFragmentResumed(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentResumed(fragment, j);
        }
    }

    public void onFragmentPaused(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentPaused(fragment, j);
        }
    }

    public void onFragmentStopped(Fragment fragment, long j) {
        this.k--;
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.j(fragment, j);
        }
        b bVar = (b) this.f49a.get(fragment);
        if (bVar != null) {
            bVar.onFragmentStopped(fragment);
            this.f49a.remove(fragment);
        }
        if (this.k == 0) {
            this.mFragment = null;
        }
    }

    public void onFragmentSaveInstanceState(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentSaveInstanceState(fragment, j);
        }
    }

    public void onFragmentViewDestroyed(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentViewDestroyed(fragment, j);
        }
    }

    public void onFragmentDestroyed(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentDestroyed(fragment, j);
        }
    }

    public void onFragmentDetached(Fragment fragment, long j) {
        ModelLifecycleListener aVar = (ModelLifecycleListener) this.map.get(fragment);
        if (aVar != null) {
            aVar.onFragmentDetached(fragment, j);
        }
        this.map.remove(fragment);
    }
}
