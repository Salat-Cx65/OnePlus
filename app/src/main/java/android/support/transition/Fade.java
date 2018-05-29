package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.DefaultRetryPolicy;

public class Fade extends Visibility {
    public static final int IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    private static final String PROPNAME_TRANSITION_ALPHA = "android:fade:transitionAlpha";

    private static class FadeAnimatorListener extends AnimatorListenerAdapter {
        private boolean mLayerTypeChanged;
        private final View mView;

        FadeAnimatorListener(View view) {
            this.mLayerTypeChanged = false;
            this.mView = view;
        }

        public void onAnimationStart(Animator animation) {
            if (ViewCompat.hasOverlappingRendering(this.mView) && this.mView.getLayerType() == 0) {
                this.mLayerTypeChanged = true;
                this.mView.setLayerType(OUT, null);
            }
        }

        public void onAnimationEnd(Animator animation) {
            ViewUtils.setTransitionAlpha(this.mView, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            if (this.mLayerTypeChanged) {
                this.mView.setLayerType(0, null);
            }
        }
    }

    class AnonymousClass_1 extends TransitionListenerAdapter {
        final /* synthetic */ View val$view;

        AnonymousClass_1(View view) {
            this.val$view = view;
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            ViewUtils.setTransitionAlpha(this.val$view, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            ViewUtils.clearNonTransitionAlpha(this.val$view);
            transition.removeListener(this);
        }
    }

    public Fade(int fadingMode) {
        setMode(fadingMode);
    }

    public Fade(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.FADE);
        setMode(TypedArrayUtils.getNamedInt(a, (XmlResourceParser) attrs, "fadingMode", 0, getMode()));
        a.recycle();
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put(PROPNAME_TRANSITION_ALPHA, Float.valueOf(ViewUtils.getTransitionAlpha(transitionValues.view)));
    }

    private Animator createAnimation(View view, float startAlpha, float endAlpha) {
        if (startAlpha == endAlpha) {
            return null;
        }
        ViewUtils.setTransitionAlpha(view, startAlpha);
        Animator anim = ObjectAnimator.ofFloat(view, ViewUtils.TRANSITION_ALPHA, new float[]{endAlpha});
        anim.addListener(new FadeAnimatorListener(view));
        addListener(new AnonymousClass_1(view));
        return anim;
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        float startAlpha = getStartAlpha(startValues, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        if (startAlpha == 1.0f) {
            startAlpha = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        }
        return createAnimation(view, startAlpha, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        ViewUtils.saveNonTransitionAlpha(view);
        return createAnimation(view, getStartAlpha(startValues, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT), AutoScrollHelper.RELATIVE_UNSPECIFIED);
    }

    private static float getStartAlpha(TransitionValues startValues, float fallbackValue) {
        float startAlpha = fallbackValue;
        if (startValues == null) {
            return startAlpha;
        }
        Float startAlphaFloat = (Float) startValues.values.get(PROPNAME_TRANSITION_ALPHA);
        return startAlphaFloat != null ? startAlphaFloat.floatValue() : startAlpha;
    }
}
