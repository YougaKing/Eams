package com.taobao.monitor.impl.data;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: CanvasCalculator */
public class CanvasCalculator implements ICalculator {
    private HashSet<Drawable> a = new HashSet<>();
    private final View b;
    private final View c;
    private boolean f = false;

    public CanvasCalculator(View view, View view2) {
        this.b = view;
        this.c = view2;
    }

    private float a(View view, List<ViewInfo> list) {
        Drawable drawable;
        byte b2;
        byte b3 = 0;
        if (!ViewUtils.a(view, this.c)) {
            return 0.0f;
        }
        if (view.getHeight() < ViewUtils.h / 20) {
            return 1.0f;
        }
        if (view.getVisibility() != 0) {
            return 0.0f;
        }
        if (view instanceof ViewStub) {
            return 0.0f;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup instanceof WebView) {
                if (DefaultWebView.DEFAULT_WEB_VIEW.isWebViewLoadFinished((WebView) viewGroup)) {
                    return 1.0f;
                }
                return 0.0f;
            } else if (!WebViewProxy.INSTANCE.isWebView(viewGroup)) {
                View[] a2 = ViewUtils.a(viewGroup);
                if (a2 == null) {
                    return 0.0f;
                }
                int i = 0;
                int i2 = 0;
                for (View view2 : a2) {
                    if (view2 == null) {
                        break;
                    }
                    i++;
                    ArrayList arrayList = new ArrayList();
                    if (a(view2, arrayList) > 0.8f) {
                        int i3 = i2 + 1;
                        list.add(ViewInfo.a(view2, this.c));
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            ((ViewInfo) it.next()).recycle();
                        }
                        i2 = i3;
                    } else {
                        list.addAll(arrayList);
                    }
                }
                if (view.getHeight() < ViewUtils.h / 8 && (((viewGroup instanceof LinearLayout) || (viewGroup instanceof RelativeLayout)) && i == i2 && i != 0)) {
                    return 1.0f;
                }
                float a3 = new LineTreeCalculator(com.taobao.monitor.impl.util.b.a(30)).a((View) viewGroup, list, this.c);
                if (a3 > 0.8f) {
                    return 1.0f;
                }
                if (view.getWidth() * view.getHeight() <= ((ViewUtils.g / 3) * ViewUtils.h) / 4 && (view.getWidth() < ViewUtils.g / 3 || view.getHeight() < ViewUtils.h / 4)) {
                    ViewInfo a4 = ViewInfo.a(viewGroup, this.c);
                    int i4 = (a4.top + a4.bottom) / 2;
                    int i5 = (a4.right + a4.left) / 2;
                    Iterator it2 = list.iterator();
                    byte b4 = 0;
                    while (true) {
                        byte b5 = b3;
                        if (!it2.hasNext()) {
                            break;
                        }
                        ViewInfo kVar = (ViewInfo) it2.next();
                        if (kVar.top < i4 && i4 < kVar.bottom && kVar.left < i5 && i5 < kVar.right) {
                            return 1.0f;
                        }
                        int i6 = (kVar.top + kVar.bottom) / 2;
                        int i7 = (kVar.right + kVar.left) / 2;
                        if (i4 <= i6) {
                            b3 = (byte) (b5 | 1);
                        } else {
                            b3 = b5;
                        }
                        if (i4 >= i6) {
                            b3 = (byte) (b3 | 2);
                        }
                        if (i5 <= i7) {
                            b2 = (byte) (b4 | 1);
                        } else {
                            b2 = b4;
                        }
                        if (i5 >= i7) {
                            b2 = (byte) (b2 | 2);
                        }
                        if (b3 == 3 && b2 == 3) {
                            return 1.0f;
                        }
                        b4 = b2;
                    }
                }
                return a3;
            } else if (WebViewProxy.INSTANCE.isWebViewLoadFinished(viewGroup)) {
                return 1.0f;
            } else {
                return 0.0f;
            }
        } else if (view instanceof ImageView) {
            Drawable drawable2 = ((ImageView) view).getDrawable();
            if (VERSION.SDK_INT >= 23 && (drawable2 instanceof DrawableWrapper)) {
                drawable2 = ((DrawableWrapper) drawable2).getDrawable();
            }
            if (!a(drawable2) || this.a.contains(drawable2)) {
                Drawable background = view.getBackground();
                if (VERSION.SDK_INT < 23 || !(background instanceof DrawableWrapper)) {
                    drawable = background;
                } else {
                    drawable = ((DrawableWrapper) drawable2).getDrawable();
                }
                if (!a(drawable) || this.a.contains(drawable)) {
                    return 0.0f;
                }
                this.a.add(drawable);
                return 1.0f;
            }
            this.a.add(drawable2);
            return 1.0f;
        } else if (!(view instanceof TextView)) {
            return 1.0f;
        } else {
            if (view instanceof EditText) {
                this.f = view.isFocusable();
                return 1.0f;
            } else if (TextUtils.isEmpty(((TextView) view).getText().toString())) {
                return 0.0f;
            } else {
                return 1.0f;
            }
        }
    }

    private boolean a(Drawable drawable) {
        return (drawable instanceof BitmapDrawable) || (drawable instanceof NinePatchDrawable) || (drawable instanceof AnimationDrawable) || (drawable instanceof ShapeDrawable) || (drawable instanceof PictureDrawable);
    }

    public float a() {
        ArrayList<ViewInfo> arrayList = new ArrayList<>();
        float a2 = a(this.b, arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((ViewInfo) it.next()).recycle();
        }
        this.a.clear();
        if (this.f) {
            return 1.0f;
        }
        return a2;
    }
}
