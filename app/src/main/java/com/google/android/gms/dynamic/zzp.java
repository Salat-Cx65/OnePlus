package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.zzo;

public abstract class zzp<T> {
    private final String zzaSG;
    private T zzaSH;

    protected zzp(String str) {
        this.zzaSG = str;
    }

    protected final T zzaS(Context context) throws zzq {
        if (this.zzaSH == null) {
            zzbr.zzu(context);
            Context remoteContext = zzo.getRemoteContext(context);
            if (remoteContext == null) {
                throw new zzq("Could not get remote context.");
            }
            try {
                this.zzaSH = zzb((IBinder) remoteContext.getClassLoader().loadClass(this.zzaSG).newInstance());
            } catch (Throwable e) {
                throw new zzq("Could not load creator class.", e);
            } catch (Throwable e2) {
                throw new zzq("Could not instantiate creator.", e2);
            } catch (Throwable e22) {
                throw new zzq("Could not access creator.", e22);
            }
        }
        return this.zzaSH;
    }

    protected abstract T zzb(IBinder iBinder);
}
