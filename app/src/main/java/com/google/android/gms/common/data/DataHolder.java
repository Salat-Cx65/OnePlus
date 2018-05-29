package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@KeepName
public final class DataHolder extends com.google.android.gms.common.internal.safeparcel.zza implements Closeable {
    public static final Creator<DataHolder> CREATOR;
    private static final zza zzaFK;
    private boolean mClosed;
    private final String[] zzaFD;
    private Bundle zzaFE;
    private final CursorWindow[] zzaFF;
    private final Bundle zzaFG;
    private int[] zzaFH;
    int zzaFI;
    private boolean zzaFJ;
    private int zzakw;
    private final int zzaxw;

    public static class zza {
        private final String[] zzaFD;
        private final ArrayList<HashMap<String, Object>> zzaFL;
        private final String zzaFM;
        private final HashMap<Object, Integer> zzaFN;
        private boolean zzaFO;
        private String zzaFP;

        private zza(String[] strArr, String str) {
            this.zzaFD = (String[]) zzbr.zzu(strArr);
            this.zzaFL = new ArrayList();
            this.zzaFM = str;
            this.zzaFN = new HashMap();
            this.zzaFO = false;
            this.zzaFP = null;
        }

        public com.google.android.gms.common.data.DataHolder.zza zza(ContentValues contentValues) {
            zzc.zzr(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Entry entry : contentValues.valueSet()) {
                hashMap.put((String) entry.getKey(), entry.getValue());
            }
            return zza(hashMap);
        }

        public com.google.android.gms.common.data.DataHolder.zza zza(HashMap<String, Object> hashMap) {
            int i;
            zzc.zzr(hashMap);
            if (this.zzaFM == null) {
                i = -1;
            } else {
                Object obj = hashMap.get(this.zzaFM);
                if (obj == null) {
                    i = -1;
                } else {
                    Integer num = (Integer) this.zzaFN.get(obj);
                    if (num == null) {
                        this.zzaFN.put(obj, Integer.valueOf(this.zzaFL.size()));
                        i = -1;
                    } else {
                        i = num.intValue();
                    }
                }
            }
            if (i == -1) {
                this.zzaFL.add(hashMap);
            } else {
                this.zzaFL.remove(i);
                this.zzaFL.add(i, hashMap);
            }
            this.zzaFO = false;
            return this;
        }

        public final DataHolder zzav(int i) {
            return new DataHolder(0, null, null);
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    static {
        CREATOR = new zzf();
        zzaFK = new zze(new String[0], null);
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.zzaFJ = true;
        this.zzakw = i;
        this.zzaFD = strArr;
        this.zzaFF = cursorWindowArr;
        this.zzaxw = i2;
        this.zzaFG = bundle;
    }

    private DataHolder(zza com_google_android_gms_common_data_DataHolder_zza, int i, Bundle bundle) {
        this(com_google_android_gms_common_data_DataHolder_zza.zzaFD, zza(com_google_android_gms_common_data_DataHolder_zza, -1), i, null);
    }

    private DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.zzaFJ = true;
        this.zzakw = 1;
        this.zzaFD = (String[]) zzbr.zzu(strArr);
        this.zzaFF = (CursorWindow[]) zzbr.zzu(cursorWindowArr);
        this.zzaxw = i;
        this.zzaFG = bundle;
        zzqP();
    }

    public static zza zza(String[] strArr) {
        return new zza(null, null);
    }

