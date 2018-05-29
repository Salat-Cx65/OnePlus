package com.nineoldandroids.animation;

import android.view.View;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;

final class PreHoneycombCompat {
    static Property<View, Float> ALPHA;
    static Property<View, Float> PIVOT_X;
    static Property<View, Float> PIVOT_Y;
    static Property<View, Float> ROTATION;
    static Property<View, Float> ROTATION_X;
    static Property<View, Float> ROTATION_Y;
    static Property<View, Float> SCALE_X;
    static Property<View, Float> SCALE_Y;
    static Property<View, Integer> SCROLL_X;
    static Property<View, Integer> SCROLL_Y;
    static Property<View, Float> TRANSLATION_X;
    static Property<View, Float> TRANSLATION_Y;
    static Property<View, Float> X;
    static Property<View, Float> Y;

    static class AnonymousClass_10 extends FloatProperty<View> {
        AnonymousClass_10(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleY());
        }
    }

    static class AnonymousClass_11 extends IntProperty<View> {
        AnonymousClass_11(String x0) {
            super(x0);
        }

        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollX(value);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollX());
        }
    }

    static class AnonymousClass_12 extends IntProperty<View> {
        AnonymousClass_12(String x0) {
            super(x0);
        }

        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollY(value);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollY());
        }
    }

    static class AnonymousClass_13 extends FloatProperty<View> {
        AnonymousClass_13(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getX());
        }
    }

    static class AnonymousClass_14 extends FloatProperty<View> {
        AnonymousClass_14(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getY());
        }
    }

    static class AnonymousClass_1 extends FloatProperty<View> {
        AnonymousClass_1(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setAlpha(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getAlpha());
        }
    }

    static class AnonymousClass_2 extends FloatProperty<View> {
        AnonymousClass_2(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotX());
        }
    }

    static class AnonymousClass_3 extends FloatProperty<View> {
        AnonymousClass_3(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotY());
        }
    }

    static class AnonymousClass_4 extends FloatProperty<View> {
        AnonymousClass_4(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationX());
        }
    }

    static class AnonymousClass_5 extends FloatProperty<View> {
        AnonymousClass_5(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationY());
        }
    }

    static class AnonymousClass_6 extends FloatProperty<View> {
        AnonymousClass_6(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotation(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotation());
        }
    }

    static class AnonymousClass_7 extends FloatProperty<View> {
        AnonymousClass_7(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationX());
        }
    }

    static class AnonymousClass_8 extends FloatProperty<View> {
        AnonymousClass_8(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationY());
        }
    }

    static class AnonymousClass_9 extends FloatProperty<View> {
        AnonymousClass_9(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleX());
        }
    }

    static {
        ALPHA = new AnonymousClass_1("alpha");
        PIVOT_X = new AnonymousClass_2("pivotX");
        PIVOT_Y = new AnonymousClass_3("pivotY");
        TRANSLATION_X = new AnonymousClass_4("translationX");
        TRANSLATION_Y = new AnonymousClass_5("translationY");
        ROTATION = new AnonymousClass_6("rotation");
        ROTATION_X = new AnonymousClass_7("rotationX");
        ROTATION_Y = new AnonymousClass_8("rotationY");
        SCALE_X = new AnonymousClass_9("scaleX");
        SCALE_Y = new AnonymousClass_10("scaleY");
        SCROLL_X = new AnonymousClass_11("scrollX");
        SCROLL_Y = new AnonymousClass_12("scrollY");
        X = new AnonymousClass_13("x");
        Y = new AnonymousClass_14("y");
    }

    private PreHoneycombCompat() {
    }
}
