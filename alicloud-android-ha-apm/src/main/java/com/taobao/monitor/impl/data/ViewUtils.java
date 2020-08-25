package com.taobao.monitor.impl.data;

import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import com.taobao.monitor.impl.common.Global;
import java.lang.reflect.Field;

/* compiled from: ViewUtils */
class ViewUtils {
    private static Field mChildrenField;
    static int mWidth;
    static int mHeight;

    static {
        Display defaultDisplay = ((WindowManager) Global.instance().context().getSystemService("window")).getDefaultDisplay();
        mHeight = defaultDisplay.getHeight();
        mWidth = defaultDisplay.getWidth();
        try {
            mChildrenField = ViewGroup.class.getDeclaredField("mChildren");
            mChildrenField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static View[] getChildrenView(ViewGroup viewGroup) {
        try {
            return (View[]) mChildrenField.get(viewGroup);
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

    static boolean isAvailable(View view, View parentView) {
        int[] location = topAndLeft(view, parentView);
        int top = location[1];
        int height = location[1] + view.getHeight();
        int left = location[0];
        int width = location[0] + view.getWidth();
        if (top > mHeight || height < 0 || width < 0 || left > mWidth || height - top <= 0) {
            return false;
        }
        return true;
    }

    /* renamed from: a reason: collision with other method in class */
    static int[] topAndLeft(View view, View parentView) {
        int[] iArr = {0, 0};
        while (view != parentView) {
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
