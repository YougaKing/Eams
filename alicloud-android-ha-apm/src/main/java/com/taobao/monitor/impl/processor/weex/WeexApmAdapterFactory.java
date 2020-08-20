package com.taobao.monitor.impl.processor.weex;

import com.taobao.monitor.impl.common.DynamicConstants;
import com.taobao.monitor.impl.common.Global;
import com.taobao.monitor.performance.IApmAdapterFactory;
import com.taobao.monitor.performance.IWXApmAdapter;
import java.util.Map;

/* compiled from: WeexApmAdapterFactory */
public class WeexApmAdapterFactory implements IApmAdapterFactory {
    private IWXApmAdapter a = new IWXApmAdapter() {
        public void onStart(String str) {
        }

        public void onEnd() {
        }

        public void onEvent(String str, Object obj) {
        }

        public void onStage(String str, long j) {
        }

        public void addProperty(String str, Object obj) {
        }

        public void addStatistic(String str, double d) {
        }

        public void addBiz(String str, Map<String, Object> map) {
        }

        public void addBizAbTest(String str, Map<String, Object> map) {
        }

        public void addBizStage(String str, Map<String, Object> map) {
        }

        public void onStart() {
        }

        public void onStop() {
        }
    };

    /* renamed from: com.taobao.monitor.impl.processor.a.a$a reason: collision with other inner class name */
    /* compiled from: WeexApmAdapterFactory */
    private static class C0001a implements IWXApmAdapter {
        /* access modifiers changed from: private */
        public final IWXApmAdapter b;

        private C0001a(IWXApmAdapter iWXApmAdapter) {
            this.b = iWXApmAdapter;
        }

        public void onStart(final String str) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.onStart(str);
                }
            });
        }

        public void onEnd() {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.onEnd();
                }
            });
        }

        public void onEvent(final String str, final Object obj) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.onEvent(str, obj);
                }
            });
        }

        public void onStage(final String str, final long j) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.onStage(str, j);
                }
            });
        }

        public void addProperty(final String str, final Object obj) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.addProperty(str, obj);
                }
            });
        }

        public void addStatistic(final String str, final double d) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.addStatistic(str, d);
                }
            });
        }

        public void addBiz(final String str, final Map<String, Object> map) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.addBiz(str, map);
                }
            });
        }

        public void addBizAbTest(final String str, final Map<String, Object> map) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.addBizAbTest(str, map);
                }
            });
        }

        public void addBizStage(final String str, final Map<String, Object> map) {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.addBizStage(str, map);
                }
            });
        }

        public void onStart() {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.onStart();
                }
            });
        }

        public void onStop() {
            c(new Runnable() {
                public void run() {
                    C0001a.this.b.onStop();
                }
            });
        }

        private void c(Runnable runnable) {
            Global.instance().handler().post(runnable);
        }
    }

    public IWXApmAdapter createApmAdapter() {
        return createApmAdapterByType("weex_page");
    }

    public IWXApmAdapter createApmAdapterByType(String type) {
        return new C0001a(DynamicConstants.needWeex ? new WeexProcessor(type) : this.a);
    }
}
