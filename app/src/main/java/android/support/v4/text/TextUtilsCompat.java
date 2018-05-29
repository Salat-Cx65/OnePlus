package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.Locale;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class TextUtilsCompat {
    private static final String ARAB_SCRIPT_SUBTAG = "Arab";
    private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
    @Deprecated
    public static final Locale ROOT;

    static {
        ROOT = new Locale(StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING);
    }

    @NonNull
    public static String htmlEncode(@NonNull String s) {
        if (VERSION.SDK_INT >= 17) {
            return TextUtils.htmlEncode(s);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case R.styleable.OneplusTheme_op_buttonPanelSideLayout:
                    sb.append("&quot;");
                    break;
                case R.styleable.OneplusTheme_op_multiChoiceItemLayout:
                    sb.append("&amp;");
                    break;
                case R.styleable.OneplusTheme_op_pressedTranslationZ:
                    sb.append("&#39;");
                    break;
                case R.styleable.AppCompatTheme_dialogTheme:
                    sb.append("&lt;");
                    break;
                case R.styleable.AppCompatTheme_dividerVertical:
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        if (VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale(locale);
        }
        if (!(locale == null || locale.equals(ROOT))) {
            String scriptSubtag = ICUCompat.maximizeAndGetScript(locale);
            if (scriptSubtag == null) {
                return getLayoutDirectionFromFirstChar(locale);
            }
            if (scriptSubtag.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG) || scriptSubtag.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) {
                return 1;
            }
        }
        return 0;
    }

    private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
        switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return 1;
            default:
                return 0;
        }
    }

    private TextUtilsCompat() {
    }
}
