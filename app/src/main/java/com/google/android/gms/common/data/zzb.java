package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzbr;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> zzaFw;
    protected int zzaFx;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zzaFw = (DataBuffer) zzbr.zzu(dataBuffer);
        this.zzaFx = -1;
    }

    public boolean hasNext() {
        return this.zzaFx < this.zzaFw.getCount() + -1;
    }

    public T next() {
        if (hasNext()) {
            DataBuffer dataBuffer = this.zzaFw;
            int i = this.zzaFx + 1;
            this.zzaFx = i;
            return dataBuffer.get(i);
        }
        throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(this.zzaFx).toString());
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
