package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzf;
import com.google.android.gms.common.util.zzj;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.oneplus.weather.db.CityWeatherDBHelper.CityListEntry;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount extends zza implements ReflectedParcelable {
    public static final Creator<GoogleSignInAccount> CREATOR;
    private static zzf zzalP;
    private static Comparator<Scope> zzalW;
    private int versionCode;
    private String zzIl;
    private List<Scope> zzakB;
    private String zzakZ;
    private String zzalQ;
    private String zzalR;
    private Uri zzalS;
    private String zzalT;
    private long zzalU;
    private String zzalV;
    private String zzala;
    private String zzalp;

    static {
        CREATOR = new zzb();
        zzalP = zzj.zzrX();
        zzalW = new zza();
    }

    GoogleSignInAccount(int i, String str, String str2, String str3, String str4, Uri uri, String str5, long j, String str6, List<Scope> list, String str7, String str8) {
        this.versionCode = i;
        this.zzIl = str;
        this.zzalp = str2;
        this.zzalQ = str3;
        this.zzalR = str4;
        this.zzalS = uri;
        this.zzalT = str5;
        this.zzalU = j;
        this.zzalV = str6;
        this.zzakB = list;
        this.zzakZ = str7;
        this.zzala = str8;
    }

    @Nullable
    public static GoogleSignInAccount zzbP(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        Uri uri = null;
        Object optString = jSONObject.optString("photoUrl", null);
        if (!TextUtils.isEmpty(optString)) {
            uri = Uri.parse(optString);
        }
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        Set hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        String optString2 = jSONObject.optString("id");
        String optString3 = jSONObject.optString("tokenId", null);
        String optString4 = jSONObject.optString(Scopes.EMAIL, null);
        String optString5 = jSONObject.optString(CityListEntry.COLUMN_3_DISPLAY_NAME, null);
        String optString6 = jSONObject.optString("givenName", null);
        String optString7 = jSONObject.optString("familyName", null);
        Long valueOf = Long.valueOf(parseLong);
        GoogleSignInAccount googleSignInAccount = new GoogleSignInAccount(3, optString2, optString3, optString4, optString5, uri, null, (valueOf == null ? Long.valueOf(zzalP.currentTimeMillis() / 1000) : valueOf).longValue(), zzbr.zzcF(jSONObject.getString("obfuscatedIdentifier")), new ArrayList((Collection) zzbr.zzu(hashSet)), optString6, optString7);
        googleSignInAccount.zzalT = jSONObject.optString("serverAuthCode", null);
        return googleSignInAccount;
    }

    private final JSONObject zzmx() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put("id", getId());
            }
            if (getIdToken() != null) {
                jSONObject.put("tokenId", getIdToken());
            }
            if (getEmail() != null) {
                jSONObject.put(Scopes.EMAIL, getEmail());
            }
            if (getDisplayName() != null) {
                jSONObject.put(CityListEntry.COLUMN_3_DISPLAY_NAME, getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", getFamilyName());
            }
            if (getPhotoUrl() != null) {
                jSONObject.put("photoUrl", getPhotoUrl().toString());
            }
            if (getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zzalU);
            jSONObject.put("obfuscatedIdentifier", this.zzalV);
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzakB, zzalW);
            for (Scope scope : this.zzakB) {
                jSONArray.put(scope.zzpn());
            }
            jSONObject.put("grantedScopes", jSONArray);
            return jSONObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        return !(obj instanceof GoogleSignInAccount) ? false : ((GoogleSignInAccount) obj).zzmx().toString().equals(zzmx().toString());
    }

    @Nullable
    public Account getAccount() {
        return this.zzalQ == null ? null : new Account(this.zzalQ, "com.google");
    }

    @Nullable
    public String getDisplayName() {
        return this.zzalR;
    }

    @Nullable
    public String getEmail() {
        return this.zzalQ;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzala;
    }

    @Nullable
    public String getGivenName() {
        return this.zzakZ;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet(this.zzakB);
    }

    @Nullable
    public String getId() {
        return this.zzIl;
    }

    @Nullable
    public String getIdToken() {
        return this.zzalp;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zzalS;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zzalT;
    }

    public int hashCode() {
        return zzmx().toString().hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, getId(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, getIdToken(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, getEmail(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, getDisplayName(), false);
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, getPhotoUrl(), i, false);
        zzd.zza(parcel, (int) DetectedActivity.WALKING, getServerAuthCode(), false);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, this.zzalU);
        zzd.zza(parcel, (int) ConnectionResult.SERVICE_INVALID, this.zzalV, false);
        zzd.zzc(parcel, ConnectionResult.DEVELOPER_ERROR, this.zzakB, false);
        zzd.zza(parcel, (int) ConnectionResult.LICENSE_CHECK_FAILED, getGivenName(), false);
        zzd.zza(parcel, (int) WeatherCircleView.ARC_DIN, getFamilyName(), false);
        zzd.zzI(parcel, zze);
    }

    public final boolean zzmu() {
        return zzalP.currentTimeMillis() / 1000 >= this.zzalU - 300;
    }

    @NonNull
    public final String zzmv() {
        return this.zzalV;
    }

    public final String zzmw() {
        JSONObject zzmx = zzmx();
        zzmx.remove("serverAuthCode");
        return zzmx.toString();
    }
}
