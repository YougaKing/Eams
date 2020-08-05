package com.taobao.monitor.impl.data;

import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* compiled from: LineTreeCalculator */
public class g {
    private static final boolean g = Boolean.TRUE.booleanValue();
    private static final boolean h = Boolean.FALSE.booleanValue();
    private final int padding;

    /* compiled from: LineTreeCalculator */
    private static class a {
        private static Queue<a> a = new LinkedList();
        int direction;
        int end;
        int start;
        int x;

        private a() {
        }

        /* access modifiers changed from: private */
        public static a a(int i, int i2, int i3) {
            a aVar = (a) a.poll();
            if (aVar == null) {
                aVar = new a();
            }
            aVar.x = i;
            aVar.start = i2;
            aVar.end = i3;
            return aVar;
        }

        /* access modifiers changed from: private */
        public void recycle() {
            if (a.size() < 100) {
                a.add(this);
            }
        }
    }

    /* compiled from: LineTreeCalculator */
    private static class b implements Comparator<a> {
        private b() {
        }

        /* renamed from: a */
        public int compare(a aVar, a aVar2) {
            if (aVar.x < aVar2.x) {
                return -1;
            }
            if (aVar.x != aVar2.x) {
                return 1;
            }
            if (aVar.direction == aVar2.direction) {
                return 0;
            }
            if (aVar.direction != 0) {
                return 1;
            }
            return -1;
        }
    }

    /* compiled from: LineTreeCalculator */
    private static class c {
        c a = null;
        c b = null;
        int count;
        int e;
        int end;
        int start;

        c(int i, int i2, int i3) {
            if (i > 0) {
                this.e = (i3 - i2) + 1;
            }
            this.count = i;
            this.start = i2;
            this.end = i3;
        }
    }

    public g(int i) {
        this.padding = i;
    }

    public float a(View view, List<k> list, View view2) {
        int i = 0;
        if (list == null || list.size() == 0) {
            return 0.0f;
        }
        int[] a2 = l.a(view, view2);
        int max = Math.max(0, a2[1]);
        int min = Math.min(l.h, a2[1] + view.getHeight());
        int max2 = Math.max(0, a2[0]);
        int min2 = Math.min(l.g, a2[0] + view.getWidth());
        int i2 = min2 - max2 > 0 ? min2 - max2 : 0;
        if (min - max > 0) {
            i = min - max;
        }
        int i3 = i2 * i;
        if (i3 == 0) {
            return 0.0f;
        }
        List<a> a3 = a(max, min, max2, min2, list);
        if (a3.size() == 0) {
            return 0.0f;
        }
        Collections.sort(a3, new b());
        float a4 = (((float) a(max, min, a3)) * 1.0f) / ((float) i3);
        for (a aVar : a3) {
            if (aVar != null) {
                aVar.recycle();
            }
        }
        return a4;
    }

    private int a(int i, int i2, List<a> list) {
        c cVar = new c(0, i, i2);
        int i3 = 0;
        int i4 = 0;
        for (a aVar : list) {
            if (aVar.x > i4) {
                if (cVar.e > 1) {
                    i3 += (aVar.x - i4) * (cVar.e - 1);
                }
                i4 = aVar.x;
            }
            a(cVar, aVar, aVar.direction == 0 ? g : h);
        }
        return i3;
    }

    private List<a> a(int i, int i2, int i3, int i4, List<k> list) {
        ArrayList arrayList = new ArrayList();
        for (k kVar : list) {
            int max = Math.max(i, kVar.top - this.padding);
            int min = Math.min(i2, kVar.bottom + this.padding);
            if (max <= min) {
                a b2 = a.a(kVar.left - this.padding >= i3 ? kVar.left - this.padding : i3, max, min);
                b2.direction = 0;
                int i5 = kVar.right + this.padding;
                if (i5 > i4) {
                    i5 = i4;
                }
                a b3 = a.a(i5, max, min);
                b3.direction = 1;
                arrayList.add(b2);
                arrayList.add(b3);
            }
        }
        return arrayList;
    }

    private void a(c cVar, a aVar, boolean z) {
        int i = cVar.start;
        int i2 = cVar.end;
        if (aVar.start > i || aVar.end < i2) {
            int i3 = (i + i2) / 2;
            if (i3 >= aVar.start) {
                if (cVar.a == null) {
                    cVar.a = new c(cVar.count, cVar.start, i3);
                }
                a(cVar.a, aVar, z);
            }
            if (i3 < aVar.end) {
                if (cVar.b == null) {
                    cVar.b = new c(cVar.count, i3 + 1, cVar.end);
                }
                a(cVar.b, aVar, z);
            }
            cVar.count = a(cVar);
            if (cVar.count > 0) {
                cVar.e = (i2 - i) + 1;
                return;
            }
            cVar.e = 0;
            if (cVar.a != null) {
                cVar.e += cVar.a.e;
            }
            if (cVar.b != null) {
                cVar.e += cVar.b.e;
                return;
            }
            return;
        }
        if (z) {
            cVar.count++;
        } else {
            cVar.count--;
        }
        if (cVar.a != null) {
            a(cVar.a, aVar, z);
        }
        if (cVar.b != null) {
            a(cVar.b, aVar, z);
        }
        if (cVar.count > 0) {
            cVar.e = (i2 - i) + 1;
            return;
        }
        cVar.e = 0;
        if (cVar.a != null) {
            cVar.e += cVar.a.e;
        }
        if (cVar.b != null) {
            cVar.e += cVar.b.e;
        }
    }

    private int a(c cVar) {
        c cVar2 = cVar.a;
        c cVar3 = cVar.b;
        return Math.min(cVar2 == null ? cVar.count : cVar2.count, cVar3 == null ? cVar.count : cVar3.count);
    }
}
