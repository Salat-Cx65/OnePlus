package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;

public final class zzcbn {
    private zzcbo zzaXK;
    private boolean zzuK;

    public zzcbn() {
        this.zzuK = false;
        this.zzaXK = null;
    }

    public final void initialize(Context context) {
        synchronized (this) {
            if (this.zzuK) {
                return;
            }
            try {
                this.zzaXK = zzcbp.asInterface(DynamiteModule.zza(context, DynamiteModule.zzaST, ModuleDescriptor.MODULE_ID).zzcW("com.google.android.gms.flags.impl.FlagProviderImpl"));
                this.zzaXK.init(zzn.zzw(context));
                this.zzuK = true;
            } catch (zzc e) {
                Throwable e2 = e;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", e2);
            } catch (RemoteException e3) {
                e2 = e3;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", e2);
            }
        }
    }

    public final <T> T zzb(zzcbg<T> com_google_android_gms_internal_zzcbg_T) {
        synchronized (this) {
            if (this.zzuK) {
                return com_google_android_gms_internal_zzcbg_T.zza(this.zzaXK);
            }
            T zzdH = com_google_android_gms_internal_zzcbg_T.zzdH();
            return zzdH;
        }
    }
}
