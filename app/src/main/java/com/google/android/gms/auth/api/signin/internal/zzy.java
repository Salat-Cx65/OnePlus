package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzbr;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public final class zzy {
    private static final Lock zzamF;
    private static zzy zzamG;
    private final Lock zzamH;
    private final SharedPreferences zzamI;

    static {
        zzamF = new ReentrantLock();
    }

    private zzy(Context context) {
        this.zzamH = new ReentrantLock();
        this.zzamI = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzy zzaj(Context context) {
        zzbr.zzu(context);
        zzamF.lock();
        if (zzamG == null) {
            zzamG = new zzy(context.getApplicationContext());
        }
        zzy com_google_android_gms_auth_api_signin_internal_zzy = zzamG;
        zzamF.unlock();
        return com_google_android_gms_auth_api_signin_internal_zzy;
    }

    private final GoogleSignInAccount zzbS(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String zzbU = zzbU(zzs("googleSignInAccount", str));
        if (zzbU == null) {
            return null;
        }
        try {
            return GoogleSignInAccount.zzbP(zzbU);
        } catch (JSONException e) {
            return null;
        }
    }

    private final GoogleSignInOptions zzbT(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String zzbU = zzbU(zzs("googleSignInOptions", str));
        if (zzbU == null) {
            return null;
        }
        try {
            return GoogleSignInOptions.zzbQ(zzbU);
        } catch (JSONException e) {
            return null;
        }
    }

    private final String zzbU(String str) {
        this.zzamH.lock();
        String string = this.zzamI.getString(str, null);
        this.zzamH.unlock();
        return string;
    }

    private final void zzbV(String str) {
        this.zzamH.lock();
        this.zzamI.edit().remove(str).apply();
        this.zzamH.unlock();
    }

    private final void zzr(String str, String str2) {
        this.zzamH.lock();
        this.zzamI.edit().putString(str, str2).apply();
        this.zzamH.unlock();
    }

    private static String zzs(String str, String str2) {
        String valueOf = String.valueOf(":");
        return new StringBuilder((String.valueOf(str).length() + String.valueOf(valueOf).length()) + String.valueOf(str2).length()).append(str).append(valueOf).append(str2).toString();
    }

    public final void zza(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzbr.zzu(googleSignInAccount);
        zzbr.zzu(googleSignInOptions);
        zzr("defaultGoogleSignInAccount", googleSignInAccount.zzmv());
        zzbr.zzu(googleSignInAccount);
        zzbr.zzu(googleSignInOptions);
        String zzmv = googleSignInAccount.zzmv();
        zzr(zzs("googleSignInAccount", zzmv), googleSignInAccount.zzmw());
        zzr(zzs("googleSignInOptions", zzmv), googleSignInOptions.zzmA());
    }

    public final GoogleSignInAccount zzmL() {
        return zzbS(zzbU("defaultGoogleSignInAccount"));
    }

    public final GoogleSignInOptions zzmM() {
        return zzbT(zzbU("defaultGoogleSignInAccount"));
    }

    public final void zzmN() {
        String zzbU = zzbU("defaultGoogleSignInAccount");
        zzbV("defaultGoogleSignInAccount");
        if (!TextUtils.isEmpty(zzbU)) {
            zzbV(zzs("googleSignInAccount", zzbU));
            zzbV(zzs("googleSignInOptions", zzbU));
        }
    }
}
