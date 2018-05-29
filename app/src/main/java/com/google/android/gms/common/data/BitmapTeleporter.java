package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class BitmapTeleporter extends zza implements ReflectedParcelable {
    public static final Creator<BitmapTeleporter> CREATOR;
    private ParcelFileDescriptor zzTR;
    private Bitmap zzaFt;
    private boolean zzaFu;
    private File zzaFv;
    private int zzakw;
    private int zzamt;

    static {
        CREATOR = new zza();
    }

    BitmapTeleporter(int i, ParcelFileDescriptor parcelFileDescriptor, int i2) {
        this.zzakw = i;
        this.zzTR = parcelFileDescriptor;
        this.zzamt = i2;
        this.zzaFt = null;
        this.zzaFu = false;
    }

    public BitmapTeleporter(Bitmap bitmap) {
        this.zzakw = 1;
        this.zzTR = null;
        this.zzamt = 0;
        this.zzaFt = bitmap;
        this.zzaFu = true;
    }

    private static void zza(Closeable closeable) {
        try {
            closeable.close();
        } catch (Throwable e) {
            Log.w("BitmapTeleporter", "Could not close stream", e);
        }
    }

    private final FileOutputStream zzqN() {
        if (this.zzaFv == null) {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        }
        try {
            File createTempFile = File.createTempFile("teleporter", ".tmp", this.zzaFv);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
                this.zzTR = ParcelFileDescriptor.open(createTempFile, 268435456);
                createTempFile.delete();
                return fileOutputStream;
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("Temporary file is somehow already deleted");
            }
        } catch (Throwable e2) {
            throw new IllegalStateException("Could not create temporary file", e2);
        }
    }

    public final void release() {
        if (!this.zzaFu) {
            try {
                this.zzTR.close();
            } catch (Throwable e) {
                Log.w("BitmapTeleporter", "Could not close PFD", e);
            }
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzTR == null) {
            Bitmap bitmap = this.zzaFt;
            Buffer allocate = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer(allocate);
            byte[] array = allocate.array();
            Closeable dataOutputStream = new DataOutputStream(zzqN());
            try {
                dataOutputStream.writeInt(array.length);
                dataOutputStream.writeInt(bitmap.getWidth());
                dataOutputStream.writeInt(bitmap.getHeight());
                dataOutputStream.writeUTF(bitmap.getConfig().toString());
                dataOutputStream.write(array);
                zza(dataOutputStream);
            } catch (Throwable e) {
                throw new IllegalStateException("Could not write into unlinked file", e);
            } catch (Throwable th) {
            }
        }
        int i2 = i | 1;
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzTR, i2, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzamt);
        zzd.zzI(parcel, zze);
        this.zzTR = null;
    }

    public final void zzc(File file) {
        if (file == null) {
            throw new NullPointerException("Cannot set null temp directory");
        }
        this.zzaFv = file;
    }

    public final Bitmap zzqM() {
        if (!this.zzaFu) {
            Closeable dataInputStream = new DataInputStream(new AutoCloseInputStream(this.zzTR));
            try {
                byte[] bArr = new byte[dataInputStream.readInt()];
                int readInt = dataInputStream.readInt();
                int readInt2 = dataInputStream.readInt();
                Config valueOf = Config.valueOf(dataInputStream.readUTF());
                dataInputStream.read(bArr);
                zza(dataInputStream);
                Buffer wrap = ByteBuffer.wrap(bArr);
                dataInputStream = Bitmap.createBitmap(readInt, readInt2, valueOf);
                dataInputStream.copyPixelsFromBuffer(wrap);
                this.zzaFt = dataInputStream;
                this.zzaFu = true;
            } catch (Throwable e) {
                throw new IllegalStateException("Could not read from parcel file descriptor", e);
            } catch (Throwable th) {
            }
        }
        return this.zzaFt;
    }
}
