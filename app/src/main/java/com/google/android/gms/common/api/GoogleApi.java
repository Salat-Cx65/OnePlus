package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbce;
import com.google.android.gms.internal.zzbcf;
import com.google.android.gms.internal.zzbck;
import com.google.android.gms.internal.zzbdi;
import com.google.android.gms.internal.zzben;
import com.google.android.gms.internal.zzbep;
import com.google.android.gms.internal.zzbev;
import com.google.android.gms.internal.zzbfv;
import com.google.android.gms.internal.zzbfy;
import com.google.android.gms.internal.zzbgc;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class GoogleApi<O extends ApiOptions> {
    private final Context mContext;
    private final int mId;
    private final O zzaAL;
    private final zzbcf<O> zzaAM;
    private final GoogleApiClient zzaAN;
    private final zzbfy zzaAO;
    protected final zzben zzaAP;
    private final Account zzajd;
    private final Api<O> zzayY;
    private final Looper zzrP;

    public static class zza {
        public static final com.google.android.gms.common.api.GoogleApi.zza zzaAQ;
        public final Account account;
        public final zzbfy zzaAR;
        public final Looper zzaAS;

        static {
            zzaAQ = new zzd().zzph();
        }

        private zza(zzbfy com_google_android_gms_internal_zzbfy, Account account, Looper looper) {
            this.zzaAR = com_google_android_gms_internal_zzbfy;
            this.account = account;
            this.zzaAS = looper;
        }
    }

    @MainThread
    private GoogleApi(@NonNull Activity activity, Api<O> api, O o, zza com_google_android_gms_common_api_GoogleApi_zza) {
        zzbr.zzb((Object) activity, (Object) "Null activity is not permitted.");
        zzbr.zzb((Object) api, (Object) "Api must not be null.");
        zzbr.zzb((Object) com_google_android_gms_common_api_GoogleApi_zza, (Object) "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = activity.getApplicationContext();
        this.zzayY = api;
        this.zzaAL = null;
        this.zzrP = com_google_android_gms_common_api_GoogleApi_zza.zzaAS;
        this.zzaAM = zzbcf.zza(this.zzayY, this.zzaAL);
        this.zzaAN = new zzbev(this);
        this.zzaAP = zzben.zzay(this.mContext);
        this.mId = this.zzaAP.zzqk();
        this.zzaAO = com_google_android_gms_common_api_GoogleApi_zza.zzaAR;
        this.zzajd = com_google_android_gms_common_api_GoogleApi_zza.account;
        zzbdi.zza(activity, this.zzaAP, this.zzaAM);
        this.zzaAP.zzb(this);
    }

    @Deprecated
    public GoogleApi(@NonNull Activity activity, Api<O> api, O o, zzbfy com_google_android_gms_internal_zzbfy) {
        this(activity, (Api) api, null, new zzd().zza(com_google_android_gms_internal_zzbfy).zza(activity.getMainLooper()).zzph());
    }

    protected GoogleApi(@NonNull Context context, Api<O> api, Looper looper) {
        zzbr.zzb((Object) context, (Object) "Null context is not permitted.");
        zzbr.zzb((Object) api, (Object) "Api must not be null.");
        zzbr.zzb((Object) looper, (Object) "Looper must not be null.");
        this.mContext = context.getApplicationContext();
        this.zzayY = api;
        this.zzaAL = null;
        this.zzrP = looper;
        this.zzaAM = zzbcf.zzb(api);
        this.zzaAN = new zzbev(this);
        this.zzaAP = zzben.zzay(this.mContext);
        this.mId = this.zzaAP.zzqk();
        this.zzaAO = new zzbce();
        this.zzajd = null;
    }

    @Deprecated
    public GoogleApi(@NonNull Context context, Api<O> api, O o, Looper looper, zzbfy com_google_android_gms_internal_zzbfy) {
        this(context, (Api) api, null, new zzd().zza(looper).zza(com_google_android_gms_internal_zzbfy).zzph());
    }

    public GoogleApi(@NonNull Context context, Api<O> api, O o, zza com_google_android_gms_common_api_GoogleApi_zza) {
        zzbr.zzb((Object) context, (Object) "Null context is not permitted.");
        zzbr.zzb((Object) api, (Object) "Api must not be null.");
        zzbr.zzb((Object) com_google_android_gms_common_api_GoogleApi_zza, (Object) "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = context.getApplicationContext();
        this.zzayY = api;
        this.zzaAL = o;
        this.zzrP = com_google_android_gms_common_api_GoogleApi_zza.zzaAS;
        this.zzaAM = zzbcf.zza(this.zzayY, this.zzaAL);
        this.zzaAN = new zzbev(this);
        this.zzaAP = zzben.zzay(this.mContext);
        this.mId = this.zzaAP.zzqk();
        this.zzaAO = com_google_android_gms_common_api_GoogleApi_zza.zzaAR;
        this.zzajd = com_google_android_gms_common_api_GoogleApi_zza.account;
        this.zzaAP.zzb(this);
    }

    @Deprecated
    public GoogleApi(@NonNull Context context, Api<O> api, O o, zzbfy com_google_android_gms_internal_zzbfy) {
        this(context, (Api) api, (ApiOptions) o, new zzd().zza(com_google_android_gms_internal_zzbfy).zzph());
    }

    private final <A extends zzb, T extends zzbck<? extends Result, A>> T zza(int i, @NonNull T t) {
        t.zzpA();
        this.zzaAP.zza(this, i, (zzbck) t);
        return t;
    }

    private final <TResult, A extends zzb> Task<TResult> zza(int i, @NonNull zzbgc<A, TResult> com_google_android_gms_internal_zzbgc_A__TResult) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzaAP.zza(this, i, com_google_android_gms_internal_zzbgc_A__TResult, taskCompletionSource, this.zzaAO);
        return taskCompletionSource.getTask();
    }

    public final Context getApplicationContext() {
        return this.mContext;
    }

    public final int getInstanceId() {
        return this.mId;
    }

    public final Looper getLooper() {
        return this.zzrP;
    }

    @WorkerThread
    public zze zza(Looper looper, zzbep<O> com_google_android_gms_internal_zzbep_O) {
        return this.zzayY.zzpa().zza(this.mContext, looper, new Builder(this.mContext).zze(this.zzajd).zzpl(), this.zzaAL, com_google_android_gms_internal_zzbep_O, com_google_android_gms_internal_zzbep_O);
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zza(@NonNull T t) {
        return zza(0, (zzbck) t);
    }

    public zzbfv zza(Context context, Handler handler) {
        return new zzbfv(context, handler);
    }

    public final <TResult, A extends zzb> Task<TResult> zza(zzbgc<A, TResult> com_google_android_gms_internal_zzbgc_A__TResult) {
        return zza(0, (zzbgc) com_google_android_gms_internal_zzbgc_A__TResult);
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zzb(@NonNull T t) {
        return zza(1, (zzbck) t);
    }

    public final <TResult, A extends zzb> Task<TResult> zzb(zzbgc<A, TResult> com_google_android_gms_internal_zzbgc_A__TResult) {
        return zza(1, (zzbgc) com_google_android_gms_internal_zzbgc_A__TResult);
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zzc(@NonNull T t) {
        return zza((int) RainSurfaceView.RAIN_LEVEL_SHOWER, (zzbck) t);
    }

    public final Api<O> zzpe() {
        return this.zzayY;
    }

    public final zzbcf<O> zzpf() {
        return this.zzaAM;
    }

    public final GoogleApiClient zzpg() {
        return this.zzaAN;
    }
}
