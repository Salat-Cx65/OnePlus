package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;

final class zzbgw extends ConstantState {
    int mChangingConfigurations;
    int zzaGF;

    zzbgw(zzbgw com_google_android_gms_internal_zzbgw) {
        if (com_google_android_gms_internal_zzbgw != null) {
            this.mChangingConfigurations = com_google_android_gms_internal_zzbgw.mChangingConfigurations;
            this.zzaGF = com_google_android_gms_internal_zzbgw.zzaGF;
        }
    }

    public final int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public final Drawable newDrawable() {
        return new zzbgs(this);
    }
}
