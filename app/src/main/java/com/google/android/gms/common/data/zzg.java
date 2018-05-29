package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zzg<T> extends AbstractDataBuffer<T> {
    private boolean zzaFQ;
    private ArrayList<Integer> zzaFR;

    protected zzg(DataHolder dataHolder) {
        super(dataHolder);
        this.zzaFQ = false;
    }

    private final int zzaw(int i) {
        if (i >= 0 && i < this.zzaFR.size()) {
            return ((Integer) this.zzaFR.get(i)).intValue();
        }
        throw new IllegalArgumentException(new StringBuilder(53).append("Position ").append(i).append(" is out of bounds for this buffer").toString());
    }

    private final void zzqR() {
        synchronized (this) {
            if (!this.zzaFQ) {
                int i = this.zzaCZ.zzaFI;
                this.zzaFR = new ArrayList();
                if (i > 0) {
                    this.zzaFR.add(Integer.valueOf(0));
                    String zzqQ = zzqQ();
                    Object zzd = this.zzaCZ.zzd(zzqQ, 0, this.zzaCZ.zzat(0));
                    int i2 = 1;
                    while (i2 < i) {
                        int zzat = this.zzaCZ.zzat(i2);
                        String zzd2 = this.zzaCZ.zzd(zzqQ, i2, zzat);
                        if (zzd2 == null) {
                            throw new NullPointerException(new StringBuilder(String.valueOf(zzqQ).length() + 78).append("Missing value for markerColumn: ").append(zzqQ).append(", at row: ").append(i2).append(", for window: ").append(zzat).toString());
                        }
                        if (zzd2.equals(r1)) {
                            zzd2 = r1;
                        } else {
                            this.zzaFR.add(Integer.valueOf(i2));
                        }
                        i2++;
                        String str = zzd2;
                    }
                }
                this.zzaFQ = true;
            }
        }
    }

    public final T get(int i) {
        int i2;
        zzqR();
        int zzaw = zzaw(i);
        if (i < 0 || i == this.zzaFR.size()) {
            i2 = 0;
        } else {
            i2 = i == this.zzaFR.size() + -1 ? this.zzaCZ.zzaFI - ((Integer) this.zzaFR.get(i)).intValue() : ((Integer) this.zzaFR.get(i + 1)).intValue() - ((Integer) this.zzaFR.get(i)).intValue();
            if (i2 == 1) {
                this.zzaCZ.zzat(zzaw(i));
            }
        }
        return zzi(zzaw, i2);
    }

    public int getCount() {
        zzqR();
        return this.zzaFR.size();
    }

    protected abstract T zzi(int i, int i2);

    protected abstract String zzqQ();
}
