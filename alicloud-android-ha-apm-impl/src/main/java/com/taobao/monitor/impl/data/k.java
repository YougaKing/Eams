package com.taobao.monitor.impl.data;

import android.view.View;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: ViewInfo */
class k {
    private static Queue<k> a = new LinkedList();
    public int bottom;
    public boolean k;
    public int left;
    public int right;
    public int top;

    k() {
    }

    public void recycle() {
        if (a.size() < 100) {
            a.add(this);
        }
    }

    public static k a(View view, View view2) {
        k kVar = (k) a.poll();
        if (kVar == null) {
            kVar = new k();
        }
        int[] a2 = l.a(view, view2);
        boolean z = view instanceof TextView;
        int max = Math.max(0, a2[0]);
        int min = Math.min(l.g, a2[0] + view.getWidth());
        int max2 = Math.max(0, a2[1]);
        int min2 = Math.min(l.h, a2[1] + view.getHeight());
        kVar.k = z;
        kVar.left = max;
        kVar.right = min;
        kVar.top = max2;
        kVar.bottom = min2;
        return kVar;
    }

    public String toString() {
        return "ViewInfo{top=" + this.top + ", bottom=" + this.bottom + ", left=" + this.left + ", right=" + this.right + '}';
    }
}
