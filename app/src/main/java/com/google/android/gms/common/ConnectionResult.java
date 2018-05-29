package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import com.oneplus.sdk.utils.OpAppTracker;
import java.util.Arrays;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class ConnectionResult extends zza {
    public static final int API_UNAVAILABLE = 16;
    public static final int CANCELED = 13;
    public static final Creator<ConnectionResult> CREATOR;
    public static final int DEVELOPER_ERROR = 10;
    @Deprecated
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int RESTRICTED_PROFILE = 20;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_MISSING_PERMISSION = 19;
    public static final int SERVICE_UPDATING = 18;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_FAILED = 17;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;
    public static final ConnectionResult zzazZ;
    private final PendingIntent mPendingIntent;
    private final String zzaAa;
    private int zzakw;
    private final int zzaxw;

    static {
        zzazZ = new ConnectionResult(0);
        CREATOR = new zzb();
    }

    public ConnectionResult(int i) {
        this(i, null, null);
    }

    ConnectionResult(int i, int i2, PendingIntent pendingIntent, String str) {
        this.zzakw = i;
        this.zzaxw = i2;
        this.mPendingIntent = pendingIntent;
        this.zzaAa = str;
    }

    public ConnectionResult(int i, PendingIntent pendingIntent) {
        this(i, pendingIntent, null);
    }

    public ConnectionResult(int i, PendingIntent pendingIntent, String str) {
        this(1, i, pendingIntent, str);
    }

    static String getStatusString(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DEFAULT:
                return "UNKNOWN";
            case SUCCESS:
                return "SUCCESS";
            case SERVICE_MISSING:
                return "SERVICE_MISSING";
            case SERVICE_VERSION_UPDATE_REQUIRED:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case SERVICE_DISABLED:
                return "SERVICE_DISABLED";
            case SIGN_IN_REQUIRED:
                return "SIGN_IN_REQUIRED";
            case INVALID_ACCOUNT:
                return "INVALID_ACCOUNT";
            case RESOLUTION_REQUIRED:
                return "RESOLUTION_REQUIRED";
            case NETWORK_ERROR:
                return "NETWORK_ERROR";
            case INTERNAL_ERROR:
                return "INTERNAL_ERROR";
            case SERVICE_INVALID:
                return "SERVICE_INVALID";
            case DEVELOPER_ERROR:
                return "DEVELOPER_ERROR";
            case LICENSE_CHECK_FAILED:
                return "LICENSE_CHECK_FAILED";
            case CANCELED:
                return "CANCELED";
            case TIMEOUT:
                return "TIMEOUT";
            case INTERRUPTED:
                return "INTERRUPTED";
            case API_UNAVAILABLE:
                return "API_UNAVAILABLE";
            case SIGN_IN_FAILED:
                return "SIGN_IN_FAILED";
            case SERVICE_UPDATING:
                return "SERVICE_UPDATING";
            case SERVICE_MISSING_PERMISSION:
                return "SERVICE_MISSING_PERMISSION";
            case RESTRICTED_PROFILE:
                return "RESTRICTED_PROFILE";
            case R.styleable.Toolbar_titleMargin:
                return "API_VERSION_UPDATE_REQUIRED";
            case R.styleable.AppCompatTheme_textAppearancePopupMenuHeader:
                return "UNFINISHED";
            case DRIVE_EXTERNAL_STORAGE_REQUIRED:
                return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
            default:
                return new StringBuilder(31).append("UNKNOWN_ERROR_CODE(").append(i).append(")").toString();
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConnectionResult)) {
            return false;
        }
        ConnectionResult connectionResult = (ConnectionResult) obj;
        return this.zzaxw == connectionResult.zzaxw && zzbh.equal(this.mPendingIntent, connectionResult.mPendingIntent) && zzbh.equal(this.zzaAa, connectionResult.zzaAa);
    }

    public final int getErrorCode() {
        return this.zzaxw;
    }

    @Nullable
    public final String getErrorMessage() {
        return this.zzaAa;
    }

    @Nullable
    public final PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public final boolean hasResolution() {
        return (this.zzaxw == 0 || this.mPendingIntent == null) ? false : true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaxw), this.mPendingIntent, this.zzaAa});
    }

    public final boolean isSuccess() {
        return this.zzaxw == 0;
    }

    public final void startResolutionForResult(Activity activity, int i) throws SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), i, null, SUCCESS, 0, 0);
        }
    }

    public final String toString() {
        return zzbh.zzt(this).zzg("statusCode", getStatusString(this.zzaxw)).zzg("resolution", this.mPendingIntent).zzg(OpAppTracker.DATA_MESSAGE, this.zzaAa).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, SERVICE_MISSING, this.zzakw);
        zzd.zzc(parcel, SERVICE_VERSION_UPDATE_REQUIRED, getErrorCode());
        zzd.zza(parcel, (int) SERVICE_DISABLED, getResolution(), i, false);
        zzd.zza(parcel, (int) SIGN_IN_REQUIRED, getErrorMessage(), false);
        zzd.zzI(parcel, zze);
    }
}
