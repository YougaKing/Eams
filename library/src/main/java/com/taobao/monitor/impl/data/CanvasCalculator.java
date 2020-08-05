package com.taobao.monitor.impl.data;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
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


public class CanvasCalculator implements ICalculator {
    private final View b;
    private final View c;
    private HashSet<Drawable> a = new HashSet();
    private boolean f = false;

    public CanvasCalculator(View var1, View var2) {
        this.b = var1;
        this.c = var2;
    }

    private float a(View var1, List<ViewInfo> var2) {
        if (!ViewUtils.a(var1, this.c)) {
            return 0.0F;
        } else if (var1.getHeight() < ViewUtils.h / 20) {
            return 1.0F;
        } else if (var1.getVisibility() != 0) {
            return 0.0F;
        } else if (var1 instanceof ViewStub) {
            return 0.0F;
        } else if (!(var1 instanceof ViewGroup)) {
            if (var1 instanceof ImageView) {
                Drawable var18 = ((ImageView)var1).getDrawable();
                if (Build.VERSION.SDK_INT >= 23 && var18 instanceof DrawableWrapper) {
                    var18 = ((DrawableWrapper)var18).getDrawable();
                }

                boolean var19 = this.a(var18);
                if (var19 && !this.a.contains(var18)) {
                    this.a.add(var18);
                    return 1.0F;
                } else {
                    Drawable var20 = var1.getBackground();
                    if (Build.VERSION.SDK_INT >= 23 && var20 instanceof DrawableWrapper) {
                        var20 = ((DrawableWrapper)var18).getDrawable();
                    }

                    boolean var21 = this.a(var20);
                    if (var21 && !this.a.contains(var20)) {
                        this.a.add(var20);
                        return 1.0F;
                    } else {
                        return 0.0F;
                    }
                }
            } else if (var1 instanceof TextView) {
                if (var1 instanceof EditText) {
                    this.f = var1.isFocusable();
                    return 1.0F;
                } else {
                    return TextUtils.isEmpty(((TextView)var1).getText().toString()) ? 0.0F : 1.0F;
                }
            } else {
                return 1.0F;
            }
        } else {
            ViewGroup var3 = (ViewGroup)var1;
            if (var3 instanceof WebView) {
                return com.taobao.monitor.impl.data.gc.a.isWebViewLoadFinished((WebView)var3) ? 1.0F : 0.0F;
            } else if (WebViewProxy.INSTANCE.isWebView(var3)) {
                return WebViewProxy.INSTANCE.isWebViewLoadFinished(var3) ? 1.0F : 0.0F;
            } else {
                View[] var4 = l.a(var3);
                if (var4 == null) {
                    return 0.0F;
                } else {
                    int var5 = 0;
                    int var6 = 0;
                    View[] var7 = var4;
                    int var8 = var4.length;

                    Iterator var14;
                    k var15;
                    for(int var9 = 0; var9 < var8; ++var9) {
                        View var10 = var7[var9];
                        if (var10 == null) {
                            break;
                        }

                        ++var6;
                        ArrayList var11 = new ArrayList();
                        float var12 = this.a(var10, var11);
                        if (var12 > 0.8F) {
                            ++var5;
                            k var13 = k.a(var10, this.c);
                            var2.add(var13);
                            var14 = var11.iterator();

                            while(var14.hasNext()) {
                                var15 = (k)var14.next();
                                var15.recycle();
                            }
                        } else {
                            var2.addAll(var11);
                        }
                    }

                    if (var1.getHeight() < l.h / 8 && (var3 instanceof LinearLayout || var3 instanceof RelativeLayout) && var6 == var5 && var6 != 0) {
                        return 1.0F;
                    } else {
                        g var22 = new g(com.taobao.monitor.impl.util.b.a(30));
                        float var23 = var22.a(var3, var2, this.c);
                        if (var23 > 0.8F) {
                            return 1.0F;
                        } else {
                            if (var1.getWidth() * var1.getHeight() <= l.g / 3 * l.h / 4 && (var1.getWidth() < l.g / 3 || var1.getHeight() < l.h / 4)) {
                                k var24 = k.a(var3, this.c);
                                int var25 = (var24.top + var24.bottom) / 2;
                                int var26 = (var24.left + var24.right) / 2;
                                byte var27 = 0;
                                byte var28 = 0;
                                var14 = var2.iterator();

                                while(var14.hasNext()) {
                                    var15 = (k)var14.next();
                                    if (var15.top < var25 && var25 < var15.bottom && var15.left < var26 && var26 < var15.right) {
                                        return 1.0F;
                                    }

                                    int var16 = (var15.top + var15.bottom) / 2;
                                    int var17 = (var15.left + var15.right) / 2;
                                    if (var25 <= var16) {
                                        var27 = (byte)(var27 | 1);
                                    }

                                    if (var25 >= var16) {
                                        var27 = (byte)(var27 | 2);
                                    }

                                    if (var26 <= var17) {
                                        var28 = (byte)(var28 | 1);
                                    }

                                    if (var26 >= var17) {
                                        var28 = (byte)(var28 | 2);
                                    }

                                    if (var27 == 3 && var28 == 3) {
                                        return 1.0F;
                                    }
                                }
                            }

                            return var23;
                        }
                    }
                }
            }
        }
    }

    private boolean a(Drawable var1) {
        return var1 instanceof BitmapDrawable || var1 instanceof NinePatchDrawable || var1 instanceof AnimationDrawable || var1 instanceof ShapeDrawable || var1 instanceof PictureDrawable;
    }

    public float a() {
        ArrayList var1 = new ArrayList();
        float var2 = this.a(this.b, var1);
        Iterator var3 = var1.iterator();

        while(var3.hasNext()) {
            k var4 = (k)var3.next();
            var4.recycle();
        }

        this.a.clear();
        return this.f ? 1.0F : var2;
    }
}