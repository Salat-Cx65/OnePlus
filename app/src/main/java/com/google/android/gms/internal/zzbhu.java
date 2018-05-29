package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class zzbhu {
    protected static <O, I> I zza(zzbhv<I, O> com_google_android_gms_internal_zzbhv_I__O, Object obj) {
        return zzbhv.zzc(com_google_android_gms_internal_zzbhv_I__O) != null ? com_google_android_gms_internal_zzbhv_I__O.convertBack(obj) : obj;
    }

    private static void zza(StringBuilder stringBuilder, zzbhv com_google_android_gms_internal_zzbhv, Object obj) {
        if (com_google_android_gms_internal_zzbhv.zzaIJ == 11) {
            stringBuilder.append(((zzbhu) com_google_android_gms_internal_zzbhv.zzaIP.cast(obj)).toString());
        } else if (com_google_android_gms_internal_zzbhv.zzaIJ == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(zzq.zzcK((String) obj));
            stringBuilder.append("\"");
        } else {
            stringBuilder.append(obj);
        }
    }

    private static void zza(StringBuilder stringBuilder, zzbhv com_google_android_gms_internal_zzbhv, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(stringBuilder, com_google_android_gms_internal_zzbhv, obj);
            }
        }
        stringBuilder.append("]");
    }

    public String toString() {
        Map zzrK = zzrK();
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String str : zzrK.keySet()) {
            zzbhv com_google_android_gms_internal_zzbhv = (zzbhv) zzrK.get(str);
            if (zza(com_google_android_gms_internal_zzbhv)) {
                Object zza = zza(com_google_android_gms_internal_zzbhv, zzb(com_google_android_gms_internal_zzbhv));
                if (stringBuilder.length() == 0) {
                    stringBuilder.append("{");
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append("\"").append(str).append("\":");
                if (zza == null) {
                    stringBuilder.append("null");
                } else {
                    switch (com_google_android_gms_internal_zzbhv.zzaIL) {
                        case DetectedActivity.RUNNING:
                            stringBuilder.append("\"").append(zzd.zzg((byte[]) zza)).append("\"");
                            break;
                        case ConnectionResult.SERVICE_INVALID:
                            stringBuilder.append("\"").append(zzd.zzh((byte[]) zza)).append("\"");
                            break;
                        case ConnectionResult.DEVELOPER_ERROR:
                            zzr.zza(stringBuilder, (HashMap) zza);
                            break;
                        default:
                            if (com_google_android_gms_internal_zzbhv.zzaIK) {
                                zza(stringBuilder, com_google_android_gms_internal_zzbhv, (ArrayList) zza);
                            } else {
                                zza(stringBuilder, com_google_android_gms_internal_zzbhv, zza);
                            }
                            break;
                    }
                }
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("}");
        } else {
            stringBuilder.append("{}");
        }
        return stringBuilder.toString();
    }

    protected boolean zza(zzbhv com_google_android_gms_internal_zzbhv) {
        if (com_google_android_gms_internal_zzbhv.zzaIL != 11) {
            return zzcI(com_google_android_gms_internal_zzbhv.zzaIN);
        }
        if (com_google_android_gms_internal_zzbhv.zzaIM) {
            String str = com_google_android_gms_internal_zzbhv.zzaIN;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        str = com_google_android_gms_internal_zzbhv.zzaIN;
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected Object zzb(zzbhv com_google_android_gms_internal_zzbhv) {
        String str = com_google_android_gms_internal_zzbhv.zzaIN;
        if (com_google_android_gms_internal_zzbhv.zzaIP == null) {
            return zzcH(com_google_android_gms_internal_zzbhv.zzaIN);
        }
        zzcH(com_google_android_gms_internal_zzbhv.zzaIN);
        zzbr.zza(true, "Concrete field shouldn't be value object: %s", com_google_android_gms_internal_zzbhv.zzaIN);
        boolean z = com_google_android_gms_internal_zzbhv.zzaIM;
        try {
            char toUpperCase = Character.toUpperCase(str.charAt(0));
            str = String.valueOf(str.substring(1));
            return getClass().getMethod(new StringBuilder(String.valueOf(str).length() + 4).append("get").append(toUpperCase).append(str).toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Object zzcH(String str);

    protected abstract boolean zzcI(String str);

    public abstract Map<String, zzbhv<?, ?>> zzrK();
}
