package com.google.android.gms.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class zzaj extends FilterInputStream {
    private int zzaA;

    private zzaj(InputStream inputStream) {
        super(inputStream);
        this.zzaA = 0;
    }

    public final int read() throws IOException {
        int read = super.read();
        if (read != -1) {
            this.zzaA++;
        }
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = super.read(bArr, i, i2);
        if (read != -1) {
            this.zzaA += read;
        }
        return read;
    }
}