    private static CursorWindow[] zza(zza com_google_android_gms_common_data_DataHolder_zza, int i) {
        int i2 = 0;
        if (com_google_android_gms_common_data_DataHolder_zza.zzaFD.length == 0) {
            return new CursorWindow[0];
        }
        List zzb = com_google_android_gms_common_data_DataHolder_zza.zzaFL;
        int size = zzb.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.zzaFD.length);
        int i3 = 0;
        int i4 = 0;
        while (i3 < size) {
            try {
                int i5;
                int i6;
                CursorWindow cursorWindow2;
                if (!cursorWindow.allocRow()) {
                    Log.d("DataHolder", new StringBuilder(72).append("Allocating additional cursor window for large data set (row ").append(i3).append(")").toString());
                    cursorWindow = new CursorWindow(false);
                    cursorWindow.setStartPosition(i3);
                    cursorWindow.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.zzaFD.length);
                    arrayList.add(cursorWindow);
                    if (!cursorWindow.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) zzb.get(i3);
                boolean z = true;
                for (int i7 = 0; i7 < com_google_android_gms_common_data_DataHolder_zza.zzaFD.length && z; i7++) {
                    String str = com_google_android_gms_common_data_DataHolder_zza.zzaFD[i7];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z = cursorWindow.putNull(i3, i7);
                    } else if (obj instanceof String) {
                        z = cursorWindow.putString((String) obj, i3, i7);
                    } else if (obj instanceof Long) {
                        z = cursorWindow.putLong(((Long) obj).longValue(), i3, i7);
                    } else if (obj instanceof Integer) {
                        z = cursorWindow.putLong((long) ((Integer) obj).intValue(), i3, i7);
                    } else if (obj instanceof Boolean) {
                        z = cursorWindow.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i3, i7);
                    } else if (obj instanceof byte[]) {
                        z = cursorWindow.putBlob((byte[]) obj, i3, i7);
                    } else if (obj instanceof Double) {
                        z = cursorWindow.putDouble(((Double) obj).doubleValue(), i3, i7);
                    } else if (obj instanceof Float) {
                        z = cursorWindow.putDouble((double) ((Float) obj).floatValue(), i3, i7);
                    } else {
                        String valueOf = String.valueOf(obj);
                        throw new IllegalArgumentException(new StringBuilder((String.valueOf(str).length() + 32) + String.valueOf(valueOf).length()).append("Unsupported object for column ").append(str).append(": ").append(valueOf).toString());
                    }
                }
                if (z) {
                    i5 = i3;
                    i6 = 0;
                    cursorWindow2 = cursorWindow;
                } else if (i4 != 0) {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                } else {
                    Log.d("DataHolder", new StringBuilder(74).append("Couldn't populate window data for row ").append(i3).append(" - allocating new window.").toString());
                    cursorWindow.freeLastRow();
                    CursorWindow cursorWindow3 = new CursorWindow(false);
                    cursorWindow3.setStartPosition(i3);
                    cursorWindow3.setNumColumns(com_google_android_gms_common_data_DataHolder_zza.zzaFD.length);
                    arrayList.add(cursorWindow3);
                    i5 = i3 - 1;
                    cursorWindow2 = cursorWindow3;
                    i6 = 1;
                }
                i4 = i6;
                cursorWindow = cursorWindow2;
                i3 = i5 + 1;
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                i3 = arrayList.size();
                while (i2 < i3) {
                    ((CursorWindow) arrayList.get(i2)).close();
                    i2++;
                }
                throw runtimeException;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static DataHolder zzau(int i) {
        return new DataHolder(zzaFK, i, null);
    }

    private final void zzh(String str, int i) {
        if (this.zzaFE == null || !this.zzaFE.containsKey(str)) {
            String str2 = "No such column: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zzaFI) {
            throw new CursorIndexOutOfBoundsException(i, this.zzaFI);
        }
    }

    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (int i = 0; i < this.zzaFF.length; i++) {
                    this.zzaFF[i].close();
                }
            }
        }
    }

    protected final void finalize() throws Throwable {
        if (this.zzaFJ && this.zzaFF.length > 0 && !isClosed()) {
            close();
            String valueOf = String.valueOf(toString());
            Log.e("DataBuffer", new StringBuilder(String.valueOf(valueOf).length() + 178).append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ").append(valueOf).append(")").toString());
        }
        super.finalize();
    }

    public final int getCount() {
        return this.zzaFI;
    }

    public final int getStatusCode() {
        return this.zzaxw;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaFD, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaFF, i, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaxw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaFG, false);
        zzd.zzc(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, this.zzakw);
        zzd.zzI(parcel, zze);
    }

    public final void zza(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zzh(str, i);
        this.zzaFF[i2].copyStringToBuffer(i, this.zzaFE.getInt(str), charArrayBuffer);
    }

    public final int zzat(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.zzaFI;
        zzbr.zzae(z);
        while (i2 < this.zzaFH.length) {
            if (i < this.zzaFH[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.zzaFH.length ? i2 - 1 : i2;
    }

    public final long zzb(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getLong(i, this.zzaFE.getInt(str));
    }

    public final int zzc(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getInt(i, this.zzaFE.getInt(str));
    }

    public final boolean zzcv(String str) {
        return this.zzaFE.containsKey(str);
    }

    public final String zzd(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getString(i, this.zzaFE.getInt(str));
    }

    public final boolean zze(String str, int i, int i2) {
        zzh(str, i);
        return Long.valueOf(this.zzaFF[i2].getLong(i, this.zzaFE.getInt(str))).longValue() == 1;
    }

    public final float zzf(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getFloat(i, this.zzaFE.getInt(str));
    }

    public final byte[] zzg(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getBlob(i, this.zzaFE.getInt(str));
    }

    public final boolean zzh(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].isNull(i, this.zzaFE.getInt(str));
    }

    public final Bundle zzqL() {
        return this.zzaFG;
    }

    public final void zzqP() {
        int i;
        int i2 = 0;
        this.zzaFE = new Bundle();
        for (i = 0; i < this.zzaFD.length; i++) {
            this.zzaFE.putInt(this.zzaFD[i], i);
        }
        this.zzaFH = new int[this.zzaFF.length];
        i = 0;
        while (i2 < this.zzaFF.length) {
            this.zzaFH[i2] = i;
            i += this.zzaFF[i2].getNumRows() - (i - this.zzaFF[i2].getStartPosition());
            i2++;
        }
        this.zzaFI = i;
    }
}
