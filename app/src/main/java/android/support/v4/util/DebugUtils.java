package android.support.v4.util;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import net.oneplus.weather.R;

@RestrictTo({Scope.LIBRARY_GROUP})
public class DebugUtils {
    public static void buildShortClassTag(Object cls, StringBuilder out) {
        if (cls == null) {
            out.append("null");
            return;
        }
        String simpleName = cls.getClass().getSimpleName();
        if (simpleName == null || simpleName.length() <= 0) {
            simpleName = cls.getClass().getName();
            int end = simpleName.lastIndexOf(R.styleable.AppCompatTheme_checkboxStyle);
            if (end > 0) {
                simpleName = simpleName.substring(end + 1);
            }
        }
        out.append(simpleName);
        out.append('{');
        out.append(Integer.toHexString(System.identityHashCode(cls)));
    }
}
