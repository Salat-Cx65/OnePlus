package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat.MessagingStyle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzl extends zzee implements zzk {
    public zzl() {
        attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        IObjectWrapper iObjectWrapper = null;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        IInterface zztx;
        int id;
        boolean retainInstance;
        IBinder readStrongBinder;
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zztx = zztx();
                parcel2.writeNoException();
                zzef.zza(parcel2, zztx);
                break;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                Parcelable arguments = getArguments();
                parcel2.writeNoException();
                zzef.zzb(parcel2, arguments);
                break;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                id = getId();
                parcel2.writeNoException();
                parcel2.writeInt(id);
                break;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                zztx = zzty();
                parcel2.writeNoException();
                zzef.zza(parcel2, zztx);
                break;
            case ConnectionResult.RESOLUTION_REQUIRED:
                zztx = zztz();
                parcel2.writeNoException();
                zzef.zza(parcel2, zztx);
                break;
            case DetectedActivity.WALKING:
                retainInstance = getRetainInstance();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case DetectedActivity.RUNNING:
                String tag = getTag();
                parcel2.writeNoException();
                parcel2.writeString(tag);
                break;
            case ConnectionResult.SERVICE_INVALID:
                zztx = zztA();
                parcel2.writeNoException();
                zzef.zza(parcel2, zztx);
                break;
            case ConnectionResult.DEVELOPER_ERROR:
                id = getTargetRequestCode();
                parcel2.writeNoException();
                parcel2.writeInt(id);
                break;
            case ConnectionResult.LICENSE_CHECK_FAILED:
                retainInstance = getUserVisibleHint();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case WeatherCircleView.ARC_DIN:
                zztx = getView();
                parcel2.writeNoException();
                zzef.zza(parcel2, zztx);
                break;
            case ConnectionResult.CANCELED:
                retainInstance = isAdded();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case ConnectionResult.TIMEOUT:
                retainInstance = isDetached();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case ConnectionResult.INTERRUPTED:
                retainInstance = isHidden();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case ConnectionResult.API_UNAVAILABLE:
                retainInstance = isInLayout();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case ConnectionResult.SIGN_IN_FAILED:
                retainInstance = isRemoving();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case ConnectionResult.SERVICE_UPDATING:
                retainInstance = isResumed();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case ConnectionResult.SERVICE_MISSING_PERMISSION:
                retainInstance = isVisible();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case ConnectionResult.RESTRICTED_PROFILE:
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    zztx = readStrongBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = zztx instanceof IObjectWrapper ? (IObjectWrapper) zztx : new zzm(readStrongBinder);
                }
                zzC(iObjectWrapper);
                parcel2.writeNoException();
                break;
            case R.styleable.Toolbar_titleMargin:
                setHasOptionsMenu(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case R.styleable.Toolbar_titleMarginBottom:
                setMenuVisibility(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case R.styleable.Toolbar_titleMarginEnd:
                setRetainInstance(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case R.styleable.Toolbar_titleMarginStart:
                setUserVisibleHint(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case MessagingStyle.MAXIMUM_RETAINED_MESSAGES:
                startActivity((Intent) zzef.zza(parcel, Intent.CREATOR));
                parcel2.writeNoException();
                break;
            case R.styleable.Toolbar_titleMargins:
                startActivityForResult((Intent) zzef.zza(parcel, Intent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                break;
            case R.styleable.Toolbar_titleTextAppearance:
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    zztx = readStrongBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = zztx instanceof IObjectWrapper ? (IObjectWrapper) zztx : new zzm(readStrongBinder);
                }
                zzD(iObjectWrapper);
                parcel2.writeNoException();
                break;
            default:
                return false;
        }
        return true;
    }
}
