package com.taobao.monitor.impl.trace;


import androidx.fragment.app.Fragment;

public class FragmentLifecycleDispatcher extends AbsDispatcher<FragmentLifecycleDispatcher.LifecycleListener> {
    public FragmentLifecycleDispatcher() {
    }

    public void p(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.a(var1, var2);
            }
        });
    }

    public void q(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.b(var1, var2);
            }
        });
    }

    public void r(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.c(var1, var2);
            }
        });
    }

    public void s(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.d(var1, var2);
            }
        });
    }

    public void t(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.e(var1, var2);
            }
        });
    }

    public void u(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.f(var1, var2);
            }
        });
    }

    public void v(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.g(var1, var2);
            }
        });
    }

    public void w(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.h(var1, var2);
            }
        });
    }

    public void x(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.i(var1, var2);
            }
        });
    }

    public void y(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.j(var1, var2);
            }
        });
    }

    public void z(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.k(var1, var2);
            }
        });
    }

    public void A(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.l(var1, var2);
            }
        });
    }

    public void B(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.m(var1, var2);
            }
        });
    }

    public void C(final Fragment var1, final long var2) {
        this.dispatchRunnable(new AbsDispatcher.DispatcherRunnable<FragmentLifecycleDispatcher.LifecycleListener>() {
            @Override
            public void run(LifecycleListener lifecycleListener) {
                lifecycleListener.n(var1, var2);
            }
        });
    }

    public interface LifecycleListener {
        void a(Fragment var1, long var2);

        void b(Fragment var1, long var2);

        void c(Fragment var1, long var2);

        void d(Fragment var1, long var2);

        void e(Fragment var1, long var2);

        void f(Fragment var1, long var2);

        void g(Fragment var1, long var2);

        void h(Fragment var1, long var2);

        void i(Fragment var1, long var2);

        void j(Fragment var1, long var2);

        void k(Fragment var1, long var2);

        void l(Fragment var1, long var2);

        void m(Fragment var1, long var2);

        void n(Fragment var1, long var2);
    }
}
