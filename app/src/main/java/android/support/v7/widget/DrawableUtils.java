package android.support.v7.widget;

import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import java.lang.reflect.Field;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class DrawableUtils {
    public static final Rect INSETS_NONE;
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz;

    static {
        INSETS_NONE = new Rect();
        if (VERSION.SDK_INT >= 18) {
            try {
                sInsetsClazz = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException e) {
            }
        }
    }

    private DrawableUtils() {
    }

    public static Rect getOpticalBounds(Drawable drawable) {
        if (sInsetsClazz != null) {
            try {
                drawable = DrawableCompat.unwrap(drawable);
                Object insets = drawable.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(drawable, new Object[0]);
                if (insets != null) {
                    Rect result = new Rect();
                    Field[] fields = sInsetsClazz.getFields();
                    int length = fields.length;
                    for (int i = 0; i < length; i++) {
                        Field field = fields[i];
                        String name = field.getName();
                        Object obj = -1;
                        switch (name.hashCode()) {
                            case -1383228885:
                                if (name.equals("bottom")) {
                                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                                }
                                break;
                            case 115029:
                                if (name.equals("top")) {
                                    obj = 1;
                                }
                                break;
                            case 3317767:
                                if (name.equals("left")) {
                                    obj = null;
                                }
                                break;
                            case 108511772:
                                if (name.equals("right")) {
                                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                                }
                                break;
                        }
                        switch (obj) {
                            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                                result.left = field.getInt(insets);
                                break;
                            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                                result.top = field.getInt(insets);
                                break;
                            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                                result.right = field.getInt(insets);
                                break;
                            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                                result.bottom = field.getInt(insets);
                                break;
                            default:
                                break;
                        }
                    }
                    return result;
                }
            } catch (Exception e) {
                Log.e(TAG, "Couldn't obtain the optical insets. Ignoring.");
            }
        }
        return INSETS_NONE;
    }

    static void fixDrawable(@NonNull Drawable drawable) {
        if (VERSION.SDK_INT == 21 && VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable.getClass().getName())) {
            fixVectorDrawableTinting(drawable);
        }
    }

    public static boolean canSafelyMutateDrawable(@NonNull Drawable drawable) {
        if (VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (drawable instanceof DrawableContainer) {
            ConstantState state = drawable.getConstantState();
            if (state instanceof DrawableContainerState) {
                Drawable[] children = ((DrawableContainerState) state).getChildren();
                int length = children.length;
                for (int i = 0; i < length; i++) {
                    if (!canSafelyMutateDrawable(children[i])) {
                        return false;
                    }
                }
            }
        } else if (drawable instanceof DrawableWrapper) {
            return canSafelyMutateDrawable(((DrawableWrapper) drawable).getWrappedDrawable());
        } else {
            if (drawable instanceof android.support.v7.graphics.drawable.DrawableWrapper) {
                return canSafelyMutateDrawable(((android.support.v7.graphics.drawable.DrawableWrapper) drawable).getWrappedDrawable());
            }
            if (drawable instanceof ScaleDrawable) {
                return canSafelyMutateDrawable(((ScaleDrawable) drawable).getDrawable());
            }
        }
        return true;
    }

    private static void fixVectorDrawableTinting(Drawable drawable) {
        int[] originalState = drawable.getState();
        if (originalState == null || originalState.length == 0) {
            drawable.setState(ThemeUtils.CHECKED_STATE_SET);
        } else {
            drawable.setState(ThemeUtils.EMPTY_STATE_SET);
        }
        drawable.setState(originalState);
    }

    public static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return Mode.SRC_OVER;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return Mode.SRC_IN;
            case ConnectionResult.SERVICE_INVALID:
                return Mode.SRC_ATOP;
            case ConnectionResult.TIMEOUT:
                return Mode.MULTIPLY;
            case ConnectionResult.INTERRUPTED:
                return Mode.SCREEN;
            case ConnectionResult.API_UNAVAILABLE:
                return VERSION.SDK_INT >= 11 ? Mode.valueOf("ADD") : defaultMode;
            default:
                return defaultMode;
        }
    }
}
