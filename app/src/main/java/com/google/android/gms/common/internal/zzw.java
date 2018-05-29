package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.internal.zzbff;

final class zzw extends zzt {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ zzbff zzaHr;

    zzw(Intent intent, zzbff com_google_android_gms_internal_zzbff, int i) {
        this.val$intent = intent;
        this.zzaHr = com_google_android_gms_internal_zzbff;
        this.val$requestCode = i;
    }

    public final void zzrt() {
        if (this.val$intent != null) {
            this.zzaHr.startActivityForResult(this.val$intent, this.val$requestCode);
        }
    }
}
