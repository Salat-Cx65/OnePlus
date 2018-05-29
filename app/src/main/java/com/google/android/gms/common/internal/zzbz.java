package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.util.zzk;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbz extends Button {
    public zzbz(Context context) {
        this(context, null);
    }

    private zzbz(Context context, AttributeSet attributeSet) {
        super(context, null, 16842824);
    }

    private static int zzf(int i, int i2, int i3, int i4) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return i2;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return i3;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return i4;
            default:
                throw new IllegalStateException(new StringBuilder(33).append("Unknown color scheme: ").append(i).toString());
        }
    }

    public final void zza(Resources resources, int i, int i2) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        setMinHeight((int) ((f * 48.0f) + 0.5f));
        setMinWidth((int) ((f * 48.0f) + 0.5f));
        int zzf = zzf(i2, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light);
        int zzf2 = zzf(i2, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light);
        Drawable wrap;
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                wrap = DrawableCompat.wrap(resources.getDrawable(zzf2));
                DrawableCompat.setTintList(wrap, resources.getColorStateList(R.color.common_google_signin_btn_tint));
                DrawableCompat.setTintMode(wrap, Mode.SRC_ATOP);
                setBackgroundDrawable(wrap);
                setTextColor((ColorStateList) zzbr.zzu(resources.getColorStateList(zzf(i2, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
                switch (i) {
                    case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        setText(resources.getString(R.string.common_signin_button_text));
                        break;
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        setText(resources.getString(R.string.common_signin_button_text_long));
                        break;
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        setText(null);
                        break;
                    default:
                        throw new IllegalStateException(new StringBuilder(32).append("Unknown button size: ").append(i).toString());
                }
                setTransformationMethod(null);
                if (zzk.zzaG(getContext())) {
                    setGravity(ConnectionResult.SERVICE_MISSING_PERMISSION);
                }
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zzf2 = zzf;
                wrap = DrawableCompat.wrap(resources.getDrawable(zzf2));
                DrawableCompat.setTintList(wrap, resources.getColorStateList(R.color.common_google_signin_btn_tint));
                DrawableCompat.setTintMode(wrap, Mode.SRC_ATOP);
                setBackgroundDrawable(wrap);
                setTextColor((ColorStateList) zzbr.zzu(resources.getColorStateList(zzf(i2, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
                switch (i) {
                    case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        setText(resources.getString(R.string.common_signin_button_text));
                        break;
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        setText(resources.getString(R.string.common_signin_button_text_long));
                        break;
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        setText(null);
                        break;
                    default:
                        throw new IllegalStateException(new StringBuilder(32).append("Unknown button size: ").append(i).toString());
                }
                setTransformationMethod(null);
                if (zzk.zzaG(getContext())) {
                    setGravity(ConnectionResult.SERVICE_MISSING_PERMISSION);
                }
            default:
                throw new IllegalStateException(new StringBuilder(32).append("Unknown button size: ").append(i).toString());
        }
    }
}
