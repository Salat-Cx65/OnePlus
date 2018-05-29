package com.google.android.gms.common.data;

import java.util.NoSuchElementException;

public final class zzh<T> extends zzb<T> {
    private T zzaFS;

    public zzh(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    public final T next() {
        if (hasNext()) {
            this.zzaFx++;
            if (this.zzaFx == 0) {
                this.zzaFS = this.zzaFw.get(0);
                if (!(this.zzaFS instanceof zzc)) {
                    String valueOf = String.valueOf(this.zzaFS.getClass());
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 44).append("DataBuffer reference of type ").append(valueOf).append(" is not movable").toString());
                }
            }
            ((zzc) this.zzaFS).zzar(this.zzaFx);
            return this.zzaFS;
        }
        throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(this.zzaFx).toString());
    }
}
