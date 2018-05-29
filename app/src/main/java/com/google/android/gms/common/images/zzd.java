package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzc;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public final class zzd extends zza {
    private WeakReference<OnImageLoadedListener> zzaGp;

    public zzd(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        zzc.zzr(onImageLoadedListener);
        this.zzaGp = new WeakReference(onImageLoadedListener);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzd)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzd com_google_android_gms_common_images_zzd = (zzd) obj;
        OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzaGp.get();
        OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) com_google_android_gms_common_images_zzd.zzaGp.get();
        return onImageLoadedListener2 != null && onImageLoadedListener != null && zzbh.equal(onImageLoadedListener2, onImageLoadedListener) && zzbh.equal(com_google_android_gms_common_images_zzd.zzaGh, this.zzaGh);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaGh});
    }

    protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        if (!z2) {
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzaGp.get();
            if (onImageLoadedListener != null) {
                onImageLoadedListener.onImageLoaded(this.zzaGh.uri, drawable, z3);
            }
        }
    }
}
