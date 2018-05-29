package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@RestrictTo({Scope.LIBRARY_GROUP})
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        int i = 0;
        while (i < 100) {
            File file = new File(context.getCacheDir(), prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    @android.support.annotation.RequiresApi(19)
    private static java.nio.ByteBuffer mmap(java.io.File r11_file) {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(java.io.File):java.nio.ByteBuffer");
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Try/catch wrap count limit reached in android.support.v4.graphics.TypefaceCompatUtil.mmap(java.io.File):java.nio.ByteBuffer
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:54)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:40)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:16)
	at jadx.core.ProcessClass.process(ProcessClass.java:22)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:209)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:133)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
*/
        /*
        r8 = 0;
        r7 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0024 }
        r7.<init>(r11);	 Catch:{ IOException -> 0x0024 }
        r9 = 0;
        r0 = r7.getChannel();	 Catch:{ Throwable -> 0x002b, all -> 0x0042 }
        r4 = r0.size();	 Catch:{ Throwable -> 0x002b, all -> 0x0042 }
        r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY;	 Catch:{ Throwable -> 0x002b, all -> 0x0042 }
        r2 = 0;
        r1 = r0.map(r1, r2, r4);	 Catch:{ Throwable -> 0x002b, all -> 0x0042 }
        if (r7 == 0) goto L_0x001e;
    L_0x0019:
        if (r8 == 0) goto L_0x0027;
    L_0x001b:
        r7.close();	 Catch:{ Throwable -> 0x001f }
    L_0x001e:
        return r1;
    L_0x001f:
        r2 = move-exception;
        r9.addSuppressed(r2);	 Catch:{ IOException -> 0x0024 }
        goto L_0x001e;
    L_0x0024:
        r6 = move-exception;
        r1 = r8;
        goto L_0x001e;
    L_0x0027:
        r7.close();	 Catch:{ IOException -> 0x0024 }
        goto L_0x001e;
    L_0x002b:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x002d }
    L_0x002d:
        r2 = move-exception;
        r10 = r2;
        r2 = r1;
        r1 = r10;
    L_0x0031:
        if (r7 == 0) goto L_0x0038;
    L_0x0033:
        if (r2 == 0) goto L_0x003e;
    L_0x0035:
        r7.close();	 Catch:{ Throwable -> 0x0039 }
    L_0x0038:
        throw r1;	 Catch:{ IOException -> 0x0024 }
    L_0x0039:
        r3 = move-exception;
        r2.addSuppressed(r3);	 Catch:{ IOException -> 0x0024 }
        goto L_0x0038;
    L_0x003e:
        r7.close();	 Catch:{ IOException -> 0x0024 }
        goto L_0x0038;
    L_0x0042:
        r1 = move-exception;
        r2 = r8;
        goto L_0x0031;
        */
    }

    @android.support.annotation.RequiresApi(19)
    public static java.nio.ByteBuffer mmap(android.content.Context r13_context, android.os.CancellationSignal r14_cancellationSignal, android.net.Uri r15_uri) {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Try/catch wrap count limit reached in android.support.v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:54)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:40)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:16)
	at jadx.core.ProcessClass.process(ProcessClass.java:22)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:209)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:133)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
