package android.support.v4.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontsContractCompat.FontFamilyResult;
import android.support.v4.provider.FontsContractCompat.FontInfo;
import android.support.v4.provider.FontsContractCompat.FontRequestCallback;
import android.support.v4.provider.SelfDestructiveThread.ReplyCallback;
import android.support.v4.util.LruCache;
import android.support.v4.util.Preconditions;
import android.support.v4.util.SimpleArrayMap;
import android.widget.TextView;
import com.oneplus.sdk.utils.OpBoostFramework;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import net.oneplus.weather.api.WeatherRequest.Type;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class FontsContractCompat {
    private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String PARCEL_FONT_RESULTS = "font_results";
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
    private static final String TAG = "FontsContractCompat";
    private static final SelfDestructiveThread sBackgroundThread;
    private static final Comparator<byte[]> sByteArrayComparator;
    private static final Object sLock;
    @GuardedBy("sLock")
    private static final SimpleArrayMap<String, ArrayList<ReplyCallback<Typeface>>> sPendingReplies;
    private static final LruCache<String, Typeface> sTypefaceCache;

    static class AnonymousClass_1 implements Callable<Typeface> {
        final /* synthetic */ Context val$context;
        final /* synthetic */ String val$id;
        final /* synthetic */ FontRequest val$request;
        final /* synthetic */ int val$style;

        AnonymousClass_1(Context context, FontRequest fontRequest, int i, String str) {
            this.val$context = context;
            this.val$request = fontRequest;
            this.val$style = i;
            this.val$id = str;
        }

        public Typeface call() throws Exception {
            Typeface typeface = FontsContractCompat.getFontInternal(this.val$context, this.val$request, this.val$style);
            if (typeface != null) {
                sTypefaceCache.put(this.val$id, typeface);
            }
            return typeface;
        }
    }

    static class AnonymousClass_4 implements Runnable {
        final /* synthetic */ FontRequestCallback val$callback;
        final /* synthetic */ Handler val$callerThreadHandler;
        final /* synthetic */ Context val$context;
        final /* synthetic */ FontRequest val$request;

        class AnonymousClass_7 implements Runnable {
            final /* synthetic */ int val$resultCode;

            AnonymousClass_7(int i) {
                this.val$resultCode = i;
            }

            public void run() {
                AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(this.val$resultCode);
            }
        }

        class AnonymousClass_9 implements Runnable {
            final /* synthetic */ Typeface val$typeface;

            AnonymousClass_9(Typeface typeface) {
                this.val$typeface = typeface;
            }

            public void run() {
                AnonymousClass_4.this.val$callback.onTypefaceRetrieved(this.val$typeface);
            }
        }

        AnonymousClass_4(Context context, FontRequest fontRequest, Handler handler, FontRequestCallback fontRequestCallback) {
            this.val$context = context;
            this.val$request = fontRequest;
            this.val$callerThreadHandler = handler;
            this.val$callback = fontRequestCallback;
        }

        public void run() {
            try {
                FontFamilyResult result = FontsContractCompat.fetchFonts(this.val$context, null, this.val$request);
                if (result.getStatusCode() != 0) {
                    switch (result.getStatusCode()) {
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                            this.val$callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(RESULT_CODE_WRONG_CERTIFICATES);
                                }
                            });
                            return;
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            this.val$callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY);
                                }
                            });
                            return;
                        default:
                            this.val$callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY);
                                }
                            });
                            return;
                    }
                }
                FontInfo[] fonts = result.getFonts();
                if (fonts == null || fonts.length == 0) {
                    this.val$callerThreadHandler.post(new Runnable() {
                        public void run() {
                            AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(1);
                        }
                    });
                    return;
                }
                for (FontInfo font : fonts) {
                    if (font.getResultCode() != 0) {
                        int resultCode = font.getResultCode();
                        if (resultCode < 0) {
                            this.val$callerThreadHandler.post(new Runnable() {
                                public void run() {
                                    AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY);
                                }
                            });
                            return;
                        } else {
                            this.val$callerThreadHandler.post(new AnonymousClass_7(resultCode));
                            return;
                        }
                    }
                }
                Typeface typeface = FontsContractCompat.buildTypeface(this.val$context, null, fonts);
                if (typeface == null) {
                    this.val$callerThreadHandler.post(new Runnable() {
                        public void run() {
                            AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY);
                        }
                    });
                } else {
                    this.val$callerThreadHandler.post(new AnonymousClass_9(typeface));
                }
            } catch (NameNotFoundException e) {
                this.val$callerThreadHandler.post(new Runnable() {
                    public void run() {
                        AnonymousClass_4.this.val$callback.onTypefaceRequestFailed(RESULT_CODE_PROVIDER_NOT_FOUND);
                    }
                });
            }
        }
    }

    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        static @interface FontResultStatus {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public FontFamilyResult(int statusCode, @Nullable FontInfo[] fonts) {
            this.mStatusCode = statusCode;
            this.mFonts = fonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        @RestrictTo({Scope.LIBRARY_GROUP})
        public FontInfo(@NonNull Uri uri, @IntRange(from = 0) int ttcIndex, @IntRange(from = 1, to = 1000) int weight, boolean italic, int resultCode) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = ttcIndex;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResultCode = resultCode;
        }

        @NonNull
        public Uri getUri() {
            return this.mUri;
        }

        @IntRange(from = 0)
        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        @IntRange(from = 1, to = 1000)
        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        static @interface FontRequestFailReason {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        public void onTypefaceRequestFailed(int reason) {
        }
    }

    static class AnonymousClass_2 implements ReplyCallback<Typeface> {
        final /* synthetic */ int val$style;
        final /* synthetic */ TextView val$targetView;
        final /* synthetic */ WeakReference val$textViewWeak;

        AnonymousClass_2(WeakReference weakReference, TextView textView, int i) {
            this.val$textViewWeak = weakReference;
            this.val$targetView = textView;
            this.val$style = i;
        }

        public void onReply(Typeface typeface) {
            if (((TextView) this.val$textViewWeak.get()) != null) {
                this.val$targetView.setTypeface(typeface, this.val$style);
            }
        }
    }

    static class AnonymousClass_3 implements ReplyCallback<Typeface> {
        final /* synthetic */ String val$id;

        AnonymousClass_3(String str) {
            this.val$id = str;
        }

        public void onReply(Typeface typeface) {
            synchronized (sLock) {
                ArrayList<ReplyCallback<Typeface>> replies = (ArrayList) sPendingReplies.get(this.val$id);
                sPendingReplies.remove(this.val$id);
            }
            for (int i = 0; i < replies.size(); i++) {
                ((ReplyCallback) replies.get(i)).onReply(typeface);
            }
        }
    }

    private FontsContractCompat() {
    }

    static {
        sTypefaceCache = new LruCache(16);
        sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
        sLock = new Object();
        sPendingReplies = new SimpleArrayMap();
        sByteArrayComparator = new Comparator<byte[]>() {
            public int compare(byte[] l, byte[] r) {
                if (l.length != r.length) {
                    return l.length - r.length;
                }
                for (int i = 0; i < l.length; i++) {
                    if (l[i] != r[i]) {
                        return l[i] - r[i];
                    }
                }
                return 0;
            }
        };
    }

    private static Typeface getFontInternal(Context context, FontRequest request, int style) {
        try {
            FontFamilyResult result = fetchFonts(context, null, request);
            return result.getStatusCode() == 0 ? TypefaceCompat.createFromFontInfo(context, null, result.getFonts(), style) : null;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static Typeface getFontSync(Context context, FontRequest request, @Nullable TextView targetView, int strategy, int timeout, int style) {
        String id = request.getIdentifier() + "-" + style;
        Typeface cached = (Typeface) sTypefaceCache.get(id);
        if (cached != null) {
            return cached;
        }
        boolean isBlockingFetch = strategy == 0;
        if (isBlockingFetch && timeout == -1) {
            return getFontInternal(context, request, style);
        }
        Callable<Typeface> fetcher = new AnonymousClass_1(context, request, style, id);
        if (isBlockingFetch) {
            try {
                return (Typeface) sBackgroundThread.postAndWait(fetcher, timeout);
            } catch (InterruptedException e) {
                return null;
            }
        }
        ReplyCallback<Typeface> reply = new AnonymousClass_2(new WeakReference(targetView), targetView, style);
        synchronized (sLock) {
            if (sPendingReplies.containsKey(id)) {
                ((ArrayList) sPendingReplies.get(id)).add(reply);
                return null;
            }
            ArrayList<ReplyCallback<Typeface>> pendingReplies = new ArrayList();
            pendingReplies.add(reply);
            sPendingReplies.put(id, pendingReplies);
            sBackgroundThread.postAndReply(fetcher, new AnonymousClass_3(id));
            return null;
        }
    }

    public static void requestFont(@NonNull Context context, @NonNull FontRequest request, @NonNull FontRequestCallback callback, @NonNull Handler handler) {
        handler.post(new AnonymousClass_4(context, request, new Handler(), callback));
    }

    public static Typeface buildTypeface(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fonts, 0);
    }

    @RequiresApi(19)
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap();
        for (FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    out.put(uri, TypefaceCompatUtil.mmap(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    @NonNull
    public static FontFamilyResult fetchFonts(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontRequest request) throws NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        return providerInfo == null ? new FontFamilyResult(1, null) : new FontFamilyResult(0, getFontFromProvider(context, request, providerInfo.authority, cancellationSignal));
    }

    @VisibleForTesting
    @RestrictTo({Scope.LIBRARY_GROUP})
    @Nullable
    public static ProviderInfo getProvider(@NonNull PackageManager packageManager, @NonNull FontRequest request, @Nullable Resources resources) throws NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (info.packageName.equals(request.getProviderPackage())) {
            List<byte[]> signatures = convertToByteArrayList(packageManager.getPackageInfo(info.packageName, Type.SUCCESS).signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList((Collection) requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        } else {
            throw new NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        return request.getCertificates() != null ? request.getCertificates() : FontResourcesParserCompat.readCerts(resources, request.getCertificatesArrayResId());
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals((byte[]) signatures.get(i), (byte[]) requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shas = new ArrayList();
        for (Signature signature : signatures) {
            shas.add(signature.toByteArray());
        }
        return shas;
    }

    @VisibleForTesting
    @NonNull
    static FontInfo[] getFontFromProvider(Context context, FontRequest request, String authority, CancellationSignal cancellationSignal) {
        ArrayList<FontInfo> result = new ArrayList();
        Uri uri = new Builder().scheme("content").authority(authority).build();
        Uri fileBaseUri = new Builder().scheme("content").authority(authority).appendPath("file").build();
        Cursor cursor = null;
        try {
            if (VERSION.SDK_INT > 16) {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{request.getQuery()}, null, cancellationSignal);
            } else {
                cursor = context.getContentResolver().query(uri, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{request.getQuery()}, null);
            }
            if (cursor != null && cursor.getCount() > 0) {
                int resultCodeColumnIndex = cursor.getColumnIndex(Columns.RESULT_CODE);
                ArrayList<FontInfo> result2 = new ArrayList();
                try {
                    int idColumnIndex = cursor.getColumnIndex("_id");
                    int fileIdColumnIndex = cursor.getColumnIndex(Columns.FILE_ID);
                    int ttcIndexColumnIndex = cursor.getColumnIndex(Columns.TTC_INDEX);
                    int weightColumnIndex = cursor.getColumnIndex(Columns.WEIGHT);
                    int italicColumnIndex = cursor.getColumnIndex(Columns.ITALIC);
                    while (cursor.moveToNext()) {
                        Uri fileUri;
                        int resultCode = resultCodeColumnIndex != -1 ? cursor.getInt(resultCodeColumnIndex) : 0;
                        int ttcIndex = ttcIndexColumnIndex != -1 ? cursor.getInt(ttcIndexColumnIndex) : 0;
                        if (fileIdColumnIndex == -1) {
                            fileUri = ContentUris.withAppendedId(uri, cursor.getLong(idColumnIndex));
                        } else {
                            fileUri = ContentUris.withAppendedId(fileBaseUri, cursor.getLong(fileIdColumnIndex));
                        }
                        int weight = weightColumnIndex != -1 ? cursor.getInt(weightColumnIndex) : GlobalConfig.MESSAGE_ACCU_SEARCH_CITY;
                        boolean italic = italicColumnIndex != -1 && cursor.getInt(italicColumnIndex) == 1;
                        result2.add(new FontInfo(fileUri, ttcIndex, weight, italic, resultCode));
                    }
                    result = result2;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    result = result2;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return (FontInfo[]) result.toArray(new FontInfo[0]);
        } catch (Throwable th3) {
            th2 = th3;
            if (cursor != null) {
                cursor.close();
            }
            throw th2;
        }
    }
}
