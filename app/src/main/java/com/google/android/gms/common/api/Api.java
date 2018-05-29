package com.google.android.gms.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zzd;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzg;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzq;
import com.oneplus.lib.preference.Preference;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final String mName;
    private final zzh<?, O> zzaAA;
    private final zzf<?> zzaAB;
    private final zzi<?> zzaAC;
    private final zza<?, O> zzaAz;

    public static interface ApiOptions {

        public static interface HasOptions extends com.google.android.gms.common.api.Api.ApiOptions {
        }

        public static interface NotRequiredOptions extends com.google.android.gms.common.api.Api.ApiOptions {
        }

        public static final class NoOptions implements NotRequiredOptions {
            private NoOptions() {
            }
        }

        public static interface Optional extends HasOptions, NotRequiredOptions {
        }
    }

    public static interface zzb {
    }

    public static class zzc<C extends zzb> {
    }

    public static abstract class zzd<T extends zzb, O> {
        public int getPriority() {
            return Preference.DEFAULT_ORDER;
        }

        public List<Scope> zzn(O o) {
            return Collections.emptyList();
        }
    }

    public static abstract class zza<T extends zze, O> extends zzd<T, O> {
        public abstract T zza(Context context, Looper looper, zzq com_google_android_gms_common_internal_zzq, O o, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener);
    }

    public static interface zze extends zzb {
        void disconnect();

        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

        boolean isConnected();

        boolean isConnecting();

        void zza(zzam com_google_android_gms_common_internal_zzam, Set<Scope> set);

        void zza(zzj com_google_android_gms_common_internal_zzj);

        boolean zzmE();

        Intent zzmF();

        boolean zzmt();

        boolean zzpc();
    }

    public static final class zzf<C extends zze> extends zzc<C> {
    }

    public static interface zzg<T extends IInterface> extends zzb {
        T zzd(IBinder iBinder);

        String zzda();

        String zzdb();
    }

    public static abstract class zzh<T extends zzg, O> extends zzd<T, O> {
    }

    public static final class zzi<C extends zzg> extends zzc<C> {
    }

    public <C extends zze> Api(String str, zza<C, O> com_google_android_gms_common_api_Api_zza_C__O, zzf<C> com_google_android_gms_common_api_Api_zzf_C) {
        zzbr.zzb((Object) com_google_android_gms_common_api_Api_zza_C__O, (Object) "Cannot construct an Api with a null ClientBuilder");
        zzbr.zzb((Object) com_google_android_gms_common_api_Api_zzf_C, (Object) "Cannot construct an Api with a null ClientKey");
        this.mName = str;
        this.zzaAz = com_google_android_gms_common_api_Api_zza_C__O;
        this.zzaAA = null;
        this.zzaAB = com_google_android_gms_common_api_Api_zzf_C;
        this.zzaAC = null;
    }

    public final String getName() {
        return this.mName;
    }

    public final zzd<?, O> zzoZ() {
        return this.zzaAz;
    }

    public final zza<?, O> zzpa() {
        zzbr.zza(this.zzaAz != null, (Object) "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.zzaAz;
    }

    public final zzc<?> zzpb() {
        if (this.zzaAB != null) {
            return this.zzaAB;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }
}
