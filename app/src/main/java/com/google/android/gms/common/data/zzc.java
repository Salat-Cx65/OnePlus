package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzbr;
import java.util.Arrays;

public class zzc {
    protected final DataHolder zzaCZ;
    private int zzaFA;
    protected int zzaFz;

    public zzc(DataHolder dataHolder, int i) {
        this.zzaCZ = (DataHolder) zzbr.zzu(dataHolder);
        zzar(i);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc com_google_android_gms_common_data_zzc = (zzc) obj;
        return zzbh.equal(Integer.valueOf(com_google_android_gms_common_data_zzc.zzaFz), Integer.valueOf(this.zzaFz)) && zzbh.equal(Integer.valueOf(com_google_android_gms_common_data_zzc.zzaFA), Integer.valueOf(this.zzaFA)) && com_google_android_gms_common_data_zzc.zzaCZ == this.zzaCZ;
    }

    protected final boolean getBoolean(String str) {
        return this.zzaCZ.zze(str, this.zzaFz, this.zzaFA);
    }

    protected final byte[] getByteArray(String str) {
        return this.zzaCZ.zzg(str, this.zzaFz, this.zzaFA);
    }

    protected final float getFloat(String str) {
        return this.zzaCZ.zzf(str, this.zzaFz, this.zzaFA);
    }

    protected final int getInteger(String str) {
        return this.zzaCZ.zzc(str, this.zzaFz, this.zzaFA);
    }

    protected final long getLong(String str) {
        return this.zzaCZ.zzb(str, this.zzaFz, this.zzaFA);
    }

    protected final String getString(String str) {
        return this.zzaCZ.zzd(str, this.zzaFz, this.zzaFA);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaFz), Integer.valueOf(this.zzaFA), this.zzaCZ});
    }

    public boolean isDataValid() {
        return !this.zzaCZ.isClosed();
    }

    protected final void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.zzaCZ.zza(str, this.zzaFz, this.zzaFA, charArrayBuffer);
    }

    protected final void zzar(int i) {
        boolean z = i >= 0 && i < this.zzaCZ.zzaFI;
        zzbr.zzae(z);
        this.zzaFz = i;
        this.zzaFA = this.zzaCZ.zzat(this.zzaFz);
    }

    public final boolean zzcv(String str) {
        return this.zzaCZ.zzcv(str);
    }

    protected final Uri zzcw(String str) {
        String zzd = this.zzaCZ.zzd(str, this.zzaFz, this.zzaFA);
        return zzd == null ? null : Uri.parse(zzd);
    }

    protected final boolean zzcx(String str) {
        return this.zzaCZ.zzh(str, this.zzaFz, this.zzaFA);
    }
}
