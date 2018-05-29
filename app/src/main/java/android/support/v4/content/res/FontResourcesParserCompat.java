package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.compat.R;
import android.support.v4.content.res.FontResourcesParserCompat.FamilyResourceEntry;
import android.support.v4.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import android.support.v4.provider.FontRequest;
import android.util.Base64;
import android.util.Xml;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({Scope.LIBRARY_GROUP})
public class FontResourcesParserCompat {
    private static final int DEFAULT_TIMEOUT_MILLIS = 500;
    public static final int FETCH_STRATEGY_ASYNC = 1;
    public static final int FETCH_STRATEGY_BLOCKING = 0;
    public static final int INFINITE_TIMEOUT_VALUE = -1;
    private static final int ITALIC = 1;
    private static final int NORMAL_WEIGHT = 400;

    public static interface FamilyResourceEntry {
    }

    @Retention(RetentionPolicy.SOURCE)
    public static @interface FetchStrategy {
    }

    public static final class FontFileResourceEntry {
        @NonNull
        private final String mFileName;
        private boolean mItalic;
        private int mResourceId;
        private int mWeight;

        public FontFileResourceEntry(@NonNull String fileName, int weight, boolean italic, int resourceId) {
            this.mFileName = fileName;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResourceId = resourceId;
        }

        @NonNull
        public String getFileName() {
            return this.mFileName;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResourceId() {
            return this.mResourceId;
        }
    }

    public static final class FontFamilyFilesResourceEntry implements FamilyResourceEntry {
        @NonNull
        private final FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(@NonNull FontFileResourceEntry[] entries) {
            this.mEntries = entries;
        }

        @NonNull
        public FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    public static final class ProviderResourceEntry implements FamilyResourceEntry {
        @NonNull
        private final FontRequest mRequest;
        private final int mStrategy;
        private final int mTimeoutMs;

        public ProviderResourceEntry(@NonNull FontRequest request, int strategy, int timeoutMs) {
            this.mRequest = request;
            this.mStrategy = strategy;
            this.mTimeoutMs = timeoutMs;
        }

        @NonNull
        public FontRequest getRequest() {
            return this.mRequest;
        }

        public int getFetchStrategy() {
            return this.mStrategy;
        }

        public int getTimeout() {
            return this.mTimeoutMs;
        }
    }

    @Nullable
    public static FamilyResourceEntry parse(XmlPullParser parser, Resources resources) throws XmlPullParserException, IOException {
        int type;
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type == 2) {
            return readFamilies(parser, resources);
        }
        throw new XmlPullParserException("No start tag found");
    }

    @Nullable
    private static FamilyResourceEntry readFamilies(XmlPullParser parser, Resources resources) throws XmlPullParserException, IOException {
        parser.require(RainSurfaceView.RAIN_LEVEL_SHOWER, null, "font-family");
        if (parser.getName().equals("font-family")) {
            return readFamily(parser, resources);
        }
        skip(parser);
        return null;
    }

    @Nullable
    private static FamilyResourceEntry readFamily(XmlPullParser parser, Resources resources) throws XmlPullParserException, IOException {
        TypedArray array = resources.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.FontFamily);
        String authority = array.getString(R.styleable.FontFamily_fontProviderAuthority);
        String providerPackage = array.getString(R.styleable.FontFamily_fontProviderPackage);
        String query = array.getString(R.styleable.FontFamily_fontProviderQuery);
        int certsId = array.getResourceId(R.styleable.FontFamily_fontProviderCerts, FETCH_STRATEGY_BLOCKING);
        int strategy = array.getInteger(R.styleable.FontFamily_fontProviderFetchStrategy, ITALIC);
        int timeoutMs = array.getInteger(R.styleable.FontFamily_fontProviderFetchTimeout, DEFAULT_TIMEOUT_MILLIS);
        array.recycle();
        if (authority == null || providerPackage == null || query == null) {
            List<FontFileResourceEntry> fonts = new ArrayList();
            while (parser.next() != 3) {
                if (parser.getEventType() == 2) {
                    if (parser.getName().equals("font")) {
                        fonts.add(readFont(parser, resources));
                    } else {
                        skip(parser);
                    }
                }
            }
            return fonts.isEmpty() ? null : new FontFamilyFilesResourceEntry((FontFileResourceEntry[]) fonts.toArray(new FontFileResourceEntry[fonts.size()]));
        } else {
            while (parser.next() != 3) {
                skip(parser);
            }
            return new ProviderResourceEntry(new FontRequest(authority, providerPackage, query, readCerts(resources, certsId)), strategy, timeoutMs);
        }
    }

    public static List<List<byte[]>> readCerts(Resources resources, @ArrayRes int certsId) {
        List<List<byte[]>> certs = null;
        if (certsId != 0) {
            TypedArray typedArray = resources.obtainTypedArray(certsId);
            if (typedArray.length() > 0) {
                boolean isArrayOfArrays;
                certs = new ArrayList();
                if (typedArray.getResourceId(FETCH_STRATEGY_BLOCKING, FETCH_STRATEGY_BLOCKING) != 0) {
                    isArrayOfArrays = true;
                } else {
                    isArrayOfArrays = false;
                }
                if (isArrayOfArrays) {
                    for (int i = FETCH_STRATEGY_BLOCKING; i < typedArray.length(); i++) {
                        certs.add(toByteArrayList(resources.getStringArray(typedArray.getResourceId(i, FETCH_STRATEGY_BLOCKING))));
                    }
                } else {
                    certs.add(toByteArrayList(resources.getStringArray(certsId)));
                }
            }
            typedArray.recycle();
        }
        return certs != null ? certs : Collections.emptyList();
    }

    private static List<byte[]> toByteArrayList(String[] stringArray) {
        List<byte[]> result = new ArrayList();
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            result.add(Base64.decode(stringArray[i], FETCH_STRATEGY_BLOCKING));
        }
        return result;
    }

    private static FontFileResourceEntry readFont(XmlPullParser parser, Resources resources) throws XmlPullParserException, IOException {
        boolean isItalic = true;
        TypedArray array = resources.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.FontFamilyFont);
        int weight = array.getInt(R.styleable.FontFamilyFont_fontWeight, NORMAL_WEIGHT);
        if (1 != array.getInt(R.styleable.FontFamilyFont_fontStyle, FETCH_STRATEGY_BLOCKING)) {
            isItalic = false;
        }
        int resourceId = array.getResourceId(R.styleable.FontFamilyFont_font, FETCH_STRATEGY_BLOCKING);
        String filename = array.getString(R.styleable.FontFamilyFont_font);
        array.recycle();
        while (parser.next() != 3) {
            skip(parser);
        }
        return new FontFileResourceEntry(filename, weight, isItalic, resourceId);
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        int depth = ITALIC;
        while (depth > 0) {
            switch (parser.next()) {
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    depth++;
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    depth--;
                    break;
                default:
                    break;
            }
        }
    }
}
