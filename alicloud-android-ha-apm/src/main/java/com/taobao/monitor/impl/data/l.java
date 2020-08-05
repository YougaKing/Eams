package com.taobao.monitor.impl.data;

import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import com.taobao.monitor.impl.common.Global;
import java.lang.reflect.Field;

/* compiled from: ViewUtils */
class l {
    private static Field a;
    static int g;
    static int h;

    static {
        Display defaultDisplay = ((WindowManager) Global.instance().context().getSystemService("window")).getDefaultDisplay();
        h = defaultDisplay.getHeight();
        g = defaultDisplay.getWidth();
        try {
            a = ViewGroup.class.getDeclaredField("mChildren");
            a.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static View[] a(ViewGroup viewGroup) {
        try {
            return (View[]) a.get(viewGroup);
        } catch (Throwable th) {
            int childCount = viewGroup.getChildCount();
            if (childCount <= 0) {
                return null;
            }
            View[] viewArr = new View[childCount];
            for (int i = 0; i < childCount; i++) {
                viewArr[i] = viewGroup.getChildAt(i);
            }
            return viewArr;
        }
    }

    static boolean a(View view, View view2) {
        int[] a2 = a(view, view2);
        int i = a2[1];
        int height = a2[1] + view.getHeight();
        int i2 = a2[0];
        int width = a2[0] + view.getWidth();
        if (i > h || height < 0 || width < 0 || i2 > g || height - i <= 0) {
            return false;
        }
        return true;
    }

    /* renamed from: a reason: collision with other method in class */
    static int[] m5a(View view, View view2) {
        int[] iArr = {0, 0};
        while (view != view2) {
            iArr[1] = iArr[1] + view.getTop();
            iArr[0] = iArr[0] + view.getLeft();
            ViewParent parent = view.getParent();
            if (!(parent instanceof View)) {
                break;
            }
            view = (View) parent;
        }
        return iArr;
    }
}
