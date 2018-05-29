package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzbr;

final class zzbfj extends Handler {
    private /* synthetic */ zzbfi zzaEQ;

    public zzbfj(zzbfi com_google_android_gms_internal_zzbfi, Looper looper) {
        this.zzaEQ = com_google_android_gms_internal_zzbfi;
        super(looper);
    }

    public final void handleMessage(Message message) {
        boolean z = true;
        if (message.what != 1) {
            z = false;
        }
        zzbr.zzaf(z);
        this.zzaEQ.zzb((zzbfl) message.obj);
    }
}
