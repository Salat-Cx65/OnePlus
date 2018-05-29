package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat.MessagingStyle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainDownpour;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzaz extends Binder implements zzay {
    public zzaz() {
        attachInterface(this, "com.google.android.gms.common.internal.IGmsServiceBroker");
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i > 16777215) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        zzav com_google_android_gms_common_internal_zzav;
        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
        IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder == null) {
            com_google_android_gms_common_internal_zzav = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            if (queryLocalInterface instanceof zzav) {
                com_google_android_gms_common_internal_zzav = (zzav) queryLocalInterface;
            } else {
                zzax com_google_android_gms_common_internal_zzax = new zzax(readStrongBinder);
            }
        }
        if (i == 46) {
            zza(com_google_android_gms_common_internal_zzav, parcel.readInt() != 0 ? (zzy) zzy.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            return true;
        } else if (i == 47) {
            if (parcel.readInt() != 0) {
                zzcc.CREATOR.createFromParcel(parcel);
            }
            throw new UnsupportedOperationException();
        } else {
            parcel.readInt();
            if (i != 4) {
                parcel.readString();
            }
            switch (i) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    parcel.readString();
                    parcel.createStringArray();
                    parcel.readString();
                    if (parcel.readInt() != 0) {
                        Bundle.CREATOR.createFromParcel(parcel);
                    }
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                case ConnectionResult.RESOLUTION_REQUIRED:
                case DetectedActivity.WALKING:
                case DetectedActivity.RUNNING:
                case ConnectionResult.LICENSE_CHECK_FAILED:
                case WeatherCircleView.ARC_DIN:
                case ConnectionResult.CANCELED:
                case ConnectionResult.TIMEOUT:
                case ConnectionResult.INTERRUPTED:
                case ConnectionResult.API_UNAVAILABLE:
                case ConnectionResult.SIGN_IN_FAILED:
                case ConnectionResult.SERVICE_UPDATING:
                case R.styleable.Toolbar_titleMarginEnd:
                case MessagingStyle.MAXIMUM_RETAINED_MESSAGES:
                case R.styleable.Toolbar_titleTextAppearance:
                case R.styleable.OneplusTheme_op_listLayout:
                case R.styleable.OneplusTheme_op_multiChoiceItemLayout:
                case R.styleable.OneplusTheme_op_singleChoiceItemLayout:
                case R.styleable.OneplusTheme_textAppearanceOPNumberPickerUnit:
                    if (parcel.readInt() != 0) {
                        Bundle.CREATOR.createFromParcel(parcel);
                    }
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    parcel.readString();
                    parcel.createStringArray();
                    parcel.readString();
                    parcel.readStrongBinder();
                    parcel.readString();
                    if (parcel.readInt() != 0) {
                        Bundle.CREATOR.createFromParcel(parcel);
                    }
                    break;
                case ConnectionResult.DEVELOPER_ERROR:
                    parcel.readString();
                    parcel.createStringArray();
                    break;
                case ConnectionResult.SERVICE_MISSING_PERMISSION:
                    parcel.readStrongBinder();
                    if (parcel.readInt() != 0) {
                        Bundle.CREATOR.createFromParcel(parcel);
                    }
                    break;
                case ConnectionResult.RESTRICTED_PROFILE:
                case RainDownpour.Z_RANDOM_RANGE:
                    parcel.createStringArray();
                    parcel.readString();
                    if (parcel.readInt() != 0) {
                        Bundle.CREATOR.createFromParcel(parcel);
                    }
                    break;
                case R.styleable.OneplusTheme_op_buttonPanelSideLayout:
                    parcel.readString();
                    break;
            }
            throw new UnsupportedOperationException();
        }
    }
}
