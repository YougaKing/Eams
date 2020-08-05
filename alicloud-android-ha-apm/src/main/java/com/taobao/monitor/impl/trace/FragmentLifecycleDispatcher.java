package com.taobao.monitor.impl.trace;


import androidx.fragment.app.Fragment;

/* compiled from: FragmentLifecycleDispatcher */
public class FragmentLifecycleDispatcher extends AbsDispatcher<FragmentLifecycleDispatcher.LifecycleListener> {

    /* compiled from: FragmentLifecycleDispatcher */
    public interface LifecycleListener {
        void onFragmentPreAttached(Fragment fragment, long j);

        void onFragmentAttached(Fragment fragment, long j);

        void onFragmentPreCreated(Fragment fragment, long j);

        void onFragmentCreated(Fragment fragment, long j);

        void onFragmentActivityCreated(Fragment fragment, long j);

        void onFragmentViewCreated(Fragment fragment, long j);

        void onFragmentStarted(Fragment fragment, long j);

        void onFragmentResumed(Fragment fragment, long j);

        void onFragmentPaused(Fragment fragment, long j);

        void onFragmentStopped(Fragment fragment, long j);

        void onFragmentSaveInstanceState(Fragment fragment, long j);

        void onFragmentViewDestroyed(Fragment fragment, long j);

        void onFragmentDestroyed(Fragment fragment, long j);

        void onFragmentDetached(Fragment fragment, long j);
    }

    public void onFragmentPreAttached(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentPreAttached(fragment, j);
            }
        });
    }

    public void onFragmentAttached(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentAttached(fragment, j);
            }
        });
    }

    public void onFragmentPreCreated(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentPreCreated(fragment, j);
            }
        });
    }

    public void onFragmentCreated(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentCreated(fragment, j);
            }
        });
    }

    public void onFragmentActivityCreated(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentActivityCreated(fragment, j);
            }
        });
    }

    public void onFragmentViewCreated(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentViewCreated(fragment, j);
            }
        });
    }

    public void onFragmentStarted(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentStarted(fragment, j);
            }
        });
    }

    public void onFragmentResumed(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentResumed(fragment, j);
            }
        });
    }

    public void onFragmentPaused(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentPaused(fragment, j);
            }
        });
    }

    public void onFragmentStopped(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentStopped(fragment, j);
            }
        });
    }

    public void onFragmentSaveInstanceState(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentSaveInstanceState(fragment, j);
            }
        });
    }

    public void onFragmentViewDestroyed(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentViewDestroyed(fragment, j);
            }
        });
    }

    public void onFragmentDestroyed(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentDestroyed(fragment, j);
            }
        });
    }

    public void onFragmentDetached(final Fragment fragment, final long j) {
        dispatchRunnable(new DispatcherRunnable<LifecycleListener>() {
            /* renamed from: a */
            public void run(LifecycleListener aVar) {
                aVar.onFragmentDetached(fragment, j);
            }
        });
    }
}
