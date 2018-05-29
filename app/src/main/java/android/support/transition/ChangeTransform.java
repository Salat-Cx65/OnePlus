package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.DefaultRetryPolicy;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform extends Transition {
    private static final Property<PathAnimatorMatrix, float[]> NON_TRANSLATIONS_PROPERTY;
    private static final String PROPNAME_INTERMEDIATE_MATRIX = "android:changeTransform:intermediateMatrix";
    private static final String PROPNAME_INTERMEDIATE_PARENT_MATRIX = "android:changeTransform:intermediateParentMatrix";
    private static final String PROPNAME_MATRIX = "android:changeTransform:matrix";
    private static final String PROPNAME_PARENT = "android:changeTransform:parent";
    private static final String PROPNAME_PARENT_MATRIX = "android:changeTransform:parentMatrix";
    private static final String PROPNAME_TRANSFORMS = "android:changeTransform:transforms";
    private static final boolean SUPPORTS_VIEW_REMOVAL_SUPPRESSION;
    private static final Property<PathAnimatorMatrix, PointF> TRANSLATIONS_PROPERTY;
    private static final String[] sTransitionProperties;
    private boolean mReparent;
    private Matrix mTempMatrix;
    private boolean mUseOverlay;

    static class AnonymousClass_1 extends Property<PathAnimatorMatrix, float[]> {
        AnonymousClass_1(Class x0, String x1) {
            super(x0, x1);
        }

        public float[] get(PathAnimatorMatrix object) {
            return null;
        }

        public void set(PathAnimatorMatrix object, float[] value) {
            object.setValues(value);
        }
    }

    static class AnonymousClass_2 extends Property<PathAnimatorMatrix, PointF> {
        AnonymousClass_2(Class x0, String x1) {
            super(x0, x1);
        }

        public PointF get(PathAnimatorMatrix object) {
            return null;
        }

        public void set(PathAnimatorMatrix object, PointF value) {
            object.setTranslation(value);
        }
    }

    class AnonymousClass_3 extends AnimatorListenerAdapter {
        private boolean mIsCanceled;
        private Matrix mTempMatrix;
        final /* synthetic */ Matrix val$finalEndMatrix;
        final /* synthetic */ boolean val$handleParentChange;
        final /* synthetic */ PathAnimatorMatrix val$pathAnimatorMatrix;
        final /* synthetic */ Transforms val$transforms;
        final /* synthetic */ View val$view;

        AnonymousClass_3(boolean z, Matrix matrix, View view, Transforms transforms, PathAnimatorMatrix pathAnimatorMatrix) {
            this.val$handleParentChange = z;
            this.val$finalEndMatrix = matrix;
            this.val$view = view;
            this.val$transforms = transforms;
            this.val$pathAnimatorMatrix = pathAnimatorMatrix;
            this.mTempMatrix = new Matrix();
        }

        public void onAnimationCancel(Animator animation) {
            this.mIsCanceled = true;
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.mIsCanceled) {
                if (this.val$handleParentChange && ChangeTransform.this.mUseOverlay) {
                    setCurrentMatrix(this.val$finalEndMatrix);
                } else {
                    this.val$view.setTag(R.id.transition_transform, null);
                    this.val$view.setTag(R.id.parent_matrix, null);
                }
            }
            ViewUtils.setAnimationMatrix(this.val$view, null);
            this.val$transforms.restore(this.val$view);
        }

        public void onAnimationPause(Animator animation) {
            setCurrentMatrix(this.val$pathAnimatorMatrix.getMatrix());
        }

        public void onAnimationResume(Animator animation) {
            ChangeTransform.setIdentityTransforms(this.val$view);
        }

        private void setCurrentMatrix(Matrix currentMatrix) {
            this.mTempMatrix.set(currentMatrix);
            this.val$view.setTag(R.id.transition_transform, this.mTempMatrix);
            this.val$transforms.restore(this.val$view);
        }
    }

    private static class PathAnimatorMatrix {
        private final Matrix mMatrix;
        private float mTranslationX;
        private float mTranslationY;
        private final float[] mValues;
        private final View mView;

        PathAnimatorMatrix(View view, float[] values) {
            this.mMatrix = new Matrix();
            this.mView = view;
            this.mValues = (float[]) values.clone();
            this.mTranslationX = this.mValues[2];
            this.mTranslationY = this.mValues[5];
            setAnimationMatrix();
        }

        void setValues(float[] values) {
            System.arraycopy(values, 0, this.mValues, 0, values.length);
            setAnimationMatrix();
        }

        void setTranslation(PointF translation) {
            this.mTranslationX = translation.x;
            this.mTranslationY = translation.y;
            setAnimationMatrix();
        }

        private void setAnimationMatrix() {
            this.mValues[2] = this.mTranslationX;
            this.mValues[5] = this.mTranslationY;
            this.mMatrix.setValues(this.mValues);
            ViewUtils.setAnimationMatrix(this.mView, this.mMatrix);
        }

        Matrix getMatrix() {
            return this.mMatrix;
        }
    }

    private static class Transforms {
        final float mRotationX;
        final float mRotationY;
        final float mRotationZ;
        final float mScaleX;
        final float mScaleY;
        final float mTranslationX;
        final float mTranslationY;
        final float mTranslationZ;

        Transforms(View view) {
            this.mTranslationX = view.getTranslationX();
            this.mTranslationY = view.getTranslationY();
            this.mTranslationZ = ViewCompat.getTranslationZ(view);
            this.mScaleX = view.getScaleX();
            this.mScaleY = view.getScaleY();
            this.mRotationX = view.getRotationX();
            this.mRotationY = view.getRotationY();
            this.mRotationZ = view.getRotation();
        }

        public void restore(View view) {
            ChangeTransform.setTransforms(view, this.mTranslationX, this.mTranslationY, this.mTranslationZ, this.mScaleX, this.mScaleY, this.mRotationX, this.mRotationY, this.mRotationZ);
        }

        public boolean equals(Object that) {
            if (!(that instanceof Transforms)) {
                return false;
            }
            Transforms thatTransform = (Transforms) that;
            return thatTransform.mTranslationX == this.mTranslationX && thatTransform.mTranslationY == this.mTranslationY && thatTransform.mTranslationZ == this.mTranslationZ && thatTransform.mScaleX == this.mScaleX && thatTransform.mScaleY == this.mScaleY && thatTransform.mRotationX == this.mRotationX && thatTransform.mRotationY == this.mRotationY && thatTransform.mRotationZ == this.mRotationZ;
        }

        public int hashCode() {
            int code;
            int floatToIntBits;
            int i = 0;
            if (this.mTranslationX != 0.0f) {
                code = Float.floatToIntBits(this.mTranslationX);
            } else {
                code = 0;
            }
            int i2 = code * 31;
            if (this.mTranslationY != 0.0f) {
                floatToIntBits = Float.floatToIntBits(this.mTranslationY);
            } else {
                floatToIntBits = 0;
            }
            i2 = (i2 + floatToIntBits) * 31;
            if (this.mTranslationZ != 0.0f) {
                floatToIntBits = Float.floatToIntBits(this.mTranslationZ);
            } else {
                floatToIntBits = 0;
            }
            i2 = (i2 + floatToIntBits) * 31;
            if (this.mScaleX != 0.0f) {
                floatToIntBits = Float.floatToIntBits(this.mScaleX);
            } else {
                floatToIntBits = 0;
            }
            i2 = (i2 + floatToIntBits) * 31;
            if (this.mScaleY != 0.0f) {
                floatToIntBits = Float.floatToIntBits(this.mScaleY);
            } else {
                floatToIntBits = 0;
            }
            i2 = (i2 + floatToIntBits) * 31;
            if (this.mRotationX != 0.0f) {
                floatToIntBits = Float.floatToIntBits(this.mRotationX);
            } else {
                floatToIntBits = 0;
            }
            i2 = (i2 + floatToIntBits) * 31;
            if (this.mRotationY != 0.0f) {
                floatToIntBits = Float.floatToIntBits(this.mRotationY);
            } else {
                floatToIntBits = 0;
            }
            floatToIntBits = (i2 + floatToIntBits) * 31;
            if (this.mRotationZ != 0.0f) {
                i = Float.floatToIntBits(this.mRotationZ);
            }
            return floatToIntBits + i;
        }
    }

    private static class GhostListener extends TransitionListenerAdapter {
        private GhostViewImpl mGhostView;
        private View mView;

        GhostListener(View view, GhostViewImpl ghostView) {
            this.mView = view;
            this.mGhostView = ghostView;
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            transition.removeListener(this);
            GhostViewUtils.removeGhost(this.mView);
            this.mView.setTag(R.id.transition_transform, null);
            this.mView.setTag(R.id.parent_matrix, null);
        }

        public void onTransitionPause(@NonNull Transition transition) {
            this.mGhostView.setVisibility(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        }

        public void onTransitionResume(@NonNull Transition transition) {
            this.mGhostView.setVisibility(0);
        }
    }

    static {
        boolean z = true;
        sTransitionProperties = new String[]{PROPNAME_MATRIX, PROPNAME_TRANSFORMS, PROPNAME_PARENT_MATRIX};
        NON_TRANSLATIONS_PROPERTY = new AnonymousClass_1(float[].class, "nonTranslations");
        TRANSLATIONS_PROPERTY = new AnonymousClass_2(PointF.class, "translations");
        if (VERSION.SDK_INT < 21) {
            z = false;
        }
        SUPPORTS_VIEW_REMOVAL_SUPPRESSION = z;
    }

    public ChangeTransform() {
        this.mUseOverlay = true;
        this.mReparent = true;
        this.mTempMatrix = new Matrix();
    }

    public ChangeTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mUseOverlay = true;
        this.mReparent = true;
        this.mTempMatrix = new Matrix();
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.CHANGE_TRANSFORM);
        this.mUseOverlay = TypedArrayUtils.getNamedBoolean(a, (XmlPullParser) attrs, "reparentWithOverlay", 1, true);
        this.mReparent = TypedArrayUtils.getNamedBoolean(a, (XmlPullParser) attrs, "reparent", 0, true);
        a.recycle();
    }

    public boolean getReparentWithOverlay() {
        return this.mUseOverlay;
    }

    public void setReparentWithOverlay(boolean reparentWithOverlay) {
        this.mUseOverlay = reparentWithOverlay;
    }

    public boolean getReparent() {
        return this.mReparent;
    }

    public void setReparent(boolean reparent) {
        this.mReparent = reparent;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() != 8) {
            transitionValues.values.put(PROPNAME_PARENT, view.getParent());
            transitionValues.values.put(PROPNAME_TRANSFORMS, new Transforms(view));
            Matrix matrix = view.getMatrix();
            if (matrix == null || matrix.isIdentity()) {
                matrix = null;
            } else {
                matrix = new Matrix(matrix);
            }
            transitionValues.values.put(PROPNAME_MATRIX, matrix);
            if (this.mReparent) {
                Matrix parentMatrix = new Matrix();
                ViewGroup parent = (ViewGroup) view.getParent();
                ViewUtils.transformMatrixToGlobal(parent, parentMatrix);
                parentMatrix.preTranslate((float) (-parent.getScrollX()), (float) (-parent.getScrollY()));
                transitionValues.values.put(PROPNAME_PARENT_MATRIX, parentMatrix);
                transitionValues.values.put(PROPNAME_INTERMEDIATE_MATRIX, view.getTag(R.id.transition_transform));
                transitionValues.values.put(PROPNAME_INTERMEDIATE_PARENT_MATRIX, view.getTag(R.id.parent_matrix));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
        if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
            ((ViewGroup) transitionValues.view.getParent()).startViewTransition(transitionValues.view);
        }
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(@NonNull ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null || !startValues.values.containsKey(PROPNAME_PARENT) || !endValues.values.containsKey(PROPNAME_PARENT)) {
            return null;
        }
        ViewGroup startParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
        boolean handleParentChange = this.mReparent && !parentsMatch(startParent, (ViewGroup) endValues.values.get(PROPNAME_PARENT));
        Matrix startMatrix = (Matrix) startValues.values.get(PROPNAME_INTERMEDIATE_MATRIX);
        if (startMatrix != null) {
            startValues.values.put(PROPNAME_MATRIX, startMatrix);
        }
        Matrix startParentMatrix = (Matrix) startValues.values.get(PROPNAME_INTERMEDIATE_PARENT_MATRIX);
        if (startParentMatrix != null) {
            startValues.values.put(PROPNAME_PARENT_MATRIX, startParentMatrix);
        }
        if (handleParentChange) {
            setMatricesForParent(startValues, endValues);
        }
        Animator transformAnimator = createTransformAnimator(startValues, endValues, handleParentChange);
        if (handleParentChange && transformAnimator != null && this.mUseOverlay) {
            createGhostView(sceneRoot, startValues, endValues);
            return transformAnimator;
        } else if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
            return transformAnimator;
        } else {
            startParent.endViewTransition(startValues.view);
            return transformAnimator;
        }
    }

    private ObjectAnimator createTransformAnimator(TransitionValues startValues, TransitionValues endValues, boolean handleParentChange) {
        Matrix startMatrix = (Matrix) startValues.values.get(PROPNAME_MATRIX);
        Matrix endMatrix = (Matrix) endValues.values.get(PROPNAME_MATRIX);
        if (startMatrix == null) {
            startMatrix = MatrixUtils.IDENTITY_MATRIX;
        }
        if (endMatrix == null) {
            endMatrix = MatrixUtils.IDENTITY_MATRIX;
        }
        if (startMatrix.equals(endMatrix)) {
            return null;
        }
        Transforms transforms = (Transforms) endValues.values.get(PROPNAME_TRANSFORMS);
        View view = endValues.view;
        setIdentityTransforms(view);
        startMatrixValues = new float[9];
        startMatrix.getValues(startMatrixValues);
        float[] endMatrixValues = new float[9];
        endMatrix.getValues(endMatrixValues);
        PathAnimatorMatrix pathAnimatorMatrix = new PathAnimatorMatrix(view, startMatrixValues);
        Property property = NON_TRANSLATIONS_PROPERTY;
        TypeEvaluator floatArrayEvaluator = new FloatArrayEvaluator(new float[9]);
        Object[] objArr = new Object[2];
        objArr[0] = startMatrixValues;
        objArr[1] = endMatrixValues;
        PropertyValuesHolder valuesProperty = PropertyValuesHolder.ofObject(property, floatArrayEvaluator, objArr);
        PropertyValuesHolder translationProperty = PropertyValuesHolderUtils.ofPointF(TRANSLATIONS_PROPERTY, getPathMotion().getPath(startMatrixValues[2], startMatrixValues[5], endMatrixValues[2], endMatrixValues[5]));
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(pathAnimatorMatrix, new PropertyValuesHolder[]{valuesProperty, translationProperty});
        AnimatorListenerAdapter listener = new AnonymousClass_3(handleParentChange, endMatrix, view, transforms, pathAnimatorMatrix);
        animator.addListener(listener);
        AnimatorUtils.addPauseListener(animator, listener);
        return animator;
    }

    private boolean parentsMatch(ViewGroup startParent, ViewGroup endParent) {
        if (!isValidTarget(startParent) || !isValidTarget(endParent)) {
            return startParent == endParent;
        } else {
            TransitionValues endValues = getMatchedTransitionValues(startParent, true);
            if (endValues == null) {
                return false;
            }
            return endParent == endValues.view;
        }
    }

    private void createGhostView(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        View view = endValues.view;
        Matrix localEndMatrix = new Matrix((Matrix) endValues.values.get(PROPNAME_PARENT_MATRIX));
        ViewUtils.transformMatrixToLocal(sceneRoot, localEndMatrix);
        GhostViewImpl ghostView = GhostViewUtils.addGhost(view, sceneRoot, localEndMatrix);
        if (ghostView != null) {
            ghostView.reserveEndViewTransition((ViewGroup) startValues.values.get(PROPNAME_PARENT), startValues.view);
            Transition outerTransition = this;
            while (outerTransition.mParent != null) {
                outerTransition = outerTransition.mParent;
            }
            outerTransition.addListener(new GhostListener(view, ghostView));
            if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
                if (startValues.view != endValues.view) {
                    ViewUtils.setTransitionAlpha(startValues.view, AutoScrollHelper.RELATIVE_UNSPECIFIED);
                }
                ViewUtils.setTransitionAlpha(view, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            }
        }
    }

    private void setMatricesForParent(TransitionValues startValues, TransitionValues endValues) {
        Matrix endParentMatrix = (Matrix) endValues.values.get(PROPNAME_PARENT_MATRIX);
        endValues.view.setTag(R.id.parent_matrix, endParentMatrix);
        Matrix toLocal = this.mTempMatrix;
        toLocal.reset();
        endParentMatrix.invert(toLocal);
        Matrix startLocal = (Matrix) startValues.values.get(PROPNAME_MATRIX);
        if (startLocal == null) {
            startLocal = new Matrix();
            startValues.values.put(PROPNAME_MATRIX, startLocal);
        }
        startLocal.postConcat((Matrix) startValues.values.get(PROPNAME_PARENT_MATRIX));
        startLocal.postConcat(toLocal);
    }

    private static void setIdentityTransforms(View view) {
        setTransforms(view, AutoScrollHelper.RELATIVE_UNSPECIFIED, 0.0f, 0.0f, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    private static void setTransforms(View view, float translationX, float translationY, float translationZ, float scaleX, float scaleY, float rotationX, float rotationY, float rotationZ) {
        view.setTranslationX(translationX);
        view.setTranslationY(translationY);
        ViewCompat.setTranslationZ(view, translationZ);
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
        view.setRotationX(rotationX);
        view.setRotationY(rotationY);
        view.setRotation(rotationZ);
    }
}
