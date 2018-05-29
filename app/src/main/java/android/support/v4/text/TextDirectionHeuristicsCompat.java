package android.support.v4.text;

import com.google.android.gms.common.ConnectionResult;
import java.nio.CharBuffer;
import java.util.Locale;

public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat ANYRTL_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristicCompat LOCALE;
    public static final TextDirectionHeuristicCompat LTR;
    public static final TextDirectionHeuristicCompat RTL;
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    private static interface TextDirectionAlgorithm {
        int checkRtl(CharSequence charSequence, int i, int i2);
    }

    private static class AnyStrong implements TextDirectionAlgorithm {
        public static final AnyStrong INSTANCE_LTR;
        public static final AnyStrong INSTANCE_RTL;
        private final boolean mLookForRtl;

        public int checkRtl(CharSequence cs, int start, int count) {
            boolean haveUnlookedFor = false;
            int e = start + count;
            for (int i = start; i < e; i++) {
                switch (TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality(cs.charAt(i)))) {
                    case STATE_TRUE:
                        if (this.mLookForRtl) {
                            return 0;
                        }
                        haveUnlookedFor = true;
                        break;
                    case STATE_FALSE:
                        if (!this.mLookForRtl) {
                            return STATE_FALSE;
                        }
                        haveUnlookedFor = true;
                        break;
                    default:
                        break;
                }
            }
            if (haveUnlookedFor) {
                return !this.mLookForRtl ? 0 : STATE_FALSE;
            } else {
                return STATE_UNKNOWN;
            }
        }

        private AnyStrong(boolean lookForRtl) {
            this.mLookForRtl = lookForRtl;
        }

        static {
            INSTANCE_RTL = new AnyStrong(true);
            INSTANCE_LTR = new AnyStrong(false);
        }
    }

    private static class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE;

        public int checkRtl(CharSequence cs, int start, int count) {
            int result = STATE_UNKNOWN;
            int e = start + count;
            for (int i = start; i < e && result == 2; i++) {
                result = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(cs.charAt(i)));
            }
            return result;
        }

        private FirstStrong() {
        }

        static {
            INSTANCE = new FirstStrong();
        }
    }

    private static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm mAlgorithm;

        protected abstract boolean defaultIsRtl();

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm algorithm) {
            this.mAlgorithm = algorithm;
        }

        public boolean isRtl(char[] array, int start, int count) {
            return isRtl(CharBuffer.wrap(array), start, count);
        }

        public boolean isRtl(CharSequence cs, int start, int count) {
            if (cs != null && start >= 0 && count >= 0 && cs.length() - count >= start) {
                return this.mAlgorithm == null ? defaultIsRtl() : doCheck(cs, start, count);
            } else {
                throw new IllegalArgumentException();
            }
        }

        private boolean doCheck(CharSequence cs, int start, int count) {
            switch (this.mAlgorithm.checkRtl(cs, start, count)) {
                case STATE_TRUE:
                    return true;
                case STATE_FALSE:
                    return false;
                default:
                    return defaultIsRtl();
            }
        }
    }

    private static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        TextDirectionHeuristicInternal(TextDirectionAlgorithm algorithm, boolean defaultIsRtl) {
            super(algorithm);
            this.mDefaultIsRtl = defaultIsRtl;
        }

        protected boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    private static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale INSTANCE;

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        protected boolean defaultIsRtl() {
            return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }

        static {
            INSTANCE = new TextDirectionHeuristicLocale();
        }
    }

    static {
        LTR = new TextDirectionHeuristicInternal(null, false);
        RTL = new TextDirectionHeuristicInternal(null, true);
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true);
        ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
        LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    }

    static int isRtlText(int directionality) {
        switch (directionality) {
            case STATE_TRUE:
                return STATE_FALSE;
            case STATE_FALSE:
            case STATE_UNKNOWN:
                return STATE_TRUE;
            default:
                return STATE_UNKNOWN;
        }
    }

    static int isRtlTextOrFormat(int directionality) {
        switch (directionality) {
            case STATE_TRUE:
            case ConnectionResult.TIMEOUT:
            case ConnectionResult.INTERRUPTED:
                return STATE_FALSE;
            case STATE_FALSE:
            case STATE_UNKNOWN:
            case ConnectionResult.API_UNAVAILABLE:
            case ConnectionResult.SIGN_IN_FAILED:
                return STATE_TRUE;
            default:
                return STATE_UNKNOWN;
        }
    }

    private TextDirectionHeuristicsCompat() {
    }
}
