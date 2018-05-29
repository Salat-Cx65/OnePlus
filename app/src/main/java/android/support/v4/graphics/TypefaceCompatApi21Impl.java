package android.support.v4.graphics;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.provider.FontsContractCompat.FontInfo;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RequiresApi(21)
@RestrictTo({Scope.LIBRARY_GROUP})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor fd) {
        try {
            String path = Os.readlink("/proc/self/fd/" + fd.getFd());
            return OsConstants.S_ISREG(Os.stat(path).st_mode) ? new File(path) : null;
        } catch (ErrnoException e) {
            return null;
        }
    }

    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts, int style) {
        Throwable th;
        if (fonts.length < 1) {
            return null;
        }
        FontInfo bestFont = findBestInfo(fonts, style);
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(bestFont.getUri(), "r", cancellationSignal);
            Throwable th2 = null;
            try {
                File file = getFile(pfd);
                Typeface createFromInputStream;
                if (file == null || !file.canRead()) {
                    FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                    th = null;
                    try {
                        createFromInputStream = super.createFromInputStream(context, fis);
                        if (pfd == null) {
                            return createFromInputStream;
                        }
                        if (th2 != null) {
                            try {
                                pfd.close();
                                return createFromInputStream;
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                                return createFromInputStream;
                            }
                        }
                        pfd.close();
                        return createFromInputStream;
                    } catch (Throwable th4) {
                        if (fis != null) {
                            if (th3 != null) {
                                try {
                                    fis.close();
                                } catch (Throwable th5) {
                                    Throwable th6 = th5;
                                    th3 = th2;
                                    if (pfd != null) {
                                        if (th3 == null) {
                                            pfd.close();
                                        } else {
                                            try {
                                                pfd.close();
                                            } catch (Throwable th22) {
                                                th3.addSuppressed(th22);
                                            }
                                        }
                                    }
                                    throw th6;
                                }
                            } else {
                                fis.close();
                            }
                        }
                    }
                    if (fis != null) {
                        if (th3 != null) {
                            try {
                                fis.close();
                            } catch (Throwable th52) {
                                Throwable th62 = th52;
                                th3 = th22;
                                if (pfd != null) {
                                    if (th3 == null) {
                                        pfd.close();
                                    } else {
                                        try {
                                            pfd.close();
                                        } catch (Throwable th222) {
                                            th3.addSuppressed(th222);
                                        }
                                    }
                                }
                                throw th62;
                            }
                        } else {
                            fis.close();
                        }
                    }
                } else {
                    createFromInputStream = Typeface.createFromFile(file);
                    if (pfd == null) {
                        return createFromInputStream;
                    }
                    if (th222 != null) {
                        try {
                            pfd.close();
                            return createFromInputStream;
                        } catch (Throwable th32) {
                            th222.addSuppressed(th32);
                            return createFromInputStream;
                        }
                    }
                    pfd.close();
                    return createFromInputStream;
                }
            } catch (Throwable th322) {
                Throwable th7 = th322;
                th322 = th62;
                th62 = th7;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
