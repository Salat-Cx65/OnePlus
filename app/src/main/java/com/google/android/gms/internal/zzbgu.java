package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.v7.widget.ListPopupWindow;

final class zzbgu extends Drawable {
    private static final zzbgu zzaGD;
    private static final zzbgv zzaGE;

    static {
        zzaGD = new zzbgu();
        zzaGE = new zzbgv();
    }

    private zzbgu() {
    }

    public final void draw(Canvas canvas) {
    }

    public final ConstantState getConstantState() {
        return zzaGE;
    }

    public final int getOpacity() {
        return ListPopupWindow.WRAP_CONTENT;
    }

    public final void setAlpha(int i) {
    }

    public final void setColorFilter(ColorFilter colorFilter) {
    }
}