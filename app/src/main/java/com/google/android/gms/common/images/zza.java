package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbgy;

public abstract class zza {
    final zzb zzaGh;
    private int zzaGi;
    protected int zzaGj;
    private boolean zzaGk;
    private boolean zzaGl;
    private boolean zzaGm;
    private boolean zzaGn;

    public zza(Uri uri, int i) {
        this.zzaGi = 0;
        this.zzaGj = 0;
        this.zzaGk = false;
        this.zzaGl = true;
        this.zzaGm = false;
        this.zzaGn = true;
        this.zzaGh = new zzb(uri);
        this.zzaGj = i;
    }

    final void zza(Context context, Bitmap bitmap, boolean z) {
        zzc.zzr(bitmap);
        zza(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    final void zza(Context context, zzbgy com_google_android_gms_internal_zzbgy) {
        if (this.zzaGn) {
            zza(null, false, true, false);
        }
    }

    final void zza(Context context, zzbgy com_google_android_gms_internal_zzbgy, boolean z) {
        Drawable drawable = null;
        if (this.zzaGj != 0) {
            drawable = context.getResources().getDrawable(this.zzaGj);
        }
        zza(drawable, z, false, false);
    }

    protected abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);

    protected final boolean zzc(boolean z, boolean z2) {
        return (!this.zzaGl || z2 || z) ? false : true;
    }
}
