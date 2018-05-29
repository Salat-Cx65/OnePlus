package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.xmlpull.v1.XmlPullParser;

public class Slide extends Visibility {
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private static final TimeInterpolator sAccelerate;
    private static final CalculateSlide sCalculateBottom;
    private static final CalculateSlide sCalculateEnd;
    private static final CalculateSlide sCalculateLeft;
    private static final CalculateSlide sCalculateRight;
    private static final CalculateSlide sCalculateStart;
    private static final CalculateSlide sCalculateTop;
    private static final TimeInterpolator sDecelerate;
    private CalculateSlide mSlideCalculator;
    private int mSlideEdge;

    private static interface CalculateSlide {
        float getGoneX(ViewGroup viewGroup, View view);

        float getGoneY(ViewGroup viewGroup, View view);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface GravityFlag {
    }

    private static abstract class CalculateSlideHorizontal implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX();
        }
    }

    static {
        sDecelerate = new DecelerateInterpolator();
        sAccelerate = new AccelerateInterpolator();
        sCalculateLeft = new CalculateSlideHorizontal() {
            public float getGoneX(ViewGroup sceneRoot, View view) {
                return view.getTranslationX() - ((float) sceneRoot.getWidth());
            }
        };
        sCalculateStart = new CalculateSlideHorizontal() {
            public float getGoneX(ViewGroup sceneRoot, View view) {
                boolean isRtl = true;
                if (ViewCompat.getLayoutDirection(sceneRoot) != 1) {
                    isRtl = false;
                }
                return isRtl ? view.getTranslationX() + ((float) sceneRoot.getWidth()) : view.getTranslationX() - ((float) sceneRoot.getWidth());
            }
        };
        sCalculateTop = new CalculateSlideVertical() {
            public float getGoneY(ViewGroup sceneRoot, View view) {
                return view.getTranslationY() - ((float) sceneRoot.getHeight());
            }
        };
        sCalculateRight = new CalculateSlideHorizontal() {
            public float getGoneX(ViewGroup sceneRoot, View view) {
                return view.getTranslationX() + ((float) sceneRoot.getWidth());
            }
        };
        sCalculateEnd = new CalculateSlideHorizontal() {
            public float getGoneX(ViewGroup sceneRoot, View view) {
                boolean isRtl = true;
                if (ViewCompat.getLayoutDirection(sceneRoot) != 1) {
                    isRtl = false;
                }
                return isRtl ? view.getTranslationX() - ((float) sceneRoot.getWidth()) : view.getTranslationX() + ((float) sceneRoot.getWidth());
            }
        };
        sCalculateBottom = new CalculateSlideVertical() {
            public float getGoneY(ViewGroup sceneRoot, View view) {
                return view.getTranslationY() + ((float) sceneRoot.getHeight());
            }
        };
    }

    public Slide() {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        setSlideEdge(R.styleable.AppCompatTheme_panelMenuListTheme);
    }

    public Slide(int slideEdge) {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        setSlideEdge(slideEdge);
    }

    public Slide(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.SLIDE);
        int edge = TypedArrayUtils.getNamedInt(a, (XmlPullParser) attrs, "slideEdge", 0, R.styleable.AppCompatTheme_panelMenuListTheme);
        a.recycle();
        setSlideEdge(edge);
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] position = new int[2];
        transitionValues.view.getLocationOnScreen(position);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, position);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public void setSlideEdge(int slideEdge) {
        switch (slideEdge) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                this.mSlideCalculator = sCalculateLeft;
                break;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                this.mSlideCalculator = sCalculateRight;
                break;
            case R.styleable.AppCompatTheme_colorAccent:
                this.mSlideCalculator = sCalculateTop;
                break;
            case R.styleable.AppCompatTheme_panelMenuListTheme:
                this.mSlideCalculator = sCalculateBottom;
                break;
            case GravityCompat.START:
                this.mSlideCalculator = sCalculateStart;
                break;
            case GravityCompat.END:
                this.mSlideCalculator = sCalculateEnd;
                break;
            default:
                throw new IllegalArgumentException("Invalid slide direction");
        }
        this.mSlideEdge = slideEdge;
        SidePropagation propagation = new SidePropagation();
        propagation.setSide(slideEdge);
        setPropagation(propagation);
    }

    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (endValues == null) {
            return null;
        }
        int[] position = (int[]) endValues.values.get(PROPNAME_SCREEN_POSITION);
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        return TranslationAnimationCreator.createAnimation(view, endValues, position[0], position[1], this.mSlideCalculator.getGoneX(sceneRoot, view), this.mSlideCalculator.getGoneY(sceneRoot, view), endX, endY, sDecelerate);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null) {
            return null;
        }
        int[] position = (int[]) startValues.values.get(PROPNAME_SCREEN_POSITION);
        return TranslationAnimationCreator.createAnimation(view, startValues, position[0], position[1], view.getTranslationX(), view.getTranslationY(), this.mSlideCalculator.getGoneX(sceneRoot, view), this.mSlideCalculator.getGoneY(sceneRoot, view), sAccelerate);
    }
}
