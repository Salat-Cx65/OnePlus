package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zzn;
import com.google.android.gms.auth.api.signin.internal.zzo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions extends zza implements Optional, ReflectedParcelable {
    public static final Creator<GoogleSignInOptions> CREATOR;
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN;
    public static final GoogleSignInOptions DEFAULT_SIGN_IN;
    private static Scope SCOPE_GAMES;
    private static Comparator<Scope> zzalW;
    public static final Scope zzalX;
    public static final Scope zzalY;
    public static final Scope zzalZ;
    private int versionCode;
    private Account zzajd;
    private boolean zzalj;
    private String zzalk;
    private final ArrayList<Scope> zzama;
    private final boolean zzamb;
    private final boolean zzamc;
    private String zzamd;
    private ArrayList<zzn> zzame;
    private Map<Integer, zzn> zzamf;

    public static final class Builder {
        private Account zzajd;
        private boolean zzalj;
        private String zzalk;
        private boolean zzamb;
        private boolean zzamc;
        private String zzamd;
        private Set<Scope> zzamg;
        private Map<Integer, zzn> zzamh;

        public Builder() {
            this.zzamg = new HashSet();
            this.zzamh = new HashMap();
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            this.zzamg = new HashSet();
            this.zzamh = new HashMap();
            zzbr.zzu(googleSignInOptions);
            this.zzamg = new HashSet(googleSignInOptions.zzama);
            this.zzamb = googleSignInOptions.zzamb;
            this.zzamc = googleSignInOptions.zzamc;
            this.zzalj = googleSignInOptions.zzalj;
            this.zzalk = googleSignInOptions.zzalk;
            this.zzajd = googleSignInOptions.zzajd;
            this.zzamd = googleSignInOptions.zzamd;
            this.zzamh = GoogleSignInOptions.zzw(googleSignInOptions.zzame);
        }

        private final String zzbR(String str) {
            zzbr.zzcF(str);
            boolean z = this.zzalk == null || this.zzalk.equals(str);
            zzbr.zzb(z, (Object) "two different server client ids provided");
            return str;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (this.zzamh.containsKey(Integer.valueOf(1))) {
                throw new IllegalStateException("Only one extension per type may be added");
            }
            this.zzamh.put(Integer.valueOf(1), new zzn(googleSignInOptionsExtension));
            return this;
        }

        public final GoogleSignInOptions build() {
            if (this.zzalj) {
                if (this.zzajd == null || !this.zzamg.isEmpty()) {
                    requestId();
                }
            }
            return new GoogleSignInOptions(new ArrayList(this.zzamg), this.zzajd, this.zzalj, this.zzamb, this.zzamc, this.zzalk, this.zzamd, this.zzamh, null);
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder requestEmail() {
            this.zzamg.add(zzalY);
            return this;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder requestId() {
            this.zzamg.add(zzalZ);
            return this;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder requestIdToken(String str) {
            this.zzalj = true;
            this.zzalk = zzbR(str);
            return this;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder requestProfile() {
            this.zzamg.add(zzalX);
            return this;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.zzamg.add(scope);
            this.zzamg.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder requestServerAuthCode(String str, boolean z) {
            this.zzamb = true;
            this.zzalk = zzbR(str);
            this.zzamc = z;
            return this;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder setAccountName(String str) {
            this.zzajd = new Account(zzbr.zzcF(str), "com.google");
            return this;
        }

        public final com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder setHostedDomain(String str) {
            this.zzamd = zzbr.zzcF(str);
            return this;
        }
    }

    static {
        zzalX = new Scope(Scopes.PROFILE);
        zzalY = new Scope(Scopes.EMAIL);
        zzalZ = new Scope("openid");
        SCOPE_GAMES = new Scope(Scopes.GAMES);
        DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
        DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(SCOPE_GAMES, new Scope[0]).build();
        CREATOR = new zzd();
        zzalW = new zzc();
    }

    GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, ArrayList<zzn> arrayList2) {
        this(i, (ArrayList) arrayList, account, z, z2, z3, str, str2, zzw(arrayList2));
    }

    private GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map<Integer, zzn> map) {
        this.versionCode = i;
        this.zzama = arrayList;
        this.zzajd = account;
        this.zzalj = z;
        this.zzamb = z2;
        this.zzamc = z3;
        this.zzalk = str;
        this.zzamd = str2;
        this.zzame = new ArrayList(map.values());
        this.zzamf = map;
    }

    @Nullable
    public static GoogleSignInOptions zzbQ(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        Collection hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        Object optString = jSONObject.optString("accountName", null);
        return new GoogleSignInOptions(3, new ArrayList(hashSet), !TextUtils.isEmpty(optString) ? new Account(optString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null), new HashMap());
    }

    private final JSONObject zzmx() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzama, zzalW);
            ArrayList arrayList = this.zzama;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                jSONArray.put(((Scope) obj).zzpn());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.zzajd != null) {
                jSONObject.put("accountName", this.zzajd.name);
            }
            jSONObject.put("idTokenRequested", this.zzalj);
            jSONObject.put("forceCodeForRefreshToken", this.zzamc);
            jSONObject.put("serverAuthRequested", this.zzamb);
            if (!TextUtils.isEmpty(this.zzalk)) {
                jSONObject.put("serverClientId", this.zzalk);
            }
            if (!TextUtils.isEmpty(this.zzamd)) {
                jSONObject.put("hostedDomain", this.zzamd);
            }
            return jSONObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<Integer, zzn> zzw(@Nullable List<zzn> list) {
        Map<Integer, zzn> hashMap = new HashMap();
        if (list == null) {
            return hashMap;
        }
        for (zzn com_google_android_gms_auth_api_signin_internal_zzn : list) {
            hashMap.put(Integer.valueOf(com_google_android_gms_auth_api_signin_internal_zzn.getType()), com_google_android_gms_auth_api_signin_internal_zzn);
        }
        return hashMap;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            GoogleSignInOptions googleSignInOptions = (GoogleSignInOptions) obj;
            if (this.zzame.size() > 0 || googleSignInOptions.zzame.size() > 0 || this.zzama.size() != googleSignInOptions.zzmy().size() || !this.zzama.containsAll(googleSignInOptions.zzmy())) {
                return false;
            }
            if (this.zzajd == null) {
                if (googleSignInOptions.zzajd != null) {
                    return false;
                }
            } else if (!this.zzajd.equals(googleSignInOptions.zzajd)) {
                return false;
            }
            if (TextUtils.isEmpty(this.zzalk)) {
                if (!TextUtils.isEmpty(googleSignInOptions.zzalk)) {
                    return false;
                }
            } else if (!this.zzalk.equals(googleSignInOptions.zzalk)) {
                return false;
            }
            return this.zzamc == googleSignInOptions.zzamc && this.zzalj == googleSignInOptions.zzalj && this.zzamb == googleSignInOptions.zzamb;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public final Account getAccount() {
        return this.zzajd;
    }

    public Scope[] getScopeArray() {
        return (Scope[]) this.zzama.toArray(new Scope[this.zzama.size()]);
    }

    public final String getServerClientId() {
        return this.zzalk;
    }

    public int hashCode() {
        List arrayList = new ArrayList();
        ArrayList arrayList2 = this.zzama;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((Scope) obj).zzpn());
        }
        Collections.sort(arrayList);
        return new zzo().zzo(arrayList).zzo(this.zzajd).zzo(this.zzalk).zzP(this.zzamc).zzP(this.zzalj).zzP(this.zzamb).zzmH();
    }

    public final boolean isIdTokenRequested() {
        return this.zzalj;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, zzmy(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzajd, i, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzalj);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzamb);
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, this.zzamc);
        zzd.zza(parcel, (int) DetectedActivity.WALKING, this.zzalk, false);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, this.zzamd, false);
        zzd.zzc(parcel, ConnectionResult.SERVICE_INVALID, this.zzame, false);
        zzd.zzI(parcel, zze);
    }

    public final String zzmA() {
        return zzmx().toString();
    }

    public final ArrayList<Scope> zzmy() {
        return new ArrayList(this.zzama);
    }

    public final boolean zzmz() {
        return this.zzamb;
    }
}