*/
        /*
        r9 = r13.getContentResolver();
        r1 = "r";
        r8 = r9.openFileDescriptor(r15, r1, r14);	 Catch:{ IOException -> 0x0047 }
        r11 = 0;
        r7 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
        r1 = r8.getFileDescriptor();	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
        r7.<init>(r1);	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
        r10 = 0;
        r0 = r7.getChannel();	 Catch:{ Throwable -> 0x005a, all -> 0x007a }
        r4 = r0.size();	 Catch:{ Throwable -> 0x005a, all -> 0x007a }
        r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY;	 Catch:{ Throwable -> 0x005a, all -> 0x007a }
        r2 = 0;
        r1 = r0.map(r1, r2, r4);	 Catch:{ Throwable -> 0x005a, all -> 0x007a }
        if (r7 == 0) goto L_0x002c;
    L_0x0027:
        if (r10 == 0) goto L_0x004a;
    L_0x0029:
        r7.close();	 Catch:{ Throwable -> 0x0034, all -> 0x004e }
    L_0x002c:
        if (r8 == 0) goto L_0x0033;
    L_0x002e:
        if (r11 == 0) goto L_0x0056;
    L_0x0030:
        r8.close();	 Catch:{ Throwable -> 0x0051 }
    L_0x0033:
        return r1;
    L_0x0034:
        r2 = move-exception;
        r10.addSuppressed(r2);	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
        goto L_0x002c;
    L_0x0039:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x003b }
    L_0x003b:
        r2 = move-exception;
        r12 = r2;
        r2 = r1;
        r1 = r12;
    L_0x003f:
        if (r8 == 0) goto L_0x0046;
    L_0x0041:
        if (r2 == 0) goto L_0x0076;
    L_0x0043:
        r8.close();	 Catch:{ Throwable -> 0x0071 }
    L_0x0046:
        throw r1;	 Catch:{ IOException -> 0x0047 }
    L_0x0047:
        r6 = move-exception;
        r1 = 0;
        goto L_0x0033;
    L_0x004a:
        r7.close();	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
        goto L_0x002c;
    L_0x004e:
        r1 = move-exception;
        r2 = r11;
        goto L_0x003f;
    L_0x0051:
        r2 = move-exception;
        r11.addSuppressed(r2);	 Catch:{ IOException -> 0x0047 }
        goto L_0x0033;
    L_0x0056:
        r8.close();	 Catch:{ IOException -> 0x0047 }
        goto L_0x0033;
    L_0x005a:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x005c }
    L_0x005c:
        r2 = move-exception;
        r12 = r2;
        r2 = r1;
        r1 = r12;
    L_0x0060:
        if (r7 == 0) goto L_0x0067;
    L_0x0062:
        if (r2 == 0) goto L_0x006d;
    L_0x0064:
        r7.close();	 Catch:{ Throwable -> 0x0068, all -> 0x004e }
    L_0x0067:
        throw r1;	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
    L_0x0068:
        r3 = move-exception;
        r2.addSuppressed(r3);	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
        goto L_0x0067;
    L_0x006d:
        r7.close();	 Catch:{ Throwable -> 0x0039, all -> 0x004e }
        goto L_0x0067;
    L_0x0071:
        r3 = move-exception;
        r2.addSuppressed(r3);	 Catch:{ IOException -> 0x0047 }
        goto L_0x0046;
    L_0x0076:
        r8.close();	 Catch:{ IOException -> 0x0047 }
        goto L_0x0046;
    L_0x007a:
        r1 = move-exception;
        r2 = r10;
        goto L_0x0060;
        */
    }

    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        if (copyToFile(tmpFile, res, id)) {
            ByteBuffer mmap = mmap(tmpFile);
            tmpFile.delete();
            return mmap;
        }
        tmpFile.delete();
        return null;
    }

    public static boolean copyToFile(File file, InputStream is) {
        Closeable closeable = null;
        try {
            FileOutputStream os = new FileOutputStream(file, false);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int readLen = is.read(buffer);
                    if (readLen != -1) {
                        os.write(buffer, 0, readLen);
                    } else {
                        closeQuietly(os);
                        r2 = os;
                        return true;
                    }
                }
            } catch (IOException e) {
                e = e;
                r2 = os;
                try {
                    FileOutputStream fileOutputStream;
                    IOException e2;
                    Log.e(TAG, "Error copying resource contents to temp file: " + e2.getMessage());
                    closeQuietly(fileOutputStream);
                    return false;
                } catch (Throwable th) {
                    Throwable th2 = th;
                }
            } catch (Throwable th3) {
                th2 = th3;
                fileOutputStream = os;
                closeQuietly(fileOutputStream);
                throw th2;
            }
        } catch (IOException e3) {
            e2 = e3;
            Log.e(TAG, "Error copying resource contents to temp file: " + e2.getMessage());
            closeQuietly(fileOutputStream);
            return false;
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        is = res.openRawResource(id);
        boolean copyToFile = copyToFile(file, is);
        closeQuietly(is);
        return copyToFile;
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
