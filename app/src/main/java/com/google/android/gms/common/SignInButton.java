package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzby;
import com.google.android.gms.common.internal.zzbz;
import com.google.android.gms.dynamic.zzq;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton extends FrameLayout implements OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int mColor;
    private int mSize;
    private View zzaAx;
    private OnClickListener zzaAy;

    @Retention(RetentionPolicy.SOURCE)
    public static @interface ButtonSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    public static @interface ColorScheme {
    }

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzaAy = null;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SignInButton, SIZE_STANDARD, SIZE_STANDARD);
        this.mSize = obtainStyledAttributes.getInt(R.styleable.SignInButton_buttonSize, SIZE_STANDARD);
        this.mColor = obtainStyledAttributes.getInt(R.styleable.SignInButton_colorScheme, SIZE_ICON_ONLY);
        obtainStyledAttributes.recycle();
        int i2 = this.mSize;
        obtainStyledAttributes = this.mColor;
        setStyle(i2, obtainStyledAttributes);
    }

    public final void onClick(View view) {
        if (this.zzaAy != null && view == this.zzaAx) {
            this.zzaAy.onClick(this);
        }
    }

    public final void setColorScheme(int i) {
        setStyle(this.mSize, i);
    }

    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.zzaAx.setEnabled(z);
    }

    public final void setOnClickListener(OnClickListener onClickListener) {
        this.zzaAy = onClickListener;
        if (this.zzaAx != null) {
            this.zzaAx.setOnClickListener(this);
        }
    }

    @Deprecated
    public final void setScopes(Scope[] scopeArr) {
        setStyle(this.mSize, this.mColor);
    }

    public final void setSize(int i) {
        setStyle(i, this.mColor);
    }

    public final void setStyle(int i, int i2) {
        this.mSize = i;
        this.mColor = i2;
        Context context = getContext();
        if (this.zzaAx != null) {
            removeView(this.zzaAx);
        }
        try {
            this.zzaAx = zzby.zzc(context, this.mSize, this.mColor);
        } catch (zzq e) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            int i3 = this.mSize;
            int i4 = this.mColor;
            View com_google_android_gms_common_internal_zzbz = new zzbz(context);
            com_google_android_gms_common_internal_zzbz.zza(context.getResources(), i3, i4);
            this.zzaAx = com_google_android_gms_common_internal_zzbz;
        }
        addView(this.zzaAx);
        this.zzaAx.setEnabled(isEnabled());
        this.zzaAx.setOnClickListener(this);
    }

    @Deprecated
    public final void setStyle(int i, int i2, Scope[] scopeArr) {
        setStyle(i, i2);
    }
}
