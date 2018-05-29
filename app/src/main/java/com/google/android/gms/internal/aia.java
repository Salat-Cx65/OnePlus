package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class aia<M extends ahz<M>, T> {
    public final int tag;
    private int type;
    protected final Class<T> zzcmA;
    protected final boolean zzcuX;

    private aia(int i, Class<T> cls, int i2, boolean z) {
        this.type = 11;
        this.zzcmA = cls;
        this.tag = i2;
        this.zzcuX = false;
    }

    public static <M extends ahz<M>, T extends aif> aia<M, T> zza(int i, Class<T> cls, long j) {
        return new aia(11, cls, (int) j, false);
    }

    private final Object zzb(ahw com_google_android_gms_internal_ahw) {
        Class cls = this.zzcmA;
        try {
            aif com_google_android_gms_internal_aif;
            switch (this.type) {
                case ConnectionResult.DEVELOPER_ERROR:
                    com_google_android_gms_internal_aif = (aif) cls.newInstance();
                    com_google_android_gms_internal_ahw.zza(com_google_android_gms_internal_aif, this.tag >>> 3);
                    return com_google_android_gms_internal_aif;
                case ConnectionResult.LICENSE_CHECK_FAILED:
                    com_google_android_gms_internal_aif = (aif) cls.newInstance();
                    com_google_android_gms_internal_ahw.zzb(com_google_android_gms_internal_aif);
                    return com_google_android_gms_internal_aif;
                default:
                    throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
            }
        } catch (Throwable e) {
            String valueOf = String.valueOf(cls);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Error creating instance of class ").append(valueOf).toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(cls);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Error creating instance of class ").append(valueOf).toString(), e2);
        } catch (Throwable e22) {
            throw new IllegalArgumentException("Error reading extension field", e22);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof aia)) {
            return false;
        }
        aia com_google_android_gms_internal_aia = (aia) obj;
        return this.type == com_google_android_gms_internal_aia.type && this.zzcmA == com_google_android_gms_internal_aia.zzcmA && this.tag == com_google_android_gms_internal_aia.tag;
    }

    public final int hashCode() {
        return (((((this.type + 1147) * 31) + this.zzcmA.hashCode()) * 31) + this.tag) * 31;
    }

    final T zzY(List<aii> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.zzcmA.cast(zzb(ahw.zzI(((aii) list.get(list.size() - 1)).zzbww)));
    }

    protected final void zza(Object obj, ahx com_google_android_gms_internal_ahx) {
        try {
            com_google_android_gms_internal_ahx.zzct(this.tag);
            switch (this.type) {
                case ConnectionResult.DEVELOPER_ERROR:
                    int i = this.tag >>> 3;
                    ((aif) obj).zza(com_google_android_gms_internal_ahx);
                    com_google_android_gms_internal_ahx.zzt(i, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
                case ConnectionResult.LICENSE_CHECK_FAILED:
                    com_google_android_gms_internal_ahx.zzc((aif) obj);
                default:
                    throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    protected final int zzav(Object obj) {
        int i = this.tag >>> 3;
        switch (this.type) {
            case ConnectionResult.DEVELOPER_ERROR:
                return (ahx.zzcs(i) << 1) + ((aif) obj).zzMl();
            case ConnectionResult.LICENSE_CHECK_FAILED:
                return ahx.zzb(i, (aif) obj);
            default:
                throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
        }
    }
}
