package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.internal.zzbgy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class ImageManager {
    private static final Object zzaFT;
    private static HashSet<Uri> zzaFU;
    private static ImageManager zzaFV;
    private final Context mContext;
    private final Handler mHandler;
    private final ExecutorService zzaFW;
    private final zza zzaFX;
    private final zzbgy zzaFY;
    private final Map<zza, ImageReceiver> zzaFZ;
    private final Map<Uri, ImageReceiver> zzaGa;
    private final Map<Uri, Long> zzaGb;

    @KeepName
    final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<zza> zzaGc;

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.zzaGc = new ArrayList();
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            ImageManager.this.zzaFW.execute(new zzb(this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zzb(zza com_google_android_gms_common_images_zza) {
            com.google.android.gms.common.internal.zzc.zzcz("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzaGc.add(com_google_android_gms_common_images_zza);
        }

        public final void zzc(zza com_google_android_gms_common_images_zza) {
            com.google.android.gms.common.internal.zzc.zzcz("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzaGc.remove(com_google_android_gms_common_images_zza);
        }

        public final void zzqT() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
            ImageManager.this.mContext.sendBroadcast(intent);
        }
    }

    public static interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    final class zzb implements Runnable {
        private final Uri mUri;
        private final ParcelFileDescriptor zzaGe;

        public zzb(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.zzaGe = parcelFileDescriptor;
        }

        public final void run() {
            String str = "LoadBitmapFromDiskRunnable can't be executed in the main thread";
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                String valueOf = String.valueOf(Thread.currentThread());
                String valueOf2 = String.valueOf(Looper.getMainLooper().getThread());
                Log.e("Asserts", new StringBuilder((String.valueOf(valueOf).length() + 56) + String.valueOf(valueOf2).length()).append("checkNotMainThread: current thread ").append(valueOf).append(" IS the main thread ").append(valueOf2).append("!").toString());
                throw new IllegalStateException(str);
            }
            boolean z = false;
            Bitmap bitmap = null;
            if (this.zzaGe != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.zzaGe.getFileDescriptor());
                } catch (Throwable e) {
                    String valueOf3 = String.valueOf(this.mUri);
                    Log.e("ImageManager", new StringBuilder(String.valueOf(valueOf3).length() + 34).append("OOM while loading bitmap for uri: ").append(valueOf3).toString(), e);
                    z = true;
                }
                try {
                    this.zzaGe.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ImageManager.this.mHandler.post(new zzd(this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                String valueOf4 = String.valueOf(this.mUri);
                Log.w("ImageManager", new StringBuilder(String.valueOf(valueOf4).length() + 32).append("Latch interrupted while posting ").append(valueOf4).toString());
            }
        }
    }

    final class zzc implements Runnable {
        private final zza zzaGf;

        public zzc(zza com_google_android_gms_common_images_zza) {
            this.zzaGf = com_google_android_gms_common_images_zza;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zzcz("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zzaFZ.get(this.zzaGf);
            if (imageReceiver != null) {
                ImageManager.this.zzaFZ.remove(this.zzaGf);
                imageReceiver.zzc(this.zzaGf);
            }
            zzb com_google_android_gms_common_images_zzb = this.zzaGf.zzaGh;
            if (com_google_android_gms_common_images_zzb.uri == null) {
                this.zzaGf.zza(ImageManager.this.mContext, ImageManager.this.zzaFY, true);
                return;
            }
            Bitmap zza = ImageManager.this.zza(com_google_android_gms_common_images_zzb);
            if (zza != null) {
                this.zzaGf.zza(ImageManager.this.mContext, zza, true);
                return;
            }
            Long l = (Long) ImageManager.this.zzaGb.get(com_google_android_gms_common_images_zzb.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.zzaGf.zza(ImageManager.this.mContext, ImageManager.this.zzaFY, true);
                    return;
                }
                ImageManager.this.zzaGb.remove(com_google_android_gms_common_images_zzb.uri);
            }
            this.zzaGf.zza(ImageManager.this.mContext, ImageManager.this.zzaFY);
            imageReceiver = (ImageReceiver) ImageManager.this.zzaGa.get(com_google_android_gms_common_images_zzb.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(com_google_android_gms_common_images_zzb.uri);
                ImageManager.this.zzaGa.put(com_google_android_gms_common_images_zzb.uri, imageReceiver);
            }
            imageReceiver.zzb(this.zzaGf);
            if (!(this.zzaGf instanceof zzd)) {
                ImageManager.this.zzaFZ.put(this.zzaGf, imageReceiver);
            }
            synchronized (zzaFT) {
                if (!zzaFU.contains(com_google_android_gms_common_images_zzb.uri)) {
                    zzaFU.add(com_google_android_gms_common_images_zzb.uri);
                    imageReceiver.zzqT();
                }
            }
        }
    }

    final class zzd implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private boolean zzaGg;
        private final CountDownLatch zztM;

        public zzd(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzaGg = z;
            this.zztM = countDownLatch;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zzcz("OnBitmapLoadedRunnable must be executed in the main thread");
            if (this.mBitmap != null) {
                int i = 1;
            } else {
                boolean z = false;
            }
            if (ImageManager.this.zzaFX != null) {
                if (this.zzaGg) {
                    ImageManager.this.zzaFX.evictAll();
                    System.gc();
                    this.zzaGg = false;
                    ImageManager.this.mHandler.post(this);
                    return;
                } else if (z) {
                    ImageManager.this.zzaFX.put(new zzb(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zzaGa.remove(this.mUri);
            if (imageReceiver != null) {
                ArrayList zza = imageReceiver.zzaGc;
                int size = zza.size();
                for (int i2 = 0; i2 < size; i2++) {
                    zza com_google_android_gms_common_images_zza = (zza) zza.get(i2);
                    if (z) {
                        com_google_android_gms_common_images_zza.zza(ImageManager.this.mContext, this.mBitmap, false);
                    } else {
                        ImageManager.this.zzaGb.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                        com_google_android_gms_common_images_zza.zza(ImageManager.this.mContext, ImageManager.this.zzaFY, false);
                    }
                    if (!(com_google_android_gms_common_images_zza instanceof zzd)) {
                        ImageManager.this.zzaFZ.remove(com_google_android_gms_common_images_zza);
                    }
                }
            }
            this.zztM.countDown();
            synchronized (zzaFT) {
                zzaFU.remove(this.mUri);
            }
        }
    }

    static final class zza extends LruCache<zzb, Bitmap> {
        protected final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            super.entryRemoved(z, (zzb) obj, (Bitmap) obj2, (Bitmap) obj3);
        }

        protected final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            Bitmap bitmap = (Bitmap) obj2;
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
    }

    static {
        zzaFT = new Object();
        zzaFU = new HashSet();
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.zzaFW = Executors.newFixedThreadPool(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        this.zzaFX = null;
        this.zzaFY = new zzbgy();
        this.zzaFZ = new HashMap();
        this.zzaGa = new HashMap();
        this.zzaGb = new HashMap();
    }

    public static ImageManager create(Context context) {
        if (zzaFV == null) {
            zzaFV = new ImageManager(context, false);
        }
        return zzaFV;
    }

    private final Bitmap zza(zzb com_google_android_gms_common_images_zzb) {
        return this.zzaFX == null ? null : (Bitmap) this.zzaFX.get(com_google_android_gms_common_images_zzb);
    }

    private final void zza(zza com_google_android_gms_common_images_zza) {
        com.google.android.gms.common.internal.zzc.zzcz("ImageManager.loadImage() must be called in the main thread");
        new zzc(com_google_android_gms_common_images_zza).run();
    }

    public final void loadImage(ImageView imageView, int i) {
        zza(new zzc(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zza(new zzc(imageView, uri));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        zza com_google_android_gms_common_images_zzc = new zzc(imageView, uri);
        com_google_android_gms_common_images_zzc.zzaGj = i;
        zza(com_google_android_gms_common_images_zzc);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza(new zzd(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        zza com_google_android_gms_common_images_zzd = new zzd(onImageLoadedListener, uri);
        com_google_android_gms_common_images_zzd.zzaGj = i;
        zza(com_google_android_gms_common_images_zzd);
    }
}
