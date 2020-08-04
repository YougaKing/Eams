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
    static int g;
    static int h;
    private static Field a;

    static View[] a(ViewGroup var0) {
        View[] var1 = null;

        try {
            var1 = (View[]) ((View[]) a.get(var0));
            return var1;
        } catch (Throwable var4) {
            int var2 = var0.getChildCount();
            if (var2 > 0) {
                var1 = new View[var2];

                for (int var3 = 0; var3 < var2; ++var3) {
                    var1[var3] = var0.getChildAt(var3);
                }

                return var1;
            } else {
                return null;
            }
        }
    }

    static boolean a(View var0, View var1) {
        int[] var2 = originPoint(var0, var1);
        int var3 = var2[1];
        int var4 = var2[1] + var0.getHeight();
        int var5 = var2[0];
        int var6 = var2[0] + var0.getWidth();
        return var3 <= h && var4 >= 0 && var6 >= 0 && var5 <= g && var4 - var3 > 0;
    }

    static int[] originPoint(View var0, View var1) {
        int[] var2 = new int[]{0, 0};

        ViewParent viewParent;
        for (View var3 = var0; var3 != var1; var3 = (View) viewParent) {
            var2[1] += var3.getTop();
            var2[0] += var3.getLeft();
            viewParent = var3.getParent();
            if (!(viewParent instanceof View)) {
                break;
            }
        }

        return var2;
    }

    static {
        Display var0 = ((WindowManager) Global.instance().context().getSystemService("window")).getDefaultDisplay();
        h = var0.getHeight();
        g = var0.getWidth();

        try {
            a = ViewGroup.class.getDeclaredField("mChildren");
            a.setAccessible(true);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
