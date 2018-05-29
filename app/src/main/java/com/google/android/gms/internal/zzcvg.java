package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzy;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzbs;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzq;

public final class zzcvg extends zzaa<zzcve> implements zzcuw {
    private final zzq zzaCC;
    private Integer zzaHp;
    private final Bundle zzbCP;
    private final boolean zzbCX;

    public zzcvg(Context context, Looper looper, boolean z, zzq com_google_android_gms_common_internal_zzq, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, com_google_android_gms_common_internal_zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzbCX = z;
        this.zzaCC = com_google_android_gms_common_internal_zzq;
        this.zzbCP = bundle;
        this.zzaHp = com_google_android_gms_common_internal_zzq.zzrs();
    }

    public zzcvg(Context context, Looper looper, boolean z, zzq com_google_android_gms_common_internal_zzq, zzcux com_google_android_gms_internal_zzcux, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, true, com_google_android_gms_common_internal_zzq, zza(com_google_android_gms_common_internal_zzq), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle zza(zzq com_google_android_gms_common_internal_zzq) {
        zzcux zzrr = com_google_android_gms_common_internal_zzq.zzrr();
        Integer zzrs = com_google_android_gms_common_internal_zzq.zzrs();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", com_google_android_gms_common_internal_zzq.getAccount());
        if (zzrs != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", zzrs.intValue());
        }
        if (zzrr != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzrr.zzAp());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzrr.isIdTokenRequested());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzrr.getServerClientId());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzrr.zzAq());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzrr.zzAr());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzrr.zzAs());
            if (zzrr.zzAt() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzrr.zzAt().longValue());
            }
            if (zzrr.zzAu() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzrr.zzAu().longValue());
            }
        }
        return bundle;
    }

    public final void connect() {
        zza(new zzm(this));
    }

    public final void zzAo() {
        try {
            ((zzcve) zzrd()).zzbu(this.zzaHp.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    public final void zza(zzam com_google_android_gms_common_internal_zzam, boolean z) {
        try {
            ((zzcve) zzrd()).zza(com_google_android_gms_common_internal_zzam, this.zzaHp.intValue(), z);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public final void zza(zzcvc com_google_android_gms_internal_zzcvc) {
        zzbr.zzb((Object) com_google_android_gms_internal_zzcvc, (Object) "Expecting a valid ISignInCallbacks");
        try {
            Account zzrj = this.zzaCC.zzrj();
            GoogleSignInAccount googleSignInAccount = null;
            if ("<<default account>>".equals(zzrj.name)) {
                googleSignInAccount = zzy.zzaj(getContext()).zzmL();
            }
            ((zzcve) zzrd()).zza(new zzcvh(new zzbs(zzrj, this.zzaHp.intValue(), googleSignInAccount)), com_google_android_gms_internal_zzcvc);
        } catch (Throwable e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                com_google_android_gms_internal_zzcvc.zzb(new zzcvj(8));
            } catch (RemoteException e2) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }

    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        return queryLocalInterface instanceof zzcve ? (zzcve) queryLocalInterface : new zzcvf(iBinder);
    }

    protected final String zzda() {
        return "com.google.android.gms.signin.service.START";
    }

    protected final String zzdb() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    protected final Bundle zzmm() {
        if (!getContext().getPackageName().equals(this.zzaCC.zzro())) {
            this.zzbCP.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzaCC.zzro());
        }
        return this.zzbCP;
    }

    public final boolean zzmt() {
        return this.zzbCX;
    }
}
