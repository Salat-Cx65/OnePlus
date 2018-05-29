package com.oneplus.lib.design.widget;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toolbar;
import java.lang.reflect.Field;

public class Utils {
    private static final Matrix IDENTITY;
    private static final ThreadLocal<Matrix> sMatrix;
    private static final ThreadLocal<RectF> sRectF;

    static {
        sMatrix = new ThreadLocal();
        sRectF = new ThreadLocal();
        IDENTITY = new Matrix();
    }

    static boolean objectEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    static int constrain(int amount, int low, int high) {
        if (amount < low) {
            return low;
        }
        return amount > high ? high : amount;
    }

    static float constrain(float amount, float low, float high) {
        if (amount < low) {
            return low;
        }
        return amount > high ? high : amount;
    }

    static void offsetDescendantMatrix(ViewParent target, View view, Matrix m) {
        ViewParent parent = view.getParent();
        if ((parent instanceof View) && parent != target) {
            View vp = (View) parent;
            offsetDescendantMatrix(target, vp, m);
            m.preTranslate((float) (-vp.getScrollX()), (float) (-vp.getScrollY()));
        }
        m.preTranslate((float) view.getLeft(), (float) view.getTop());
        if (!view.getMatrix().isIdentity()) {
            m.preConcat(view.getMatrix());
        }
    }

    static void offsetDescendantRect(ViewGroup parent, View descendant, Rect rect) {
        Matrix m = (Matrix) sMatrix.get();
        if (m == null) {
            m = new Matrix();
            sMatrix.set(m);
        } else {
            m.set(IDENTITY);
        }
        offsetDescendantMatrix(parent, descendant, m);
        RectF rectF = (RectF) sRectF.get();
        if (rectF == null) {
            rectF = new RectF();
        }
        rectF.set(rect);
        m.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }

    public static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        offsetDescendantRect(parent, descendant, out);
    }

    static float lerp(float startValue, float endValue, float fraction) {
        return ((endValue - startValue) * fraction) + startValue;
    }

    static int lerp(int startValue, int endValue, float fraction) {
        return Math.round(((float) (endValue - startValue)) * fraction) + startValue;
    }

    public static int getTitleMarginStart(Toolbar toolbar) {
        Field field = null;
        if (VERSION.SDK_INT >= 24) {
            return toolbar.getTitleMarginStart();
        }
        try {
            field = Toolbar.class.getDeclaredField("mTitleMarginStart");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        if (field != null) {
            try {
                return ((Integer) field.get(toolbar)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    public static int getTitleMarginTop(Toolbar toolbar) {
        Field field = null;
        if (VERSION.SDK_INT >= 24) {
            return toolbar.getTitleMarginTop();
        }
        try {
            field = Toolbar.class.getDeclaredField("mTitleMarginTop");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        if (field != null) {
            try {
                return ((Integer) field.get(toolbar)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    public static int getTitleMarginEnd(Toolbar toolbar) {
        Field field = null;
        if (VERSION.SDK_INT >= 24) {
            return toolbar.getTitleMarginEnd();
        }
        try {
            field = Toolbar.class.getDeclaredField("mTitleMarginEnd");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        if (field != null) {
            try {
                return ((Integer) field.get(toolbar)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    public static int getTitleMarginBottom(Toolbar toolbar) {
        Field field = null;
        if (VERSION.SDK_INT >= 24) {
            return toolbar.getTitleMarginBottom();
        }
        try {
            field = Toolbar.class.getDeclaredField("mTitleMarginBottom");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        if (field != null) {
            try {
                return ((Integer) field.get(toolbar)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }
}
