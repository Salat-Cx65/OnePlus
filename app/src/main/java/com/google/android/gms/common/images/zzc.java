package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.internal.zzbgs;
import com.google.android.gms.internal.zzbgx;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper.Callback;
import java.lang.ref.WeakReference;

public final class zzc extends zza {
    private WeakReference<ImageView> zzaGo;

    public zzc(ImageView imageView, int i) {
        super(null, i);
        com.google.android.gms.common.internal.zzc.zzr(imageView);
        this.zzaGo = new WeakReference(imageView);
    }

    public zzc(ImageView imageView, Uri uri) {
        super(uri, 0);
        com.google.android.gms.common.internal.zzc.zzr(imageView);
        this.zzaGo = new WeakReference(imageView);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ImageView imageView = (ImageView) this.zzaGo.get();
        ImageView imageView2 = (ImageView) ((zzc) obj).zzaGo.get();
        return (imageView2 == null || imageView == null || !zzbh.equal(imageView2, imageView)) ? false : true;
    }

    public final int hashCode() {
        return 0;
    }

    protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        Uri uri = null;
        ImageView imageView = (ImageView) this.zzaGo.get();
        if (imageView != null) {
            Object obj;
            Drawable drawable2;
            if (z2 || z3) {
                obj = null;
            } else {
                int i = 1;
            }
            if (obj != null && (imageView instanceof zzbgx)) {
                int zzqW = ((zzbgx) imageView).zzqW();
                if (this.zzaGj != 0 && zzqW == this.zzaGj) {
                    return;
                }
            }
            boolean zzc = zzc(z, z2);
            if (zzc) {
                drawable2 = imageView.getDrawable();
                if (drawable2 == null) {
                    drawable2 = null;
                } else if (drawable2 instanceof zzbgs) {
                    drawable2 = ((zzbgs) drawable2).zzqU();
                }
                drawable2 = new zzbgs(drawable2, drawable);
            } else {
                drawable2 = drawable;
            }
            imageView.setImageDrawable(drawable2);
            if (imageView instanceof zzbgx) {
                zzbgx com_google_android_gms_internal_zzbgx = (zzbgx) imageView;
                if (z3) {
                    uri = this.zzaGh.uri;
                }
                com_google_android_gms_internal_zzbgx.zzo(uri);
                com_google_android_gms_internal_zzbgx.zzax(obj != null ? this.zzaGj : 0);
            }
            if (zzc) {
                ((zzbgs) drawable2).startTransition(Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            }
        }
    }
}
