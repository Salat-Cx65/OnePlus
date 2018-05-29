package com.google.android.gms.iid;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ReflectedParcelable;

public class MessengerCompat implements ReflectedParcelable {
    public static final Creator<MessengerCompat> CREATOR;
    private Messenger zzbhb;
    private zzb zzbhc;

    static {
        CREATOR = new zzd();
    }

    public MessengerCompat(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.zzbhb = new Messenger(iBinder);
            return;
        }
        zzb com_google_android_gms_iid_zzb;
        if (iBinder == null) {
            com_google_android_gms_iid_zzb = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.iid.IMessengerCompat");
            com_google_android_gms_iid_zzb = queryLocalInterface instanceof zzb ? (zzb) queryLocalInterface : new zzc(iBinder);
        }
        this.zzbhc = com_google_android_gms_iid_zzb;
    }

    private final IBinder getBinder() {
        return this.zzbhb != null ? this.zzbhb.getBinder() : this.zzbhc.asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return getBinder().equals(((MessengerCompat) obj).getBinder());
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public final void send(Message message) throws RemoteException {
        if (this.zzbhb != null) {
            this.zzbhb.send(message);
        } else {
            this.zzbhc.send(message);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzbhb != null) {
            parcel.writeStrongBinder(this.zzbhb.getBinder());
        } else {
            parcel.writeStrongBinder(this.zzbhc.asBinder());
        }
    }
}
