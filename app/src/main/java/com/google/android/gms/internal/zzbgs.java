package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import com.android.volley.DefaultRetryPolicy;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbgs extends Drawable implements Callback {
    private int mFrom;
    private boolean zzaGA;
    private boolean zzaGB;
    private int zzaGC;
    private boolean zzaGl;
    private int zzaGq;
    private int zzaGr;
    private int zzaGs;
    private int zzaGt;
    private int zzaGu;
    private boolean zzaGv;
    private zzbgw zzaGw;
    private Drawable zzaGx;
    private Drawable zzaGy;
    private boolean zzaGz;
    private long zzahb;

    public zzbgs(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = zzbgu.zzaGD;
        }
        this.zzaGx = drawable;
        drawable.setCallback(this);
        zzbgw com_google_android_gms_internal_zzbgw = this.zzaGw;
        com_google_android_gms_internal_zzbgw.zzaGF |= drawable.getChangingConfigurations();
        if (drawable2 == null) {
            drawable2 = zzbgu.zzaGD;
        }
        this.zzaGy = drawable2;
        drawable2.setCallback(this);
        com_google_android_gms_internal_zzbgw = this.zzaGw;
        com_google_android_gms_internal_zzbgw.zzaGF |= drawable2.getChangingConfigurations();
    }

    zzbgs(zzbgw com_google_android_gms_internal_zzbgw) {
        this.zzaGq = 0;
        this.zzaGs = 255;
        this.zzaGu = 0;
        this.zzaGl = true;
        this.zzaGw = new zzbgw(com_google_android_gms_internal_zzbgw);
    }

    private final boolean canConstantState() {
        if (!this.zzaGz) {
            boolean z = (this.zzaGx.getConstantState() == null || this.zzaGy.getConstantState() == null) ? false : true;
            this.zzaGA = z;
            this.zzaGz = true;
        }
        return this.zzaGA;
    }

    public final void draw(Canvas canvas) {
        int i;
        Object obj = 1;
        int i2 = 0;
        switch (this.zzaGq) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                this.zzahb = SystemClock.uptimeMillis();
                this.zzaGq = 2;
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                if (this.zzahb >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zzahb)) / ((float) this.zzaGt);
                    if (uptimeMillis < 1.0f) {
                        i = 0;
                    }
                    if (i != 0) {
                        this.zzaGq = 0;
                    }
                    this.zzaGu = (int) ((Math.min(uptimeMillis, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) * ((float) this.zzaGr)) + 0.0f);
                }
                i2 = i;
                break;
            default:
                i2 = i;
                break;
        }
        i = this.zzaGu;
        boolean z = this.zzaGl;
        Drawable drawable = this.zzaGx;
        Drawable drawable2 = this.zzaGy;
        if (i2 != 0) {
            if (!z || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.zzaGs) {
                drawable2.setAlpha(this.zzaGs);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z) {
            drawable.setAlpha(this.zzaGs - i);
        }
        drawable.draw(canvas);
        if (z) {
            drawable.setAlpha(this.zzaGs);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zzaGs);
        }
        invalidateSelf();
    }

    public final int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.zzaGw.mChangingConfigurations) | this.zzaGw.zzaGF;
    }

    public final ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zzaGw.mChangingConfigurations = getChangingConfigurations();
        return this.zzaGw;
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zzaGx.getIntrinsicHeight(), this.zzaGy.getIntrinsicHeight());
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zzaGx.getIntrinsicWidth(), this.zzaGy.getIntrinsicWidth());
    }

    public final int getOpacity() {
        if (!this.zzaGB) {
            this.zzaGC = Drawable.resolveOpacity(this.zzaGx.getOpacity(), this.zzaGy.getOpacity());
            this.zzaGB = true;
        }
        return this.zzaGC;
    }

    public final void invalidateDrawable(Drawable drawable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final Drawable mutate() {
        if (!this.zzaGv && super.mutate() == this) {
            if (canConstantState()) {
                this.zzaGx.mutate();
                this.zzaGy.mutate();
                this.zzaGv = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    protected final void onBoundsChange(Rect rect) {
        this.zzaGx.setBounds(rect);
        this.zzaGy.setBounds(rect);
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void setAlpha(int i) {
        if (this.zzaGu == this.zzaGs) {
            this.zzaGu = i;
        }
        this.zzaGs = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zzaGx.setColorFilter(colorFilter);
        this.zzaGy.setColorFilter(colorFilter);
    }

    public final void startTransition(int i) {
        this.mFrom = 0;
        this.zzaGr = this.zzaGs;
        this.zzaGu = 0;
        this.zzaGt = 250;
        this.zzaGq = 1;
        invalidateSelf();
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final Drawable zzqU() {
        return this.zzaGy;
    }
}
