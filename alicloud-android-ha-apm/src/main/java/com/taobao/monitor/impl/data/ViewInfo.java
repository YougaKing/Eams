package com.taobao.monitor.impl.data;

import android.view.View;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: ViewInfo */
class ViewInfo {
    private static Queue<ViewInfo> a = new LinkedList();
    public int bottom;
    public boolean k;
    public int left;
    public int right;
    public int top;

    ViewInfo() {
    }

    public void recycle() {
        if (a.size() < 100) {
            a.add(this);
        }
    }

    public static ViewInfo a(View view, View view2) {
        ViewInfo kVar = (ViewInfo) a.poll();
        if (kVar == null) {
            kVar = new ViewInfo();
        }
        int[] a2 = ViewUtils.point(view, view2);
        boolean z = view instanceof TextView;
        int max = Math.max(0, a2[0]);
        int min = Math.min(ViewUtils.g, a2[0] + view.getWidth());
        int max2 = Math.max(0, a2[1]);
        int min2 = Math.min(ViewUtils.h, a2[1] + view.getHeight());
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
