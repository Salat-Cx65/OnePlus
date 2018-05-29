package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList;
    private static final Map<String, Constructor<? extends View>> sConstructorMap;
    private static final Class<?>[] sConstructorSignature;
    private static final int[] sOnClickAttrs;
    private final Object[] mConstructorArgs;

    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View hostView, @NonNull String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        public void onClick(@NonNull View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{v});
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        Method method = context.getClass().getMethod(this.mMethodName, new Class[]{View.class});
                        if (method != null) {
                            this.mResolvedMethod = method;
                            this.mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + (id == -1 ? StringUtils.EMPTY_STRING : " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'"));
        }
    }

    AppCompatViewInflater() {
        this.mConstructorArgs = new Object[2];
    }

    static {
        sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sOnClickAttrs = new int[]{16843375};
        sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
        sConstructorMap = new ArrayMap();
    }

    public final View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs, boolean inheritContext, boolean readAndroidTheme, boolean readAppTheme, boolean wrapContext) {
        Context originalContext = context;
        if (inheritContext && parent != null) {
            context = parent.getContext();
        }
        if (readAndroidTheme || readAppTheme) {
            context = themifyContext(context, attrs, readAndroidTheme, readAppTheme);
        }
        if (wrapContext) {
            context = TintContextWrapper.wrap(context);
        }
        View view = null;
        Object obj = -1;
        switch (name.hashCode()) {
            case -1946472170:
                if (name.equals("RatingBar")) {
                    obj = ConnectionResult.LICENSE_CHECK_FAILED;
                }
                break;
            case -1455429095:
                if (name.equals("CheckedTextView")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case -1346021293:
                if (name.equals("MultiAutoCompleteTextView")) {
                    obj = ConnectionResult.DEVELOPER_ERROR;
                }
                break;
            case -938935918:
                if (name.equals("TextView")) {
                    obj = null;
                }
                break;
            case -937446323:
                if (name.equals("ImageButton")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case -658531749:
                if (name.equals("SeekBar")) {
                    obj = WeatherCircleView.ARC_DIN;
                }
                break;
            case -339785223:
                if (name.equals("Spinner")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 776382189:
                if (name.equals("RadioButton")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case 1125864064:
                if (name.equals("ImageView")) {
                    obj = 1;
                }
                break;
            case 1413872058:
                if (name.equals("AutoCompleteTextView")) {
                    obj = ConnectionResult.SERVICE_INVALID;
                }
                break;
            case 1601505219:
                if (name.equals("CheckBox")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case 1666676343:
                if (name.equals("EditText")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 2001146706:
                if (name.equals("Button")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                view = new AppCompatTextView(context, attrs);
                break;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                view = new AppCompatImageView(context, attrs);
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                view = new AppCompatButton(context, attrs);
                break;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                view = new AppCompatEditText(context, attrs);
                break;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                view = new AppCompatSpinner(context, attrs);
                break;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                view = new AppCompatImageButton(context, attrs);
                break;
            case ConnectionResult.RESOLUTION_REQUIRED:
                view = new AppCompatCheckBox(context, attrs);
                break;
            case DetectedActivity.WALKING:
                view = new AppCompatRadioButton(context, attrs);
                break;
            case DetectedActivity.RUNNING:
                view = new AppCompatCheckedTextView(context, attrs);
                break;
            case ConnectionResult.SERVICE_INVALID:
                view = new AppCompatAutoCompleteTextView(context, attrs);
                break;
            case ConnectionResult.DEVELOPER_ERROR:
                view = new AppCompatMultiAutoCompleteTextView(context, attrs);
                break;
            case ConnectionResult.LICENSE_CHECK_FAILED:
                view = new AppCompatRatingBar(context, attrs);
                break;
            case WeatherCircleView.ARC_DIN:
                view = new AppCompatSeekBar(context, attrs);
                break;
        }
        if (view == null && originalContext != context) {
            view = createViewFromTag(context, name, attrs);
        }
        if (view != null) {
            checkOnClickListener(view, attrs);
        }
        return view;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attrs;
            View view;
            if (-1 == name.indexOf(R.styleable.AppCompatTheme_checkboxStyle)) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        this.mConstructorArgs[0] = null;
                        this.mConstructorArgs[1] = null;
                        return view;
                    }
                }
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return null;
            }
            view = createView(context, name, null);
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return view;
        } catch (Exception e) {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return null;
        }
    }

    private void checkOnClickListener(View view, AttributeSet attrs) {
        Context context = view.getContext();
        if (!(context instanceof ContextWrapper)) {
            return;
        }
        if (VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view)) {
            TypedArray a = context.obtainStyledAttributes(attrs, sOnClickAttrs);
            String handlerName = a.getString(0);
            if (handlerName != null) {
                view.setOnClickListener(new DeclaredOnClickListener(view, handlerName));
            }
            a.recycle();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.View createView(android.content.Context r6_context, java.lang.String r7_name, java.lang.String r8_prefix) throws java.lang.ClassNotFoundException, android.view.InflateException {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatViewInflater.createView(android.content.Context, java.lang.String, java.lang.String):android.view.View");
        /*
        this = this;
        r3 = sConstructorMap;
        r1 = r3.get(r7);
        r1 = (java.lang.reflect.Constructor) r1;
        if (r1 != 0) goto L_0x0036;
    L_0x000a:
        r4 = r6.getClassLoader();	 Catch:{ Exception -> 0x0045 }
        if (r8 == 0) goto L_0x0043;
    L_0x0010:
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0045 }
        r3.<init>();	 Catch:{ Exception -> 0x0045 }
        r3 = r3.append(r8);	 Catch:{ Exception -> 0x0045 }
        r3 = r3.append(r7);	 Catch:{ Exception -> 0x0045 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0045 }
    L_0x0021:
        r3 = r4.loadClass(r3);	 Catch:{ Exception -> 0x0045 }
        r4 = android.view.View.class;
        r0 = r3.asSubclass(r4);	 Catch:{ Exception -> 0x0045 }
        r3 = sConstructorSignature;	 Catch:{ Exception -> 0x0045 }
        r1 = r0.getConstructor(r3);	 Catch:{ Exception -> 0x0045 }
        r3 = sConstructorMap;	 Catch:{ Exception -> 0x0045 }
        r3.put(r7, r1);	 Catch:{ Exception -> 0x0045 }
    L_0x0036:
        r3 = 1;
        r1.setAccessible(r3);	 Catch:{ Exception -> 0x0045 }
        r3 = r5.mConstructorArgs;	 Catch:{ Exception -> 0x0045 }
        r3 = r1.newInstance(r3);	 Catch:{ Exception -> 0x0045 }
        r3 = (android.view.View) r3;	 Catch:{ Exception -> 0x0045 }
    L_0x0042:
        return r3;
    L_0x0043:
        r3 = r7;
        goto L_0x0021;
    L_0x0045:
        r2 = move-exception;
        r3 = 0;
        goto L_0x0042;
        */
    }

    private static Context themifyContext(Context context, AttributeSet attrs, boolean useAndroidTheme, boolean useAppTheme) {
        TypedArray a = context.obtainStyledAttributes(attrs, android.support.v7.appcompat.R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {
            themeId = a.getResourceId(android.support.v7.appcompat.R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0) {
            themeId = a.getResourceId(android.support.v7.appcompat.R.styleable.View_theme, 0);
            if (themeId != 0) {
                Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
            }
        }
        a.recycle();
        if (themeId != 0) {
            return ((context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == themeId) ? context : new ContextThemeWrapper(context, themeId);
        } else {
            return context;
        }
    }
}
