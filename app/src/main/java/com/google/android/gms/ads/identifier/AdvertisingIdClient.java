package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.AutoScrollHelper;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzo;
import com.google.android.gms.internal.zzfd;
import com.google.android.gms.internal.zzfe;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@KeepForSdkWithMembers
public class AdvertisingIdClient {
    private final Context mContext;
    @Nullable
    private com.google.android.gms.common.zza zzsA;
    @Nullable
    private zzfd zzsB;
    private boolean zzsC;
    private Object zzsD;
    @Nullable
    private zza zzsE;
    private long zzsF;

    public static final class Info {
        private final String zzsL;
        private final boolean zzsM;

        public Info(String str, boolean z) {
            this.zzsL = str;
            this.zzsM = z;
        }

        public final String getId() {
            return this.zzsL;
        }

        public final boolean isLimitAdTrackingEnabled() {
            return this.zzsM;
        }

        public final String toString() {
            String str = this.zzsL;
            return new StringBuilder(String.valueOf(str).length() + 7).append("{").append(str).append("}").append(this.zzsM).toString();
        }
    }

    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzsH;
        private long zzsI;
        CountDownLatch zzsJ;
        boolean zzsK;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzsH = new WeakReference(advertisingIdClient);
            this.zzsI = j;
            this.zzsJ = new CountDownLatch(1);
            this.zzsK = false;
            start();
        }

        private final void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.zzsH.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzsK = true;
            }
        }

        public final void run() {
            try {
                if (!this.zzsJ.await(this.zzsI, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException e) {
                disconnect();
            }
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000, false);
    }

    public AdvertisingIdClient(Context context, long j, boolean z) {
        this.zzsD = new Object();
        zzbr.zzu(context);
        if (z) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            this.mContext = context;
        } else {
            this.mContext = context;
        }
        this.zzsC = false;
        this.zzsF = j;
    }

    @Nullable
    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        float f = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        boolean z = false;
        try {
            Context remoteContext = zzo.getRemoteContext(context);
            if (remoteContext != null) {
                SharedPreferences sharedPreferences = remoteContext.getSharedPreferences("google_ads_flags", 0);
                z = sharedPreferences.getBoolean("gads:ad_id_app_context:enabled", false);
                f = sharedPreferences.getFloat("gads:ad_id_app_context:ping_ratio", AutoScrollHelper.RELATIVE_UNSPECIFIED);
            }
        } catch (Throwable e) {
            Log.w("AdvertisingIdClient", "Error while reading from SharedPreferences ", e);
        }
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1, z);
        try {
            advertisingIdClient.start(false);
            Info info = advertisingIdClient.getInfo();
            advertisingIdClient.zza(info, z, f, null);
            advertisingIdClient.finish();
            f = info;
            return f;
        } catch (Throwable e2) {
            advertisingIdClient.zza(null, z, f, e2);
            advertisingIdClient.finish();
            return null;
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    private final void start(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzbr.zzcG("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzsC) {
                finish();
            }
            this.zzsA = zzd(this.mContext);
            this.zzsB = zza(this.mContext, this.zzsA);
            this.zzsC = true;
            if (z) {
                zzai();
            }
        }
    }

    private static zzfd zza(Context context, com.google.android.gms.common.zza com_google_android_gms_common_zza) throws IOException {
        try {
            return zzfe.zzc(com_google_android_gms_common_zza.zza(10000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        }
    }

    private final void zza(Info info, boolean z, float f, Throwable th) {
        if (Math.random() <= ((double) f)) {
            Bundle bundle = new Bundle();
            bundle.putString("app_context", z ? "1" : "0");
            if (info != null) {
                bundle.putString("limit_ad_tracking", info.isLimitAdTrackingEnabled() ? "1" : "0");
            }
            if (!(info == null || info.getId() == null)) {
                bundle.putString("ad_id_size", Integer.toString(info.getId().length()));
            }
            if (th != null) {
                bundle.putString("error", th.getClass().getName());
            }
            Builder buildUpon = Uri.parse("https://pagead2.googlesyndication.com/pagead/gen_204?id=gmob-apps").buildUpon();
            for (String str : bundle.keySet()) {
                buildUpon.appendQueryParameter(str, bundle.getString(str));
            }
            new zza(this, buildUpon.build().toString()).start();
        }
    }

    private final void zzai() {
        synchronized (this.zzsD) {
            if (this.zzsE != null) {
                this.zzsE.zzsJ.countDown();
                try {
                    this.zzsE.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzsF > 0) {
                this.zzsE = new zza(this, this.zzsF);
            }
        }
    }

    private static com.google.android.gms.common.zza zzd(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            switch (zze.zzoU().isGooglePlayServicesAvailable(context)) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    Object com_google_android_gms_common_zza = new com.google.android.gms.common.zza();
                    Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                    intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                    if (com.google.android.gms.common.stats.zza.zzrT().zza(context, intent, com_google_android_gms_common_zza, 1)) {
                        return com_google_android_gms_common_zza;
                    }
                    throw new IOException("Connection failure");
                default:
                    throw new IOException("Google Play services not available");
            }
        } catch (NameNotFoundException e) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public void finish() {
        zzbr.zzcG("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext == null || this.zzsA == null) {
                return;
            }
            try {
                if (this.zzsC) {
                    com.google.android.gms.common.stats.zza.zzrT();
                    this.mContext.unbindService(this.zzsA);
                }
            } catch (Throwable e) {
                Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", e);
            } catch (Throwable e2) {
                Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", e2);
            }
            this.zzsC = false;
            this.zzsB = null;
            this.zzsA = null;
        }
    }

    public Info getInfo() throws IOException {
        Info info;
        zzbr.zzcG("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            try {
                if (!this.zzsC) {
                    synchronized (this.zzsD) {
                        if (this.zzsE == null || !this.zzsE.zzsK) {
                            throw new IOException("AdvertisingIdClient is not connected.");
                        }
                    }
                    start(false);
                    if (!this.zzsC) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                }
                zzbr.zzu(this.zzsA);
                zzbr.zzu(this.zzsB);
                info = new Info(this.zzsB.getId(), this.zzsB.zzb(true));
            } catch (Throwable e) {
                Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                throw new IOException("Remote exception");
            } catch (Throwable e2) {
                throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
            } catch (Throwable th) {
            }
        }
        zzai();
        return info;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        start(true);
    }
}
